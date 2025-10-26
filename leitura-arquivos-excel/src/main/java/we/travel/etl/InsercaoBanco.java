package we.travel.etl;

import org.springframework.jdbc.core.JdbcTemplate;

public class InsercaoBanco {
    private final JdbcTemplate jdbcTemplate;

    public InsercaoBanco(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void inserirQuery(String uf, String municipio, Boolean aeroporto, Boolean guia, int qtdGuia, String acesso, Boolean conservacao, Boolean termais, String hidrico){
        jdbcTemplate.update("INSERT INTO DESTINO (uf, municipio,possui_aeroporto,possui_guia,qtd_guia,modais_acesso,possui_conservacao,possui_termais,presenca_hidrica) VALUES (?,?,?,?,?,?,?,?,?)",
                uf, municipio, aeroporto,guia,qtdGuia, acesso,conservacao,termais,hidrico);
    }
}
