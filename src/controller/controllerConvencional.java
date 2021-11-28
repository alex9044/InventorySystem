package controller;

import DAO.ClienteDAO;
import DAO.ConvencionalDAO;
import DAO.ProdutoDAO;
import autocomplete.AutoCompleteDecorator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.ModCliente;
import model.ModCombo;
import model.ModConvencional;
import model.ModProduto;
import util.ModHeader;
import util.ModRowEstado;
import util.Util;
import view.frmMenu;
import view.frmParcelas;

/**
 *
 * @author ALEX
 */
public class controllerConvencional implements ActionListener, MouseListener, KeyListener{
    
    private Util utilidades;
    private ModConvencional modeloConvencional;
    private ModProduto modeloProduto;
    private ConvencionalDAO repositorio;
    private ProdutoDAO repositorioProduto;
    private frmMenu view;
    
    /*MODELOS*/
    //TabelaNovoConvecional
    DefaultTableModel tabelaNConv = new DefaultTableModel();
    //TabelaConvencional
    DefaultTableModel tabelaConvecional = new DefaultTableModel();
    //TabelaNovaVenda
    DefaultTableModel tabelaNovaVenda = new DefaultTableModel();
    
    /*ESTILIZAÇÃO*/
    DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
    DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
    
    //Formatar Data
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    //Contem o valor da quantidade do produto se ele ja exite na tabelaNovoConvencional
    private int quantidade = 0;
    
    private int id_convencional = 0;
    //Utilizada para verificar se é um  novo registro ou se esta moficicando
    private String operacao = "";
    
    frmParcelas frmParcela;
    
