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
import model.ModCategoria;


/**
 *
 * @author ALEX PC
 */
public class CategoriaDAO extends conexao{
    
    PreparedStatement ps;
    Connection conex;
    ResultSet rs;
       
    //INSERIR UM NOVO REGISTRO
    public boolean registrarCategoria(ModCategoria modeloCategoria){
        
        String sql = "INSERT INTO categorias (categoria)VALUES(?)";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, modeloCategoria.getDescricao());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        } finally {
            setFecharConexao(conex);
        }
        
    }
     //RETORNA UMA LISTA DE CATEGORIAS
     public ArrayList listarCategorias(String textoBusca){
        
        ArrayList<ModCategoria> listaCategorias =  new ArrayList();
        String sql = "SELECT * FROM categorias ORDER BY estado ASC";
        String procurar = "SELECT * FROM categorias WHERE categoria LIKE '%"+textoBusca+"%'";
        
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
                ModCategoria categoria = new ModCategoria();
                categoria.setId_categoria(rs.getInt("id"));
                categoria.setDescricao(rs.getString("categoria"));
                categoria.setEstado(rs.getString("estado"));
                listaCategorias.add(categoria);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return listaCategorias;
    }
    //MODIFICAR UM REGISTRO
    public boolean modificarCategoria(ModCategoria modeloCategoria){
        
        String sql = "UPDATE categorias SET categoria=? WHERE id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, modeloCategoria.getDescricao());
            ps.setInt(2, modeloCategoria.getId_categoria());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    //DESATIVAR, ATIVAR REGISTRO DA TABELACATEGORIA
    public boolean acao(String estado, int id_categoria){
        
        String sql = "UPDATE categorias SET estado=? WHERE id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, id_categoria);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            setFecharConexao(conex);
        }
    }
}
