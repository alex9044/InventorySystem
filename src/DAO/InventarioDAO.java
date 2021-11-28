/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import conex.conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.ModInventario;
/**
 *
 * @author ALEX PC
 */
public class InventarioDAO extends conexao {
    
    PreparedStatement ps;
    Connection conex;
    ResultSet rs;  
    
    public ArrayList listarInventario(){
        
        ArrayList<ModInventario> listaInventario =  new ArrayList();
        String sql = "select i.id, i.data as data, i.motivo as motivo, i.id_produto, p.descricao as descricao, i.custo as custo, i.stock_anterior, i.stock_atual, i.stock_atual - i.stock_anterior as diferenca from produto p, inventario i where i.id_produto = p.id";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next() == true){
                ModInventario inventario =  new ModInventario();
                inventario.setId(rs.getInt("id"));
                inventario.setMotivo(rs.getString("motivo"));
                inventario.setId_produto(rs.getString("descricao"));
                inventario.setStock_atual(rs.getInt("stock_atual"));
                inventario.setStock_anterior(rs.getInt("stock_anterior"));
                inventario.setDiferenca(rs.getInt("diferenca"));
                inventario.setCusto(rs.getFloat("custo"));
                inventario.setData(rs.getDate("data"));
                listaInventario.add(inventario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally {
            setFecharConexao(conex);
        }
        return listaInventario;
    }
      
    public boolean modificarStock(int id_produto,int novo_stock){
        
        String sql = "update produto set stock = ? where id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, novo_stock);
            ps.setInt(2, id_produto);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
            return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public void processarInventario(int id_produto, int stock_anterior, String motivo,int novo_stock, float custo){
        
        String sql = "insert into inventario(data, id_produto, stock_anterior, stock_atual, custo, motivo)values(curdate(),?,?,?,?,?)";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id_produto);
            ps.setFloat(2, stock_anterior);
            ps.setFloat(3, novo_stock);
            ps.setFloat(4, custo);
            ps.setString(5, motivo); 
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public boolean deletarInventario(int id_inventario){
        
        String sql = "DELETE FROM inventario WHERE  id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id_inventario);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error ao eliminar Inventario!: " + e);
            return false;
        }
    }
}
