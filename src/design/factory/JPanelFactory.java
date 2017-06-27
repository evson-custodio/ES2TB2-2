/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design.factory;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import view.PanelAdicionarAluno;
import view.PanelAdicionarBoletim;
import view.PanelAdicionarDisciplina;
import view.PanelAlterarAluno;
import view.PanelAlterarBoletim;
import view.PanelAlterarDisciplina;
import view.PanelListAlunos;
import view.PanelListBoletins;
import view.PanelListDisciplinas;
import view.PanelMenu;

/**
 *
 * @author evson
 */
public abstract class JPanelFactory {
    private static final Map<String, JPanel> PANELS = new HashMap<>();
    
    public static JPanel getPanel(String key) {
        JPanel panel = null;
        
        try {
            panel = PANELS.get(key);
            
            if (panel == null) {
                if (key.equalsIgnoreCase("menu")) {
                    panel = new PanelMenu();
                    PANELS.put(key, panel);
                }
                else if (key.equalsIgnoreCase("listAlunos")) {
                    panel = new PanelListAlunos();
                    PANELS.put(key, panel);
                }
                else if (key.equalsIgnoreCase("listDisciplinas")) {
                    panel = new PanelListDisciplinas();
                    PANELS.put(key, panel);
                }
                else if (key.equalsIgnoreCase("listBoletins")) {
                    panel = new PanelListBoletins();
                    PANELS.put(key, panel);
                }
                else if (key.equalsIgnoreCase("adicionarAluno")) {
                    panel = new PanelAdicionarAluno();
                    PANELS.put(key, panel);
                }
                else if (key.equalsIgnoreCase("adicionarDisciplina")) {
                    panel = new PanelAdicionarDisciplina();
                    PANELS.put(key, panel);
                }
                else if (key.equalsIgnoreCase("adicionarBoletim")) {
                    panel = new PanelAdicionarBoletim();
                    PANELS.put(key, panel);
                }
                else if (key.equalsIgnoreCase("alterarAluno")) {
                    panel = new PanelAlterarAluno();
                    PANELS.put(key, panel);
                }
                else if (key.equalsIgnoreCase("alterarDisciplina")) {
                    panel = new PanelAlterarDisciplina();
                    PANELS.put(key, panel);
                }
                else if (key.equalsIgnoreCase("alterarBoletim")) {
                    panel = new PanelAlterarBoletim();
                    PANELS.put(key, panel);
                }
            }
        }
        catch (NullPointerException | ClassCastException ex) {
            Logger.getLogger(JPanelFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return panel;
    }
}
