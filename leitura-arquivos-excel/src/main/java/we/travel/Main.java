package we.travel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String nomeArquivo = "relatorio-turismo.xlsx";

        // Carregando o arquivo excel
        Path caminho = Path.of(nomeArquivo);
        InputStream arquivo = Files.newInputStream(caminho);

        // Extraindo os livros do arquivo
        LeitorExcel leitorExcel = new LeitorExcel();
        List<Destino> destinoList = leitorExcel.extrarDestinos(nomeArquivo, arquivo);

        // Fechando o arquivo após a extração
        arquivo.close();

        System.out.println("Destinos extraídos:");
        for (Destino destino : destinoList) {
            System.out.println(destino);
        }
    }
}