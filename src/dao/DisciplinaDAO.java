/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.MySQL;
import dao.sql.DisciplinaSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Disciplina;
import util.CRUD;
import util.JDBC;
import util.Model;

/**
 *
 * @author evson
 */
public final class DisciplinaDAO implements CRUD<Integer, Disciplina> {
    private static final DisciplinaDAO DAO = new DisciplinaDAO();
    private final Connection CONNECTION = JDBC.getConnection(MySQL.DRIVER, MySQL.URL, MySQL.USER, MySQL.PASSWORD);
    private final Map<Integer, Disciplina> DISCIPLINAS = new HashMap<>();
    
    private DisciplinaDAO() {
    }
    
    public static DisciplinaDAO getInstance() {
        return DAO;
    }

    public Map<Integer, Disciplina> getDisciplinas() {
        return DISCIPLINAS;
    }
    
    @Override
    public void create(Model model) {
        try {
            Disciplina disciplina = (Disciplina) model;
            
            CONNECTION.setAutoCommit(false);
            PreparedStatement statement = CONNECTION.prepareStatement(DisciplinaSQL.CREATE, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(1, disciplina.getNome());

            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            result.next();

            disciplina.setId(result.getInt(1));
            DISCIPLINAS.put(disciplina.getId(), disciplina);

            result.close();
            statement.close();

            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            try {
                CONNECTION.rollback();
                CONNECTION.setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch (ClassCastException | NullPointerException ex) {
            Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Disciplina get(Model model) {
        Disciplina disciplina = null;
        
        try {
            disciplina = DISCIPLINAS.get(((Disciplina) model).getId());
            
            if (disciplina == null) {
                disciplina = (Disciplina) model;
                PreparedStatement statement = CONNECTION.prepareStatement(DisciplinaSQL.GET);
                ResultSet result;

                statement.setInt(1, disciplina.getId());

                result = statement.executeQuery();

                result.next();
                disciplina.setNome(result.getString(2));

                DISCIPLINAS.put(disciplina.getId(), disciplina);

                result.close();
                statement.close();
            }
        }
        catch (ClassCastException | SQLException | NullPointerException ex) {
            Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return disciplina;
    }
    
    @Override
    public Map<Integer, Disciplina> query() {
        Map<Integer, Disciplina> disciplinas = new HashMap<>();
        
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(DisciplinaSQL.QUERY);
            ResultSet result = statement.executeQuery();
            Disciplina disciplina;

            while(result.next()) {
                disciplina = DISCIPLINAS.get(result.getInt(1));
                if (disciplina == null) {
                    disciplina = new Disciplina(result.getInt(1), result.getString(2), null);
                    DISCIPLINAS.put(disciplina.getId(), disciplina);
                }
                disciplinas.put(disciplina.getId(), disciplina);
            }
            
            result.close();
            statement.close();
        }
        catch (SQLException | NullPointerException ex) {
            Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return disciplinas;
    }

    @Override
    public void update(Model model) {
        try {
            Disciplina disciplina = (Disciplina) model;
            
            CONNECTION.setAutoCommit(false);
            PreparedStatement statement = CONNECTION.prepareStatement(DisciplinaSQL.UPDATE_ID);

            statement.setString(1, disciplina.getNome());
            statement.setInt(2, disciplina.getId());

            statement.executeUpdate();
            
            DISCIPLINAS.put(disciplina.getId(), disciplina);

            statement.close();

            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            try {
                CONNECTION.rollback();
                CONNECTION.setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch (ClassCastException | NullPointerException ex) {
            Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete() {
        try {
            CONNECTION.setAutoCommit(false);
            PreparedStatement statement = CONNECTION.prepareStatement(DisciplinaSQL.DELETE);

            statement.executeUpdate();
            
            DISCIPLINAS.clear();
            BoletimDAO.getInstance().delete();

            statement.close();

            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            try {
                CONNECTION.rollback();
                CONNECTION.setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch (NullPointerException ex) {
            Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Model model) {
        try {
            Disciplina disciplina = (Disciplina) model;
            
            CONNECTION.setAutoCommit(false);
            PreparedStatement statement = CONNECTION.prepareStatement(DisciplinaSQL.DELETE_ID);

            statement.setInt(1, disciplina.getId());

            statement.executeUpdate();
            
            BoletimDAO.getInstance().delete(disciplina);
            DISCIPLINAS.remove(disciplina.getId());

            statement.close();

            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            try {
                CONNECTION.rollback();
                CONNECTION.setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch (ClassCastException | NullPointerException ex) {
            Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
