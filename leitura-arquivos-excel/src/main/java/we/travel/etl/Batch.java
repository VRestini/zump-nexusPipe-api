package we.travel.etl;

import we.travel.base.Destino;
import we.travel.base.HistoricoVenda;

import java.util.List;

public class Batch {
    List<Destino> dadosDestino;
    List<HistoricoVenda> dadosHistorico;
    public Batch(List<HistoricoVenda> dadosHistorico, String nul) {
        this.dadosHistorico = dadosHistorico;
    }

    public Batch(List<Destino> dados) {
        this.dadosDestino = dados;
    }

    public void executarDestino() {
        int qtdLote = 1000;
        LeitorDestino extracao = new LeitorDestino();
        Load carregamento = new Load();
        carregamento.carregamentoEmLoteDestino(dadosDestino, qtdLote);
    }
    public void executarHistorico() {
        int qtdLote = 100;
        LeitorHistoricoVenda extracao = new LeitorHistoricoVenda();
        Load carregamento = new Load();
        carregamento.carregamentoEmLoteHistorico(dadosHistorico, qtdLote);
    }
}
