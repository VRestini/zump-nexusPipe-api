package we.travel.etl;

import java.util.List;

public class Batch {
    List<Destino> dados;

    public Batch(List<Destino> dados) {
        this.dados = dados;
    }

    public void executar() {
        int qtdLote = 1000;
        LeitorExcel extracao = new LeitorExcel();
        Load carregamento = new Load();
        carregamento.carregamentoEmLote(dados, qtdLote);
    }
}
