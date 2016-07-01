package Models;

import java.util.List;

/**
 *
 * @author luis
 */
public class UtentesList {
    private int CC;
    private List<Utente> UtentesList;

    public UtentesList(int CC, List<Utente> UtentesList) {
        this.CC = CC;
        this.UtentesList = UtentesList;
    }

    public int getCC() {
        return CC;
    }

    public void setCC(int CC) {
        this.CC = CC;
    }

    public List<Utente> getUtentesList() {
        return UtentesList;
    }

    public void setUtentesList(List<Utente> UtentesList) {
        this.UtentesList = UtentesList;
    }
 
}
