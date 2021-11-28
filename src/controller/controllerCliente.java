/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.ClienteDAO;
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
import model.ModCliente;
import model.ModCombo;
import util.ModHeader;
import util.ModRowEstado;
import view.frmMenu;

/**
 *
 * @author ALEX PC
 */
public class controllerCliente implements ActionListener, MouseListener , KeyListener{
    
    private ModCliente modeloCliente;
    private ClienteDAO repositorio;
    private frmMenu view;
    
    DefaultTableModel modelo =  new DefaultTableModel();
    
    public controllerCliente(ModCliente modeloCliente, ClienteDAO repositorio, frmMenu view) {
        this.modeloCliente = modeloCliente;
        this.repositorio = repositorio;
        this.view = view;
        this.view.btnRegistrarCliente.addActionListener(this);
        this.view.btnModificarCliente.addActionListener(this);
        this.view.menuEliminarCliente.addActionListener(this);
        this.view.menuAtivarCliente.addActionListener(this);
        this.view.TabelaClientes.addMouseListener(this);
        this.view.btnNovoCliente.addActionListener(this);
        this.view.txtBuscarCliente.addKeyListener(this);
        this.view.lblClientes.addMouseListener(this);
        preecherTabela();
        ajustarTabelaCliente();
        completarComboBox();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == view.btnRegistrarCliente){
            if(view.txtNomeCliente.getText().equals("") 
               || view.txtEnderecoCliente.getText().equals("")
               || view.txtTelefoneCliente.getText().equals("")){
               JOptionPane.showMessageDialog(null, "Por favor, complete todos os campos");
            }else{
               modeloCliente.setId_cliente(Integer.parseInt(view.txtidCliente.getText()));
               modeloCliente.setNome(view.txtNomeCliente.getText());
               modeloCliente.setCi(view.txtCiCliente.getText());
               modeloCliente.setTelefone(view.txtTelefoneCliente.getText());
               modeloCliente.setEndereco(view.txtEnderecoCliente.getText());
               modeloCliente.setEmail(view.txtEmailCliente.getText());
               if (repositorio.registrarCliente(modeloCliente)) {
                   limparTabela();
                   preecherTabela();
                   limparComboBox();
                   completarComboBox();
                   limparCampos();
                   JOptionPane.showMessageDialog(null, "Cliente Cadastrado!");
               }else{
                   JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao registra Cliente");
               }
            }
        }else if(e.getSource() == view.btnModificarCliente){
            if(view.txtidCliente.getText().equals("0")){
               JOptionPane.showMessageDialog(null, "Selecione um Cliente");
            }else if(view.txtNomeCliente.getText().equals("") 
               || view.txtEnderecoCliente.getText().equals("")
               || view.txtTelefoneCliente.getText().equals("")){
               JOptionPane.showMessageDialog(null, "Por favor, complete todos os campos");
            }else{
               modeloCliente.setId_cliente(Integer.parseInt(view.txtidCliente.getText()));
               modeloCliente.setNome(view.txtNomeCliente.getText());
               modeloCliente.setCi(view.txtCiCliente.getText());
               modeloCliente.setTelefone(view.txtTelefoneCliente.getText());
               modeloCliente.setEndereco(view.txtEnderecoCliente.getText());
               modeloCliente.setEmail(view.txtEmailCliente.getText());
               if (repositorio.modificarCliente(modeloCliente)) {
                   limparTabela();
                   preecherTabela();
                   limparComboBox();
                   completarComboBox();
                   limparCampos();
                   JOptionPane.showMessageDialog(null, "Cliente Modificado!");
                   view.btnRegistrarCliente.setEnabled(true);
               }else{
                   JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao modificar Cliente");
               }
            }
        }else if(e.getSource() == view.menuEliminarCliente){
            if(view.txtidCliente.getText().equals("0")){
                JOptionPane.showMessageDialog(null, "Selecione um Cliente para desativar"); 
            }else {
                int id = Integer.parseInt(view.txtidCliente.getText());
                if(repositorio.acao("Inativo", id)){
                    limparTabela();
                    preecherTabela();
                    limparComboBox();
                    completarComboBox();
                    limparCampos();
                    JOptionPane.showMessageDialog(null, "Cliente desativado!");
                    view.btnRegistrarCliente.setEnabled(true);
                }else{
                    JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao desativar Cliente");
                }
            }
        }else if(e.getSource() == view.menuAtivarCliente){
            if(view.txtidCliente.getText().equals("0")){
                JOptionPane.showMessageDialog(null, "Selecione um Usuario para Ativar"); 
            }else {
                int id = Integer.parseInt(view.txtidCliente.getText());
                if(repositorio.acao("Ativo", id)){
                    limparTabela();
                    preecherTabela();
                    limparComboBox();
                    completarComboBox();
                    limparCampos();
                    JOptionPane.showMessageDialog(null, "Cliente Ativo!");
                    view.btnRegistrarCliente.setEnabled(true);
                }else{
                    JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao ativar Cliente");
                }
            }
        }else{
            limparCampos();
            view.btnRegistrarCliente.setEnabled(true);
        }    
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == view.TabelaClientes){
            int fila = view.TabelaClientes.rowAtPoint(e.getPoint());
            view.txtidCliente.setText(view.TabelaClientes.getValueAt(fila, 0).toString());
            view.txtNomeCliente.setText(view.TabelaClientes.getValueAt(fila, 1).toString());
            view.txtEnderecoCliente.setText(view.TabelaClientes.getValueAt(fila, 2).toString());
            view.txtTelefoneCliente.setText(view.TabelaClientes.getValueAt(fila, 3).toString());
            view.txtCiCliente.setText(view.TabelaClientes.getValueAt(fila, 4).toString());
            view.txtEmailCliente.setText(view.TabelaClientes.getValueAt(fila, 5).toString());
            view.btnRegistrarCliente.setEnabled(false);
        }else if(e.getSource() == view.lblClientes){
            view.TabbedPanePrincipal.setSelectedIndex(1);
            view.txtBuscarCliente.requestFocus();
            limparTabela();
            preecherTabela();
            limparComboBox();
            completarComboBox();
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
        if (e.getSource() == view.txtBuscarCliente) {
            limparTabela();
            preecherTabela();
        }
    }
    private void ajustarTabelaCliente(){
        //insere o header da tabelaClientes
        JTableHeader header = view.TabelaClientes.getTableHeader();
        header.setDefaultRenderer(new ModHeader());
        view.TabelaClientes.setTableHeader(header);
        
        DefaultTableCellRenderer cellRendere = new DefaultTableCellRenderer();
        cellRendere.setHorizontalAlignment(SwingConstants.CENTER);
        ModRowEstado boxEstado = new ModRowEstado();
        
        view.TabelaClientes.getColumn(modelo.getColumnName(0)).setCellRenderer(cellRendere);
        view.TabelaClientes.getColumn(modelo.getColumnName(3)).setCellRenderer(cellRendere);
        view.TabelaClientes.getColumn(modelo.getColumnName(4)).setCellRenderer(cellRendere);
        view.TabelaClientes.getColumn(modelo.getColumnName(6)).setCellRenderer(boxEstado);
        view.TabelaClientes.getColumn(modelo.getColumnName(1)).setPreferredWidth(200);
        view.TabelaClientes.getColumn(modelo.getColumnName(2)).setPreferredWidth(200);
        view.TabelaClientes.getColumn(modelo.getColumnName(5)).setPreferredWidth(180);
        view.TabelaClientes.getColumn(modelo.getColumnName(4)).setPreferredWidth(100);
        view.TabelaClientes.setModel(modelo);
    }
    
    private void preecherTabela(){
        ArrayList<ModCliente> list = repositorio.listarClientes(view.txtBuscarCliente.getText());
        modelo = (DefaultTableModel) view.TabelaClientes.getModel();
        Object[] objeto = new Object[7];
        for (int i = 0; i < list.size(); i++) {
            objeto[0] = returnID(list.get(i).getId_cliente());
            objeto[1] = list.get(i).getNome();
            objeto[2] = list.get(i).getEndereco();
            objeto[3] = list.get(i).getTelefone(); 
            objeto[4] = list.get(i).getCi();
            objeto[5] = list.get(i).getEmail();
            objeto[6] = list.get(i).getEstado();
            modelo.addRow(objeto);
        }
        view.TabelaClientes.setModel(modelo);
    }
    
    private void limparTabela(){
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i-1;
        }
    }
    
    private void limparCampos(){
        view.txtidCliente.setText("0");
        view.txtCiCliente.setText("");;
        view.txtEnderecoCliente.setText("");
        view.txtTelefoneCliente.setText("");
        view.txtNomeCliente.setText("");
        view.txtEmailCliente.setText("");
    }
    
    private void completarComboBox(){
        ArrayList<ModCliente> list = repositorio.listarClientes(view.txtBuscarCliente.getText());
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId_cliente();
            String nome = list.get(i).getNome();
            String ruc = list.get(i).getCi();
            view.cbxClienteNV.addItem(new ModCombo(id,nome,ruc));
            view.cbxClienteConv.addItem(new ModCombo(id,nome,ruc));
        }
    }
    
    private void limparComboBox(){
        view.cbxClienteNV.removeAllItems();
        view.cbxClienteConv.removeAllItems();
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
