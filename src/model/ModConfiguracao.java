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
public class ModConfiguracao {
    
    private int id_confi;
    private String nomeEmpresa;
    private String ruc;
    private String telefone;
    private String endereco;
    private String mensagem;

    public ModConfiguracao() {
    }

    public ModConfiguracao(int id_confi, String nomeEmpresa, String ruc, String telefone, String endereco, String mensagem) {
        this.id_confi = id_confi;
        this.nomeEmpresa = nomeEmpresa;
        this.ruc = ruc;
        this.telefone = telefone;
        this.endereco = endereco;
        this.mensagem = mensagem;
    }

    public int getId_confi() {
        return id_confi;
    }

    public void setId_confi(int id_confi) {
        this.id_confi = id_confi;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    
}
