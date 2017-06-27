/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.MySQL;
import dao.sql.BoletimSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Aluno;
import model.Boletim;
import model.Disciplina;
import util.CRUD;
import util.JDBC;
import util.Model;

/**
 *
 * @author evson
 */
public final class BoletimDAO implements CRUD<Integer, Boletim> {
    private static final BoletimDAO DAO = new BoletimDAO();
    private final Connection CONNECTION = JDBC.getConnection(MySQL.DRIVER, MySQL.URL, MySQL.USER, MySQL.PASSWORD);
    private final Map<Integer, Boletim> BOLETINS = new HashMap<>();
    
    private BoletimDAO() {
    }
    
    public static BoletimDAO getInstance() {
        return DAO;
    }

    public Map<Integer, Boletim> getBoletins() {
        return BOLETINS;
    }
    
    @Override
    public void create(Model model) {
        try {
            Boletim boletim = (Boletim) model;
            
            CONNECTION.setAutoCommit(false);
            PreparedStatement statement = CONNECTION.prepareStatement(BoletimSQL.CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            
            if (boletim.getTesteB1() != null) {
                statement.setDouble(1, boletim.getTesteB1());
            }
            else {
                statement.setNull(1, Types.DOUBLE);
            }
            
            if (boletim.getProvaB1() != null) {
                statement.setDouble(2, boletim.getProvaB1());
            }
            else {
                statement.setNull(2, Types.DOUBLE);
            }
            
            if (boletim.getTesteB2() != null) {
                statement.setDouble(3, boletim.getTesteB2());
            }
            else {
                statement.setNull(3, Types.DOUBLE);
            }
            
            if (boletim.getProvaB2() != null) {
                statement.setDouble(4, boletim.getProvaB2());
            }
            else {
                statement.setNull(4, Types.DOUBLE);
            }
            
            statement.setInt(5, boletim.getAluno().getId());
            statement.setInt(6, boletim.getDisciplina().getId());

            statement.executeUpdate();
            
            ResultSet result = statement.getGeneratedKeys();
            result.next();
            
            boletim.setId(result.getInt(1));
            BOLETINS.put(boletim.getId(), boletim);

            statement.close();

            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            try {
                CONNECTION.rollback();
                CONNECTION.setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch (ClassCastException | NullPointerException ex) {
            Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Boletim get(Model model) {
        Boletim boletim = null;
        Aluno aluno;
        Disciplina disciplina;
        
        try {
            boletim = BOLETINS.get(((Boletim) model).getId());
            
            if (boletim == null) {
                boletim = (Boletim) model;
                PreparedStatement statement = CONNECTION.prepareStatement(BoletimSQL.GET);
                ResultSet result;

                statement.setInt(1, boletim.getId());

                result = statement.executeQuery();

                result.next();
                boletim.setTesteB1(result.getDouble(2));
                boletim.setProvaB1(result.getDouble(3));
                boletim.setTesteB2(result.getDouble(4));
                boletim.setProvaB2(result.getDouble(5));
                aluno = AlunoDAO.getInstance().get(new Aluno(result.getInt(6), null, null, null));
                disciplina = DisciplinaDAO.getInstance().get(new Disciplina(result.getInt(7), null, null));
                aluno.addBoletim(boletim);
                disciplina.addBoletim(boletim);
                boletim.setAluno(aluno);
                boletim.setDisciplina(disciplina);
                BOLETINS.put(boletim.getId(), boletim);

                result.close();
                statement.close();
            }
        }
        catch (ClassCastException | SQLException | NullPointerException ex) {
            Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return boletim;
    }
    
    public Boletim get(Aluno aluno, Disciplina disciplina) {
        Boletim boletim = null;
        
        try {
            for(Boletim b : BOLETINS.values()) {
                if (b.getAluno().equals(aluno) && b.getDisciplina().equals(disciplina)) {
                    return b;
                }
            }
            
            PreparedStatement statement = CONNECTION.prepareStatement(BoletimSQL.GET_ID_ALUNO_AND_ID_DISCIPLINA);
            ResultSet result;

            statement.setInt(1, aluno.getId());
            statement.setInt(2, disciplina.getId());

            result = statement.executeQuery();
            
            result.next();
            boletim = new Boletim(result.getInt(1), result.getDouble(2), result.getDouble(3), result.getDouble(4), result.getDouble(5), aluno, disciplina);
            aluno.addBoletim(boletim);
            disciplina.addBoletim(boletim);
            BOLETINS.put(boletim.getId(), boletim);

            result.close();
            statement.close();
        }
        catch (ClassCastException | SQLException | NullPointerException ex) {
            Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return boletim;
    }
    
    @Override
    public Map<Integer, Boletim> query() {
        Map<Integer, Boletim> boletins = new HashMap<>();
        
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(BoletimSQL.QUERY);
            ResultSet result = statement.executeQuery();
            Boletim boletim;
            Aluno aluno;
            Disciplina disciplina;

            while(result.next()) {
                boletim = BOLETINS.get(result.getInt(1));
                if (boletim == null) {
                    aluno = AlunoDAO.getInstance().get(new Aluno(result.getInt(6), null, null, null));
                    disciplina = DisciplinaDAO.getInstance().get(new Disciplina(result.getInt(7), null, null));
                    boletim = new Boletim(result.getInt(1), (Double)result.getObject(2), (Double)result.getObject(3), (Double)result.getObject(4), (Double)result.getObject(5), aluno, disciplina);
                    aluno.addBoletim(boletim);
                    disciplina.addBoletim(boletim);
                    BOLETINS.put(boletim.getId(), boletim);
                }
                boletins.put(boletim.getId(), boletim);
            }
            
            result.close();
            statement.close();
        }
        catch (SQLException | NullPointerException ex) {
            Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return boletins;
    }
    
    public Map<Integer, Boletim> query(Model model) {
        Map<Integer, Boletim> boletins = new HashMap<>();
        
        try {
            PreparedStatement statement = null;
            Boletim boletim;
            Aluno aluno;
            Disciplina disciplina;
            
            if (model instanceof Aluno) {
                aluno = (Aluno) model;
                statement = CONNECTION.prepareStatement(BoletimSQL.QUERY_ID_ALUNO);
                statement.setInt(1, aluno.getId());
                
                ResultSet result = statement.executeQuery();

                while(result.next()) {
                    boletim = BOLETINS.get(result.getInt(1));
                    if (boletim == null) {
                        disciplina = DisciplinaDAO.getInstance().get(new Disciplina(result.getInt(7), null, null));
                        boletim = new Boletim(result.getInt(1), result.getDouble(2), result.getDouble(3), result.getDouble(4), result.getDouble(5), aluno, disciplina);
                        aluno.addBoletim(boletim);
                        disciplina.addBoletim(boletim);
                        BOLETINS.put(boletim.getId(), boletim);
                    }
                    boletins.put(boletim.getId(), boletim);
                }

                result.close();
                statement.close();
            }
            else if (model instanceof Disciplina) {
                disciplina = (Disciplina) model;
                statement = CONNECTION.prepareStatement(BoletimSQL.QUERY_ID_DISCIPLINA);
                statement.setInt(1, disciplina.getId());
                
                ResultSet result = statement.executeQuery();

                while(result.next()) {
                    boletim = BOLETINS.get(result.getInt(1));
                    if (boletim == null) {
                        aluno = AlunoDAO.getInstance().get(new Aluno(result.getInt(6), null, null, null));
                        boletim = new Boletim(result.getInt(1), result.getDouble(2), result.getDouble(3), result.getDouble(4), result.getDouble(5), aluno, disciplina);
                        aluno.addBoletim(boletim);
                        disciplina.addBoletim(boletim);
                        BOLETINS.put(boletim.getId(), boletim);
                    }
                    boletins.put(boletim.getId(), boletim);
                }

                result.close();
                statement.close();
            }
        }
        catch (ClassCastException | SQLException | NullPointerException ex) {
            Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return boletins;
    }

    @Override
    public void update(Model model) {
        try {
            Boletim boletim = (Boletim) model;
            
            CONNECTION.setAutoCommit(false);
            PreparedStatement statement = CONNECTION.prepareStatement(BoletimSQL.UPDATE_ID);

            statement.setDouble(1, boletim.getTesteB1());
            statement.setDouble(2, boletim.getProvaB1());
            statement.setDouble(3, boletim.getTesteB2());
            statement.setDouble(4, boletim.getProvaB2());
            statement.setInt(5, boletim.getId());

            statement.executeUpdate();

            BOLETINS.put(boletim.getId(), boletim);

            statement.close();

            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            try {
                CONNECTION.rollback();
                CONNECTION.setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch (ClassCastException | NullPointerException ex) {
            Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete() {
        try {
            CONNECTION.setAutoCommit(false);
            PreparedStatement statement = CONNECTION.prepareStatement(BoletimSQL.DELETE);

            statement.executeUpdate();
            
            for (Aluno aluno : AlunoDAO.getInstance().getAlunos().values()) {
                aluno.getBoletins().clear();
            }
            
            for (Disciplina disciplina : DisciplinaDAO.getInstance().getDisciplinas().values()) {
                disciplina.getBoletins().clear();
            }
            
            BOLETINS.clear();

            statement.close();

            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            try {
                CONNECTION.rollback();
                CONNECTION.setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch (NullPointerException ex) {
            Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Model model) {
        try {
            CONNECTION.setAutoCommit(false);
            PreparedStatement statement;
            
            if (model instanceof Boletim) {
                Boletim boletim = (Boletim) model;
                statement = CONNECTION.prepareStatement(BoletimSQL.DELETE_ID);
                statement.setInt(1, boletim.getId());
                
                statement.executeUpdate();
                
                boletim.getAluno().removeBoletim(boletim);
                boletim.getDisciplina().removeBoletim(boletim);                
                BOLETINS.remove(boletim.getId());
                
                statement.close();
            }
            else if (model instanceof Aluno) {
                Aluno aluno = (Aluno) model;
                statement = CONNECTION.prepareStatement(BoletimSQL.DELETE_ID_ALUNO);
                statement.setInt(1, aluno.getId());
                
                statement.executeUpdate();
                
                for(Boletim boletim : aluno.getBoletins().values()) {
                    boletim.getDisciplina().removeBoletim(boletim);
                    BOLETINS.remove(boletim.getId());
                }
                aluno.getBoletins().clear();
                
                statement.close();
            }
            else if (model instanceof Disciplina) {
                Disciplina disciplina = (Disciplina) model;
                statement = CONNECTION.prepareStatement(BoletimSQL.DELETE_ID_DISCIPLINA);
                statement.setInt(1, disciplina.getId());
                
                statement.executeUpdate();
                
                for(Boletim boletim : disciplina.getBoletins().values()) {
                    boletim.getAluno().removeBoletim(boletim);
                    BOLETINS.remove(boletim.getId());
                }
                disciplina.getBoletins().clear();
                
                statement.close();
            }
            
            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            try {
                CONNECTION.rollback();
                CONNECTION.setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch (ClassCastException | NullPointerException ex) {
            Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(Aluno aluno, Disciplina disciplina) {
        try {
            CONNECTION.setAutoCommit(false);
            PreparedStatement statement = CONNECTION.prepareStatement(BoletimSQL.DELETE_ID_ALUNO_AND_ID_DISCIPLINA);

            statement.setInt(1, aluno.getId());
            statement.setInt(2, disciplina.getId());

            statement.executeUpdate();
            
            for (Boletim boletim : aluno.getBoletins().values()) {
                if (boletim.getDisciplina().equals(disciplina)) {
                    aluno.removeBoletim(boletim);
                    disciplina.removeBoletim(boletim);
                    BOLETINS.remove(boletim.getId());
                    break;
                }
            }

            statement.close();
            
            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            try {
                CONNECTION.rollback();
                CONNECTION.setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch (NullPointerException ex) {
            Logger.getLogger(BoletimDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}