package model;

import java.util.Date;

/**
 *
 * @author ALEX PC
 */
public class ModInventario {
    
    private int id;
    private int stock_atual;
    private int stock_anterior;
    private String id_produto;
    private float custo;
    private int diferenca;
    private Date data;
    private String motivo;
   

    public ModInventario() {
    }

    public ModInventario(int id, int stock_atual, int stock_anterior, String id_produto, float custo,int diferenca, Date data, String motivo) {
        this.id = id;
        this.stock_atual = stock_atual;
        this.stock_anterior = stock_anterior;
        this.id_produto = id_produto;
        this.custo = custo;
        this.diferenca = diferenca;
        this.data = data;
        this.motivo = motivo;
    }

    public String getId_produto() {
        return id_produto;
    }

    public void setId_produto(String id_produto) {
        this.id_produto = id_produto;
    }

    public int getDiferenca() {
        return diferenca;
    }

    public void setDiferenca(int diferenca) {
        this.diferenca = diferenca;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock_atual() {
        return stock_atual;
    }

    public void setStock_atual(int stock_atual) {
        this.stock_atual = stock_atual;
    }

    public int getStock_anterior() {
        return stock_anterior;
    }

    public void setStock_anterior(int stock_anterior) {
        this.stock_anterior = stock_anterior;
    }

    public float getCusto() {
        return custo;
    }

    public void setCusto(float custo) {
        this.custo = custo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
