package model;

import java.util.Date;

/**
 *
 * @author ALEX PC
 */
public class ModVenda {
    
    private int id_venda;
    private Date data;
    private String dataParcela;
    private float total;
    private String tipo_venda;
    private String nro_factura;
    private String estado;
    private String cliente;
    private int id_cliente;
    

    public ModVenda() {
    }

    public ModVenda(int id_venda, String dataParcela, Date data, float total, String tipo_venda, String nro_factura, String estado, String cliente, int id_cliente) {
        this.id_venda = id_venda;
        this.dataParcela = dataParcela;
        this.data = data;
        this.total = total;
        this.tipo_venda = tipo_venda;
        this.nro_factura = nro_factura;
        this.estado = estado;
        this.cliente = cliente;
        this.id_cliente = id_cliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
    public String getNro_factura() {
        return nro_factura;
    }

    public void setNro_factura(String nro_factura) {
        this.nro_factura = nro_factura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    

    public String getDataParcela() {
        return dataParcela;
    }

    public void setDataParcela(String dataParcela) {
        this.dataParcela = dataParcela;
    }
    
    
    public int getId_venda() {
        return id_venda;
    }

    public void setId_venda(int id_venda) {
        this.id_venda = id_venda;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }


    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getTipo_venda() {
        return tipo_venda;
    }

    public void setTipo_venda(String tipo_venda) {
        this.tipo_venda = tipo_venda;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    } 
}
