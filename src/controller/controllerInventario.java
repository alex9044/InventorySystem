package controller;

import DAO.InventarioDAO;
import DAO.ProdutoDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.ModInventario;
import model.ModProduto;
import util.ModHeader;
import util.Util;
import view.frmMenu;

/**
 *
 * @author ALEX PC
 */
public class controllerInventario implements ActionListener, KeyListener, MouseListener {
    
    private Util utilidades;
    private ModInventario modeloInventario;
    private ProdutoDAO repositorioProduto;
    private InventarioDAO repositorio;
    private frmMenu view;
    
    DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
    DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
    DefaultTableModel tabelaInventario;
    
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    
    public controllerInventario(ModInventario modeloInventario, InventarioDAO repositorio,ProdutoDAO repositorioProduto, Util util,frmMenu view) {
        this.modeloInventario = modeloInventario;
        this.repositorio = repositorio;
        this.repositorioProduto = repositorioProduto;
        this.utilidades = util;
        this.view = view;
        this.view.btnModificarInventario.addActionListener(this);
        this.view.txtStock_atual_inventario.addActionListener(this);
        this.view.txtidInventario.addKeyListener(this);
        this.view.lblInventario.addMouseListener(this);
        this.view.txtInventario.addKeyListener(this);
        this.view.txtidInventario.addMouseListener(this);
        this.view.menuEliminarInventario.addActionListener(this);
        preencherTabelaInventario();
        ajustarTabelaInventario();
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.btnModificarInventario){
            if(view.txtidInventario.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Insira ID do produto!");
            }else if(Integer.parseInt(view.txtidInventario.getText())<= 0){
                JOptionPane.showMessageDialog(null, "Insira ID do produto!");
            }else if(view.txtInventario.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Complete o campo Em Inventario!");
            }else if(view.txtMotivo.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Complete o campo Motivo!");
            }else{
                int id_produto = Integer.parseInt(view.txtidInventario.getText());
                int  novo_stock = Integer.parseInt(view.txtInventario.getText());
                int stock_atual = Integer.parseInt(view.txtStock_atual_inventario.getText());
                String motivo = view.txtMotivo.getText();
                float custo = utilidades.convertirFloat(view.txtCusto_Inventario.getText());
                if( novo_stock == 0){
                    JOptionPane.showMessageDialog(null, "Por favor insira uma quantidade maior a 0");
                }else if(repositorio.modificarStock(id_produto,  novo_stock) == true){
                    repositorio.processarInventario(id_produto, stock_atual, motivo, novo_stock, custo);
                    JOptionPane.showMessageDialog(null, "Inventario Realizado com sucesso!");
                    limparTabelaInventario();
                    preencherTabelaInventario();
                    limparCampos();
                    view.txtProdutoInvetario.setEnabled(true);
                    view.txtStock_atual_inventario.setEnabled(true);
                    view.txtCusto_Inventario.setEnabled(true);
                }
            }
        }else if(e.getSource() == view.menuEliminarInventario){
            int id_inventario = Integer.parseInt(view.TabelaInventarios.getValueAt(view.TabelaInventarios.getSelectedRow(), 0).toString());
            if (repositorio.deletarInventario(id_inventario)) {
                JOptionPane.showMessageDialog(null, "Registro eliminado!");
                limparTabelaInventario();
                preencherTabelaInventario();
            }
        }
        
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getSource() == view.txtidInventario){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                ModProduto produtoModelo = new ModProduto();
                produtoModelo = repositorioProduto.verProduto(Integer.parseInt(view.txtidInventario.getText()));
                view.txtProdutoInvetario.setText(produtoModelo.getDescricao());
                view.txtProdutoInvetario.setEnabled(false);
                view.txtStock_atual_inventario.setText(String.valueOf(produtoModelo.getStock()));
                view.txtStock_atual_inventario.setEnabled(false);
                view.txtCusto_Inventario.setText(utilidades.formatoCosto((float) produtoModelo.getCusto_compra()));
                view.txtCusto_Inventario.setEnabled(false);
                view.txtInventario.requestFocus();
                view.txtidInventario.setText(returnID(Integer.parseInt(view.txtidInventario.getText())));
            }
        }else if(e.getSource() == view.txtInventario){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                if(view.txtInventario.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Complete o campo Em inventario!");
                    view.txtInventario.requestFocus();
                }else{
                    view.txtMotivo.requestFocus();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == view.lblInventario){
            view.txtidInventario.setText("");
            view.txtidInventario.requestFocus();
            view.TabbedPanePrincipal.setSelectedIndex(11);
            limparTabelaInventario();
            preencherTabelaInventario();
            limparCampos();
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
    
    
    private void limparTabelaInventario(){
        for (int i = 0; i < tabelaInventario.getRowCount(); i++) {
            tabelaInventario.removeRow(i);
            i = i-1;
        }
    }

    private void limparCampos(){
        view.txtidInventario.setText("");
        view.txtProdutoInvetario.setText("");
        view.txtStock_atual_inventario.setText("");
        view.txtCusto_Inventario.setText("");
        view.txtInventario.setText("");
        view.txtMotivo.setText("");
    }
    
    
    private void ajustarTabelaInventario(){
        
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        
        view.TabelaInventarios.getColumn(tabelaInventario.getColumnName(0)).setCellRenderer(centro);
        view.TabelaInventarios.getColumn(tabelaInventario.getColumnName(3)).setCellRenderer(centro);
        view.TabelaInventarios.getColumn(tabelaInventario.getColumnName(4)).setCellRenderer(centro);
        view.TabelaInventarios.getColumn(tabelaInventario.getColumnName(5)).setCellRenderer(centro);
        view.TabelaInventarios.getColumn(tabelaInventario.getColumnName(7)).setCellRenderer(centro);
        view.TabelaInventarios.getColumn(tabelaInventario.getColumnName(6)).setCellRenderer(direita);
        view.TabelaInventarios.getColumn(tabelaInventario.getColumnName(1)).setPreferredWidth(400);
        view.TabelaInventarios.getColumn(tabelaInventario.getColumnName(2)).setPreferredWidth(350);
        view.TabelaInventarios.setModel(tabelaInventario);
    }
    
    private void preencherTabelaInventario(){
        //Insere o header na tabelaInventario
        JTableHeader header = view.TabelaInventarios.getTableHeader();
        header.setDefaultRenderer(new ModHeader());
        view.TabelaInventarios.setTableHeader(header);
        //Carrega tabela inventarios
        ArrayList<ModInventario> list = repositorio.listarInventario();
        tabelaInventario = (DefaultTableModel) view.TabelaInventarios.getModel();
        
        Object[] objeto = new Object[8];
        for (int i = 0; i < list.size(); i++) {
            objeto[0] = returnID(list.get(i).getId());
            objeto[1] = list.get(i).getMotivo();
            objeto[2] = list.get(i).getId_produto();
            objeto[3] = list.get(i).getStock_atual();
            objeto[4] = list.get(i).getStock_anterior();
            objeto[5] = list.get(i).getDiferenca();
            objeto[6] = utilidades.formatoCosto(list.get(i).getCusto());
            objeto[7] = dateFormat.format(list.get(i).getData());
            tabelaInventario.addRow(objeto);
        }
        view.TabelaInventarios.setModel(tabelaInventario);
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
