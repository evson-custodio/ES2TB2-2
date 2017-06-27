/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DisciplinaDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import model.Disciplina;

/**
 *
 * @author evson
 */
public class DisciplinaController extends Observable {
    private static final DisciplinaController CONTROL = new DisciplinaController();
    private static final String[] NOME_COLUNAS = { "ID", "Nome", "Média Disciplina" };
    private static final List<Disciplina> DISCIPLINAS = new ArrayList<>(DisciplinaDAO.getInstance().query().values());

    private DisciplinaController() {
    }

    public static DisciplinaController getInstance() {
        return CONTROL;
    }

    public static List<Disciplina> getDisciplinas() {
        return DISCIPLINAS;
    }

    public static String[] getNomeColunas() {
        return NOME_COLUNAS;
    }
    
    public Object[] getDisciplinaObject(Object object) {
        Disciplina disciplina = (Disciplina) object;
        return new Object[] {
            disciplina.getId(),
            disciplina.getNome(),
            disciplina.getMediaTurma() != null ? disciplina.getMediaTurma() : "-"
        };
    }
    
    public Object[][] getDisciplinasObjects() {
        Object[][] data = new Object[DISCIPLINAS.size()][NOME_COLUNAS.length];
        
        for (int i = 0; i < DISCIPLINAS.size(); i++) {
            data[i] = getDisciplinaObject(DISCIPLINAS.get(i));
        }
        
        return data;
    }
    
    public void adicionarDisciplina(String nome) {
        Disciplina disciplina = new Disciplina(null, nome, null);
        DisciplinaDAO.getInstance().create(disciplina);
        DISCIPLINAS.add(disciplina);
        setChanged();
        notifyObservers(disciplina);
    }
}
