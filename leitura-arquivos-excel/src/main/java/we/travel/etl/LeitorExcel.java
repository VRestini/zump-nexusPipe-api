package we.travel.etl;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;
import we.travel.ModaisAcesso;
import we.travel.PresencaHidrica;
import we.travel.log.Log;

public class LeitorExcel {
    private Log log;

    public LeitorExcel() {
        this.log = new Log();
    }

    public List<Destino> extrarDestinos(String nomeArquivo, InputStream arquivo) {
        try {
            //System.out.println("\nIniciando leitura do arquivo %s\n".formatted(nomeArquivo));

            // Criando um objeto Workbook a partir do arquivo recebido
            Workbook workbook;
            if (nomeArquivo.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(arquivo);
            } else {
                workbook = new HSSFWorkbook(arquivo);
            }

            Sheet sheet = workbook.getSheetAt(0);
            ConexaoBanco conexaoBanco = new ConexaoBanco();
            List<Destino> destinosExtraidos = new ArrayList<>();
            JdbcTemplate template = conexaoBanco.getJdbcTemplate();
            InsercaoBanco insercaoBanco = new InsercaoBanco(template);
            // Iterando sobre as linhas da planilha
            for (Row row : sheet) {

                if (row.getRowNum() == 0) {
                    continue;
                }
                if (row.getRowNum() < 3) continue;
                // Extraindo valor das células e criando objeto Livro
                //System.out.println("Lendo linha " + row.getRowNum());
                Destino destino = new Destino();
                destino.setUf( row.getCell(0).getStringCellValue());
                destino.setMunicipio(row.getCell(1).getStringCellValue());
                destino.setPossuiGuia(possuiSimNao(row, 40));
                if (destino.getPossuiGuia())
                    destino.setQtdGuia(Integer.parseInt(row.getCell(41).getStringCellValue()));
                else
                    destino.setQtdGuia(0);
                destino.setPossuiAeroporto(possuiSimNao(row,49));
                destino.setModaisAcessos(acesso(row,52));
                destino.setPresencaHidricas(hidricos(row,95));
                destino.setAguasTermais(possuiSimNao(row, 96));
                destino.setUnidadesConservacao(possuiSimNao(row,80));
                destino.setPossuiLocadora(possuiSimNao(row,42));

                destinosExtraidos.add(destino);
                //insercaoBanco.inserirQuery(destino.getUf(),destino.getMunicipio(),destino.getPossuiAeroporto(),destino.getPossuiGuia(),destino.getQtdGuia(),destino.getModaisAcessos().toString(),destino.getUnidadesConservacao(), destino.getAguasTermais(), destino.getPresencaHidricas().toString());
            }

            // Fechando o workbook após a leitura
            workbook.close();
            log.dispararLog("LEITURA_CONCLUIDA", nomeArquivo,   "sem detalhes");
            //System.out.println("\nLeitura do arquivo finalizada\n");

            return destinosExtraidos;
        } catch (IOException e) {
            // Caso ocorra algum erro durante a leitura do arquivo uma exceção será lançada
            log.dispararLog("ERRO_LEITURA", nomeArquivo, "Erro: " + e.getMessage());
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
    private List<ModaisAcesso> acesso(Row row, int i){
        String valor = row.getCell(i).getStringCellValue();
        List<ModaisAcesso> lista = new ArrayList<>();
        if (valor.contains("Aeroporto"))
            lista.add(ModaisAcesso.Aeroporto);
        if (valor.contains("Rodovia"))
            lista.add(ModaisAcesso.Rodovia);
        if (valor.contains("Ferrovia"))
            lista.add(ModaisAcesso.Ferrovia);
        if (valor.contains("Hidrovia"))
            lista.add(ModaisAcesso.Hidrovia);
        if (valor.contains("Outros"))
            lista.add(ModaisAcesso.Outros);
        return lista;
    }private List<PresencaHidrica> hidricos(Row row, int i){
        Cell cell = row.getCell(i);
        if (cell == null){
            List<PresencaHidrica> lista = new ArrayList<>();;
            return lista;
        }
        String valor = row.getCell(i).getStringCellValue();
        List<PresencaHidrica> lista = new ArrayList<>();

        if (valor.contains("Rios"))
            lista.add(PresencaHidrica.Rios);
        if (valor.contains("Praias"))
            lista.add(PresencaHidrica.Praias);
        if (valor.contains("Lagos"))
            lista.add(PresencaHidrica.Lagos);
        if (valor.contains("Lagoas"))
            lista.add(PresencaHidrica.Lagoas);
        return lista;
    }

}
