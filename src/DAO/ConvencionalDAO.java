package DAO;

import conex.conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.ModConvencional;

/**
 *
 * @author ALEX
 */
public class ConvencionalDAO extends conexao {
    
    PreparedStatement ps;
    Connection conex;
    ResultSet rs;
    
    public boolean insertarConvecional(ModConvencional modeloConvencional){
        
        String sql = "INSERT INTO convencional(id_cliente,total)values(?,?)";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, modeloConvencional.getId_cliente());
            ps.setFloat(2, modeloConvencional.getTotal());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }finally{
            setFecharConexao(conex);
        }   
    }
    
    public boolean insertarItemConvecional(int id_convencional, int  id_produto, float preco, int quantidade){
        
        String sql = "INSERT INTO detalhe_convencional(id_convencional, id_produto, preco, quantidade)values(?,?,?,?)";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id_convencional);
            ps.setInt(2, id_produto);
            ps.setFloat(3, preco);
            ps.setInt(4, quantidade);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }finally{
            setFecharConexao(conex);
        }   
    }
    
    public void eliminarItemConvecional(int id_convencional, int  id_produto){
        
        String sql = "DELETE FROM  detalhe_convencional WHERE id_convencional = ? AND id_produto = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id_convencional);
            ps.setInt(2, id_produto);
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public boolean modificarConvencional(ModConvencional modeloConvencional){
        
        String sql = "UPDATE convencional SET id_cliente = ?, total = ? WHERE id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, modeloConvencional.getId_cliente());
            ps.setFloat(2, modeloConvencional.getTotal());
            ps.setInt(3, modeloConvencional.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }finally{
            setFecharConexao(conex);
        }   
    }
    
    public boolean verificaConvencionalItems(int id_convencional){
        
        String sql = "SELECT * FROM detalhe_convencional WHERE id_convencional = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id_convencional);
            rs = ps.executeQuery();
            if (rs.next() == true) {
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return false;
    }
    
    public int verConvencionalId(){
        
        String sql = "SELECT MAX(id) AS id FROM convencional";
        int id_convencional = 0;
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                id_convencional = rs.getInt("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return id_convencional;
    }
    
    public ArrayList listarConvencional(String textoBusca){
        ArrayList<ModConvencional> listaConvencional =  new ArrayList();
        String sql = "SELECT v.*, c.nome as cliente FROM convencional v,  clientes c WHERE v.id_cliente = c.id ORDER BY v.estado DESC" ;
        String procurar = "SELECT v.*, c.nome as cliente FROM convencional v, clientes c WHERE v.id_cliente = c.id AND c.nome LIKE '%"+textoBusca+"%'";
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
                ModConvencional convencional = new ModConvencional();
                convencional.setId(rs.getInt("id"));
                convencional.setData(rs.getDate("data"));
                convencional.setTotal(rs.getFloat("total"));
                convencional.setId_cliente(rs.getInt("id_cliente"));
                convencional.setEstado(rs.getString("estado"));
                convencional.setCliente(rs.getString("cliente"));
                listaConvencional.add(convencional);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return listaConvencional;
    }
    
    public ArrayList listarItemsConvencional(int id_convencional){
        ArrayList<ModConvencional> listaItems =  new ArrayList();
        String sql = "SELECT d.*, p.descricao as produto FROM detalhe_convencional d, produto p WHERE d.id_produto = p.id AND d.id_convencional = ?";
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id_convencional);
            rs = ps.executeQuery();
            while(rs.next() == true){
                ModConvencional convencional = new ModConvencional();
                convencional.setId(rs.getInt("id_convencional"));
                convencional.setId_produto(rs.getInt("id_produto"));
                convencional.setTotal(rs.getFloat("preco"));
                convencional.setId_cliente(rs.getInt("quantidade"));
                convencional.setProduto(rs.getString("produto"));
                listaItems.add(convencional);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return listaItems;
    }
    
    public boolean eliminarConvencional(int id_convencional){
        
        String sql = "DELETE FROM convencional where id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id_convencional);
            ps.execute();
            return true;
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public void eliminarItemsConvencional(int id_convencional){
        
        String sql = "DELETE FROM detalhe_convencional where id_convencional = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id_convencional);
            ps.execute();
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public int verCliente(int id_convencional){
        
        String sql = "SELECT id_cliente FROM convencional WHERE id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id_convencional);
            rs = ps.executeQuery();
            if (rs.next() == true) {
                return rs.getInt("id_cliente");
            } 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return 1;
    }
}
