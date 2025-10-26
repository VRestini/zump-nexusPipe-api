package we.travel.log;

import org.springframework.jdbc.core.JdbcTemplate;
import we.travel.etl.ConexaoBanco;
import we.travel.etl.Destino;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Log {
    LocalDateTime dtHoraAtual; // pega a data e hora atual
    DateTimeFormatter dtHoraAtualFormatada;
    InsercaoBancoLog insercaoBancoLog;

    public Log() {
;
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        JdbcTemplate template = conexaoBanco.getJdbcTemplate();
        insercaoBancoLog = new InsercaoBancoLog(template);
    }
    public void dispararLog(String acao, String arquivo, String detalhes){
        dtHoraAtual = LocalDateTime.now();
        dtHoraAtualFormatada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataHoraFormatada = dtHoraAtual.format(dtHoraAtualFormatada);
        System.out.printf("[%s] - %s - %s - %s%n", dataHoraFormatada, acao, arquivo, detalhes);
        insercaoBancoLog.inserirQuery(dataHoraFormatada, acao, arquivo, "detalhes");

    }
}
