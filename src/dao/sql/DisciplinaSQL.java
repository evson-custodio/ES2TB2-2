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
public interface DisciplinaSQL {
    public static final String CREATE       = "insert into Disciplina(nome) values(?);";
    public static final String GET          = "select * from Disciplina where idDisciplina = ?;";
    public static final String QUERY        = "select * from Disciplina";
    public static final String UPDATE_ID    = "update Disciplina set nome = ? where idDisciplina = ?;";
    public static final String DELETE       = "delete from Disciplina;";
    public static final String DELETE_ID    = "delete from Disciplina where idDisciplina = ?;";
}
