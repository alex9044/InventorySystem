package DAO;

import conex.conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.ModConfiguracao;

/**
 *
 * @author ALEX PC
 */
public class ConfigDAO extends conexao{
    
    PreparedStatement ps;
    Connection conex;
    ResultSet rs;
    
    public ModConfiguracao getConfig(){
        String sql = "SELECT * FROM configuracao";
        ModConfiguracao config = new ModConfiguracao();
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next() == true){
                config.setId_confi(rs.getInt("id_config"));
                config.setRuc(rs.getString("ruc"));
                config.setNomeEmpresa(rs.getString("nome"));
                config.setEndereco(rs.getString("endereco"));
                config.setTelefone(rs.getString("telefone"));
                config.setMensagem(rs.getString("mensagem"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally {
            setFecharConexao(conex);
        }
        return config;
    }
    
    public boolean atualizarConfig(ModConfiguracao config){
        
        String sql = "UPDATE configuracao SET ruc = ?, nome = ?, endereco = ?, telefone = ?, mensagem = ? WHERE id_config = 1";
        
         try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, config.getRuc());
            ps.setString(2, config.getNomeEmpresa());
            ps.setString(3, config.getEndereco());
            ps.setString(4, config.getTelefone());
            ps.setString(5, config.getMensagem());
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
