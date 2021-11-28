
package DAO;

import conex.conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import model.ModProduto;
import model.ModVenda;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;



/**
 *
 * @author ALEX PC
 */
public class ProdutoDAO extends conexao{
    
    PreparedStatement ps;
    Connection conex;
    ResultSet rs;

    
    public boolean insertarProduto(ModProduto produto){
        
        String sql = "INSERT INTO produto (codigo,descricao,custo,preco,id_fornecedor,id_marca,id_categoria)VALUES(?,?,?,?,?,?,?)";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, produto.getCodigo());
            ps.setString(2, produto.getDescricao());
            ps.setDouble(3, produto.getCusto_compra());
            ps.setDouble(4, produto.getPreco_venda());
            ps.setInt(5, produto.getId_fornecedor());
            ps.setInt(6, produto.getId_marca());
            ps.setInt(7, produto.getId_categoria());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public ArrayList listarProdutos(String textoBusca){
        ArrayList<ModProduto> listaProdutos =  new ArrayList();
        String sql = "SELECT * FROM produto ORDER BY estado ASC";
        String procurar = "SELECT * FROM produto WHERE codigo LIKE '%"+textoBusca+"%' OR descricao LIKE '%"+textoBusca+"%'";
        
        try {
            conex = getAbrirConexao();
            if(textoBusca.equals("")){
                ps = conex.prepareStatement(sql);
                rs = ps.executeQuery();
            }else{
                ps = conex.prepareStatement(procurar);
                rs = ps.executeQuery();
            } 
            
            while(rs.next() == true){
                ModProduto produto = new ModProduto();
                produto.setId_produto(rs.getInt("id"));
                produto.setCodigo(rs.getString("codigo"));
                produto.setPreco_venda(rs.getFloat("preco"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setStock(rs.getInt("stock"));
                produto.setEstado(rs.getString("estado"));
                listaProdutos.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return listaProdutos;
    }
    
    public boolean modificarProduto(ModProduto produto){
        
        String sql = "UPDATE produto SET codigo = ?,descricao = ?,custo = ? ,preco = ?, id_fornecedor = ?, id_marca =?, id_categoria =? WHERE id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, produto.getCodigo());
            ps.setString(2, produto.getDescricao());
            ps.setDouble(3, produto.getCusto_compra());
            ps.setDouble(4, produto.getPreco_venda());
            ps.setInt(5, produto.getId_fornecedor());
            ps.setInt(6, produto.getId_marca());
            ps.setInt(7, produto.getId_categoria());
            ps.setInt(8, produto.getId_produto());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public boolean acao(String estado, int id_produto){
        
        String sql = "UPDATE produto SET estado = ? WHERE id =?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, id_produto);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public String verEstadoProduto(int id_produto){
        
        String estado = "";
        String sql = "SELECT estado FROM produto WHERE id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id_produto);
            rs = ps.executeQuery();
            if (rs.next() == true) {
                estado = rs.getString("estado");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return estado;
    }
    
    public boolean eliminarCompra(int id_compra){
        
        String sql = "DELETE FROM compras where id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id_compra);
            ps.execute();
            return true;
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public ModProduto verProduto(int idProduto){
        
        ModProduto produto = new ModProduto();
        String sql = "SELECT p.*, f.id ,f.fornecedor ,m.id ,m.marca, c.id, c.categoria "
                    + "FROM produto p INNER JOIN fornecedores f ON p.id_fornecedor = f.id \n" 
                    +"INNER JOIN marcas m ON p.id_marca = m.id INNER JOIN categorias c "
                    + "ON p.id_categoria = c.id WHERE p.id = ?";
        try{
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, idProduto);
            rs = ps.executeQuery();
            if (rs.next() == true) {
                produto.setCodigo(rs.getString("codigo"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setCusto_compra(rs.getFloat("custo"));
                produto.setPreco_venda(rs.getFloat("preco"));
                produto.setStock(rs.getInt("stock"));
                produto.setId_fornecedor(rs.getInt("id_fornecedor"));
                produto.setId_marca(rs.getInt("id_marca"));
                produto.setId_categoria(rs.getInt("id_categoria"));
                produto.setFornecedor(rs.getString("fornecedor"));
                produto.setMarca(rs.getString("marca"));
                produto.setCategoria(rs.getString("categoria"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return produto;
    }
    
    public ModProduto verProdutoCodigo(String codigoPro){
        
        ModProduto produto = new ModProduto();
        String sql = "SELECT * FROM produto WHERE codigo = ?";
        try{
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, codigoPro);
            rs = ps.executeQuery();
            if (rs.next()) {
                produto.setCodigo(rs.getString("codigo"));
                produto.setId_produto(rs.getInt("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setCusto_compra(rs.getFloat("custo"));
                produto.setStock(rs.getInt("stock"));
                produto.setPreco_venda(rs.getFloat("preco"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return produto;
    }
    
    public boolean verificarCodigo(String codigoPro){
        
        String sql = "SELECT * FROM produto WHERE codigo = ?";
        
         try{
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, codigoPro);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public boolean insertarCompras(int id_fornecedor, float total){
        
        String sql = "INSERT INTO compras (id_fornecedor,total)values(?,?)";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id_fornecedor);
            ps.setFloat(2, total);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }finally{
            setFecharConexao(conex);
        }   
    }
    
    public boolean insertarItemCompra(int id_compra, int  id_produto, float preco, int quantidade , float subtotal, String nro_factura){
        
        String sql = "INSERT INTO detalhe_compra (id_compra, id_produto, preco, quantidade, subtotal, nro_factura)values(?,?,?,?,?,?)";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id_compra);
            ps.setInt(2, id_produto);
            ps.setFloat(3, preco);
            ps.setInt(4, quantidade);
            ps.setFloat(5, subtotal);
            ps.setString(6, nro_factura);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public boolean modificarStock(int stock, int id_produto){
        
        String sql = "UPDATE produto SET stock = ? WHERE id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, stock);
            ps.setInt(2, id_produto);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public int verCompraId(){
        
        String sql = "SELECT MAX(id) AS id FROM compras";
        int id_compra = 0;
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                id_compra = rs.getInt("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally {
            setFecharConexao(conex);
        }
        return id_compra;
    }
    
    public void gerarFactura(int id_compra){
        try {
            conex = getAbrirConexao();
            JasperReport notaCompra = null;
            String path = "/relatorios/notaCompra.jasper";
            
            Map parametros = new HashMap();
            parametros.put("id_compra", id_compra);
            
            notaCompra =  (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            
            JasperPrint jPrint = JasperFillManager.fillReport(notaCompra, parametros, conex);
            
            JasperViewer nota = new JasperViewer(jPrint, false);
            
            nota.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            nota.setVisible(true);
            
        } catch (JRException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public ArrayList listarCompras(String textoBusca){
        
        ArrayList<ModVenda> listaCompras =  new ArrayList();
        String sql = "SELECT * FROM compras";
        String procurar = "SELECT * FROM compras WHERE id =" + textoBusca;
        
        try {
            conex = getAbrirConexao();
            if(textoBusca.equals("")){
                ps = conex.prepareStatement(sql);
                rs = ps.executeQuery();
            }else{
                ps = conex.prepareStatement(procurar);
                rs = ps.executeQuery();
            } 
            
            while(rs.next() == true){
                ModVenda compra = new ModVenda();
                compra.setId_venda(rs.getInt("id"));
                compra.setData(rs.getDate("data"));
                compra.setTotal(rs.getFloat("total"));
                compra.setId_cliente(rs.getInt("id_fornecedor"));
                listaCompras.add(compra);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            setFecharConexao(conex);
        }
        return listaCompras;
    }
}
