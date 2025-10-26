package we.travel.log;

import org.springframework.jdbc.core.JdbcTemplate;

public class InsercaoBancoLog {
    private final JdbcTemplate jdbcTemplate;

    public InsercaoBancoLog(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void inserirQuery(String datahora, String acao, String arquivo, String detalhes){
        jdbcTemplate.update("INSERT INTO LOG_EXCEL (data_hora, acao, arquivo, detalhes) VALUES (?,?,?,?)",
                datahora, acao, arquivo, detalhes);
    }
}

