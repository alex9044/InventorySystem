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
public class ModCombo {
    
    private int id;
    private String nome;
    private String ruc;

    public ModCombo(int id, String nome, String ruc) {
        this.id = id;
        this.nome = nome;
        this.ruc = ruc;
    }


    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
    
    public ModCombo() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public String toString(){
        return this.getNome();
    }
    
}
