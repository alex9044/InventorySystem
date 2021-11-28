/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.UsuarioDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.ModUsuario;
import util.ModHeader;
import util.ModRowEstado;
import view.frmMenu;

/**
 *
 * @author ALEX PC
 */
public class controllerUsuario implements ActionListener, MouseListener, KeyListener{

    private ModUsuario us;
    private UsuarioDAO usDAO;
    private frmMenu view;
    
    //Completa a TabelaUsuarios
    DefaultTableModel modelo = new DefaultTableModel();

    public controllerUsuario(ModUsuario us, UsuarioDAO usDAO, frmMenu view) {
        this.us = us;
        this.usDAO = usDAO;
        this.view = view;
        this.view.btnRegistrarUsuario.addActionListener(this);
        this.view.btnModificarUsuario.addActionListener(this);
        this.view.menuEliminarUsuario.addActionListener(this);
        this.view.menuAtivarUsuario.addActionListener(this);
        this.view.txtBuscarUsuario.addKeyListener(this);
        this.view.TabelaUsuario.addMouseListener(this);
        this.view.btnNovoUsuario.addActionListener(this);
        this.view.lblUsuarios.addMouseListener(this);
        ajustarTabelaUsuario();
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.btnRegistrarUsuario){
            if(view.txtUsuario.getText().equals("") 
                || view.txtNomeUsuario.getText().equals("")
                || String.valueOf(view.txtSenhaUsuario.getPassword()).equals("")){
                JOptionPane.showMessageDialog(null, "Por favor, complete todos os campos");
            }else{
                us.setUsuario(view.txtUsuario.getText());
                us.setNome(view.txtNomeUsuario.getText());
                us.setSenha(String.valueOf(view.txtSenhaUsuario.getPassword()));
                us.setCaixa(view.cbxCaixaUsuario.getSelectedItem().toString());
                us.setCargo(view.cbxCargoUsuario.getSelectedItem().toString());
                if(usDAO.insertarUsuario(us) == true){
                    limparTabela();
                    preecherTabelaUsuario();
                    limparCampos();
                    JOptionPane.showMessageDialog(null, "Usuario cadastrado!");
                }else{
                    JOptionPane.showConfirmDialog(null, "ERRO: Ocorreu um erro ao registrar usuario");
                }
            }
        }else if(e.getSource() == view.btnModificarUsuario){
            if(view.txtUsuario.getText().equals("") 
                || view.txtNomeUsuario.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Por favor, complete todos os campos");
            }else{
                us.setId_usuario(Integer.parseInt(view.txtidUsuario.getText()));
                us.setUsuario(view.txtUsuario.getText());
                us.setNome(view.txtNomeUsuario.getText());
                us.setCaixa(view.cbxCaixaUsuario.getSelectedItem().toString());
                us.setCargo(view.cbxCargoUsuario.getSelectedItem().toString());
                if(usDAO.modificarUsuario(us) == true){
                    limparTabela();
                    preecherTabelaUsuario();
                    limparCampos();
                    JOptionPane.showMessageDialog(null, "Usuario Modificado!");
                    view.btnRegistrarUsuario.setEnabled(true);
                }else{
                    JOptionPane.showConfirmDialog(null, "ERRO: Ocorreu um erro ao modificar usuario");
                }
            }
        }else if(e.getSource() == view.menuEliminarUsuario){
            if(view.txtidUsuario.equals("")){
                JOptionPane.showMessageDialog(null, "Selecione um Usuario para desativar"); 
            }else {
                int id = Integer.parseInt(view.txtidUsuario.getText());
                if(usDAO.acao("Inativo", id)){
                    limparTabela();
                    preecherTabelaUsuario();
                    limparCampos();
                    JOptionPane.showMessageDialog(null, "Usuario desativado!");
                }else{
                    JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao desativar usuario");
                }
            }
        }else if(e.getSource() == view.menuAtivarUsuario){
            if(view.txtidUsuario.equals("")){
                JOptionPane.showMessageDialog(null, "Selecione um Usuario para Ativar"); 
            }else {
                int id = Integer.parseInt(view.txtidUsuario.getText());
                if(usDAO.acao("Ativo", id)){
                    limparTabela();
                    preecherTabelaUsuario();
                    limparCampos();
                    JOptionPane.showMessageDialog(null, "Usuario Ativo!");
                }else{
                    JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao ativar usuario");
                }
            }
        }else if(e.getSource() == view.btnNovoUsuario){
            view.btnRegistrarUsuario.setEnabled(true);
            limparCampos();
        }    
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == view.TabelaUsuario){
            int fila = view.TabelaUsuario.rowAtPoint(e.getPoint());
            view.txtidUsuario.setText(view.TabelaUsuario.getValueAt(fila, 0).toString());
            view.txtUsuario.setText(view.TabelaUsuario.getValueAt(fila, 1).toString());
            view.txtNomeUsuario.setText(view.TabelaUsuario.getValueAt(fila, 2).toString());
            view.cbxCaixaUsuario.setSelectedItem(view.TabelaUsuario.getValueAt(fila, 4).toString());
            view.cbxCargoUsuario.setSelectedItem(view.TabelaUsuario.getValueAt(fila, 3).toString());
            view.txtSenhaUsuario.setEnabled(false);
            view.btnRegistrarUsuario.setEnabled(false);
        }else if(e.getSource() == view.lblUsuarios){
            view.TabbedPanePrincipal.setSelectedIndex(3);
            view.txtBuscarUsuario.requestFocus();
            limparTabela();
            preecherTabelaUsuario();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == view.txtBuscarUsuario) {
            limparTabela();
            preecherTabelaUsuario();
        }
    } 
    
    private void ajustarTabelaUsuario(){
        //Insere o header da TabelaUsuario
        JTableHeader header = view.TabelaUsuario.getTableHeader();
        header.setDefaultRenderer(new ModHeader());
        view.TabelaUsuario.setTableHeader(header);
        
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        ModRowEstado boxEstado = new ModRowEstado();
        
        preecherTabelaUsuario();
        view.TabelaUsuario.getColumn(modelo.getColumnName(0)).setCellRenderer(centro);
        view.TabelaUsuario.getColumn(modelo.getColumnName(5)).setCellRenderer(boxEstado);
        view.TabelaUsuario.getColumn(modelo.getColumnName(4)).setCellRenderer(centro);
        view.TabelaUsuario.getColumn(modelo.getColumnName(2)).setPreferredWidth(300);
        view.TabelaUsuario.setModel(modelo);
    }
    
    public void preecherTabelaUsuario(){
        ArrayList<ModUsuario> list = usDAO.listarUsuarios(view.txtBuscarUsuario.getText());
        modelo = (DefaultTableModel) view.TabelaUsuario.getModel();
        Object[] objeto = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            objeto[0] = returnID(list.get(i).getId_usuario());
            objeto[1] = list.get(i).getUsuario();
            objeto[2] = list.get(i).getNome();
            objeto[3] = list.get(i).getCargo();
            objeto[4] = list.get(i).getCaixa();
            objeto[5] = list.get(i).getEstado ();
            modelo.addRow(objeto);
        }
        view.TabelaUsuario.setModel(modelo);
    }
    
    public void limparTabela(){
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i-1;
        }
    }
    
    public void limparCampos(){
        view.txtNomeUsuario.setText("");
        view.txtidUsuario.setText("0");
        view.txtUsuario.setText("");
    }
    
    private String returnID(int number){
        String zero = "0";
        String numero = "";
        int length = String.valueOf(number).length();
        if(length == 1){
                numero = zero+zero+number;
        }
        if(length == 2){
                numero = zero+number;
        }
        if(length == 3){
                numero = ""+number;
        }
        return numero;
    }

}
