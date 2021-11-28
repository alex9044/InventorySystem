/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import DAO.ProdutoDAO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import model.ModInventario;
import model.ModProduto;

/**
 *
 * @author ALEX
 */
public class TabelaInventario extends AbstractTableModel{
    
    
    ModProduto produto;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Util util = new Util();
    String[] colunas = {"ID","Motivo","Produto","Stock_Atual","Stock_Anterior","Diferença","Custo_Produto","Data"};
    List<ModInventario> listaInventario = null;
    
    
    public TabelaInventario(List<ModInventario> listaInventarios) {
        this.listaInventario = listaInventarios;
        
    }
 
    
    @Override
    public int getRowCount() {
        return listaInventario.size();
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
                return returnID(listaInventario.get(row).getId());
            case 1:
                return listaInventario.get(row).getMotivo();
            case 2:
                return listaInventario.get(row).getId_produto();
            case 3: 
                return listaInventario.get(row).getStock_atual();
            case 4: 
                return listaInventario.get(row).getStock_anterior();
            case 5: 
                return listaInventario.get(row).getDiferenca();
            case 6:
                return util.formatoCosto((float) listaInventario.get(row).getCusto());
            case 7:
                return dateFormat.format(listaInventario.get(row).getData());
        }
        return null;
    }
    
    private String returnID(int number){
        String zero = "0";
        String numero = "";
        int length = String.valueOf(number).length();
        if(length == 1){
                numero = zero+zero+number;
        }
        if(length == 2){
                numero = zero+number;
        }
        if(length == 3){
                numero = ""+number;
        }
        return numero;
    }
}
