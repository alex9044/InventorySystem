/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.UsuarioDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.ModUsuario;
import view.frmMenu;
import view.frmLogin;

/**
 *
 * @author ALEX PC
 */
public class controllerLogin implements ActionListener{
    
    private ModUsuario us;
    private UsuarioDAO usDAO;
    private frmLogin view;

    public controllerLogin(ModUsuario us, UsuarioDAO usDAO, frmLogin view) {
        this.us = us;
        this.usDAO = usDAO;
        this.view = view;
        this.view.btnLogin.addActionListener(this);
        this.view.btnCancelar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.btnLogin){
            if (view.txtUsuario.getText().equals("") || String.valueOf(view.txtPassword.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "Complete todos os campos");
            }else{
                String usuario = view.txtUsuario.getText();
                String senha = String.valueOf(view.txtPassword.getPassword());
                us = usDAO.login(usuario, senha);
                if(us.getUsuario() != null){
                    frmMenu menu = new frmMenu();
                    menu.setVisible(true);
                    this.view.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Usuario o senha incorretos");
                }
            }
        }else{
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja fechar a aplicação?", "Inventory System", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(resposta == 0 ){
                System.exit(0);
            }
        }
    }
}
