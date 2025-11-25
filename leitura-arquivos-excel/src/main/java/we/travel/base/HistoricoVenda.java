package we.travel.base;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HistoricoVenda {
    private String uf;
    private String municipio;
    private Integer qtdTuristas;
    private String cluster;

    public HistoricoVenda() {
    }
    public HistoricoVenda(String uf, String municipio, Integer qtdTuristas, String cluster) {
        this.uf = uf;
        this.municipio = municipio;
        this.qtdTuristas = qtdTuristas;
        this.cluster = cluster;
    }
    // 2 até 136
}
