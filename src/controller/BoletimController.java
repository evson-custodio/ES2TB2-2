/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BoletimDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import model.Aluno;
import model.Boletim;
import model.Disciplina;

/**
 *
 * @author evson
 */
public final class BoletimController extends Observable {
    private static final BoletimController CONTROL = new BoletimController();
    private static final String[] NOME_COLUNAS = { "ID", "Aluno", "Disciplina", "Teste B1", "Prova B1", "Nota B1", "Teste B2", "Prova B2", "Nota B2", "Média", "Status" };
    private static final List<Boletim> BOLETINS = new ArrayList<>(BoletimDAO.getInstance().query().values());

    private BoletimController() {
//        AlunoController.getInstance().addObserver(this);
//        DisciplinaController.getInstance().addObserver(this);
    }

    public static BoletimController getInstance() {
        return CONTROL;
    }

    public static List<Boletim> getBoletins() {
        return BOLETINS;
    }
    
    public static String[] getNomeColunas() {
        return NOME_COLUNAS;
    }
    
    public Object[] getBoletimObject(Object object) {
        Boletim boletim = (Boletim) object;
        return new Object[] {
            boletim.getId(),
            boletim.getAluno().getNome(),
            boletim.getDisciplina().getNome(),
            boletim.getTesteB1() != null ? boletim.getTesteB1() : "-",
            boletim.getProvaB1() != null ? boletim.getProvaB1() : "-",
            boletim.getSomaB1() != null ? boletim.getSomaB1() : "-",
            boletim.getTesteB2() != null ? boletim.getTesteB2() : "-",
            boletim.getProvaB2() != null ? boletim.getProvaB2() : "-",
            boletim.getSomaB2() != null ? boletim.getSomaB2() : "-",
            boletim.getMedia() != null ? boletim.getMedia() : "-",
            boletim.getStatus() != null ? boletim.getStatus() : "-"
        };
    }
    
    public Object[][] getBoletinsObjects() {
        Object[][] data = new Object[BOLETINS.size()][NOME_COLUNAS.length];
        
        for (int i = 0; i < BOLETINS.size(); i++) {
            data[i] = getBoletimObject(BOLETINS.get(i));
        }
        
        return data;
    }
    
    
    public List<String> getAlunosNomes() {
        List<String> alunosNomes = new ArrayList<>();
        
        for(Aluno aluno : AlunoController.getAlunos()) {
            alunosNomes.add(aluno.getNome());
        }
        
        return alunosNomes;
    }
    
    public List<String> getDisciplinasNomes() {
        List<String> disciplinasNomes = new ArrayList<>();
        
        for (Disciplina disciplina : DisciplinaController.getDisciplinas()) {
            disciplinasNomes.add(disciplina.getNome());
        }
        
        return disciplinasNomes;
    }
    
    public void adicionarBoletim(Integer indexAluno, Integer indexDisciplina) {
        Boletim boletim = new Boletim(null, null, null, null, null, AlunoController.getAlunos().get(indexAluno), DisciplinaController.getDisciplinas().get(indexDisciplina));
        BoletimDAO.getInstance().create(boletim);
        BOLETINS.add(boletim);
        setChanged();
        notifyObservers(boletim);
    }
//
//    @Override
//    public void update(Observable o, Object arg) {
//        setChanged();
//        notifyObservers();
//    }
}

