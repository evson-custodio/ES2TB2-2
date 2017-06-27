/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AlunoDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import model.Aluno;

/**
 *
 * @author evson
 */
public final class AlunoController extends Observable {
    private static final AlunoController CONTROL = new AlunoController();
    private static final String[] NOME_COLUNAS = { "ID", "Nome", "Matrícula", "Média Pessoal" };
    private static final List<Aluno> ALUNOS = new ArrayList<>(AlunoDAO.getInstance().query().values());

    private AlunoController() {
    }

    public static AlunoController getInstance() {
        return CONTROL;
    }

    public static List<Aluno> getAlunos() {
        return ALUNOS;
    }
    
    public static String[] getNomeColunas() {
        return NOME_COLUNAS;
    }
    
    public Object[] getAlunoObject(Object object) {
        Aluno aluno = (Aluno) object;
        return new Object[] {
            aluno.getId(),
            aluno.getNome(),
            aluno.getMatricula(),
            aluno.getMediaPessoal() != null ? aluno.getMediaPessoal() : "-"
        };
    }
    
    public Object[][] getAlunosObjects() {
        Object[][] data = new Object[ALUNOS.size()][NOME_COLUNAS.length];
        
        for (int i = 0; i < ALUNOS.size(); i++) {
            data[i] = getAlunoObject(ALUNOS.get(i));
        }
        
        return data;
    }
    
    public void adicionarAluno(String nome, String matricula) {
        Aluno aluno = new Aluno(null, nome, matricula, null);
        AlunoDAO.getInstance().create(aluno);
        ALUNOS.add(aluno);
        setChanged();
        notifyObservers(aluno);
    }
}
