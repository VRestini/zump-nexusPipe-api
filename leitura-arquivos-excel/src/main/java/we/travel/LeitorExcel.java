package we.travel;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LeitorExcel {

    public List<Destino> extrarDestinos(String nomeArquivo, InputStream arquivo) {
        try {
            System.out.println("\nIniciando leitura do arquivo %s\n".formatted(nomeArquivo));

            // Criando um objeto Workbook a partir do arquivo recebido
            Workbook workbook;
            if (nomeArquivo.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(arquivo);
            } else {
                workbook = new HSSFWorkbook(arquivo);
            }

            Sheet sheet = workbook.getSheetAt(0);

            List<Destino> destinosExtraidos = new ArrayList<>();
            String aeroporto;
            String possuiGuia;
            String possuiAguasTermais;
            String possuiConservacao;
            String possuiLocadora;
            // Iterando sobre as linhas da planilha
            for (Row row : sheet) {

                if (row.getRowNum() == 0) {
                    System.out.println("\nLendo cabeçalho");

                    for (int i = 0; i < 13; i++) {
                        String coluna = row.getCell(i).getStringCellValue();
                        System.out.println("Coluna " + i + ": " + coluna);
                    }

                    System.out.println("--------------------");
                    continue;
                }

                // Extraindo valor das células e criando objeto Livro
                System.out.println("Lendo linha " + row.getRowNum());

                Destino destino = new Destino();
                destino.setUf( row.getCell(0).getStringCellValue());
                destino.setMunicipio(row.getCell(1).getStringCellValue());
                destino.setPossuiGuia(possuiSimNao(row, 2));
                if (destino.getPossuiGuia())
                    destino.setQtdGuia(Integer.parseInt(row.getCell(3).getStringCellValue()));
                else
                    destino.setQtdGuia(0);
                destino.setPossuiAeroporto(possuiSimNao(row,4));
                destino.setAguasTermais(possuiSimNao(row, 7));
                destino.setUnidadesConservacao(possuiSimNao(row,8));
                destino.setPossuiLocadora(possuiSimNao(row,10));
                destinosExtraidos.add(destino);
            }

            // Fechando o workbook após a leitura
            workbook.close();

            System.out.println("\nLeitura do arquivo finalizada\n");

            return destinosExtraidos;
        } catch (IOException e) {
            // Caso ocorra algum erro durante a leitura do arquivo uma exceção será lançada
            throw new RuntimeException(e);
        }
    }

    private LocalDate converterDate(Date data) {
        return data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    private Boolean possuiSimNao(Row row, int i){
        String possuiGuia;
        possuiGuia = row.getCell(i).getStringCellValue();
        if(possuiGuia.equalsIgnoreCase("sim"))
            return true;
        else
            return false;
    }

}
