/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import DAO.ClienteDAO;
import com.itextpdf.text.Header;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.ModCliente;
import model.ModVenda;

/**
 *
 * @author ALEX
 */
public class TableVendas extends AbstractTableModel{
    
    ModCliente cliente;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Util util = new Util();
    String[] colunas = {"Nro. Nota","Cliente","Total","Data","Tipo Venda","Estado"};
    List<ModVenda> listaVendas = null;
    
    
    
    
    
    public TableVendas(List<ModVenda> listaAlunos) {
        this.listaVendas = listaAlunos;
    }
 
    
    @Override
    public int getRowCount() {
        return listaVendas.size();
    }

     @Override
    public int getColumnCount() {
        return colunas.length;
    }
    
    @Override
    public String getColumnName(int num){
        return this.colunas[num];
    }
    
   

    @Override
    public Object getValueAt(int row, int col) {
        switch(col){
            case 0: 
                return returnNro_factura(listaVendas.get(row).getId_venda());
            case 1:
                cliente = new ClienteDAO().verCliente(listaVendas.get(row).getId_cliente());
                return cliente.getNome();
            case 2: 
                return util.formatoPrecio(listaVendas.get(row).getTotal());
            case 3: 
                return dateFormat.format(listaVendas.get(row).getData());
            case 4: 
                return listaVendas.get(row).getTipo_venda();
            case 5: 
                return listaVendas.get(row).getEstado();
        }
        return null;
    }
    
    
    private String returnNro_factura(int number){
        String numero = "";
        int length = String.valueOf(number).length();
        if(length == 1){
                numero = "0000"+number;
        }
        if(length == 2){
                numero = "000"+number;
        }
        if(length == 3){
                numero = "00"+number;
        }
        if(length == 4){
                numero = "0"+number;
        }
        if(length == 5){
                numero = ""+number;
        }
        return numero;
    }
}
