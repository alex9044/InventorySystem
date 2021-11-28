package controller;

import DAO.FornecedorDAO;
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
import model.ModFornecedor;
import util.ModHeader;
import util.ModRowEstado;
import view.frmMenu;

/**
 *
 * @author ALEX PC
 */
public class controllerFornecedor implements ActionListener, MouseListener , KeyListener{
    
    private ModFornecedor modeloFornecedor;
    private FornecedorDAO repositorio;
    private frmMenu view;
    
    DefaultTableModel modelo =  new DefaultTableModel();
    

    public controllerFornecedor(ModFornecedor modeloFornecedor, FornecedorDAO repositorio, frmMenu view) {
        this.modeloFornecedor = modeloFornecedor;
        this.repositorio = repositorio;
        this.view = view;
        this.view.btnModificarFornecedor.addActionListener(this);
        this.view.btnRegistrarFornecedor.addActionListener(this);
        this.view.btnNovoFornecedor.addActionListener(this);
        this.view.TabelaFornecedor.addMouseListener(this);
        this.view.menuAtivarFornecedor.addActionListener(this);
        this.view.menuEliminarFornecedor.addActionListener(this);
        this.view.lblFornecedores.addMouseListener(this);
        this.view.txtBuscarFornecedor.addKeyListener(this);
        preencherTabela();
        ajustarTabelaFornecedor();
        completarComboBox();
        AutoCompleteDecorator.decorate(view.cbxFornecedor);
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == view.btnRegistrarFornecedor){
            if(view.txtNomeFornecedor.getText().equals("") 
               || view.txtEnderecoFornecedor.getText().equals("")
               || view.txtTelefoneFornecedor.getText().equals("")){
               JOptionPane.showMessageDialog(null, "Por favor, complete todos os campos");
            }else{
               modeloFornecedor.setId_fornecedor(Integer.parseInt(view.txtidFornecedor.getText()));
               modeloFornecedor.setNome(view.txtNomeFornecedor.getText());
               modeloFornecedor.setRuc(view.txtRuc.getText());
               modeloFornecedor.setTelefone(view.txtTelefoneFornecedor.getText());
               modeloFornecedor.setEndereco(view.txtEnderecoFornecedor.getText());
               if (repositorio.registrarFornecedor(modeloFornecedor)) {
                   limparTabela();
                   preencherTabela();
                   limparCampos();
                   limparComboBox();
                   completarComboBox();
                   JOptionPane.showMessageDialog(null, "Fornecedor Cadastrado!");
               }else{
                   JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao registra Fornecedor");
               }
            }
        }else if(e.getSource() == view.btnModificarFornecedor){
            if(view.txtidFornecedor.getText().equals("0")){
               JOptionPane.showMessageDialog(null, "Selecione um Fornecedor");
            }else if(view.txtNomeFornecedor.getText().equals("") 
               || view.txtEnderecoFornecedor.getText().equals("")
               || view.txtTelefoneFornecedor.getText().equals("")){
               JOptionPane.showMessageDialog(null, "Por favor, complete todos os campos");
            }else{
               modeloFornecedor.setId_fornecedor(Integer.parseInt(view.txtidFornecedor.getText()));
               modeloFornecedor.setNome(view.txtNomeFornecedor.getText());
               modeloFornecedor.setRuc(view.txtRuc.getText());
               modeloFornecedor.setTelefone(view.txtTelefoneFornecedor.getText());
               modeloFornecedor.setEndereco(view.txtEnderecoFornecedor.getText());
               if (repositorio.modificarFornecedor(modeloFornecedor)) {
                   limparTabela();
                   preencherTabela();
                   limparCampos();
                   limparComboBox();
                   completarComboBox();
                   JOptionPane.showMessageDialog(null, "Fornecedor Modificado!");
                   view.btnRegistrarFornecedor.setEnabled(true);
               }else{
                   JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao modificar Fornecedor");
               }
            }
        }else if(e.getSource() == view.menuEliminarFornecedor){
            if(view.txtidFornecedor.getText().equals("0")){
                JOptionPane.showMessageDialog(null, "Selecione um Fornecedor para desativar"); 
            }else {
                int id = Integer.parseInt(view.txtidFornecedor.getText());
                if(repositorio.acao("Inativo", id)){
                    limparTabela();
                    preencherTabela();
                    limparCampos();
                    JOptionPane.showMessageDialog(null, "Fornecedor desativado!");
                    view.btnRegistrarFornecedor.setEnabled(true);
                }else{
                    JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao desativar Fornecedor");
                }
            }
        }else if(e.getSource() == view.menuAtivarFornecedor){
            if(view.txtidFornecedor.getText().equals("0")){
                JOptionPane.showMessageDialog(null, "Selecione um Fornecedor para Ativar"); 
            }else {
                int id = Integer.parseInt(view.txtidFornecedor.getText());
                if(repositorio.acao("Ativo", id)){
                    limparTabela();
                    preencherTabela();
                    limparCampos();
                    JOptionPane.showMessageDialog(null, "Fornecedor Ativo!");
                    view.btnRegistrarFornecedor.setEnabled(true);
                }else{
                    JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao ativar Fornecedor");
                }
            }
        }else{
            limparCampos();
            view.btnRegistrarFornecedor.setEnabled(true);
        }    
    }
    
    

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == view.TabelaFornecedor){
            int fila = view.TabelaFornecedor.rowAtPoint(e.getPoint());
            view.txtidFornecedor.setText(view.TabelaFornecedor.getValueAt(fila, 0).toString());
            view.txtNomeFornecedor.setText(view.TabelaFornecedor.getValueAt(fila, 1).toString());
            view.txtTelefoneFornecedor.setText(view.TabelaFornecedor.getValueAt(fila, 2).toString());
            view.txtRuc.setText(view.TabelaFornecedor.getValueAt(fila, 3).toString());
            view.txtEnderecoFornecedor.setText(view.TabelaFornecedor.getValueAt(fila, 4).toString());
            view.btnRegistrarFornecedor.setEnabled(false);
        }else if(e.getSource() == view.lblFornecedores){
            view.TabbedPanePrincipal.setSelectedIndex(2);
            view.txtBuscarFornecedor.requestFocus();
            limparTabela();
            preencherTabela();
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
        if (e.getSource() == view.txtBuscarFornecedor) {
            limparTabela();
            preencherTabela();
        }
    }
    
    private void limparComboBox(){
        view.cbxFornecedor.removeAllItems();
        view.cbxFornecedorNC.removeAllItems();
    }
    
    private void completarComboBox(){
        ArrayList<ModFornecedor> list = repositorio.listarFornecedores(view.txtBuscarFornecedor.getText());
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId_fornecedor();
            String nome = list.get(i).getNome();
            view.cbxFornecedor.addItem(new ModCombo(id, nome,""));
            view.cbxFornecedorNC.addItem(new ModCombo(id, nome,""));
        }
    }
    
    public void ajustarTabelaFornecedor(){
        
        //Insere o header TabelaFornecedor
        JTableHeader header = view.TabelaFornecedor.getTableHeader();
        header.setDefaultRenderer(new ModHeader());
        view.TabelaFornecedor.setTableHeader(header);
        
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        ModRowEstado boxEstado = new ModRowEstado();
        
        
        view.TabelaFornecedor.getColumn(modelo.getColumnName(0)).setCellRenderer(centro);
        view.TabelaFornecedor.getColumn(modelo.getColumnName(5)).setCellRenderer(boxEstado);
        view.TabelaFornecedor.getColumn(modelo.getColumnName(1)).setPreferredWidth(350);
        view.TabelaFornecedor.getColumn(modelo.getColumnName(4)).setPreferredWidth(300);
        view.TabelaFornecedor.setModel(modelo);
    }
    
    public void preencherTabela(){
        
        ArrayList<ModFornecedor> list = repositorio.listarFornecedores(view.txtBuscarFornecedor.getText());
        modelo = (DefaultTableModel) view.TabelaFornecedor.getModel();
        Object[] objeto = new Object[7];
        for (int i = 0; i < list.size(); i++) {
            objeto[0] = returnID(list.get(i).getId_fornecedor());
            objeto[1] = list.get(i).getNome();
            objeto[2] = list.get(i).getTelefone();
            objeto[3] = list.get(i).getRuc();
            objeto[4] = list.get(i).getEndereco();
            objeto[5] = list.get(i).getEstado();
            modelo.addRow(objeto);
        }
        view.TabelaFornecedor.setModel(modelo);
    }
    
    public void limparTabela(){
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i-1;
        }
    }
    
    public void limparCampos(){
        view.txtidFornecedor.setText("0");
        view.txtRuc.setText("");;
        view.txtEnderecoFornecedor.setText("");
        view.txtTelefoneFornecedor.setText("");
        view.txtNomeFornecedor.setText("");
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
