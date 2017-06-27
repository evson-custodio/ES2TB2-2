/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author evson
 */
public abstract class SwingComponents {
    public static JPanel setPainelBody(JPanel painel) {
        try {
            painel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, Color.black));
            painel.setPreferredSize(new Dimension(670, 325));
        }
        catch(NullPointerException ex) {
            Logger.getLogger(SwingComponents.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return painel;
    }
    
    public static JPanel setPainel(JPanel painel, Integer width, Integer height) {
        try {
            painel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, Color.black));
            painel.setPreferredSize(new Dimension(width, height));
        }
        catch(NullPointerException ex) {
            Logger.getLogger(SwingComponents.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return painel;
    }
    
    public static JLabel setFrameTitulo(JLabel label) {
        try {
            label.setPreferredSize(new Dimension(146, 50));
            label.setFont(new Font("dialog", 1, 36));
        }
        catch(NullPointerException ex) {
            Logger.getLogger(SwingComponents.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return label;
    }
    
    public static JFrame setFrame(JFrame frame) {
        try {
            for (Component comp : frame.getContentPane().getComponents()) {
                if (comp.getName().equals("titulo")) {
                    SwingComponents.setFrameTitulo((JLabel) comp);
                }
                else if (comp.getName().equals("body")) {
                    SwingComponents.setPainelBody((JPanel) comp);
                }
            }
        }
        catch(NullPointerException ex) {
            Logger.getLogger(SwingComponents.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return frame;
    }
}
