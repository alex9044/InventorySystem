package model;

import java.util.Date;

/**
 *
 * @author ALEX
 */
public class ModConvencional {
    
    private int id, id_produto,id_cliente;
    private float total;
    private String estado;
    private String cliente;
    private String produto;
    private Date data;

    public ModConvencional() {
    }

    public ModConvencional(int id, int id_produto, int id_cliente, float total, String estado, String cliente, String produto, Date data) {
        this.id = id;
        this.id_produto = id_produto;
        this.id_cliente = id_cliente;
        this.total = total;
        this.estado = estado;
        this.cliente = cliente;
        this.produto = produto;
        this.data = data;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    
}
