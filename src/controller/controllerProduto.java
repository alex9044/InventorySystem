package controller;

import DAO.FornecedorDAO;
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
import model.ModFornecedor;
import model.ModCombo;
import util.ModHeader;
import model.ModProduto;
import model.ModVenda;
import util.ModRowEstado;
import util.Util;
import view.frmMenu;

/**
 *
 * @author ALEX PC
 */
public class controllerProduto implements ActionListener, MouseListener, KeyListener{
    
    private Util utilidade;
    private ProdutoDAO repositorio;
    private FornecedorDAO repositorioFornecedor;
    private ModProduto modeloProduto;
    private frmMenu view;
    
    
    /*MODELOS*/
    //TabelaProdutos
    DefaultTableModel tabelaProduto = new DefaultTableModel();
    //TabelaNovaCompra
    DefaultTableModel tabelaNC = new DefaultTableModel();
    //TabelaCompras
    DefaultTableModel tabelaCompra = new DefaultTableModel();
    
    /*ESTILIZAÇÃO*/
    ModRowEstado boxEstado = new ModRowEstado();    
    DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
    DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
    
    //Formatar data
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    //Contem o valor da quantidade do produto se ele ja exite na tabelaNovaCompra
    private int quantidade = 0;

    public controllerProduto(ProdutoDAO repositorio, ModProduto modeloProduto,FornecedorDAO repositorioFornecedor, Util util,frmMenu view){
        this.repositorio = repositorio;
        this.modeloProduto = modeloProduto;
        this.repositorioFornecedor = repositorioFornecedor;
        this.utilidade = util;
        this.view = view;
        this.view.btnRegistrarProduto.addActionListener(this);
        this.view.lblNovaCompra.addMouseListener(this);
        this.view.btnNovoProduto.addActionListener(this);
        this.view.btnModificarProduto.addActionListener(this);
        this.view.menuEliminarProduto.addActionListener(this);
        this.view.menuAtivarProduto.addActionListener(this);
        this.view.TabelaProdutos.addMouseListener(this);
        this.view.lblProdutos.addMouseListener(this);
        this.view.txtCodigoNC.addKeyListener(this);
        this.view.txtQuantidadeNC.addKeyListener(this);
        this.view.txtPrecoNC.addKeyListener(this);
        this.view.TabelaNovaCompra.addKeyListener(this);
        this.view.txtProcurarProdutomenu.addKeyListener(this);
        this.view.btnGerarCompra.addActionListener(this);
        this.view.btnCancelarCompra.addActionListener(this);
        this.view.lblCompras.addMouseListener(this);
        this.view.menuVerCompra.addActionListener(this);
        this.view.menuEliminarCompra.addActionListener(this);
        this.view.txtProcurarCompra.addKeyListener(this);
        this.view.menuEliminarItemCompra.addActionListener(this);
        view.lblGSNC.setVisible(false);
        preencherTabelaProdutos();
        preencherTabelaCompras();
        ajustarTabelaProdutos();
        ajustarTabelaNovaCompra();
        AutoCompleteDecorator.decorate(view.cbxFornecedorNC);
    }

    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.btnRegistrarProduto){
            if(view.txtDescricaoProduto.getText().equals("") 
                || view.txtCodigoProduto.getText().equals("")
                || view.txtPrecoCompra.getText().equals("")
                || view.txtPrecoVenda.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Por favor, complete todos os campos");
            }else{
                if (repositorio.verificarCodigo(view.txtCodigoProduto.getText()) == true){
                    modeloProduto = repositorio.verProdutoCodigo(view.txtCodigoProduto.getText());
                    view.txtCodigoProduto.setText(modeloProduto.getCodigo());
                    view.txtDescricaoProduto.setText(modeloProduto.getDescricao());
                    view.txtPrecoCompra.setText(utilidade.formatoPrecio(modeloProduto.getCusto_compra()));
                    view.txtPrecoVenda.setText(utilidade.formatoPrecio(modeloProduto.getPreco_venda()));
                    view.cbxCategoria.setSelectedItem(new ModCombo(modeloProduto.getId_categoria(), modeloProduto.getCategoria(),""));
                    view.cbxFornecedor.setSelectedItem(new ModCombo(modeloProduto.getId_fornecedor(), modeloProduto.getFornecedor(),""));
                    view.cbxMarca.setSelectedItem(new ModCombo(modeloProduto.getId_marca(), modeloProduto.getMarca(),""));
                    JOptionPane.showMessageDialog(null, "O codigo já exite no banco de dados");
                    view.txtCodigoProduto.requestFocus();
                }else{
                    modeloProduto.setCodigo(view.txtCodigoProduto.getText());
                    modeloProduto.setDescricao(view.txtDescricaoProduto.getText());
                    modeloProduto.setPreco_venda(utilidade.convertirFloat(view.txtPrecoVenda.getText()));
                    modeloProduto.setCusto_compra(utilidade.convertirFloat(view.txtPrecoCompra.getText()));
                    ModCombo itemFornecedor = (ModCombo) view.cbxFornecedor.getSelectedItem();
                    ModCombo itemMarca = (ModCombo) view.cbxMarca.getSelectedItem();
                    ModCombo itemCategoria = (ModCombo) view.cbxCategoria.getSelectedItem();
                    modeloProduto.setId_fornecedor(itemFornecedor.getId());
                    modeloProduto.setId_marca(itemMarca.getId());
                    modeloProduto.setId_categoria(itemCategoria.getId());
                    if(repositorio.insertarProduto(modeloProduto) == true){
                        limparTabelaProdutos();
                        preencherTabelaProdutos();
                        limparCamposProduto();
                        JOptionPane.showMessageDialog(null, "Produto cadastrado!");
                    }else{
                        JOptionPane.showConfirmDialog(null, "ERRO: Ocorreu um erro ao registrar produto");
                    }
                }
                
            }
        }else if(e.getSource() == view.btnModificarProduto){
            if(view.txtDescricaoProduto.getText().equals("") 
                || view.txtCodigoProduto.getText().equals("")
                || view.txtPrecoCompra.getText().equals("")
                || view.txtPrecoVenda.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Por favor, complete todos os campos");
            }else{
                modeloProduto.setCodigo(view.txtCodigoProduto.getText());
                modeloProduto.setDescricao(view.txtDescricaoProduto.getText());
                modeloProduto.setPreco_venda(utilidade.convertirFloat(view.txtPrecoVenda.getText()));
                modeloProduto.setCusto_compra(utilidade.convertirFloat(view.txtPrecoCompra.getText()));
                ModCombo itemFornecedor = (ModCombo) view.cbxFornecedor.getSelectedItem();
                ModCombo itemMarca = (ModCombo) view.cbxMarca.getSelectedItem();
                ModCombo itemCategoria = (ModCombo) view.cbxCategoria.getSelectedItem();
                modeloProduto.setId_fornecedor(itemFornecedor.getId());
                modeloProduto.setId_marca(itemMarca.getId());
                modeloProduto.setId_categoria(itemCategoria.getId());
                modeloProduto.setId_produto(Integer.parseInt(view.txtidProduto.getText()));
                if(repositorio.modificarProduto(modeloProduto) == true){
                    limparTabelaProdutos();
                    preencherTabelaProdutos();
                    limparCamposProduto();
                    JOptionPane.showMessageDialog(null, "Produto Modificado!");
                    view.btnRegistrarProduto.setEnabled(true);
                }else{
                    JOptionPane.showConfirmDialog(null, "ERRO: Ocorreu um erro ao modificar Produto");
                }
            }
        }else if(e.getSource() == view.menuEliminarProduto){
            if(view.txtidProduto.toString().equals("0")){
                JOptionPane.showMessageDialog(null, "Selecione um Produto para desativar"); 
            }else {
                int id = Integer.parseInt(view.txtidProduto.getText());
                if(repositorio.acao("Inativo", id)){
                    limparTabelaProdutos();
                    preencherTabelaProdutos();
                    limparCamposProduto();
                    JOptionPane.showMessageDialog(null, "Produto desativado!");
                }else{
                    JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao desativar Produto");
                }
            }
        }else if(e.getSource() == view.menuAtivarProduto){
            if(view.txtidProduto.toString().equals("0")){
                JOptionPane.showMessageDialog(null, "Selecione um Produto para Ativar"); 
            }else {
                int id = Integer.parseInt(view.txtidProduto.getText());
                if(repositorio.acao("Ativo", id)){
                    limparTabelaProdutos();
                    preencherTabelaProdutos();
                    limparCamposProduto();
                    JOptionPane.showMessageDialog(null, "Produto Ativo!");
                }else{
                    JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro ao ativar Produto");
                }
            }
        }else if(e.getSource() == view.btnGerarCompra){
            ModCombo fornecedor = (ModCombo) view.cbxFornecedorNC.getSelectedItem();
            int id_fornecedor = fornecedor.getId();
            //total armazena o total da compra
            float total = utilidade.convertirFloat(view.lblValorTotalNC.getText());
            if (total != 0) {
                insertarCompra(id_fornecedor,total);
                limparCamposNovaCompra();
                view.txtIdNc.setEnabled(true);       
                view.txtProdutoNC.setEnabled(true);
                view.txtTotalNC.setEnabled(true);
                view.lblGSNC.setVisible(false);
                view.cbxFornecedorNC.setSelectedIndex(0);
            }else{
                JOptionPane.showMessageDialog(null, "Adicione um Produto!");
                view.txtCodigoNC.requestFocus();
            }
        }else if(e.getSource() == view.btnNovoProduto){
            limparCamposProduto();
            view.btnRegistrarProduto.setEnabled(true);
            view.cbxCategoria.setSelectedIndex(0);
            view.cbxFornecedor.setSelectedIndex(0);
            view.cbxMarca.setSelectedIndex(0);
        }else if(e.getSource() == view.btnCancelarCompra){
            limparCamposNovaCompra();
            limparTabelaNovaCompra();
            view.lblGSNC.setVisible(false);
            view.cbxFornecedorNC.setSelectedIndex(0);
            view.txtCodigoNC.requestFocus();
        }else if(e.getSource() == view.menuVerCompra){
            String id_compra = view.TabelaCompras.getValueAt(view.TabelaCompras.getSelectedRow(), 0).toString();
            if (id_compra.equals("")){
                JOptionPane.showMessageDialog(null, "Seleccione um registro para ver!");
            }else{
                repositorio.gerarFactura(Integer.parseInt(id_compra));
            }
        }else if(e.getSource() == view.menuEliminarCompra){
            String id_compra = view.TabelaCompras.getValueAt(view.TabelaCompras.getSelectedRow(), 0).toString();
            if (id_compra.equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione Nota!");
            }else if(repositorio.eliminarCompra(Integer.parseInt(id_compra)) == true){
                limparTabelaCompras();
                preencherTabelaCompras();
                JOptionPane.showMessageDialog(null, "Nota eliminada!");
            }
        }else if(e.getSource() == view.menuEliminarItemCompra){
            int fila = view.TabelaNovaCompra.getSelectedRow();
            tabelaNC.removeRow(fila);
            view.TabelaNovaCompra.setModel(tabelaNC);
            calcularCompra();
        }  
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == view.TabelaProdutos){
            int fila = view.TabelaProdutos.rowAtPoint(e.getPoint());
            view.txtidProduto.setText(view.TabelaProdutos.getValueAt(fila, 0).toString());
            modeloProduto = repositorio.verProduto(Integer.parseInt(view.txtidProduto.getText()));
            view.txtDescricaoProduto.setText(modeloProduto.getDescricao());
            view.txtPrecoCompra.setText(utilidade.formatoCosto((float) modeloProduto.getCusto_compra()));
            view.txtPrecoVenda.setText(utilidade.formatoPrecio((float) modeloProduto.getPreco_venda()));
            view.txtCodigoProduto.setText(modeloProduto.getCodigo());
            view.cbxCategoria.setSelectedItem(new ModCombo(modeloProduto.getId_categoria(), modeloProduto.getCategoria(),""));
            view.cbxFornecedor.setSelectedItem(new ModCombo(modeloProduto.getId_fornecedor(), modeloProduto.getFornecedor(),""));
            view.cbxMarca.setSelectedItem(new ModCombo(modeloProduto.getId_marca(), modeloProduto.getMarca(),""));
            view.btnRegistrarProduto.setEnabled(false);
        }else if(e.getSource() == view.lblProdutos){
            view.TabbedPanePrincipal.setSelectedIndex(0);
            view.txtProcurarProdutomenu.requestFocus();
            limparTabelaProdutos();
            preencherTabelaProdutos();
            view.btnRegistrarProduto.setEnabled(true);
        }else if(e.getSource() == view.lblNovaCompra){
            view.TabbedPanePrincipal.setSelectedIndex(7);
            view.txtCodigoNC.requestFocus();
        }else if(e.getSource() == view.lblCompras){
            view.txtProcurarCompra.setText("");
            limparTabelaCompras();
            preencherTabelaCompras();
            view.txtProcurarCompra.requestFocus();
            view.TabbedPanePrincipal.setSelectedIndex(9);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getSource() == view.txtCodigoNC){
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (view.txtCodigoNC.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese o codigo do produto");
                }else{
                    String codigoPro = view.txtCodigoNC.getText();
                    modeloProduto = repositorio.verProdutoCodigo(codigoPro);
                    view.txtIdNc.setText(returnID(modeloProduto.getId_produto()));
                    view.txtProdutoNC.setText(modeloProduto.getDescricao());
                    view.txtPrecoNC.setText(utilidade.formatoPrecio((float) modeloProduto.getCusto_compra()));
                    view.txtQuantidadeNC.setText("1");
                    view.txtQuantidadeNC.requestFocus();
                }
            }
        }else if(e.getSource() == view.txtQuantidadeNC){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    if(view.txtQuantidadeNC.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Quantidade deve ser maior a 0!");
                    }else{
                        int novaQuantidade = Integer.parseInt(view.txtQuantidadeNC.getText());
                        String descricaoProdNC = view.txtProdutoNC.getText();
                        float preco_compra = utilidade.convertirFloat(view.txtPrecoNC.getText());
                        int id = Integer.parseInt(view.txtIdNc.getText());
                        if(novaQuantidade > 0){
                            if (repositorio.verEstadoProduto(Integer.parseInt(view.txtIdNc.getText())).equals("Inativo")) {
                                JOptionPane.showMessageDialog(null, "Produto Inativo!");
                            }else{
                                tabelaNC = (DefaultTableModel) view.TabelaNovaCompra.getModel();
                                ArrayList listaCompras = new ArrayList();
                                int item = 1;
                                listaCompras.add(item);
                                listaCompras.add(id);
                                if (verificaProdutoTabela(id)==true) {
                                    listaCompras.add(descricaoProdNC);
                                    listaCompras.add(quantidade + novaQuantidade);
                                    listaCompras.add(preco_compra);
                                    listaCompras.add((quantidade + novaQuantidade) * preco_compra);
                                    Object[] obj = new Object[5];
                                    obj[0] = returnID((int) listaCompras.get(1));
                                    obj[1] = listaCompras.get(2);
                                    obj[2] = listaCompras.get(3);
                                    obj[3] = utilidade.formatoCosto((float) listaCompras.get(4));
                                    obj[4] = utilidade.formatoCosto((float) listaCompras.get(5));
                                    tabelaNC.addRow(obj);
                                }else{
                                    listaCompras.add(descricaoProdNC);
                                    listaCompras.add(novaQuantidade);
                                    listaCompras.add(preco_compra);
                                    listaCompras.add(novaQuantidade* preco_compra);
                                    Object[] obj = new Object[5];
                                    obj[0] = returnID((int) listaCompras.get(1));
                                    obj[1] = listaCompras.get(2);
                                    obj[2] = listaCompras.get(3);
                                    obj[3] = utilidade.formatoCosto((float) listaCompras.get(4));
                                    obj[4] = utilidade.formatoCosto((float) listaCompras.get(5));
                                    tabelaNC.addRow(obj);
                                }
                                ajustarTabelaNovaCompra();
                                limparCamposNovaCompra();
                                calcularCompra();
                                view.txtIdNc.setEnabled(true);       
                                view.txtProdutoNC.setEnabled(true);
                                view.txtTotalNC.setEnabled(true);
                                view.txtCodigoNC.requestFocus();   
                            }
                        }
                    }
                }
        }else if(e.getSource() == view.txtProcurarCompra) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                limparTabelaCompras();
                preencherTabelaCompras();
            } 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == view.txtQuantidadeNC) {
            int quantidade;
            float preco_compra;
            if (view.txtQuantidadeNC.getText().equals("")) {
                preco_compra = utilidade.convertirFloat(view.txtPrecoNC.getText());
                quantidade = 1;
                view.txtTotalNC.setText(utilidade.formatoPrecio(preco_compra));
            }else{
                quantidade = Integer.parseInt(view.txtQuantidadeNC.getText());
                preco_compra = utilidade.convertirFloat(view.txtPrecoNC.getText());
                view.txtTotalNC.setText(utilidade.formatoCosto(quantidade * preco_compra));   
            }
        }else if(e.getSource() == view.txtProcurarProdutomenu){
            limparTabelaProdutos();
            preencherTabelaProdutos();
            view.TabbedPanePrincipal.setSelectedIndex(0);
        }
    }
    
    private void limparTabelaProdutos(){
        for (int i = 0; i < tabelaProduto.getRowCount(); i++) {
            tabelaProduto.removeRow(i);
            i = i-1;
        }
    }
    
    private void limparTabelaNovaCompra(){
        for (int i = 0; i < tabelaNC.getRowCount(); i++) {
            tabelaNC.removeRow(i);
            i = i-1;
        }
    }
    
    private void limparTabelaCompras(){
        for (int i = 0; i < tabelaCompra.getRowCount(); i++) {
            tabelaCompra.removeRow(i);
            i = i-1;
        }
    }
    
    private void ajustarTabelaProdutos(){
        
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        
        //Insere o header da tabelaProdutos
        JTableHeader headerTblProdutos= view.TabelaProdutos.getTableHeader();
        headerTblProdutos.setDefaultRenderer(new ModHeader());
        view.TabelaProdutos.setTableHeader(headerTblProdutos);
        
        view.TabelaProdutos.getColumn(tabelaProduto.getColumnName(0)).setCellRenderer(centro);
        view.TabelaProdutos.getColumn(tabelaProduto.getColumnName(3)).setCellRenderer(direita);
        view.TabelaProdutos.getColumn(tabelaProduto.getColumnName(4)).setCellRenderer(centro);
        view.TabelaProdutos.getColumn(tabelaProduto.getColumnName(5)).setCellRenderer(boxEstado);
        view.TabelaProdutos.getColumn(tabelaProduto.getColumnName(1)).setPreferredWidth(250);
        view.TabelaProdutos.getColumn(tabelaProduto.getColumnName(2)).setPreferredWidth(500);
        view.TabelaProdutos.setModel(tabelaProduto);
    }
    
    private void ajustarTabelaNovaCompra(){
        
        //Adiciona o header da tabelaNovaCompra
        JTableHeader headerTblNovaCompra = view.TabelaNovaCompra.getTableHeader();
        headerTblNovaCompra.setDefaultRenderer(new ModHeader());
        view.TabelaNovaCompra.setTableHeader(headerTblNovaCompra);
        
        view.TabelaNovaCompra.getColumn("Id-Produto").setCellRenderer(centro);
        view.TabelaNovaCompra.getColumn("Quantidade").setCellRenderer(centro);
        view.TabelaNovaCompra.getColumn("Total").setCellRenderer(direita);
        view.TabelaNovaCompra.getColumn("Preço").setCellRenderer(direita);
        view.TabelaNovaCompra.getColumn("Descrição").setPreferredWidth(1100);
    }
    
    
    private void limparCamposProduto(){
        view.txtidProduto.setText("0");
        view.txtCodigoProduto.setText("");
        view.txtDescricaoProduto.setText("");
        view.txtPrecoCompra.setText("");
        view.txtPrecoVenda.setText("");
        view.cbxFornecedor.setSelectedIndex(0);
        view.cbxMarca.setSelectedIndex(0);
        view.cbxCategoria.setSelectedIndex(0);
        view.txtCodigoProduto.requestFocus();
    }
    
    private void limparCamposNovaCompra(){
        view.txtCodigoNC.setText("");
        view.txtProdutoNC.setText("");
        view.txtIdNc.setText("");
        view.txtPrecoNC.setText("");
        view.txtQuantidadeNC.setText("");
        view.txtTotalNC.setText("");
        view.lblValorTotalNC.setText("");
    }
    
    //Verifica se já exite o produto na tabelaNovaCompra
    public boolean verificaProdutoTabela(int id_produto){
        
        int filas = view.TabelaNovaCompra.getRowCount(); 
        for (int i = 0; i < filas; i++) {
            int id_produto_tbl = Integer.parseInt(view.TabelaNovaCompra.getValueAt(i, 0).toString());
            if (id_produto_tbl == id_produto) {
                quantidade = Integer.parseInt(tabelaNC.getValueAt(i, 2).toString());
                tabelaNC.removeRow(i);
                return true;
            }
        }
        return false;
    }
    
    //Calcula o valor da Compra com as Colunas quantidade e preço da tabelaNovaCompra
    private void calcularCompra(){
        float total = 0;
        int filas = view.TabelaNovaCompra.getRowCount(); 
        for (int i = 0; i < filas; i++) {
            float total_tbl = utilidade.convertirFloat(view.TabelaNovaCompra.getValueAt(i, 3).toString());
            int qtd = Integer.parseInt(view.TabelaNovaCompra.getValueAt(i, 2).toString());
            total = total+(total_tbl*qtd);
        }
        view.lblValorTotalNC.setText(utilidade.formatoPrecio(total));
        view.lblGSNC.setVisible(true);
    }
    
    //Solicita registro de compra ao Banco de Datos
    private void insertarCompra(int id_fornecedor, float total){
        
        if(repositorio.insertarCompras(id_fornecedor, total)){
            //id_compra armazena Id da ultima compra
            int id_compra = repositorio.verCompraId();
            for (int i = 0; i < view.TabelaNovaCompra.getRowCount(); i++) {
                String nro_factura = returnNro_factura(id_compra);
                float preco = utilidade.convertirFloat(view.TabelaNovaCompra.getValueAt(i, 3).toString());
                int quantidade = Integer.parseInt(view.TabelaNovaCompra.getValueAt(i, 2).toString());
                int id_produto = Integer.parseInt(view.TabelaNovaCompra.getValueAt(i, 0).toString());
                float subTotal = preco * quantidade;
                //Insere no banco os detalhes da Compra
                repositorio.insertarItemCompra(id_compra,id_produto,preco,quantidade,subTotal,nro_factura);
                //Verifica o Stock do Produto
                modeloProduto = repositorio.verProduto(id_produto);
                int stockAtual = modeloProduto.getStock()+quantidade;
                //Modifica Stock do Produto
                repositorio.modificarStock(stockAtual,id_produto);
            }
            limparTabelaNovaCompra();
            JOptionPane.showMessageDialog(null, "Compra Realizada");
            //Emite Factura
            repositorio.gerarFactura(id_compra);
            view.lblValorTotalNC.setText("");
        } 
    }
    
    //Carrega a TabelaProdutos 
    private void preencherTabelaProdutos(){
        
        //Preenche a tabelaProdutos 
        ArrayList<ModProduto> list = repositorio.listarProdutos(view.txtProcurarProdutomenu.getText());
        tabelaProduto = (DefaultTableModel) view.TabelaProdutos.getModel();
        Object[] objeto = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            objeto[0] = returnID(list.get(i).getId_produto());
            objeto[1] = list.get(i).getCodigo();
            objeto[2] = list.get(i).getDescricao();
            objeto[3] = utilidade.formatoPrecio(list.get(i).getPreco_venda());
            objeto[4] = list.get(i).getStock();
            objeto[5] = list.get(i).getEstado ();
            tabelaProduto.addRow(objeto);
        }
        view.TabelaProdutos.setModel(tabelaProduto);
    }
    
    //Carrega a TabelaCompras
    private void preencherTabelaCompras(){
        
        //Adiciona o header da tabelaCompras
        JTableHeader header = view.TabelaCompras.getTableHeader();
        header.setDefaultRenderer(new ModHeader());
        view.TabelaCompras.setTableHeader(header);
        
        //Preenche tabelaCompras
        ArrayList<ModVenda> list = repositorio.listarCompras(String.valueOf(view.txtProcurarCompra.getText()));
        tabelaCompra = (DefaultTableModel) view.TabelaCompras.getModel();
        ModFornecedor fornecedor = new ModFornecedor();
        Object[] objeto = new Object[4];
        for (int i = 0; i < list.size(); i++) {
            objeto[0] = returnNro_factura(list.get(i).getId_venda());
            fornecedor = repositorioFornecedor.verFornecedor(list.get(i).getId_cliente());
            objeto[1] = fornecedor.getNome();
            objeto[2] = utilidade.formatoCosto(list.get(i).getTotal());
            objeto[3] = dateFormat.format(list.get(i).getData());
            tabelaCompra.addRow(objeto);
        }
        
        //Ajusta a tabelaCompras
        view.TabelaCompras.getColumn(tabelaCompra.getColumnName(0)).setCellRenderer(centro);
        view.TabelaCompras.getColumn(tabelaCompra.getColumnName(1)).setPreferredWidth(500);
        view.TabelaCompras.getColumn(tabelaCompra.getColumnName(2)).setCellRenderer(direita);
        view.TabelaCompras.getColumn(tabelaCompra.getColumnName(3)).setCellRenderer(centro);
        view.TabelaCompras.setModel(tabelaCompra);
    }
    
    //Personaliza o Id
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
    
    //Personaliza o Nro_factura
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



