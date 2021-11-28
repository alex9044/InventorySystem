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
import model.ModMarca;

/**
 *
 * @author ALEX PC
 */
public class MarcaDAO extends conexao{
    
    PreparedStatement ps;
    Connection conex;
    ResultSet rs;
       
    //INSERIR UM NOVO REGISTRO
    public boolean registrarMarca(ModMarca modeloMarca){
        
        String sql = "INSERT INTO marcas (marca)VALUES(?)";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, modeloMarca.getDescricao());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        } finally {
            setFecharConexao(conex);
        }
        
    }
     //RETORNA UMA LISTA DE MARCAS
    public ArrayList listarMarcas(String textoBusca){
        
        ArrayList<ModMarca> listaMarcas =  new ArrayList();
        String sql = "SELECT * FROM marcas ORDER BY estado ASC";
        String procurar = "SELECT * FROM marcas WHERE marca LIKE '%"+textoBusca+"%'";
        
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
                ModMarca marca = new ModMarca();
                marca.setId_marca(rs.getInt("id"));
                marca.setDescricao(rs.getString("marca"));
                marca.setEstado(rs.getString("estado"));
                listaMarcas.add(marca);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return listaMarcas;
    }
    //MODIFICAR UM REGISTRO
    public boolean modificarMarca(ModMarca modeloMarca){
        
        String sql = "UPDATE marcas SET marca=? WHERE id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, modeloMarca.getDescricao());
            ps.setInt(2, modeloMarca.getId_marca());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    //DESATIVAR, ATIVAR REGISTRO DA TABELAMARCA
    public boolean acao(String estado, int id_marca){
        
        String sql = "UPDATE marcas SET estado=? WHERE id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, id_marca);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    
    //Eliminar marca do banco de dados
    public boolean eliminarMarca(int id_marca){
        
        String sql = "DELETE FROM marcas WHERE id ="+id_marca;
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        
    }
}
