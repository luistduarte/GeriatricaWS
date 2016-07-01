package Models;

import java.util.List;

/**
 *
 * @author luis
 */
public class AvalList {

    private int num_utente;
    private List<Avaliacao> AvaliacaoList;

    public AvalList(int num_utente, List<Avaliacao> AvaliacaoList) {
        this.num_utente = num_utente;
        this.AvaliacaoList = AvaliacaoList;
    }

    public int getNum_utente() {
        return num_utente;
    }

    public void setNum_utente(int num_utente) {
        this.num_utente = num_utente;
    }

    public List<Avaliacao> getAvaliacaoList() {
        return AvaliacaoList;
    }

    public void setAvaliacaoList(List<Avaliacao> AvaliacaoList) {
        this.AvaliacaoList = AvaliacaoList;
    }
    
}
