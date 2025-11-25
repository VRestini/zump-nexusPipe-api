package we.travel.s3;

import we.travel.base.HistoricoVenda;
import we.travel.etl.Batch;
import we.travel.etl.LeitorHistoricoVenda;
import we.travel.log.Log;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ArquivosHistorico {
    List<String> arquivos = new ArrayList<>();
    Log log = new Log();
    public ArquivosHistorico() {
        arquivos.add("voos.xlsx");
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
                LeitorHistoricoVenda leitorHistoricoVenda = new LeitorHistoricoVenda();
                List<HistoricoVenda> historicoVendaList = leitorHistoricoVenda.extrairHistoricoVenda(nomeArquivo, arquivoLido);
                Batch batch = new Batch(historicoVendaList,"");
                batch.executarHistorico();
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
