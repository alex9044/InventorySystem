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
public class ModCliente {
    
    private int id_cliente;
    private String nome;
    private String ci;
    private String telefone;
    private String endereco;
    private String email;
    private String estado;

    public ModCliente(int id_cliente, String nome, String ci, String telefone, String endereco, String email, String estado) {
        this.id_cliente = id_cliente;
        this.nome = nome;
        this.ci = ci;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.estado = estado;
    }
    
    public ModCliente(){
    
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
