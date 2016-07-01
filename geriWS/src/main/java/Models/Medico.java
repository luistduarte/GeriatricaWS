package Models;

import java.util.Date;

/**
 *
 * @author luis
 */
public class Medico {
    
    private int CC;
    private String nome;
    private String pass;
    private int tipo_espe;
    private Date data_nasc;

    public Medico(int CC, String nome, String pass, int tipo_espe, Date data_nasc) {
        this.CC = CC;
        this.nome = nome;
        this.pass = pass;
        this.tipo_espe = tipo_espe;
        this.data_nasc = data_nasc;
    }

    public int getCC() {
        return CC;
    }

    public void setCC(int CC) {
        this.CC = CC;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipo_espe() {
        return tipo_espe;
    }

    public void setTipo_espe(int tipo_espe) {
        this.tipo_espe = tipo_espe;
    }

    public Date getData_nasc() {
        return data_nasc;
    }

    public void setData_nasc(Date data_nasc) {
        this.data_nasc = data_nasc;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    } 
}
