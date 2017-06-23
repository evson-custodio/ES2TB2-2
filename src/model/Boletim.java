/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;
import util.Model;

/**
 *
 * @author evson
 */
public class Boletim implements Model {
    private Integer id;
    private Double testeB1;
    private Double provaB1;
    private Double testeB2;
    private Double provaB2;
    private Aluno aluno;
    private Disciplina disciplina;

    public Boletim() {
    }

    public Boletim(Integer id, Double testeB1, Double provaB1, Double testeB2, Double provaB2, Aluno aluno, Disciplina disciplina) {
        this.id = id;
        this.testeB1 = testeB1;
        this.provaB1 = provaB1;
        this.testeB2 = testeB2;
        this.provaB2 = provaB2;
        this.aluno = aluno;
        this.disciplina = disciplina;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTesteB1(Double testeB1) {
        this.testeB1 = testeB1;
    }

    public void setProvaB1(Double provaB1) {
        this.provaB1 = provaB1;
    }

    public void setTesteB2(Double testeB2) {
        this.testeB2 = testeB2;
    }

    public void setProvaB2(Double provaB2) {
        this.provaB2 = provaB2;
    }
    
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Integer getId() {
        return id;
    }

    public Double getTesteB1() {
        return testeB1;
    }

    public Double getProvaB1() {
        return provaB1;
    }

    public Double getTesteB2() {
        return testeB2;
    }

    public Double getProvaB2() {
        return provaB2;
    }
    
    public Aluno getAluno() {
        return aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }
    
    public Double getSomaB1() {
        if (testeB1 == null && provaB1 == null) {
            return null;
        }
        
        return ((testeB1 != null ? testeB1 : 0.0) + (provaB1 != null ? provaB1 : 0.0));
    }
    
    public Double getSomaB2() {
        if (testeB2 == null && provaB2 == null) {
            return null;
        }
        
        return ((testeB2 != null ? testeB2 : 0.0) + (provaB2 != null ? provaB2 : 0.0));
    }
    
    public Double getMedia() {
        Double somaB1 = getSomaB1();
        Double somaB2 = getSomaB2();
        
        if (somaB1 == null && somaB2 == null) {
            return null;
        }
        else {
            return ((somaB1 != null ? somaB1 : 0.0) + (somaB2 != null ? somaB2 : 0.0)) / 2.0;
        }
    }
    
    public String getStatus() {
        Double media = getMedia();
        
        if (media == null) {
            return null;
        }
        else if (media >= 7.0) {
            return "Aprovado";
        }
        else if (media >= 3.0) {
            return "Recuperação";
        }
        else if (media < 3.0) {
            return "Reprovado";
        }
        else {
            return "Notas Invalidas!";
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Boletim other = (Boletim) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | TesteB1: " + testeB1 + " | ProvaB1: " + provaB1 + " | TesteB2: " + testeB2 + " | ProvaB2: " + provaB2 + " | Aluno: " + aluno + " | Disciplina: " + disciplina;
    }
}
