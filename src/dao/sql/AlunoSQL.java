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
public interface AlunoSQL {
    public static final String CREATE       = "insert into Aluno(nome, matricula) values(?, ?);";
    public static final String GET          = "select * from Aluno where idAluno = ?;";
    public static final String QUERY        = "select * from Aluno";
    public static final String UPDATE_ID    = "update Aluno set nome = ?, matricula = ? where idAluno = ?;";
    public static final String DELETE       = "delete from Aluno;";
    public static final String DELETE_ID    = "delete from Aluno where idAluno = ?;";
}
