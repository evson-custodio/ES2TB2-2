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
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author evson
 */
public abstract class JTableFactory {
    private static final Map<String, DefaultTableModel> TABLES = new HashMap<>();
    
    public static DefaultTableModel getTable(String key) {
        DefaultTableModel table = null;
        
        try {
            table = TABLES.get(key);
            
            if (table == null) {
                if (key.equalsIgnoreCase("aluno")) {
                    table = new DefaultTableModel(null,
                            new String[] {
                                "ID", "Nome", "Matrícula", "Média Pessoal", "Deletar"
                            })
                    {
                        Class[] types = new Class [] {
                            Integer.class, String.class, String.class, Double.class, JButton.class
                        };
                        boolean[] canEdit = new boolean [] {
                            false, false, false, false, false
                        };

                        @Override
                        public Class getColumnClass(int columnIndex) {
                            return types [columnIndex];
                        }

                        @Override
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return canEdit [columnIndex];
                        }
                    };
                }
                else if (key.equalsIgnoreCase("disciplina")) {
                    table = new DefaultTableModel(null,
                            new String[] {
                                "ID", "Nome", "Média Disciplina", "Deletar"
                            })
                    {
                        Class[] types = new Class [] {
                            Integer.class, String.class, Double.class, JButton.class
                        };
                        boolean[] canEdit = new boolean [] {
                            false, false, false, false
                        };

                        @Override
                        public Class getColumnClass(int columnIndex) {
                            return types [columnIndex];
                        }

                        @Override
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return canEdit [columnIndex];
                        }
                    };
                }
                else if (key.equalsIgnoreCase("boletim")) {
                    table = new DefaultTableModel(null,
                            new String[] {
                                "ID", "Aluno", "Disciplina", "Teste B1", "Prova B1", "Nota B1", "Teste B2", "Prova B2", "Nota B2", "Média", "Status", "Editar"
                            })
                    {
                        Class[] types = new Class [] {
                            Integer.class, String.class, String.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, String.class, JButton.class
                        };
                        boolean[] canEdit = new boolean [] {
                            false, false, false, true, true, false, true, true, false, false, false, false
                        };

                        @Override
                        public Class getColumnClass(int columnIndex) {
                            return types [columnIndex];
                        }

                        @Override
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return canEdit [columnIndex];
                        }
                    };
                }
            }
        }
        catch (NullPointerException | ClassCastException ex) {
            Logger.getLogger(JTableFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return table;
    }
}
