/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.List;

/**
 *
 * @author luis
 */
public class EspecialidadeList {
    private List<Especialidade> EspecialidadeList;

    public EspecialidadeList(List<Especialidade> EspecialidadeList) {
        this.EspecialidadeList = EspecialidadeList;
    }

    public List<Especialidade> getEspecialidadeList() {
        return EspecialidadeList;
    }

    public void setEspecialidadeList(List<Especialidade> EspecialidadeList) {
        this.EspecialidadeList = EspecialidadeList;
    }
}
