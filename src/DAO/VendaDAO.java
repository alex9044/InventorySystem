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
import model.ModBaixaParcela;
import model.ModParcela;
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
public class VendaDAO extends conexao{
    
    PreparedStatement ps;
    Connection conex;
    ResultSet rs;
    
    public ArrayList listarVenda(String textoBusca){
        ArrayList<ModVenda> listaVendas =  new ArrayList();
        String sql = "SELECT v.*, c.nome as cliente FROM vendas v,  clientes c WHERE v.id_cliente = c.id ORDER BY v.estado DESC" ;
        String procurar = "SELECT v.*, c.nome as cliente FROM vendas v, clientes c WHERE v.id_cliente = c.id AND v.id LIKE '%"+textoBusca+"%'";
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
                ModVenda venda = new ModVenda();
                venda.setId_venda(rs.getInt("id"));
                venda.setData(rs.getDate("data"));
                venda.setTotal(rs.getFloat("total"));
                venda.setId_cliente(rs.getInt("id_cliente"));
                venda.setTipo_venda(rs.getString("tipo_venda"));
                venda.setEstado(rs.getString("estado"));
                venda.setCliente(rs.getString("cliente"));
                listaVendas.add(venda);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return listaVendas;
    }
    
    public boolean insertarVendas(ModVenda modeloVenda){
        
        String sql = "INSERT INTO vendas(id_cliente,total,tipo_venda)values(?,?,?)";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, modeloVenda.getId_cliente());
            ps.setFloat(2, modeloVenda.getTotal());
            ps.setString(3, modeloVenda.getTipo_venda());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }finally{
            setFecharConexao(conex);
        }   
    }
    
    public boolean eliminarVenda(int id_venda){
        
        String sql = "DELETE FROM vendas where id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id_venda);
            ps.execute();
            return true;
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    
    
    public int verVendaId(){
        
        String sql = "SELECT MAX(id) AS id FROM vendas";
        int id_venda = 0;
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                id_venda = rs.getInt("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return id_venda;
    }
    
    public boolean insertarItemVenda(int id_venda, int  id_produto, float preco, int quantidade , float subtotal, String nro_factura){
        
        String sql = "INSERT INTO detalhe_venda(id_venda, id_produto, preco, quantidade, subtotal, nro_factura)values(?,?,?,?,?,?)";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, id_venda);
            ps.setInt(2, id_produto);
            ps.setFloat(3, preco);
            ps.setInt(4, quantidade);
            ps.setFloat(5, subtotal);
            ps.setString(6,  nro_factura);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }finally{
            setFecharConexao(conex);
        }   
    }
    
    public ModVenda verVenda(ModVenda modeloVenda){
        
        String sql = "Select * from vendas where id =" + modeloVenda.getId_venda();
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next() == true){
                modeloVenda.setData(rs.getDate("data"));
                modeloVenda.setId_cliente(rs.getInt("id_cliente"));
                modeloVenda.setTipo_venda(rs.getString("tipo_venda"));
                modeloVenda.setTotal(rs.getFloat("total"));
                modeloVenda.setEstado(rs.getString("estado"));
            } 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage()); 
        }finally{
            setFecharConexao(conex);
        }
        return modeloVenda;
    }
    
    public ModVenda verEstadoVenda(ModVenda modeloVenda){
        
        String sql = "Select * from vendas where id =" + modeloVenda.getId_venda();
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next() == true){
                modeloVenda.setEstado(rs.getString("estado"));
            } 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage()); 
        }finally{
            setFecharConexao(conex);
        }
        return modeloVenda;
    }
    
    public boolean atualizarEstadoVenda(int id_venda, String estado){
        
        String sql = "update vendas set estado = ? where id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, id_venda);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            setFecharConexao(conex);
        }
    }

    public void salvarParcelas(ModParcela modeloParcela){
        
        String sql = "insert into parcela_venda(id_venda,valor_venda,valor_total,nro_parcelas,valor_parcela,data_venc) values (?,?,?,?,?,?)";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setInt(1, modeloParcela.getId_venda());
            ps.setFloat(2, modeloParcela.getValorVenda());
            ps.setFloat(3, modeloParcela.getValorTotal());
            ps.setInt(4, modeloParcela.getNroParcelas());
            ps.setFloat(5, modeloParcela.getValorParcela());
            ps.setString(6, modeloParcela.getDataVenc());
            ps.execute();
            //return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            //return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public ModBaixaParcela buscarParcelas(int id_venda){
       
        ModBaixaParcela modeloBaixaParcela = new ModBaixaParcela();
        
        String sql = "Select * from parcela_venda where id_venda = " + id_venda;
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next() == true) {   
                modeloBaixaParcela.setId_parcela(rs.getInt("id"));
                modeloBaixaParcela.setData_venc(rs.getString("data_venc"));
                modeloBaixaParcela.setId_venda(rs.getInt("id_venda"));
                modeloBaixaParcela.setValor_parcela(rs.getFloat("valor_parcela"));
            }    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage()); 
        }finally{
            setFecharConexao(conex);
        }
        return modeloBaixaParcela;
    }
    
    public void darBaixaParcela(int id_parcela){
        
        String sql = "update parcela_venda set estado = ? where id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setString(1, "Pago");
            ps.setInt(2, id_parcela);
            ps.execute();
            //return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            //return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public void atualizarValorParcela(int id_parcela, float valor){
        
        String sql = "update parcela_venda set valor_parcela = ? where id = ?";
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            ps.setFloat(1, valor);
            ps.setInt(2, id_parcela);
            ps.execute();
            //return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            //return false;
        }finally{
            setFecharConexao(conex);
        }
    }
    
    public ArrayList<ModBaixaParcela> listarVendaParcelas(int id_venda){
        
        ArrayList<ModBaixaParcela> listarParcelas = new ArrayList<ModBaixaParcela>();
        
        String sql = "Select * from parcela_venda where id_venda = "+ id_venda;
        
        try {
            conex = getAbrirConexao();
            ps = conex.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next() == true) {                
                ModBaixaParcela parcela = new ModBaixaParcela();
                parcela.setId_parcela(rs.getInt("id"));
                parcela.setId_venda(rs.getInt("id_venda"));
                parcela.setData_venc(rs.getString("data_venc"));
                parcela.setValor_parcela(rs.getFloat("valor_parcela"));
                parcela.setEstado(rs.getString("estado"));
                listarParcelas.add(parcela);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            setFecharConexao(conex);
        }
        return listarParcelas;
    }
    
    public JasperViewer gerarNota(int id_venda){
        try {
            conex = getAbrirConexao();
            JasperReport notaVenda = null;
            String path = "/relatorios/notaVenda.jasper";
            
            Map parametros = new HashMap();
            parametros.put("id_venda", id_venda);
            
            notaVenda =  (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            
            JasperPrint jPrint = JasperFillManager.fillReport(notaVenda, parametros, conex);
            
            return new JasperViewer(jPrint, false);
          
        } catch (JRException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            setFecharConexao(conex);
        }
        return null;
    }
    
    public void gerarComprovante(int id_parcela, String nro_factura){
        try {
            conex = getAbrirConexao();
            JasperReport comprovanteParce = null;
            String path = "/relatorios/comprovanteParce.jasper";
            
            Map parametros = new HashMap();
            parametros.put("venda", id_parcela);
            parametros.putIfAbsent("nro_factura", nro_factura);
            
            comprovanteParce =  (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            
            JasperPrint jPrint = JasperFillManager.fillReport(comprovanteParce, parametros, conex);
            
            JasperViewer nota = new JasperViewer(jPrint, false);
            
            nota.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            nota.setVisible(true);
            nota.toFront();
            
        } catch (JRException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            setFecharConexao(conex);
        }
    }
}
