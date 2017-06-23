/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.sql;

/**
 *
 * @author evson
 */
public interface BoletimSQL {
    public static final String CREATE                               = "insert into Boletim(idBoletim, testeB1, provaB1, testeB2, provaB2, Aluno_idAluno, Disciplina_idDisciplina) values(?, ?, ?, ?, ?, ?, ?);";
    public static final String GET                                  = "select * from Boletim where idBoletim = ?;";
    public static final String GET_ID_ALUNO_AND_ID_DISCIPLINA       = "select * from Boletim where Aluno_idAluno = ? and Disciplina_idDisciplina = ?;";
    public static final String QUERY                                = "select * from Boletim";
    public static final String QUERY_ID_ALUNO                       = "select * from Boletim where Aluno_idAluno = ?;";
    public static final String QUERY_ID_DISCIPLINA                  = "select * from Boletim where Disciplina_idDisciplina = ?;";
    public static final String UPDATE_ID                            = "update Boletim set testeB1 = ?, provaB1 = ?, testeB2 = ?, provaB2 = ? where idBoletim = ?;";
    public static final String DELETE                               = "delete from Boletim;";
    public static final String DELETE_ID                            = "delete from Boletim where idBoletim = ?;";
    public static final String DELETE_ID_ALUNO                      = "delete from Boletim where Aluno_idAluno = ?;";
    public static final String DELETE_ID_DISCIPLINA                 = "delete from Boletim where Disciplina_idDisciplina = ?;";
    public static final String DELETE_ID_ALUNO_AND_ID_DISCIPLINA    = "delete from Boletim where Aluno_idAluno = ? and Disciplina_idDisciplina = ?;";
}
