/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author evson
 */
public class Boletim {
    private Aluno aluno;
    private Disciplina disciplina;
    private Double testeB1;
    private Double provaB1;
    private Double testeB2;
    private Double provaB2;

    public Boletim() {
    }

    public Boletim(Aluno aluno, Disciplina disciplina, Double testeB1, Double provaB1, Double testeB2, Double provaB2) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.testeB1 = testeB1;
        this.provaB1 = provaB1;
        this.testeB2 = testeB2;
        this.provaB2 = provaB2;
    }
    
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
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

    public Aluno getAluno() {
        return aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.aluno);
        hash = 41 * hash + Objects.hashCode(this.disciplina);
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
        if (!Objects.equals(this.aluno, other.aluno)) {
            return false;
        }
        return Objects.equals(this.disciplina, other.disciplina);
    }
}
