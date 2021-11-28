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
public class ModProduto {
    private int id_produto;
    private String codigo;
    private String descricao;
    private int id_fornecedor;
    private int id_marca;
    private int id_categoria;
    private int stock ;
    private float preco_venda;
    private float custo_compra;
    private String estado;
    private String categoria;
    private String fornecedor;
    private String marca;

    public ModProduto(int id_produto, String codigo, String descricao, int id_fornecedor, int id_marca, int id_categoria, int stock, float preco_venda, float custo_compra, String estado, String categoria, String fornecedor, String marca) {
        this.id_produto = id_produto;
        this.codigo = codigo;
        this.descricao = descricao;
        this.id_fornecedor = id_fornecedor;
        this.id_marca = id_marca;
        this.id_categoria = id_categoria;
        this.stock = stock;
        this.preco_venda = preco_venda;
        this.custo_compra = custo_compra;
        this.estado = estado;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
        this.marca = marca;
    }

    
    public ModProduto() {
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    
    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId_fornecedor() {
        return id_fornecedor;
    }

    public void setId_fornecedor(int id_fornecedor) {
        this.id_fornecedor = id_fornecedor;
    }

    public int getId_marca() {
        return id_marca;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPreco_venda() {
        return preco_venda;
    }

    public void setPreco_venda(float preco_venda) {
        this.preco_venda = preco_venda;
    }

    public float getCusto_compra() {
        return custo_compra;
    }

    public void setCusto_compra(float custo_compra) {
        this.custo_compra = custo_compra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
     
    
    
}
