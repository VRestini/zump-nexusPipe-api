package we.travel;

import org.springframework.jdbc.core.JdbcTemplate;
import we.travel.base.Destino;
import we.travel.database.ConexaoBanco;
import we.travel.database.InsercaoBanco;
import we.travel.etl.*;
import we.travel.log.Log;
import we.travel.s3.S3Provider;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static we.travel.slack.SlackSender.sendSimpleMessage;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> arquivos = new ArrayList<>();
        Log log = new Log();
        S3Provider s3Provider = new S3Provider("wetravel-saw");
        log.dispararLog("PROCESSO_INICIADO", "", "Iniciando processamento dos arquivos na S3" + 0);
        s3Provider.puxarArquivo();
        log.dispararLog("PROCESSO", "", "Arquivos da S3 foram puxados" + 0);
        arquivos.add("acre.xlsx" );
        arquivos.add("alagoas.xlsx");
        arquivos.add("amapa.xlsx" );
        arquivos.add("amazonas.xlsx" );
        arquivos.add("bahia.xlsx" );
        arquivos.add("ceara.xlsx" );
        arquivos.add("distrito_federal.xlsx");
        arquivos.add("espirito_santo.xlsx" );
        arquivos.add("goias.xlsx" );
        arquivos.add("maranhao.xlsx" );
        arquivos.add("mato_grosso.xlsx" );
        arquivos.add("mato_grosso_sul.xlsx");
        arquivos.add("minas_gerais.xlsx" );
        arquivos.add("para.xlsx" );
        arquivos.add("paraiba.xlsx");
        arquivos.add("parana.xlsx" );
        arquivos.add("pernambuco.xlsx" );
        arquivos.add("piaui.xlsx" );
        arquivos.add("rio_janeiro.xlsx" );
        arquivos.add("rio_grande_norte.xlsx" );
        arquivos.add("rio_grande_sul.xlsx");
        arquivos.add("rondonia.xlsx" );
        arquivos.add("roraima.xlsx" );
        arquivos.add("santa_catarina.xlsx");
        arquivos.add("sao_paulo.xlsx" );
        arquivos.add("sergipe.xlsx" );
        arquivos.add("tocantis.xlsx" );

        log.dispararLog("PROCESSO", "", "Quantidade de arquivos baixados: " + arquivos.size());
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        JdbcTemplate template = conexaoBanco.getJdbcTemplate();
        InsercaoBanco insercaoBanco = new InsercaoBanco(template);
        insercaoBanco.deletarQuery();
        // carregando o arquivo excel
        for (String arquivo : arquivos) {
            try{
                String nomeArquivo = arquivo;
                Path caminho = Path.of(nomeArquivo);
                InputStream arquivoLido = Files.newInputStream(caminho);
                if (!Files.exists(caminho)) {
                    throw new RuntimeException("Arquivo não encontrado dentro do fylesystem: " + nomeArquivo);
                }
                LeitorDestino leitorDestino = new LeitorDestino();
                List<Destino> destinoList = leitorDestino.extrarDestinos(nomeArquivo, arquivoLido);
                Batch batch = new Batch(destinoList);
                batch.executar();
                arquivoLido.close();
                System.out.println("Destinos extraídos:");
            } catch (Exception e) {
                log.dispararLog("ERRO_ARQUIVO", arquivo, "Falha no processamento: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
        sendSimpleMessage("teste! ");
        log.dispararLog("PROCESSAMENTO_CONCLUIDO", "","Todos os arquivos foram processados e a base foi atualizada");
    }
}