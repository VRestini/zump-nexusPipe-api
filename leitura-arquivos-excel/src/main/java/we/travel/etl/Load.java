package we.travel.etl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class Load {
    private String urlBanco;
    private String usuario;
    private String senha;

    private String query = "INSERT INTO DESTINO (uf, municipio,possui_aeroporto,possui_guia,qtd_guia,modais_acesso,possui_conservacao,possui_termais,presenca_hidrica) VALUES (?,?,?,?,?,?,?,?,?)";
    public Load() {
        try (InputStream input = getClass().getResourceAsStream("/application.properties")) {
            Properties props = new Properties();
            props.load(input);
            urlBanco = props.getProperty("db.url");
            usuario = props.getProperty("db.username");
            senha = props.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String carregamentoEmLote(List<Destino> dados, int tamanhoLote) {
        try (Connection conexao = DriverManager.getConnection(urlBanco, usuario, senha);
             PreparedStatement insercao = conexao.prepareStatement(query)) {
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
}
