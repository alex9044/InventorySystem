package DAO;

import conex.conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.ModFornecedor;

/**
 *
 * @author ALEX PC
 */
public class FornecedorDAO extends conexao{
    
    PreparedStatement ps;
    Connection conex;
    ResultSet rs;
       
    //INSERIR UM NOVO REGISTRO
    public boolean registrarFornecedor(ModFornecedor modeloFornecedor){
        
        String sql = "INSERT INTO fornecedores(fornecedor,telefone,nro_empresa,endereco)VALUES(?,?,?,?)";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, modeloFornecedor.getNome());
            ps.setString(2, modeloFornecedor.getTelefone());
            ps.setString(3, modeloFornecedor.getRuc());
            ps.setString(4, modeloFornecedor.getEndereco());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        } finally {
            setFecharConexao(conex);
        }
        
    }
     //PREENCHE A TABELA DO FORM FORNECEDORES
     public ArrayList listarFornecedores(String textoBusca){
        
        ArrayList<ModFornecedor> listaFornecedores =  new ArrayList();
        String sql = "SELECT * FROM fornecedores ORDER BY estado ASC";
        String procurar = "SELECT * FROM fornecedores WHERE fornecedor LIKE '%"+textoBusca+"%' OR nro_empresa LIKE '%"+textoBusca+"%'";
        
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
                ModFornecedor fornecedor = new ModFornecedor();
                fornecedor.setId_fornecedor(rs.getInt("id"));
                fornecedor.setNome(rs.getString("fornecedor"));
                fornecedor.setRuc(rs.getString("nro_empresa"));
                fornecedor.setEndereco(rs.getString("endereco"));
                fornecedor.setTelefone(rs.getString("telefone"));
                fornecedor.setEstado(rs.getString("estado"));
                listaFornecedores.add(fornecedor);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally {
            setFecharConexao(conex);
        }
        return listaFornecedores;
     }
    
    public boolean modificarFornecedor(ModFornecedor modeloFornecedor){
        
        String sql = "UPDATE fornecedores SET fornecedor = ?,telefone = ?,nro_empresa = ?,endereco = ?WHERE id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, modeloFornecedor.getNome());
            ps.setString(2, modeloFornecedor.getTelefone());
            ps.setString(3, modeloFornecedor.getRuc());
            ps.setString(4, modeloFornecedor.getEndereco());
            ps.setInt(5, modeloFornecedor.getId_fornecedor());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally {
            setFecharConexao(conex);
        }
    }
    
    public boolean acao(String estado, int id_fornecedor){
        
        String sql = "UPDATE fornecedores SET estado =? WHERE id =?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, id_fornecedor);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally {
            setFecharConexao(conex);
        }
    } 
    
    public ModFornecedor verFornecedor(int id_fornecedor){
        
        String sql = "SELECT * FROM fornecedores WHERE id = " + id_fornecedor;
        ModFornecedor fornecedor = new ModFornecedor();
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next() == true) {
                fornecedor.setRuc(rs.getString("nro_empresa"));
                fornecedor.setEndereco(rs.getString("endereco"));
                fornecedor.setEstado(rs.getString("estado"));
                fornecedor.setNome(rs.getString("fornecedor"));
                fornecedor.setTelefone(rs.getString("telefone"));
            } 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return fornecedor;
    }
}
