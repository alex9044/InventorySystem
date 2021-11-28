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
import model.ModUsuario;

/**
 *
 * @author ALEX PC
 */
public class UsuarioDAO extends conexao {
    
    PreparedStatement ps;
    Connection conex;
    ResultSet rs;
    
    public ModUsuario login(String usuario, String senha){
        
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?";
        ModUsuario us = new ModUsuario();
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, senha);
            rs = ps.executeQuery();
            if(rs.next() == true){
                us.setId_usuario(rs.getInt("id"));
                us.setUsuario(rs.getString("usuario"));
                us.setNome(rs.getString("nome"));
                us.setSenha(rs.getString("senha"));
                us.setEstado(rs.getString("estado"));
                us.setCaixa(rs.getString("caixa"));
                us.setCargo(rs.getString("cargo"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally {
            setFecharConexao(conex);
        }
        return us;
    }
    
    public boolean insertarUsuario(ModUsuario usuario){
        
        String sql = "INSERT INTO usuarios (usuario,senha,nome,caixa,cargo)VALUES(?,?,?,?,?)";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getSenha());
            ps.setString(3, usuario.getNome());
            ps.setString(4, usuario.getCaixa());
            ps.setString(5, usuario.getCargo());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally {
            setFecharConexao(conex);
        }
    }
    
    public ArrayList listarUsuarios(String textoBusca){
        
        ArrayList<ModUsuario> listaUsuarios =  new ArrayList();
        String sql = "SELECT * FROM usuarios ORDER BY estado ASC";
        String procurar = "SELECT * FROM usuarios WHERE usuario LIKE '%"+textoBusca+"%' OR nome LIKE '%"+textoBusca+"%'";
        
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
                ModUsuario usuario =  new ModUsuario();
                usuario.setId_usuario(rs.getInt("id"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEstado(rs.getString("estado"));
                usuario.setCaixa(rs.getString("caixa"));
                usuario.setCargo(rs.getString("cargo"));
                listaUsuarios.add(usuario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally {
            setFecharConexao(conex);
        }
        return listaUsuarios;
    }
    
    public boolean modificarUsuario(ModUsuario usuario){
        
        String sql = "UPDATE usuarios SET usuario = ?,nome = ?,caixa = ?,cargo = ? WHERE id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getNome());
            ps.setString(3, usuario.getCaixa());
            ps.setString(4, usuario.getCargo());
            ps.setInt(5, usuario.getId_usuario());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally {
            setFecharConexao(conex);
        }
    }
    
    public boolean acao(String estado, int id_usuario){
        
        String sql = "UPDATE usuarios SET estado = ? WHERE id =?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, id_usuario);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally {
            setFecharConexao(conex);
        }
    }
}
