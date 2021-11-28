/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALEX PC
 */
public class Util {
    
    
    //variveis para mascara e formatos 
    private DecimalFormatSymbols simbolo;
    private DecimalFormat mascara;
    private String configRegional = "Es";

    //variveis para decimais em quantidade, preço e custo
    private byte decimal_Precio = 3;
    private byte decimal_Costo = 3;
    private byte decimal_Cantidad = 0;

    public Util() {
        simbolo = DecimalFormatSymbols.getInstance(Locale.forLanguageTag(configRegional));
    }
    
    
    
    public boolean validarInt(String valor){
         try {
             Integer.parseInt(valor);
             return true;
         } catch (Exception e) {
             return false;
         }
    }
    
    //função para aplicar formato ou mascara nos numeros -> custo
    public String formatoCosto(float costo) {
        //decimal_Costo=2
        if (decimal_Costo == 0) {
            mascara = new DecimalFormat("###,##0", simbolo);
        } else if (decimal_Costo == 1) {
            mascara = new DecimalFormat("###,##0.0", simbolo);
        } else if (decimal_Costo == 2) {
            mascara = new DecimalFormat("###,##0.00", simbolo);
        } else if (decimal_Costo == 3) {
            mascara = new DecimalFormat("###,##0.000", simbolo);
        } else if (decimal_Costo == 4) {
            mascara = new DecimalFormat("###,##0.0000", simbolo);
        } else if (decimal_Costo == 5) {
            mascara = new DecimalFormat("###,##0.00000", simbolo);
        }
        
        return mascara.format(costo);
    }
    
    //função para aplicar formato ou mascara nos numeros -> preço
    public String formatoPrecio(float precio){                        
        
        if(decimal_Precio==0){
            mascara = new DecimalFormat("###,##0",simbolo);
        }else if(decimal_Precio == 1){
            mascara = new DecimalFormat("###,##0.0",simbolo);
        }else if(decimal_Precio == 2){
            mascara = new DecimalFormat("###,##0.00",simbolo);
        }else if(decimal_Precio == 3){
            mascara = new DecimalFormat("###,##0.000",simbolo);
        }else if(decimal_Precio == 4){
            mascara = new DecimalFormat("###,##0.0000",simbolo);
        }else if(decimal_Precio == 5){
            mascara = new DecimalFormat("###,##0.00000",simbolo);
        }              
        return mascara.format(precio);                
    }
    
     public String formatoCantidad(float qty){                            
        if(decimal_Cantidad==0){
            mascara = new DecimalFormat("###,##0",simbolo);
        }else if(decimal_Cantidad == 1){
            mascara = new DecimalFormat("###,##0.0",simbolo);
        }else if(decimal_Cantidad == 2){
            mascara = new DecimalFormat("###,##0.00",simbolo);
        }else if(decimal_Cantidad == 3){
            mascara = new DecimalFormat("###,##0.000",simbolo);
        }else if(decimal_Cantidad == 4){
            mascara = new DecimalFormat("###,##0.0000",simbolo);
        }else if(decimal_Cantidad == 5){
            mascara = new DecimalFormat("###,##0.00000",simbolo);
        }              
        return mascara.format(qty);                
    }
     
    public void validarNumeros(String texto, java.awt.event.KeyEvent evt){
         char letra = evt.getKeyChar();
         if(configRegional.equals("Us")){
             if(letra != '.' && (letra<'0' || letra >'9')){
                 evt.consume();
             }else{
                 if(letra=='.'){
                     if(texto.indexOf(".")!= -1){
                         evt.consume();
                     }
                 }
             }
         }else{
              if(letra != ',' && (letra<'0' || letra >'9')){
                 evt.consume();
             }else{
                 if(letra==','){
                     if(texto.indexOf(",")!= -1){
                         evt.consume();
                     }
                 }
             }
         }
     }
     
    public float convertirFloat(String numero){
        
        DecimalFormat format = new DecimalFormat("###,##0.000", simbolo);
        float valor = 0;
        try {
            valor = format.parse(numero).floatValue();
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return valor;
    }
     
    public String formatoValorEnGs(float precio) {

        mascara = new DecimalFormat("###,##0", simbolo);
        return mascara.format(precio);
    }
    
    
}
