/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ALEX PC
 */
public class ModParcela {
    private int id_venda;
    private int nroParcelas;
    private float valorVenda;
    private float valorTotal;
    private float valorParcela;
    private String dataVenc;
    private String estado;

    public ModParcela(int id_venda, int nroParcelas, float valorVenda, float valorTotal, float valorParcela, String dataVenc, String estado) {
        this.id_venda = id_venda;
        this.nroParcelas = nroParcelas;
        this.valorVenda = valorVenda;
        this.valorTotal = valorTotal;
        this.valorParcela = valorParcela;
        this.dataVenc = dataVenc;
        this.estado = estado;
    }

    public ModParcela() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public int getId_venda() {
        return id_venda;
    }

    public void setId_venda(int id_venda) {
        this.id_venda = id_venda;
    }

    public int getNroParcelas() {
        return nroParcelas;
    }

    public void setNroParcelas(int nroParcelas) {
        this.nroParcelas = nroParcelas;
    }

    public float getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(float valorVenda) {
        this.valorVenda = valorVenda;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public float getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(float valorParcela) {
        this.valorParcela = valorParcela;
    }

    public String getDataVenc() {
        return dataVenc;
    }

    public void setDataVenc(String dataVenc) {
        this.dataVenc = dataVenc;
    }
    
    
}
