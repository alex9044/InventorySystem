
package controller;

import DAO.ClienteDAO;
import DAO.ConvencionalDAO;
import DAO.ProdutoDAO;
import DAO.VendaDAO;
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
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.ModCliente;
import model.ModCombo;
import model.ModProduto;
import model.ModVenda;
import net.sf.jasperreports.view.JasperViewer;
import util.ModHeader;
import util.ModRowEstado;
import util.Util;
import view.frmMenu;
import view.frmParcelas;
import view.frmParcelasBaixa;

/**
 *
 * @author ALEX PC
 */
public class controllerVenda implements ActionListener, KeyListener, MouseListener{
    
    private Util utilidades;
    private VendaDAO repositorioVenda;
    private ProdutoDAO repositorioProduto;
    private ModVenda modeloVenda;
    private ModProduto modeloProduto;
    private frmMenu view;
    private frmParcelas frmParcela;
    
    /*MODELOS*/
    //TabelaNovaVenda
    DefaultTableModel tabelaNV = new DefaultTableModel();
    //TabelaVendas
    DefaultTableModel tabelaVenda = new DefaultTableModel();
    
    /*ESTILIZAÇÃO*/
    DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
    DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
    ModRowEstado boxEstado =  new ModRowEstado();
    
    //Formatar Data
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    //Contem o valor da quantidade se o produto ja exite na TabelaNovaVenda
    private int quantidade = 0;
    //Contem o id usado para eliminar o condicional apos a venda
    private int id_convencional = 0;
    
    
    public controllerVenda(VendaDAO repositorioVenda, ModVenda modeloVenda, ProdutoDAO repositorioProduto, ModProduto modeloProduto, Util util, frmParcelas frmParcela ,frmMenu view) {
        this.repositorioVenda = repositorioVenda;
        this.modeloVenda = modeloVenda;
        this.repositorioProduto = repositorioProduto;
        this.modeloProduto = modeloProduto;
        this.utilidades = util;
        this.frmParcela = frmParcela;
        this.view = view;
        this.view.txtCodigoNV.addKeyListener(this);
        this.view.txtQuantidadeNV.addKeyListener(this);
        this.view.txtIdNV.addKeyListener(this);
        this.view.txtProdutoNV.addKeyListener(this);
        this.view.txtStockNV.addKeyListener(this);
        this.view.txtPrecoNV.addKeyListener(this);
        this.view.txtTotalNV.addKeyListener(this);
        this.view.txtValorRecebidoNV.addKeyListener(this);
        this.view.TabelaNovaVenda.addKeyListener(this);
        this.view.btnGerarVenda.addActionListener(this);
        this.view.txtProcurarVenda.addKeyListener(this);
        this.view.btnCancelarVenda.addActionListener(this);
        this.view.lblNovaVenda.addMouseListener(this);
        this.view.lblVendas.addMouseListener(this);
        this.view.menuVerVenda.addActionListener(this);
        this.view.menuEliminarVenda.addActionListener(this);
        this.view.menuEliminarItemVenda.addActionListener(this);
        this.view.menuNovaVenda.addActionListener(this);
        //Esconde o label Gs da sessao nova venda 
        view.lblGSNV.setVisible(false);
        //Carrega o cbxClienteNV
        AutoCompleteDecorator.decorate(view.cbxClienteNV);
        
        ajustarTabelaNovaVenda();
        preencherTabelaVendas();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.btnGerarVenda){
            if(view.lblValorTotalNV.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Adicione um produto!");
                view.txtCodigoNV.requestFocus();
            } else {
                if(id_convencional != 0){
                    insertarVenda();
                    new ConvencionalDAO().eliminarConvencional(id_convencional);
                    limparCamposNovaVenda();
                    view.lblGSNV.setVisible(false);
                    view.cbxClienteNV.setSelectedIndex(0);
                    view.cbxTipoVenda.setSelectedIndex(0);
                }else{
                    insertarVenda();
                    limparCamposNovaVenda();
                    view.lblGSNV.setVisible(false);
                    view.cbxClienteNV.setSelectedIndex(0);
                    view.cbxTipoVenda.setSelectedIndex(0);
                }
            }
        }else if(e.getSource() == view.btnCancelarVenda){
            limparTabelaNovaVenda();
            limparCamposNovaVenda();
            id_convencional = 0 ;
            view.lblValorTotalNV.setText("");
            view.lblGSNV.setVisible(false);
            view.cbxClienteNV.setSelectedIndex(0);
        }else if(e.getSource() == view.menuVerVenda){
            int id_venda = Integer.parseInt(view.TabelaVendas.getValueAt(view.TabelaVendas.getSelectedRow(), 0).toString());
            modeloVenda.setId_venda(id_venda);
            modeloVenda = repositorioVenda.verVenda(modeloVenda);
            modeloVenda = repositorioVenda.verEstadoVenda(modeloVenda);
            if (modeloVenda.getEstado().equals("Pendente")) {
                frmParcelasBaixa frmParcelaBaixa = new frmParcelasBaixa(modeloVenda);
                frmParcelaBaixa.setVisible(true);
            }else{
                repositorioVenda.gerarNota(id_venda);
            }
        }else if(e.getSource() == view.menuEliminarVenda){
            String id_venda = view.TabelaVendas.getValueAt(view.TabelaVendas.getSelectedRow(), 0).toString();
            if (id_venda.equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione registro!");
            }else if(repositorioVenda.eliminarVenda(Integer.parseInt(id_venda)) == true){
                limparTabelaVenda();
                preencherTabelaVendas();
                JOptionPane.showMessageDialog(null, "Registro eliminado!");
            }
        }else if(e.getSource() == view.menuEliminarItemVenda){
            int fila = view.TabelaNovaVenda.getSelectedRow();
            tabelaNV.removeRow(fila);
            view.TabelaNovaVenda.setModel(tabelaNV);
            calcularVenda();
        }else if(e.getSource() == view.menuNovaVenda){
            id_convencional = Integer.parseInt(view.TabelaConvencional.getValueAt(view.TabelaConvencional.getSelectedRow(), 0).toString());
            System.out.println("Funciona"+id_convencional);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getSource() == view.txtCodigoNV){
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (view.txtCodigoNV.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese o codigo do produto");
                }else{
                    String codigoPro = view.txtCodigoNV.getText();
                    modeloProduto = repositorioProduto.verProdutoCodigo(codigoPro);
                    view.txtIdNV.setText(returnID(modeloProduto.getId_produto()));
                    view.txtProdutoNV.setText(modeloProduto.getDescricao());
                    view.txtPrecoNV.setText(utilidades.formatoPrecio((float) modeloProduto.getPreco_venda()));
                    if (verificaProdutoTabela(Integer.parseInt(view.txtIdNV.getText()),"") == true) {
                        view.txtStockNV.setText(modeloProduto.getStock() - quantidade + "");
                    }else{
                        view.txtStockNV.setText(String.valueOf(modeloProduto.getStock()));
                    }
                    view.txtQuantidadeNV.setText("1");
                    view.txtQuantidadeNV.requestFocus();
                }
            }
        }else if(e.getSource() == view.txtQuantidadeNV){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    int novaQuantidade = Integer.parseInt(view.txtQuantidadeNV.getText());
                    String descricaoProdNV = view.txtProdutoNV.getText();
                    float preco_venda = utilidades.convertirFloat(view.txtPrecoNV.getText());
                    int id = Integer.parseInt(view.txtIdNV.getText());
                    int stock = Integer.parseInt(view.txtStockNV.getText());
                    if(novaQuantidade <= stock){
                        if (repositorioProduto.verEstadoProduto(Integer.parseInt(view.txtIdNV.getText())).equals("Inativo")) {
                            JOptionPane.showMessageDialog(null, "Produto Inativo!");
                        }else{
                            tabelaNV = (DefaultTableModel) view.TabelaNovaVenda.getModel();
                            ArrayList listaVendas = new ArrayList();
                            int item = 1;
                            listaVendas.add(item);
                            listaVendas.add(id);
                            if (verificaProdutoTabela(id, "adicionar") == true) {
                                listaVendas.add(descricaoProdNV);
                                listaVendas.add(quantidade + novaQuantidade);
                                listaVendas.add(preco_venda);
                                listaVendas.add((quantidade + novaQuantidade) * preco_venda);
                                Object[] obj = new Object[5];
                                obj[0] = returnID((int) listaVendas.get(1));
                                obj[1] = listaVendas.get(2);
                                obj[2] = listaVendas.get(3);
                                obj[3] = utilidades.formatoCosto((float) listaVendas.get(4));
                                obj[4] = utilidades.formatoCosto((float) listaVendas.get(5));
                                tabelaNV.addRow(obj);
                            }else{
                                listaVendas.add(descricaoProdNV);
                                listaVendas.add(novaQuantidade);
                                listaVendas.add(preco_venda);
                                listaVendas.add(novaQuantidade * preco_venda);
                                Object[] obj = new Object[5];
                                obj[0] = returnID((int) listaVendas.get(1));
                                obj[1] = listaVendas.get(2);
                                obj[2] = listaVendas.get(3);
                                obj[3] = utilidades.formatoCosto((float) listaVendas.get(4));
                                obj[4] = utilidades.formatoCosto((float) listaVendas.get(5));
                                tabelaNV.addRow(obj);
                            }
                            view.TabelaNovaVenda.setModel(tabelaNV);
                            calcularVenda();
                            limparCamposNovaVenda();
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "Stock Indinsponivel!");
                        limparCamposNovaVenda();
                    }
                }
            }else if(e.getSource() == view.txtValorRecebidoNV){
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    float total = utilidades.convertirFloat(view.lblValorTotalNV.getText());
                    float valor_recebido =  utilidades.convertirFloat(view.txtValorRecebidoNV.getText());
                    view.txtValorRecebidoNV.setText(utilidades.formatoPrecio(valor_recebido));
                    view.txtTrocoNV.setText(utilidades.formatoCosto(valor_recebido-total));
                }
            }else if(e.getSource() == view.txtProcurarVenda){
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    limparTabelaVenda();
                    preencherTabelaVendas();
                }
            }else if(e.getSource() == view.txtPrecoNV){
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    view.txtQuantidadeNV.requestFocus();
                    view.txtPrecoNV.setText(utilidades.formatoPrecio(utilidades.convertirFloat(view.txtPrecoNV.getText())));
                }
            }else if(e.getSource() == view.txtProcurarVenda){
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    limparTabelaVenda();
                    preencherTabelaVendas();
                } 
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == view.txtQuantidadeNV) {
            int quantidade;
            float preco_venda;
            if (view.txtQuantidadeNV.getText().equals("")) {
                preco_venda =  utilidades.convertirFloat(view.txtPrecoNV.getText());
                quantidade = 1;
                view.txtTotalNV.setText(utilidades.formatoPrecio(preco_venda));
            }else{
                quantidade = Integer.parseInt(view.txtQuantidadeNV.getText());
                preco_venda =  utilidades.convertirFloat(view.txtPrecoNV.getText());
                view.txtTotalNV.setText(utilidades.formatoPrecio(quantidade * preco_venda));   
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()== view.lblNovaVenda){
            view.TabbedPanePrincipal.setSelectedIndex(6);
            view.txtCodigoNV.requestFocus();
        }else if(e.getSource() == view.lblVendas){
            view.TabbedPanePrincipal.setSelectedIndex(8);
            view.txtProcurarVenda.setText("");
            view.txtProcurarVenda.requestFocus();
            limparTabelaVenda();
            preencherTabelaVendas();
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
    
    //Verifica se já exite o produto na tabelaNovaVenda
    public boolean verificaProdutoTabela(int id_produto, String operacao){
        
        int filas = view.TabelaNovaVenda.getRowCount(); 
        for (int i = 0; i < filas; i++) {
            int id_produto_tbl = Integer.parseInt(view.TabelaNovaVenda.getValueAt(i, 0).toString());
            if (id_produto_tbl == id_produto) {
                quantidade = Integer.parseInt(tabelaNV.getValueAt(i, 2).toString());
                if(operacao.equals("adicionar")){
                    tabelaNV.removeRow(i);
                }
                return true;
            }
        }
        return false;
    }
    
    //Solicita registro de venda ao Banco de dados
    private void insertarVenda(){
        
        ModCombo cliente = (ModCombo) view.cbxClienteNV.getSelectedItem();
        
        modeloVenda.setId_cliente(cliente.getId());
        modeloVenda.setTotal(utilidades.convertirFloat(view.lblValorTotalNV.getText()));
        modeloVenda.setTipo_venda((String) view.cbxTipoVenda.getSelectedItem());
        String estado;
        if(modeloVenda.getTipo_venda().equals("Parcelado")){
            estado = "Pendente";
        }else{
            estado = "Finalizado";
        }
        if(repositorioVenda.insertarVendas(modeloVenda)){
            modeloVenda.setId_venda(repositorioVenda.verVendaId());
            repositorioVenda.verVenda(modeloVenda);
            modeloVenda.setNro_factura(returnNro_factura(modeloVenda.getId_venda()));
            for (int i = 0; i < view.TabelaNovaVenda.getRowCount(); i++) {
                float preco = utilidades.convertirFloat(view.TabelaNovaVenda.getValueAt(i, 3).toString());
                int quantidade = Integer.parseInt(view.TabelaNovaVenda.getValueAt(i, 2).toString());
                int id_produto = Integer.parseInt(view.TabelaNovaVenda.getValueAt(i, 0).toString());
                float subTotal = preco * quantidade;
                repositorioVenda.atualizarEstadoVenda(modeloVenda.getId_venda(), estado);
                repositorioVenda.insertarItemVenda(modeloVenda.getId_venda(),id_produto,preco,quantidade,subTotal,modeloVenda.getNro_factura());
                modeloProduto = repositorioProduto.verProduto(id_produto);
                int stockAtual = modeloProduto.getStock() - quantidade;
                repositorioProduto.modificarStock(stockAtual,id_produto);
            }
            limparTabelaNovaVenda();
            if (modeloVenda.getTipo_venda().equals("Parcelado")) {
                frmParcela = new frmParcelas(modeloVenda);
                frmParcela.setVisible(true);
                view.lblValorTotalNV.setText("");
            }else{
                JOptionPane.showMessageDialog(null, "Venda Realizada!");
                //Retorna o Jasper da Nota
                JasperViewer nota = repositorioVenda.gerarNota(repositorioVenda.verVendaId());
                nota.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                nota.setVisible(true);
                nota.toFront();
                view.lblValorTotalNV.setText("");
            }
        } 
    }
    
    private void limparTabelaNovaVenda(){
        for (int i = 0; i < tabelaNV.getRowCount(); i++){
            tabelaNV.removeRow(i);
            i = i-1;
        }
    }
    
    private void limparTabelaVenda(){
        for (int i = 0; i < tabelaVenda.getRowCount(); i++){
            tabelaVenda.removeRow(i);
            i = i-1;
        }
    }
    
    private void ajustarTabelaNovaVenda(){
        
        //Insere o Header na tabelaNV
        JTableHeader headerTblNovaVenda = view.TabelaNovaVenda.getTableHeader();
        headerTblNovaVenda.setDefaultRenderer(new ModHeader());
        view.TabelaNovaVenda.setTableHeader(headerTblNovaVenda);
        
        view.TabelaNovaVenda.getColumn("Id-Produto").setCellRenderer(centro);
        view.TabelaNovaVenda.getColumn("Quantidade").setCellRenderer(centro);
        view.TabelaNovaVenda.getColumn("Preço").setCellRenderer(direita);
        view.TabelaNovaVenda.getColumn("Total").setCellRenderer(direita);
        view.TabelaNovaVenda.getColumn("Descrição").setPreferredWidth(1100);
    }
    
    //Carrega a TabelaVendas
    private void preencherTabelaVendas(){
        
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        
        //Insere o Header na tabelaVendas
        JTableHeader header = view.TabelaVendas.getTableHeader();
        header.setDefaultRenderer(new ModHeader());
        view.TabelaVendas.setTableHeader(header);
        
        //Preenche a tabelaVendas
        ArrayList<ModVenda> list = repositorioVenda.listarVenda(view.txtProcurarVenda.getText());
        tabelaVenda = (DefaultTableModel) view.TabelaVendas.getModel();
        tabelaVenda.setColumnIdentifiers(new Object[]{"Nro Nota","Cliente","Total","Data","Tipo Venda","Estado"});
        Object[] objeto = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            objeto[0] = returnNro_factura(list.get(i).getId_venda());
            ModCliente cliente = new ClienteDAO().verCliente(list.get(i).getId_cliente());
            objeto[1] = cliente.getNome();
            objeto[2] = utilidades.formatoPrecio(list.get(i).getTotal());
            objeto[3] = dateFormat.format(list.get(i).getData());
            objeto[4] = list.get(i).getTipo_venda();
            objeto[5] = list.get(i).getEstado ();
            tabelaVenda.addRow(objeto);
        }
        view.TabelaVendas.setModel(tabelaVenda);
        
        //ajustar TabelaVendas
        view.TabelaVendas.getColumn("Nro Nota").setCellRenderer(centro);
        view.TabelaVendas.getColumn("Data").setCellRenderer(centro);
        view.TabelaVendas.getColumn("Tipo Venda").setCellRenderer(centro);
        view.TabelaVendas.getColumn("Estado").setCellRenderer(boxEstado);
        view.TabelaVendas.getColumn("Total").setCellRenderer(direita);
        view.TabelaVendas.getColumn("Cliente").setPreferredWidth(400);
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
}
