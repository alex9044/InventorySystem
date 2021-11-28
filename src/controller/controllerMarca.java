/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.MarcaDAO;
import autocomplete.AutoCompleteDecorator;
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
import model.ModCombo;
import model.ModMarca;
import util.ModHeader;
import util.ModRowEstado;
import view.frmMenu;

/**
 *
 * @author ALEX PC
 */
public class controllerMarca implements ActionListener, MouseListener, KeyListener{
    
    MarcaDAO repositorio;
    ModMarca modeloMarca;
    frmMenu view;
    
    DefaultTableModel modelo = new DefaultTableModel();

    public controllerMarca(MarcaDAO repositorio, ModMarca modeloMarca, frmMenu view) {
        this.repositorio = repositorio;
        this.modeloMarca = modeloMarca;
        this.view = view;
        this.view.btnModificarMarca.addActionListener(this);
        this.view.btnNovoMarca.addActionListener(this);
        this.view.btnRegistrarMarca.addActionListener(this);
        this.view.TabelaMarca.addMouseListener(this);
        this.view.txtBuscarMarcas.addKeyListener(this);
        this.view.menuAtivarMarca.addActionListener(this);
        this.view.menuDesativarMarca.addActionListener(this);
        this.view.lblMarcas.addMouseListener(this);
        preecherTabela();
        ajustarTabelaMarca();
        completarComboBox();
        AutoCompleteDecorator.decorate(view.cbxMarca);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == view.btnRegistrarMarca){
            if(view.txtNomeMarca.getText().equals("")){
               JOptionPane.showMessageDialog(null, "Por favor, complete todos os campos");
            }else{
               modeloMarca.setId_marca(Integer.parseInt(view.txtidMarca.getText()));
               modeloMarca.setDescricao(view.txtNomeMarca.getText());
               if (repositorio.registrarMarca(modeloMarca)) {
                   limparTabela();
                   preecherTabela();
                   limparCampos();
                   limparComboBox();
                   completarComboBox();
                   JOptionPane.showMessageDialog(null, "Marca Cadastrada!");
               }else{
                   JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao registra Marca");
               }
            }
        }else if(e.getSource() == view.btnModificarMarca){
            if(view.txtidMarca.getText().equals("0")){
               JOptionPane.showMessageDialog(null, "Selecione um Marca");
            }else if(view.txtNomeMarca.getText().equals("")){
               JOptionPane.showMessageDialog(null, "Por favor, complete todos os campos");
            }else{
               modeloMarca.setId_marca(Integer.parseInt(view.txtidMarca.getText()));
               modeloMarca.setDescricao(view.txtNomeMarca.getText());
               if (repositorio.modificarMarca(modeloMarca)) {
                   limparTabela();
                   preecherTabela();
                   limparCampos();
                   limparComboBox();
                   completarComboBox();
                   JOptionPane.showMessageDialog(null, "Marca Modificado!");
                   view.btnRegistrarMarca.setEnabled(true);
               }else{
                   JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao modificar Marca");
               }
            }
        }else if(e.getSource() == view.menuDesativarMarca){
            if(view.txtidMarca.getText().equals("0")){
                JOptionPane.showMessageDialog(null, "Selecione um Marca para desativar"); 
            }else {
                int id = Integer.parseInt(view.txtidMarca.getText());
                if(repositorio.acao("Inativo", id)){
                    limparTabela();
                    preecherTabela();
                    limparCampos();
                    JOptionPane.showMessageDialog(null, "Marca desativada!");
                    view.btnRegistrarMarca.setEnabled(true);
                }else{
                    JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao desativar Marca");
                }
            }
        }else if(e.getSource() == view.menuAtivarMarca){
            if(view.txtidMarca.getText().equals("0")){
                JOptionPane.showMessageDialog(null, "Selecione um Marca para Ativar"); 
            }else {
                int id = Integer.parseInt(view.txtidMarca.getText());
                if(repositorio.acao("Ativo", id)){
                    limparTabela();
                    preecherTabela();
                    limparCampos();
                    JOptionPane.showMessageDialog(null, "Marca Ativo!");
                    view.btnRegistrarMarca.setEnabled(true);
                }else{
                    JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao ativar Marca");
                }
            }
        }else if(e.getSource() == view.btnNovoMarca){
            limparCampos();
            view.btnRegistrarMarca.setEnabled(true);
        }
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == view.TabelaMarca){
            int fila = view.TabelaMarca.rowAtPoint(e.getPoint());
            view.txtidMarca.setText(view.TabelaMarca.getValueAt(fila, 0).toString());
            view.txtNomeMarca.setText(view.TabelaMarca.getValueAt(fila, 1).toString());
            view.btnRegistrarMarca.setEnabled(false);
        }else if(e.getSource() == view.lblMarcas){
            view.TabbedPanePrincipal.setSelectedIndex(5);
            view.txtBuscarMarcas.requestFocus();
            limparTabela();
            preecherTabela();
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
        if (e.getSource() == view.txtBuscarMarcas) {
            limparTabela();
            preecherTabela();
        }
    }  
    private void ajustarTabelaMarca(){
        
        //Insere o header na tableMarcas
        JTableHeader header = view.TabelaMarca.getTableHeader();
        header.setDefaultRenderer(new ModHeader());
        view.TabelaMarca.setTableHeader(header);
        
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        ModRowEstado boxEstado = new ModRowEstado();
        
        view.TabelaMarca.getColumn(modelo.getColumnName(0)).setCellRenderer(centro);
        view.TabelaMarca.getColumn(modelo.getColumnName(2)).setCellRenderer(boxEstado);
        view.TabelaMarca.setModel(modelo);
    }
    
    public void preecherTabela(){
        ArrayList<ModMarca> list = repositorio.listarMarcas(view.txtBuscarMarcas.getText());
        modelo = (DefaultTableModel) view.TabelaMarca.getModel();
        Object[] objeto = new Object[7];
        for (int i = 0; i < list.size(); i++) {
            objeto[0] = returnID(list.get(i).getId_marca());  
            objeto[1] = list.get(i).getDescricao();
            objeto[2] = list.get(i).getEstado();
            modelo.addRow(objeto);
        }
        view.TabelaMarca.setModel(modelo);
    }
    
    public void limparTabela(){
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i-1;
        }
    }
    
    public void limparCampos(){
        view.txtidMarca.setText("0");
        view.txtNomeMarca.setText("");;
    }

    
    private void completarComboBox(){
        ArrayList<ModMarca> list = repositorio.listarMarcas(view.txtBuscarMarcas.getText());
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId_marca();
            String nome = list.get(i).getDescricao();
            view.cbxMarca.addItem(new ModCombo(id, nome,""));
        }
    }
    
    private void limparComboBox(){
        view.cbxMarca.removeAllItems();
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
