package we.travel.etl;

import we.travel.base.Destino;
import we.travel.base.HistoricoVenda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Load {
    private String urlBanco;
    private String usuario;
    private String senha;

    private String queryDestino = "INSERT INTO DESTINO (uf, municipio,possui_aeroporto,possui_guia,qtd_guia,modais_acesso,possui_conservacao,possui_termais,presenca_hidrica) VALUES (?,?,?,?,?,?,?,?,?)";
    private String queryHistorico = "INSERT INTO historico_venda (uf, municipio,turistas, cluster) VALUES (?,?,?,?)";
    public Load() {
        try {
            urlBanco = System.getenv("DB_URL");
            usuario = System.getenv("DB_USERNAME");
            senha = System.getenv("DB_PASSWORD");
            //urlBanco = ("jdbc:mysql://localhost:3306/nexus?useSSL=false&serverTimezone=UTC");
            //usuario = ("nexus");
            //senha = ("senha_nexus");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
    public String carregamentoEmLoteDestino(List<Destino> dados, int tamanhoLote) {
        try (Connection conexao = DriverManager.getConnection(urlBanco, usuario, senha);
             PreparedStatement insercao = conexao.prepareStatement(queryDestino)) {
            conexao.setAutoCommit(false);
            for (int i = 0; i < dados.size(); i++) {
                insercao.setString(1, dados.get(i).getUf());
                insercao.setString(2, dados.get(i).getMunicipio());
                insercao.setBoolean(3, dados.get(i).getPossuiAeroporto());
                insercao.setBoolean(4, dados.get(i).getPossuiGuia());
                insercao.setInt(5, dados.get(i).getQtdGuia());
                insercao.setString(6, dados.get(i).getModaisAcessos().toString());
                insercao.setBoolean(7, dados.get(i).getUnidadesConservacao());
                insercao.setBoolean(8, dados.get(i).getAguasTermais());
                insercao.setString(9, dados.get(i).getPresencaHidricas().toString());
                insercao.addBatch();
                if ((i + 1) % tamanhoLote == 0) {
                    insercao.executeBatch();
                }
            }
            insercao.executeBatch();
            conexao.commit();
            return "Lote inserido com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro no carregamento em lote: " + e.getMessage();
        }
    }
    public String carregamentoEmLoteHistorico(List<HistoricoVenda> dados, int tamanhoLote) {
        try (Connection conexao = DriverManager.getConnection(urlBanco, usuario, senha);
             PreparedStatement insercao = conexao.prepareStatement(queryHistorico)) {
            conexao.setAutoCommit(false);
            for (int i = 0; i < dados.size(); i++) {
                insercao.setString(1, dados.get(i).getUf());
                insercao.setString(2, dados.get(i).getMunicipio());
                insercao.setInt(3, dados.get(i).getQtdTuristas());
                insercao.setString(4, dados.get(i).getCluster());
                insercao.addBatch();
                if ((i + 1) % tamanhoLote == 0) {
                    insercao.executeBatch();
                }
            }
            insercao.executeBatch();
            conexao.commit();
            return "Lote inserido com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro no carregamento em lote: " + e.getMessage();
        }
    }
}
