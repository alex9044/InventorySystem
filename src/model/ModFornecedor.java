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
public class ModFornecedor {
    
    private int id_fornecedor;
    private String nome;
    private String telefone;
    private String ruc;
    private String endereco;
    private String estado;

    public ModFornecedor(int id_fornecedor, String nome, String telefone, String ruc, String endereco, String estado) {
        this.id_fornecedor = id_fornecedor;
        this.nome = nome;
        this.telefone = telefone;
        this.ruc = ruc;
        this.endereco = endereco;
        this.estado = estado;
    }

    public ModFornecedor() {
    }
    

    public int getId_fornecedor() {
        return id_fornecedor;
    }

    public void setId_fornecedor(int id_fornecedor) {
        this.id_fornecedor = id_fornecedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
}
