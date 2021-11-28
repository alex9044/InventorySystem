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
import model.ModCliente;

/**
 *
 * @author ALEX PC
 */
public class ClienteDAO extends conexao {
    
    PreparedStatement ps;
    Connection conex;
    ResultSet rs;
       
    //INSERIR UM NOVO REGISTRO
    public boolean registrarCliente(ModCliente modeloCliente){
        
        String sql = "INSERT INTO clientes (nome,ci,endereco,telefone,email)VALUES(?,?,?,?,?)";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, modeloCliente.getNome());
            ps.setString(2, modeloCliente.getCi());
            ps.setString(3, modeloCliente.getEndereco());
            ps.setString(4, modeloCliente.getTelefone());
            ps.setString(5, modeloCliente.getEmail());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        } finally {
            setFecharConexao(conex);
        }
    }
    //PREENCHE A TABELA DO FORM CLIENTES
    public ArrayList listarClientes(String textoBusca){
        
        ArrayList<ModCliente> listaClientes =  new ArrayList();
        String sql = "SELECT * FROM clientes ORDER BY estado ASC";
        String procurar = "SELECT * FROM clientes WHERE nome LIKE '%"+textoBusca+"%' OR ci LIKE '%"+textoBusca+"%'";
        
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
                ModCliente cliente =  new ModCliente();
                cliente.setId_cliente(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCi(rs.getString("ci"));
                cliente.setEmail(rs.getString("email"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEstado(rs.getString("estado"));
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally {
            setFecharConexao(conex);
        }
        return listaClientes;
    }
    
    public boolean modificarCliente(ModCliente cliente){
        
        String sql = "UPDATE clientes SET nome = ?,ci = ?,endereco = ?,telefone = ?, email=? WHERE id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCi());
            ps.setString(3, cliente.getEndereco());
            ps.setString(4, cliente.getTelefone());
            ps.setString(5, cliente.getEmail());
            ps.setInt(6, cliente.getId_cliente());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally {
            setFecharConexao(conex);
        }
    }
    
    public boolean acao(String estado, int id_cliente){
        
        String sql = "UPDATE clientes SET estado =? WHERE id =?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, id_cliente);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally {
            setFecharConexao(conex);
        }
    }
    
    public ModCliente verCliente(int id_cliente){
        
        String sql = "SELECT * FROM clientes WHERE id = " + id_cliente;
        ModCliente cliente = new ModCliente();
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next() == true) {
                cliente.setCi(rs.getString("ci"));
                cliente.setEmail(rs.getString("email"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setEstado(rs.getString("estado"));
                cliente.setNome(rs.getString("nome"));
                cliente.setTelefone(rs.getString("telefone"));
            } 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return cliente;
    }
}
