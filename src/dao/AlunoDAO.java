/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.MySQL;
import dao.sql.AlunoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Aluno;
import util.CRUD;
import util.JDBC;
import util.Model;

/**
 *
 * @author evson
 */
public final class AlunoDAO implements CRUD<Integer, Aluno> {
    private static final AlunoDAO DAO = new AlunoDAO();
    private final Connection CONNECTION = JDBC.getConnection(MySQL.DRIVER, MySQL.URL, MySQL.USER, MySQL.PASSWORD);
    private final Map<Integer, Aluno> ALUNOS = new HashMap<>();
    
    private AlunoDAO() {
    }
    
    public static AlunoDAO getInstance() {
        return DAO;
    }

    public Map<Integer, Aluno> getAlunos() {
        return ALUNOS;
    }

    @Override
    public void create(Model model) {
        
        try {
            Aluno aluno = (Aluno) model;
            
            CONNECTION.setAutoCommit(false);
            PreparedStatement statement = CONNECTION.prepareStatement(AlunoSQL.CREATE, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(1, aluno.getNome());
            statement.setString(2, aluno.getMatricula());

            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            result.next();

            aluno.setId(result.getInt(1));
            ALUNOS.put(aluno.getId(), aluno);

            result.close();
            statement.close();

            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            try {
                CONNECTION.rollback();
                CONNECTION.setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch (ClassCastException | NullPointerException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Aluno get(Model model) {
        Aluno aluno = null;
        
        try {
            aluno = ALUNOS.get(((Aluno) model).getId());
            
            if (aluno == null) {
                aluno = (Aluno) model;
                PreparedStatement statement = CONNECTION.prepareStatement(AlunoSQL.GET);
                ResultSet result;

                statement.setInt(1, aluno.getId());

                result = statement.executeQuery();

                result.next();
                aluno.setNome(result.getString(2));
                aluno.setMatricula(result.getString(3));

                ALUNOS.put(aluno.getId(), aluno);

                result.close();
                statement.close();
            }
        }
        catch (ClassCastException | SQLException | NullPointerException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return aluno;
    }
    
    @Override
    public Map<Integer, Aluno> query() {
        Map<Integer, Aluno> alunos = new HashMap<>();
        
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(AlunoSQL.QUERY);
            ResultSet result = statement.executeQuery();
            Aluno aluno;

            while(result.next()) {
                aluno = ALUNOS.get(result.getInt(1));
                if (aluno == null) {
                    aluno = new Aluno(result.getInt(1), result.getString(2), result.getString(3), null);
                    ALUNOS.put(aluno.getId(), aluno);
                }
                alunos.put(aluno.getId(), aluno);
            }
            
            result.close();
            statement.close();
        }
        catch (SQLException | NullPointerException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return alunos;
    }

    @Override
    public void update(Model model) {
        try {
            Aluno aluno = (Aluno) model;
            
            CONNECTION.setAutoCommit(false);
            PreparedStatement statement = CONNECTION.prepareStatement(AlunoSQL.UPDATE_ID);

            statement.setString(1, aluno.getNome());
            statement.setString(2, aluno.getMatricula());
            statement.setInt(3, aluno.getId());

            statement.executeUpdate();
            
            ALUNOS.put(aluno.getId(), aluno);

            statement.close();

            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            try {
                CONNECTION.rollback();
                CONNECTION.setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch (ClassCastException | NullPointerException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete() {
        try {
            CONNECTION.setAutoCommit(false);
            PreparedStatement statement = CONNECTION.prepareStatement(AlunoSQL.DELETE);

            statement.executeUpdate();
            
            ALUNOS.clear();
            BoletimDAO.getInstance().delete();

            statement.close();

            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            try {
                CONNECTION.rollback();
                CONNECTION.setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch (NullPointerException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Model model) {
        try {
            Aluno aluno = (Aluno) model;
            
            CONNECTION.setAutoCommit(false);
            PreparedStatement statement = CONNECTION.prepareStatement(AlunoSQL.DELETE_ID);

            statement.setInt(1, aluno.getId());

            statement.executeUpdate();
            
            BoletimDAO.getInstance().delete(aluno);
            ALUNOS.remove(aluno.getId());

            statement.close();

            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            try {
                CONNECTION.rollback();
                CONNECTION.setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch (ClassCastException | NullPointerException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
