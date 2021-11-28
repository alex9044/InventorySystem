package model;

/**
 *
 * @author ALEX PC
 */
public class ModBaixaParcela {
    
    private int id_parcela;
    private int id_venda;
    private String data_venc;
    private float valor_parcela;
    private float valor_total_parcela;
    private String estado;

    public ModBaixaParcela(int id_parcela, int id_venda, String data_venc, float valor_parcela, float valor_total_parcela, String estado) {
        this.id_parcela = id_parcela;
        this.id_venda = id_venda;
        this.data_venc = data_venc;
        this.valor_parcela = valor_parcela;
        this.valor_total_parcela = valor_total_parcela;
        this.estado = estado;
    }

    public ModBaixaParcela() {
    }

    public float getValor_parcela() {
        return valor_parcela;
    }

    public void setValor_parcela(float valor_parcela) {
        this.valor_parcela = valor_parcela;
    }

    public float getValor_total_parcela() {
        return valor_total_parcela;
    }

    public void setValor_total_parcela(float valor_total_parcela) {
        this.valor_total_parcela = valor_total_parcela;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public int getId_parcela() {
        return id_parcela;
    }

    public void setId_parcela(int id_parcela) {
        this.id_parcela = id_parcela;
    }

    public int getId_venda() {
        return id_venda;
    }

    public void setId_venda(int id_venda) {
        this.id_venda = id_venda;
    }

    public String getData_venc() {
        return data_venc;
    }

    public void setData_venc(String data_venc) {
        this.data_venc = data_venc;
    }
    
}
