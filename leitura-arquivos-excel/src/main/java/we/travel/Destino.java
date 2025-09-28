package we.travel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter @Setter
public class Destino {
    private String uf;
    private String municipio;
    private Boolean possuiAeroporto;
    private Boolean possuiLocadora;
    // na base a locadora é de automoveis, imoveis, embarcacoes e aeronaves
    // dá pra quebrar em outros atributos
    private Boolean possuiGuia;
    private int qtdGuia;
    private List<ModaisAcesso> modaisAcessos;
    private Boolean unidadesConservacao;
    // da pra quebrar em outros atributos
    private Boolean aguasTermais;
    private List<PresencaHidrica> presencaHidricas;
    public Destino(){

    }
    public Destino(String uf, String municipio, Boolean possuiAeroporto, Boolean possuiLocadora, Boolean possuiGuia, int qtdGuia, List<ModaisAcesso> modaisAcessos, Boolean unidadesConservacao, Boolean aguasTermais, List<PresencaHidrica> presencaHidricas) {
        this.uf = uf;
        this.municipio = municipio;
        this.possuiAeroporto = possuiAeroporto;
        this.possuiLocadora = possuiLocadora;
        this.possuiGuia = possuiGuia;
        this.qtdGuia = qtdGuia;
        this.modaisAcessos = modaisAcessos;
        this.unidadesConservacao = unidadesConservacao;
        this.aguasTermais = aguasTermais;
        this.presencaHidricas = presencaHidricas;
    }

    @Override
    public String toString() {
        return """
                {
                    UF:%s  |  Município: %s
                    Possui aeroporto: %s  |  Possui locadora: %s
                    Possui guia: %s  |  quantidade: %d
                    Modais de acesso: %s
                    Possui unidades de conservacao: %s  |  Possui aguas termais: %s
                    Presencas hidricas: %s
                      
                }
                """.formatted(uf, municipio, possuiAeroporto,possuiLocadora,possuiGuia,qtdGuia, modaisAcessos,unidadesConservacao,aguasTermais,presencaHidricas);
    }
}
