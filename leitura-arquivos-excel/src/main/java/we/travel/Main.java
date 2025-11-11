package we.travel;

import we.travel.etl.Batch;
import we.travel.etl.Destino;
import we.travel.etl.LeitorExcel;
import we.travel.log.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> arquivos = new ArrayList<>();
        Log log = new Log();
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
        log.dispararLog("PROCESSO_INICIADO", "", "QUANTIDADE DE ARQUIVOS: " + arquivos.size());

        // Carregando o arquivo excel
        for (String arquivo : arquivos) {
            try{
                String nomeArquivo = arquivo;
                Path caminho = Path.of(nomeArquivo);
                InputStream arquivoLido = Files.newInputStream(caminho);
                LeitorExcel leitorExcel = new LeitorExcel();
                List<Destino> destinoList = leitorExcel.extrarDestinos(nomeArquivo, arquivoLido);
                Batch batch = new Batch(destinoList);
                batch.executar();
                arquivoLido.close();
                //System.out.println("Destinos extraídos:");
                /*for (Destino destino : destinoList) {
                    System.out.println(destino);
                }*/
            } catch (Exception e) {
                log.dispararLog("ERRO_ARQUIVO", arquivo, "Falha no processamento: " + e.getMessage());
                throw new RuntimeException(e);
            }

        }
        log.dispararLog("PROCESSAMENTO_CONCLUIDO", "",
                "Todos os arquivos foram processados");

    }
}