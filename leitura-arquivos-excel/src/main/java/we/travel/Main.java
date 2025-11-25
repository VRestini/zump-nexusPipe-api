package we.travel;

import org.springframework.jdbc.core.JdbcTemplate;
import we.travel.database.ConexaoBanco;
import we.travel.database.InsercaoBanco;
import we.travel.log.Log;
import we.travel.s3.ArquivosDestino;
import we.travel.s3.ArquivosHistorico;
import we.travel.s3.S3Provider;

import java.io.IOException;
import java.util.List;

import static we.travel.slack.SlackSender.sendSimpleMessage;

public class Main {

    public static void main(String[] args) throws IOException {
        Log log = new Log();
        ArquivosDestino destinos = new ArquivosDestino();
        ArquivosHistorico historico = new ArquivosHistorico();
        S3Provider s3Provider = new S3Provider("wetravel-saw");
        log.dispararLog("PROCESSO_INICIADO", "", "Iniciando processo de ETL" + 0);
        //log.dispararLog("PROCESSO", "", "Iniciando processamento de DESTINO dos arquivosDestino na S3" + 0);
        s3Provider.puxarArquivo();
        log.dispararLog("PROCESSO", "", "Arquivos da S3foram puxados" + 0);
        List<String> arquivosDestino = destinos.getArquivos();
        List<String> arquivoHistorico = historico.getArquivos();
        log.dispararLog("PROCESSO", "", "Quantidade de arquivos excel DESTINO baixados: " + arquivosDestino.size());
        log.dispararLog("PROCESSO", "", "Quantidade de arquivos excel HISTORICO baixados: " + arquivoHistorico.size());
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        JdbcTemplate template = conexaoBanco.getJdbcTemplate();
        InsercaoBanco insercaoBanco = new InsercaoBanco(template);
        insercaoBanco.deletarQuery();
        destinos.extrair();
        historico.extrair();

        sendSimpleMessage("teste! ");
        log.dispararLog("PROCESSAMENTO_CONCLUIDO", "","Todos os arquivosDestino foram processados e a base foi atualizada");
    }
}