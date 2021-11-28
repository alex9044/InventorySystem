/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.CategoriaDAO;
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
import model.ModCategoria;
import model.ModCombo;
import util.ModHeader;
import util.ModRowEstado;
import view.frmMenu;

/**
 *
 * @author ALEX PC
 */
public class controllerCategoria implements ActionListener, MouseListener, KeyListener{
    
    CategoriaDAO repositorio;
    ModCategoria modeloCategoria;
    frmMenu view;
    
    DefaultTableModel modelo = new DefaultTableModel();

    public controllerCategoria(CategoriaDAO repositorio, ModCategoria modeloCategoria, frmMenu view) {
        this.repositorio = repositorio;
        this.modeloCategoria = modeloCategoria;
        this.view = view;
        this.view.btnRegistrarCategoria.addActionListener(this);
        this.view.TabelaCategoria.addMouseListener(this);
        this.view.btnModificarCategoria.addActionListener(this);
        this.view.btnNovoCategoria.addActionListener(this);
        this.view.menuAtivarCategoria.addActionListener(this);
        this.view.menuEliminarCategoria.addActionListener(this);
        this.view.txtBuscarCategoria.addKeyListener(this);
        this.view.lblCategorias.addMouseListener(this);
        this.view.cbxCategoria.addMouseListener(this);
        ajustarTabelaCategoria();
        completarComboBox();
        AutoCompleteDecorator.decorate(view.cbxCategoria);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == view.btnRegistrarCategoria) {
            if(view.txtNomeCategoria.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Complete todos os campos");
            }else{
                modeloCategoria.setId_categoria(Integer.parseInt(view.txtidCategoria.getText()));
                modeloCategoria.setDescricao(view.txtNomeCategoria.getText().toString());
                if (repositorio.registrarCategoria(modeloCategoria)) {
                    JOptionPane.showMessageDialog(null, "Categoria Registrada!");
                    limparTabela();
                    preecherTabela();
                    limparCampos();
                    limparComboBox();
                    completarComboBox();
                }else{
                    JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao registrar Categoria");
                }
            }
        }else if(e.getSource() == view.btnModificarCategoria){
            if(view.txtidCategoria.getText().equals("0")){
               JOptionPane.showMessageDialog(null, "Selecione um Categoria");
            }else if(view.txtNomeCategoria.getText().equals("")){
               JOptionPane.showMessageDialog(null, "Por favor, complete todos os campos");
            }else{
               modeloCategoria.setId_categoria(Integer.parseInt(view.txtidCategoria.getText()));
               modeloCategoria.setDescricao(view.txtNomeCategoria.getText());
              
               if (repositorio.modificarCategoria(modeloCategoria)) {
                   limparTabela();
                   preecherTabela();
                   limparCampos();
                   limparComboBox();
                   completarComboBox();
                   JOptionPane.showMessageDialog(null, "Categoria Modificada!");
                   view.btnRegistrarCategoria.setEnabled(true);
               }else{
                   JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao modificar Categoria");
               }
            }
        }else if(e.getSource() == view.menuEliminarCategoria){
            if(view.txtidCategoria.getText().equals("0")){
                JOptionPane.showMessageDialog(null, "Selecione uma Categoria para desativar"); 
            }else {
                int id = Integer.parseInt(view.txtidCategoria.getText());
                if(repositorio.acao("Inativo", id)){
                    limparTabela();
                    preecherTabela();
                    limparCampos();
                    JOptionPane.showMessageDialog(null, "Categoria desativada!");
                    view.btnRegistrarCategoria.setEnabled(true);
                }else{
                    JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao desativar Categoria");
                }
            }
        }else if(e.getSource() == view.menuAtivarCategoria){
            if(view.txtidCategoria.getText().equals("0")){
                JOptionPane.showMessageDialog(null, "Selecione uma categoria para Ativar"); 
            }else {
                int id = Integer.parseInt(view.txtidCategoria.getText());
                if(repositorio.acao("Ativo", id)){
                    limparTabela();
                    preecherTabela();
                    limparCampos();
                    JOptionPane.showMessageDialog(null, "Categoria Ativo!");
                    view.btnRegistrarCategoria.setEnabled(true);
                }else{
                    JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao ativar Categoria");
                }
            }
        }else if(e.getSource() == view.btnNovoCategoria){
            view.btnRegistrarCategoria.setEnabled(true);
            limparCampos();
        }
    }
    
    

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == view.TabelaCategoria){
            int fila = view.TabelaCategoria.rowAtPoint(e.getPoint());
            view.txtidCategoria.setText(view.TabelaCategoria.getValueAt(fila, 0).toString());
            view.txtNomeCategoria.setText(view.TabelaCategoria.getValueAt(fila, 1).toString());
            view.btnRegistrarCategoria.setEnabled(false);
        }else if(e.getSource() == view.lblCategorias){
            view.TabbedPanePrincipal.setSelectedIndex(4);
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
        if (e.getSource() == view.txtBuscarCategoria) {
            limparTabela();
            preecherTabela();
        }
    }
    
    private void ajustarTabelaCategoria(){
        
        //Insere o header na tableCategorias
        JTableHeader header = view.TabelaCategoria.getTableHeader();
        header.setDefaultRenderer(new ModHeader());
        view.TabelaCategoria.setTableHeader(header);
        
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        ModRowEstado boxEstado = new ModRowEstado();
        
        preecherTabela();
        view.TabelaCategoria.getColumn(modelo.getColumnName(0)).setCellRenderer(centro);
        view.TabelaCategoria.getColumn(modelo.getColumnName(2)).setCellRenderer(boxEstado);
        view.TabelaCategoria.setModel(modelo);
    }
    
    private void preecherTabela(){
        ArrayList<ModCategoria> list = repositorio.listarCategorias(view.txtBuscarCategoria.getText());
        modelo = (DefaultTableModel) view.TabelaCategoria.getModel();
        Object[] objeto = new Object[3];
        for (int i = 0; i < list.size(); i++) {
            objeto[0] = returnID(list.get(i).getId_categoria());
            objeto[1] = list.get(i).getDescricao();
            objeto[2] = list.get(i).getEstado();
            modelo.addRow(objeto);
        }
        view.TabelaCategoria.setModel(modelo);
    }
    
    private void limparTabela(){
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i-1;
        }
    }
    
    private void limparCampos(){
        view.txtidCategoria.setText("0");
        view.txtNomeCategoria.setText("");
    }
    
    private void completarComboBox(){
        ArrayList<ModCategoria> list = repositorio.listarCategorias(view.txtBuscarCategoria.getText());
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId_categoria();
            String nome = list.get(i).getDescricao();
            view.cbxCategoria.addItem(new ModCombo(id, nome, ""));
        }
    }
    
    private void limparComboBox(){
        view.cbxCategoria.removeAllItems();
    }
    
    private String returnID(int number){
        String numero = "";
        int length = String.valueOf(number).length();
        if(length == 1){
            numero = "00"+number;
        }
        if(length == 2){
            numero = "0"+number;
        }
        if(length == 3){
            numero = ""+number;
        }
        return numero;
    }
}
