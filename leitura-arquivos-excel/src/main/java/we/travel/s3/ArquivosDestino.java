package we.travel.s3;

import we.travel.base.Destino;
import we.travel.etl.Batch;
import we.travel.etl.LeitorDestino;
import we.travel.log.Log;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ArquivosDestino {
    List<String> arquivos = new ArrayList<>();
    Log log = new Log();
    public ArquivosDestino() {
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
    }
    public void extrair(){
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
                batch.executarDestino();
                arquivoLido.close();
                System.out.println("Destinos extraídos:");
            } catch (Exception e) {
                log.dispararLog("ERRO_ARQUIVO", arquivo, "Falha no processamento: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
    public List<String> getArquivos() {
        return arquivos;
    }
}