    public controllerConvencional(Util utilidades, ModConvencional modeloConvencional, ModProduto modeloProduto, ConvencionalDAO repositorio, ProdutoDAO repositorioProduto, frmMenu view) {
        this.utilidades = utilidades;
        this.modeloConvencional = modeloConvencional;
        this.modeloProduto = modeloProduto;
        this.repositorio = repositorio;
        this.repositorioProduto = repositorioProduto;
        this.view = view;
        this.view.txtCodigoConv.addKeyListener(this);
        this.view.txtQuantidadeConv.addKeyListener(this);
        this.view.lblConvencional.addMouseListener(this);
        this.view.lblNovoConvencional.addMouseListener(this);
        this.view.btnGerarConvecional.addActionListener(this);
        this.view.btnCancelarConvencional.addActionListener(this);
        this.view.menuEliminarConvencional.addActionListener(this);
        this.view.menuNovaVenda.addActionListener(this);
        this.view.btnCancelarVenda.addActionListener(this);
        this.view.btnGerarVenda.addActionListener(this);
        this.view.menuEliminarItemVenda.addActionListener(this);
        this.view.menuModificarConvencional.addActionListener(this);
        this.view.lblNovaVenda.addMouseListener(this);
        this.view.menuEliminarItemConvencional.addActionListener(this);
        this.view.txtProcurarConvencional.addKeyListener(this);
        this.view.txtCodigoNV.addKeyListener(this);
        view.lblGSConv.setVisible(false);
        AutoCompleteDecorator.decorate(view.cbxClienteConv);
        ajustarTabelaNovoConvecional();
        preencherTabelaConvencional();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.btnGerarConvecional){
            if(view.lblValorTotalConv.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Adicione um produto!");
                view.txtCodigoNV.requestFocus();
            }else {
                int resposta = JOptionPane.showConfirmDialog(null, "Confirmar?", "Inventory System", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(resposta == 0 ){
                    insertarConvencional(id_convencional);
                    limparCamposNovoConvencional();
                    view.lblValorTotalConv.setText("");
                    view.lblGSConv.setVisible(false);
                    view.cbxClienteConv.setSelectedIndex(0);
                }
            }
        }else if(e.getSource() == view.btnCancelarConvencional){
            limparTabelaNovoConvecional();
            limparCamposNovoConvencional();
            view.lblValorTotalConv.setText("");
            view.lblGSConv.setVisible(false);
            view.btnGerarConvecional.setText("Confirmar");
            view.cbxClienteConv.setSelectedIndex(0);
            if (operacao.equals("Modificar")) {
                view.TabbedPanePrincipal.setSelectedIndex(13);
                limparTabelaConvecional();
                preencherTabelaConvencional();
                operacao = "";
            }
        }else if(e.getSource() == view.menuEliminarConvencional){
            id_convencional = Integer.parseInt(view.TabelaConvencional.getValueAt(view.TabelaConvencional.getSelectedRow(), 0).toString());
            if (id_convencional == 0) {
                JOptionPane.showMessageDialog(null, "Seleccione Registro!");
            }else if(repositorio.eliminarConvencional(id_convencional) == true){
                limparTabelaConvecional();
                preencherTabelaConvencional();
                JOptionPane.showMessageDialog(null, "Registro eliminado!");
            }
        }else if(e.getSource() == view.menuNovaVenda){
            id_convencional = Integer.parseInt(view.TabelaConvencional.getValueAt(view.TabelaConvencional.getSelectedRow(), 0).toString());
            limparTabelaNovaVenda();
            preencherTabelaNovaVenda(id_convencional);
            informarComboBox(repositorio.verCliente(id_convencional));
            view.txtCodigoNV.setEnabled(false);
            view.txtQuantidadeNV.setEnabled(false);
            view.txtPrecoNV.setEnabled(false);
            view.TabbedPanePrincipal.setSelectedIndex(6);
        }else if(e.getSource() == view.btnCancelarVenda){
            limparTabelaNovaVenda();
            view.txtCodigoNV.setEnabled(true);
            view.txtQuantidadeNV.setEnabled(true);
            view.txtPrecoNV.setEnabled(true);
            view.TabbedPanePrincipal.setSelectedIndex(13);
        }else if(e.getSource() == view.btnGerarVenda){
            limparTabelaNovaVenda();
        }else if(e.getSource() == view.menuEliminarItemVenda){
            int fila = view.TabelaNovaVenda.getSelectedRow();
            tabelaNovaVenda.removeRow(fila);
            view.TabelaNovaVenda.setModel(tabelaNovaVenda);
            calcularVenda();
        }else if(e.getSource() == view.menuModificarConvencional){
            view.TabbedPanePrincipal.setSelectedIndex(12);
            view.btnGerarConvecional.setText("Modificar");
            id_convencional = Integer.parseInt(view.TabelaConvencional.getValueAt(view.TabelaConvencional.getSelectedRow(), 0).toString());
            operacao = "Modificar";
            limparCamposNovoConvencional();
            limparTabelaNovoConvecional();
            preencherTabelaNovoConvencional(id_convencional);
            informarComboBox(repositorio.verCliente(id_convencional));
        }else if (e.getSource() == view.menuEliminarItemConvencional) {
            eliminaItemLista();
        }
    
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == view.lblNovoConvencional) {
            view.TabbedPanePrincipal.setSelectedIndex(12);
            view.txtCodigoConv.requestFocus();
        }else if (e.getSource() == view.lblConvencional) {
            view.TabbedPanePrincipal.setSelectedIndex(13);
            limparTabelaConvecional();
            preencherTabelaConvencional();
        }else if(e.getSource() == view.lblNovaVenda){
            view.cbxClienteConv.setSelectedIndex(0);
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
        if(e.getSource() == view.txtCodigoConv){
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (view.txtCodigoConv.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese o codigo do produto");
                }else{
                    String codigoPro = view.txtCodigoConv.getText();
                    modeloProduto = repositorioProduto.verProdutoCodigo(codigoPro);
                    view.txtIdConv.setText(returnID(modeloProduto.getId_produto()));
                    view.txtProdutoConv.setText(modeloProduto.getDescricao());
                    view.txtPrecoConv.setText(utilidades.formatoPrecio((float) modeloProduto.getPreco_venda()));
                    if (verificaProdutoTabela(Integer.parseInt(view.txtIdConv.getText()),"") == true) {
                        view.txtStockConv.setText(modeloProduto.getStock() - quantidade + "");
                    }else{
                        view.txtStockConv.setText(String.valueOf(modeloProduto.getStock()));
                    }
                    view.txtQuantidadeConv.setText("1");
                    view.txtQuantidadeConv.requestFocus();
                }
            }
        }else if(e.getSource() == view.txtQuantidadeConv){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    int novaQuantidade = Integer.parseInt(view.txtQuantidadeConv.getText());
                    String descricaoProdConv = view.txtProdutoConv.getText();
                    float preco_venda = utilidades.convertirFloat(view.txtPrecoConv.getText());
                    int id = Integer.parseInt(view.txtIdConv.getText());
                    int stock = Integer.parseInt(view.txtStockConv.getText());
                    if(novaQuantidade <= stock){
                        if (repositorioProduto.verEstadoProduto(Integer.parseInt(view.txtIdConv.getText())).equals("Inativo")) {
                            JOptionPane.showMessageDialog(null, "Produto Inativo!");
                        }else{
                            tabelaNConv = (DefaultTableModel) view.TabelaNovoConvecional.getModel();
                            ArrayList listaConvencional = new ArrayList();
                            int item = 1;
                            listaConvencional.add(item);
                            listaConvencional.add(id);
                            if (verificaProdutoTabela(id,"adicionar") == true) {
                                listaConvencional.add(descricaoProdConv);
                                listaConvencional.add(quantidade + novaQuantidade);
                                listaConvencional.add(preco_venda);
                                listaConvencional.add((quantidade + novaQuantidade) * preco_venda);
                                Object[] obj = new Object[5];
                                obj[0] = returnID((int) listaConvencional.get(1));
                                obj[1] = listaConvencional.get(2);
                                obj[2] = listaConvencional.get(3);
                                obj[3] = utilidades.formatoCosto((float) listaConvencional.get(4));
                                obj[4] = utilidades.formatoCosto((float) listaConvencional.get(5));
                                tabelaNConv.addRow(obj);
                            }else{
                                listaConvencional.add(descricaoProdConv);
                                listaConvencional.add(novaQuantidade);
                                listaConvencional.add(preco_venda);
                                listaConvencional.add(novaQuantidade * preco_venda);
                                Object[] obj = new Object[5];
                                obj[0] = returnID((int) listaConvencional.get(1));
                                obj[1] = listaConvencional.get(2);
                                obj[2] = listaConvencional.get(3);
                                obj[3] = utilidades.formatoCosto((float) listaConvencional.get(4));
                                obj[4] = utilidades.formatoCosto((float) listaConvencional.get(5));
                                tabelaNConv.addRow(obj);
                            }
                            view.TabelaNovoConvecional.setModel(tabelaNConv);
                            calcularConvencional();
                            limparCamposNovoConvencional();
                            view.txtCodigoConv.requestFocus();
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "Stock Indinsponivel!");
                        limparCamposNovoConvencional();
                        view.txtCodigoConv.requestFocus();
                    }
                }
        }else if (e.getSource() == view.txtCodigoNV) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                view.txtQuantidadeNV.requestFocus();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == view.txtQuantidadeConv) {
            int quantidade;
            float preco_venda;
            if (view.txtQuantidadeConv.getText().equals("")) {
                preco_venda =  utilidades.convertirFloat(view.txtPrecoConv.getText());
                quantidade = 1;
                view.txtTotalConv.setText(utilidades.formatoPrecio(preco_venda));
            }else{
                quantidade = Integer.parseInt(view.txtQuantidadeConv.getText());
                preco_venda =  utilidades.convertirFloat(view.txtPrecoConv.getText());
                view.txtTotalConv.setText(utilidades.formatoPrecio(quantidade * preco_venda));   
            }
        }else if(e.getSource() == view.txtProcurarConvencional){
            limparTabelaConvecional();
            preencherTabelaConvencional();
        }
    }
    
    //Verifica se já exite o produto na tabelaNConv
    public boolean verificaProdutoTabela(int id_produto, String operacao){
        
        int filas = view.TabelaNovoConvecional.getRowCount(); 
        for (int i = 0; i < filas; i++) {
            int id_produto_tbl = Integer.parseInt(view.TabelaNovoConvecional.getValueAt(i, 0).toString());
            if (id_produto_tbl == id_produto) {
                quantidade = Integer.parseInt(tabelaNConv.getValueAt(i, 2).toString());
                if(operacao.equals("adicionar")){
                    tabelaNConv.removeRow(i);
                }
                return true;
            }
        }
        return false;
    }
    
    //Solicita registro de convecional ao Banco de dados
    private void insertarConvencional(int id_convencional){
        
        ModCombo cliente = (ModCombo) view.cbxClienteConv.getSelectedItem();
        
        modeloConvencional.setId(id_convencional);
        modeloConvencional.setId_cliente(cliente.getId());
        modeloConvencional.setTotal(utilidades.convertirFloat(view.lblValorTotalConv.getText()));
        
        if (operacao.equals("Modificar")) {
            if(repositorio.modificarConvencional(modeloConvencional)){
                repositorio.eliminarItemsConvencional(modeloConvencional.getId());
                for (int i = 0; i < view.TabelaNovoConvecional.getRowCount(); i++) {
                    float preco = utilidades.convertirFloat(view.TabelaNovoConvecional.getValueAt(i, 3).toString());
                    int quantidade = Integer.parseInt(view.TabelaNovoConvecional.getValueAt(i, 2).toString());
                    int id_produto = Integer.parseInt(view.TabelaNovoConvecional.getValueAt(i, 0).toString());
                    repositorio.insertarItemConvecional(modeloConvencional.getId(),id_produto,preco,quantidade);
                    modeloProduto = repositorioProduto.verProduto(id_produto);
                    int stockAtual = modeloProduto.getStock() - quantidade;
                    repositorioProduto.modificarStock(stockAtual,id_produto);
                }
                limparTabelaNovoConvecional();
                limparTabelaConvecional();
                preencherTabelaConvencional();
                JOptionPane.showMessageDialog(null, "Registro Modificado!");
                view.lblValorTotalNV.setText("");
                view.TabbedPanePrincipal.setSelectedIndex(13);
                operacao = "";
            }
        }else {
            if(repositorio.insertarConvecional(modeloConvencional)){
                modeloConvencional.setId(repositorio.verConvencionalId());
                for (int i = 0; i < view.TabelaNovoConvecional.getRowCount(); i++) {
                    float preco = utilidades.convertirFloat(view.TabelaNovoConvecional.getValueAt(i, 3).toString());
                    int quantidade = Integer.parseInt(view.TabelaNovoConvecional.getValueAt(i, 2).toString());
                    int id_produto = Integer.parseInt(view.TabelaNovoConvecional.getValueAt(i, 0).toString());
                    repositorio.insertarItemConvecional(modeloConvencional.getId(),id_produto,preco,quantidade);
                    modeloProduto = repositorioProduto.verProduto(id_produto);
                    int stockAtual = modeloProduto.getStock() - quantidade;
                    repositorioProduto.modificarStock(stockAtual,id_produto);
                }
                limparTabelaNovoConvecional();
                JOptionPane.showMessageDialog(null, "Saída Realizada!");
                view.lblValorTotalNV.setText("");
            }
        }
    }
    
    private void ajustarTabelaNovoConvecional(){
        
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        
        //Insere o Header na tabelaNConv
        
        JTableHeader headerTblNovoConvencional = view.TabelaNovoConvecional.getTableHeader();
        headerTblNovoConvencional.setDefaultRenderer(new ModHeader());
        view.TabelaNovoConvecional.setTableHeader(headerTblNovoConvencional);
        
        view.TabelaNovoConvecional.getColumn("Id-Produto").setCellRenderer(centro);
        view.TabelaNovoConvecional.getColumn("Quantidade").setCellRenderer(centro);
        view.TabelaNovoConvecional.getColumn("Preço").setCellRenderer(direita);
        view.TabelaNovoConvecional.getColumn("Descrição").setPreferredWidth(1100);
        
    }
    
    //Calcula o valor total do convencional usando a coluna quantidade e preco
    private void calcularConvencional(){
        
        float total = 0;
        int filas = view.TabelaNovoConvecional.getRowCount(); 
        for (int i = 0; i < filas; i++) {
            float total_tbl = utilidades.convertirFloat(view.TabelaNovoConvecional.getValueAt(i, 3).toString());
            int qtd = Integer.parseInt(view.TabelaNovoConvecional.getValueAt(i, 2).toString());
            total = total+(total_tbl*qtd);
        }
        view.lblValorTotalConv.setText(utilidades.formatoCosto((float) total));
        view.lblGSConv.setVisible(true);
    }
    
    
    
    //Calcula o total da venda usando a coluna Total da tabelaNovaVenda
    private void calcularVenda(){
        
        float total = 0;
        int filas = view.TabelaNovaVenda.getRowCount(); 
        for (int i = 0; i < filas; i++) {
            total = total + utilidades.convertirFloat(String.valueOf(view.TabelaNovaVenda.getValueAt(i, 4)));
        }
        view.lblValorTotalNV.setText(utilidades.formatoCosto((float) total));
        view.lblGSNV.setVisible(true);
        
    }
    
    //Set o Cliente do registro convencional
    private void informarComboBox(int id_cliente){
        
        ArrayList<ModCliente> list = new ClienteDAO().listarClientes("");
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId_cliente();
            if(id == id_cliente){
                view.cbxClienteNV.setSelectedIndex(i);
                view.cbxClienteConv.setSelectedIndex(i);
            }
        }
        
    }
    
    //Carregar a tabelaNovaVenda
    public void preencherTabelaNovaVenda(int id_convencional){
        
        ArrayList<ModConvencional> listaProdutos = repositorio.listarItemsConvencional(id_convencional);
        
        tabelaNovaVenda = (DefaultTableModel) view.TabelaNovaVenda.getModel();
        Object[] obj = new Object[5];
        for (int i = 0; i <listaProdutos.size(); i++) {
            obj[0] = returnID((int) listaProdutos.get(i).getId_produto());
            obj[1] = listaProdutos.get(i).getProduto();
            obj[2] = listaProdutos.get(i).getId_cliente();
            obj[3] = utilidades.formatoCosto((float) listaProdutos.get(i).getTotal());
            obj[4] = utilidades.formatoCosto(listaProdutos.get(i).getId_cliente()* listaProdutos.get(i).getTotal());
            tabelaNovaVenda.addRow(obj);
        }
        view.TabelaNovaVenda.setModel(tabelaNovaVenda);
        calcularVenda();                                            
    }
    
    //Carregar tabelaNovoConvecional
    public void preencherTabelaNovoConvencional(int id_convencional){
        
        ArrayList<ModConvencional> listaProdutos = repositorio.listarItemsConvencional(id_convencional);
        
        tabelaNConv = (DefaultTableModel) view.TabelaNovoConvecional.getModel();
        Object[] obj = new Object[5];
        for (int i = 0; i <listaProdutos.size(); i++) {
            obj[0] = returnID((int) listaProdutos.get(i).getId_produto());
            obj[1] = listaProdutos.get(i).getProduto();
            obj[2] = listaProdutos.get(i).getId_cliente();
            obj[3] = utilidades.formatoCosto((float) listaProdutos.get(i).getTotal());
            obj[4] = utilidades.formatoCosto(listaProdutos.get(i).getId_cliente()* listaProdutos.get(i).getTotal());
            tabelaNConv.addRow(obj);
        }
        view.TabelaNovoConvecional.setModel(tabelaNConv);
        calcularConvencional();                                            
    }
    
    //Carrega a TabelaConvecional
    private void preencherTabelaConvencional(){
        
        ModRowEstado boxEstado =  new ModRowEstado();
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        
        //Insere o Header na tabelaConvencional
        JTableHeader header = view.TabelaConvencional.getTableHeader();
        header.setDefaultRenderer(new ModHeader());
        view.TabelaConvencional.setTableHeader(header);
        
        //Preenche a tabelaConvencional
        ArrayList<ModConvencional> list = repositorio.listarConvencional(view.txtProcurarConvencional.getText());
        tabelaConvecional = (DefaultTableModel) view.TabelaConvencional.getModel();
        tabelaConvecional.setColumnIdentifiers(new Object[]{"Id","Cliente","Total","Data"});
        Object[] objeto = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            objeto[0] = returnIdconvencional(list.get(i).getId());
            ModCliente cliente = new ClienteDAO().verCliente(list.get(i).getId_cliente());
            objeto[1] = cliente.getNome();
            objeto[2] = utilidades.formatoPrecio(list.get(i).getTotal());
            objeto[3] = dateFormat.format(list.get(i).getData());
            objeto[4] = list.get(i).getEstado ();
            tabelaConvecional.addRow(objeto);
        }
        view.TabelaConvencional.setModel(tabelaConvecional);
        
        //ajustar TabelaConvencional
        view.TabelaConvencional.getColumn("Id").setCellRenderer(centro);
        view.TabelaConvencional.getColumn("Data").setCellRenderer(centro);
        view.TabelaConvencional.getColumn("Total").setCellRenderer(direita);
        view.TabelaConvencional.getColumn("Cliente").setPreferredWidth(700);
        
    }
    
    //Elimina item da tabelaNovoconvencional
    private void eliminaItemLista(){
        int fila = view.TabelaNovoConvecional.getSelectedRow();
        int id_produto = Integer.parseInt(tabelaNConv.getValueAt(view.TabelaNovoConvecional.getSelectedRow(), 0).toString());
        if (id_convencional != 0 && id_produto != 0) {
            repositorio.eliminarItemConvecional(id_convencional, id_produto);
        }
        tabelaNConv.removeRow(fila);
        view.TabelaNovoConvecional.setModel(tabelaNConv);
    }
    
    private void limparCamposNovoConvencional(){
        
        view.txtCodigoConv.setText("");
        view.txtProdutoConv.setText("");
        view.txtIdConv.setText("");
        view.txtPrecoConv.setText("");
        view.txtQuantidadeConv.setText("");
        view.txtTotalConv.setText("");
        view.txtStockConv.setText("");
        view.txtCodigoConv.requestFocus();
        
    }
    
    private void limparTabelaNovaVenda(){
        
        for (int i = 0; i < tabelaNovaVenda.getRowCount(); i++){
            tabelaNovaVenda.removeRow(i);
            i = i-1;
        }
        
    }
    
    private void limparTabelaNovoConvecional(){
        
        for (int i = 0; i < tabelaNConv.getRowCount(); i++){
            tabelaNConv.removeRow(i);
            i = i-1;
        }   
        
    }
    
    private void limparTabelaConvecional(){
        
        for (int i = 0; i < tabelaConvecional.getRowCount(); i++){
            tabelaConvecional.removeRow(i);
            i = i-1;
        }   
        
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
    
    private String returnIdconvencional(int number){
        
        String zero = "0";
        String numero = "";
        int length = String.valueOf(number).length();
        if(length == 1){
            numero = zero+zero+zero+number;
        }
        if(length == 2){
            numero = zero+zero+number;
        }
        if(length == 3){
            numero = zero+number;
        }
        if(length == 4){
            numero = ""+number;
        }
        return numero;
    }
    
    private String returnNro_factura(int number){
        String numero = "";
        int length = String.valueOf(number).length();
        if(length == 1){
                numero = "0000"+number;
        }
        if(length == 2){
                numero = "000"+number;
        }
        if(length == 3){
                numero = "00"+number;
        }
        if(length == 4){
                numero = "0"+number;
        }
        if(length == 5){
                numero = ""+number;
        }
        return numero;
    }
    
    private void limparCamposNovaVenda(){
        view.txtCodigoNV.setText("");
        view.txtProdutoNV.setText("");
        view.txtIdNV.setText("");
        view.txtPrecoNV.setText("");
        view.txtQuantidadeNV.setText("");
        view.txtTotalNV.setText("");
        view.txtStockNV.setText("");
        view.txtTrocoNV.setText("");
        view.txtValorRecebidoNV.setText("");
        view.txtCodigoNV.requestFocus();
    }

}
