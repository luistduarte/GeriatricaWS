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
public class Avaliacao {
    private int id_avaliacao;
    private String descricao;
    private int resumo;
    private Date data;

    public Avaliacao(int id_avaliacao, String descricao, int resumo, Date data) {
        this.id_avaliacao = id_avaliacao;
        this.descricao = descricao;
        this.resumo = resumo;
        this.data = data;
    }

    public int getid_avaliacao() {
        return id_avaliacao;
    }

    public void setid_avaliacao(int id_avaliacao) {
        this.id_avaliacao = id_avaliacao;
    }

    public String getdescricao() {
        return descricao;
    }

    public void setdescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getresumo() {
        return resumo;
    }

    public void setresumo(int resumo) {
        this.resumo = resumo;
    }

    public Date getdata() {
        return data;
    }

    public void setdata(Date data) {
        this.data = data;
    }

    

    
   
}
