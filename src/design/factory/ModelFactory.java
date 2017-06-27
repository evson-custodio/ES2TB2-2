/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design.factory;

import dao.AlunoDAO;
import dao.BoletimDAO;
import dao.DisciplinaDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Aluno;
import model.Boletim;
import model.Disciplina;
import util.Model;

/**
 *
 * @author evson
 */
public abstract class ModelFactory {
    
    public static Model getModel(String key) {
        try {
            if (key.equalsIgnoreCase("aluno")) {
                return new Aluno();
            }
            else if (key.equalsIgnoreCase("disciplina")) {
                return new Disciplina();
            }
            else if (key.equalsIgnoreCase("boletim")) {
                return new Boletim();
            }
        }
        catch (NullPointerException | ClassCastException ex) {
            Logger.getLogger(ModelFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static Model getModel(String key, Integer id) {
        Model model = null;
        
        try {
            if (key.equalsIgnoreCase("aluno")) {
                model = AlunoDAO.getInstance().get(new Aluno(id, null, null, null));
                BoletimDAO.getInstance().get(model);
            }
            else if (key.equalsIgnoreCase("disciplina")) {
                model = DisciplinaDAO.getInstance().get(new Disciplina(id, null, null));
                BoletimDAO.getInstance().get(model);
            }
            else if (key.equalsIgnoreCase("boletim")) {
                model = BoletimDAO.getInstance().get(new Boletim(id, null, null, null, null, null, null));
            }
        }
        catch (NullPointerException | ClassCastException ex) {
            Logger.getLogger(ModelFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return model;
    }
}
