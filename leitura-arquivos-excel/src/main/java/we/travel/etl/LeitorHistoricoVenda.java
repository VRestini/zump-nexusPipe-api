package we.travel.etl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import we.travel.base.Destino;
import we.travel.base.HistoricoVenda;
import we.travel.log.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LeitorHistoricoVenda {
    private Log log;

    public LeitorHistoricoVenda() {
        this.log = new Log();
    }
    public List<HistoricoVenda> extrairHistoricoVenda(String nomeArquivo, InputStream arquivo) {
        try {
            Workbook workbook;
            if (nomeArquivo.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(arquivo);
            } else {
                workbook = new HSSFWorkbook(arquivo);
            }
            Sheet sheet = workbook.getSheetAt(0);
            List<HistoricoVenda> historicoVendasExtraidos = new ArrayList<>();
            for (Row row : sheet) {

                if (row.getRowNum() >= 0) continue;
                HistoricoVenda historicoVenda = new HistoricoVenda();
                historicoVenda.setUf(row.getCell(3).getStringCellValue());
                historicoVenda.setMunicipio(row.getCell(2).getStringCellValue());
                historicoVenda.setQtdTuristas((int) row.getCell(5).getNumericCellValue());
                historicoVenda.setCluster(row.getCell(7).getStringCellValue());
                historicoVendasExtraidos.add(historicoVenda);
            }
            workbook.close();
            log.dispararLog("LEITURA_CONCLUIDA", nomeArquivo, "sem detalhes");
            return historicoVendasExtraidos;
        } catch (Exception e) {
            log.dispararLog("ERRO_LEITURA", nomeArquivo, "Erro: " + e.getMessage());
            throw new RuntimeException(e);
        }


    }


}
