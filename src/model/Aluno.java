/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author evson
 */
public class Aluno {
    private Integer id;
    private String nome;
    private String matricula;
    private Map<Integer, Boletim> boletins;

    public Aluno() {
    }

    public Aluno(Integer id, String nome, String matricula, Map<Integer, Boletim> boletins) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.boletins = boletins;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setBoletins(Map<Integer, Boletim> boletins) {
        this.boletins = boletins;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public Map<Integer, Boletim> getBoletins() {
        return boletins;
    }
    
    public void addBoletim(Boletim boletim) {
        try {
            if (boletins == null) {
                boletins = new HashMap<>();
            }
            if (!boletins.containsKey(boletim.hashCode())) {
                boletins.put(boletim.hashCode(), boletim);
            }
        }
        catch (NullPointerException ex) {
            Logger.getLogger(Aluno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void removeBoletim(Boletim boletim) {
        try {
            if (boletins != null) {
                boletins.remove(boletim.hashCode());
            }
        }
        catch (NullPointerException ex) {
            Logger.getLogger(Aluno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final Aluno other = (Aluno) obj;
        return Objects.equals(this.id, other.id);
    }
}
