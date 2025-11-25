package we.travel.log;

import org.springframework.jdbc.core.JdbcTemplate;
import we.travel.database.ConexaoBanco;
import we.travel.database.InsercaoBanco;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    LocalDateTime dtHoraAtual; // pega a data e hora atual
    DateTimeFormatter dtHoraAtualFormatada;
    InsercaoBanco insercaoBancoLog;

    public Log() {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        JdbcTemplate template = conexaoBanco.getJdbcTemplate();
        insercaoBancoLog = new InsercaoBanco(template);
    }
    public void dispararLog(String acao, String arquivo, String detalhes){
        dtHoraAtual = LocalDateTime.now();
        dtHoraAtualFormatada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataHoraFormatada = dtHoraAtual.format(dtHoraAtualFormatada);
        System.out.printf("[%s] - %s - %s - %s%n", dataHoraFormatada, acao, arquivo, detalhes);
        insercaoBancoLog.inserirLog(dataHoraFormatada, acao, arquivo, detalhes);

    }
}
