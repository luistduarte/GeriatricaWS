/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Date;

/**
 *
 * @author luis
 */
public class Utente {

    private int nume_uten;
    private String nome;
    private Date data_nasc;
    private int AVAL_AVDB;
    private int AVAL_AIVD;
    private int AVAL_MARC;
    private int AVAL_AFEC;
    private int AVAL_COGN;
    private int AVAL_NUTR;

    public Utente(int nume_uten, String nome) {
        this.nume_uten = nume_uten;
        this.nome = nome;
    }

    public Utente(int nume_uten, String nome, Date data_nasc, int AVAL_AVDB, int AVAL_AIVD, 
            int AVAL_MARC, int AVAL_AFEC, int AVAL_COGN, int AVAL_NUTR) {
        this.nume_uten = nume_uten;
        this.nome = nome;
        this.data_nasc = data_nasc;

        this.AVAL_AVDB = AVAL_AVDB;
        this.AVAL_AIVD = AVAL_AIVD;
        this.AVAL_MARC = AVAL_MARC;
        this.AVAL_AFEC = AVAL_AFEC;
        this.AVAL_COGN = AVAL_COGN;
        this.AVAL_NUTR = AVAL_NUTR;
    }

    public int getNume_uten() {
        return nume_uten;
    }

    public void setNume_uten(int nume_uten) {
        this.nume_uten = nume_uten;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData_nasc() {
        return data_nasc;
    }

    public void setData_nasc(Date data_nasc) {
        this.data_nasc = data_nasc;
    }

    public int getAVAL_AVDB() {
        return AVAL_AVDB;
    }

    public void setAVAL_AVDB(int AVAL_AVDB) {
        this.AVAL_AVDB = AVAL_AVDB;
    }

    public int getAVAL_AIVD() {
        return AVAL_AIVD;
    }

    public void setAVAL_AIVD(int AVAL_AIVD) {
        this.AVAL_AIVD = AVAL_AIVD;
    }

    public int getAVAL_MARC() {
        return AVAL_MARC;
    }

    public void setAVAL_MARC(int AVAL_MARC) {
        this.AVAL_MARC = AVAL_MARC;
    }

    public int getAVAL_AFEC() {
        return AVAL_AFEC;
    }

    public void setAVAL_AFEC(int AVAL_AFEC) {
        this.AVAL_AFEC = AVAL_AFEC;
    }

    public int getAVAL_COGN() {
        return AVAL_COGN;
    }

    public void setAVAL_COGN(int AVAL_COGN) {
        this.AVAL_COGN = AVAL_COGN;
    }

    public int getAVAL_NUTR() {
        return AVAL_NUTR;
    }

    public void setAVAL_NUTR(int AVAL_NUTR) {
        this.AVAL_NUTR = AVAL_NUTR;
    }

}
