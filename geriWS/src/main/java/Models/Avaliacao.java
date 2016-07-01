package Models;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author luis
 */
public class Avaliacao {
    private String descricao;
    private int resumo;
    private Timestamp time;
    private int tipo_avaliacao;
    private int nume_utente;
    private int cc_medi;

    public Avaliacao(String descricao, int resumo, Timestamp time, int tipo_avaliacao, int nume_utente, int cc_medi) {
        this.descricao = descricao;
        this.resumo = resumo;
        this.time = time;
        this.tipo_avaliacao = tipo_avaliacao;
        this.nume_utente = nume_utente;
        this.cc_medi = cc_medi;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getResumo() {
        return resumo;
    }

    public void setResumo(int resumo) {
        this.resumo = resumo;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getTipo_avaliacao() {
        return tipo_avaliacao;
    }

    public void setTipo_avaliacao(int tipo_avaliacao) {
        this.tipo_avaliacao = tipo_avaliacao;
    }

    public int getNume_utente() {
        return nume_utente;
    }

    public void setNume_utente(int nume_utente) {
        this.nume_utente = nume_utente;
    }

    public int getCc_medi() {
        return cc_medi;
    }

    public void setCc_medi(int cc_medi) {
        this.cc_medi = cc_medi;
    }

}
