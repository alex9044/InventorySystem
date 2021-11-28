package model;

/**
 *
 * @author ALEX PC
 */
public class ModCategoria {
    
    private int id_categoria;
    private String descricao;
    private String estado;

    public ModCategoria(int id_categoria, String descricao, String estado) {
        this.id_categoria = id_categoria;
        this.descricao = descricao;
        this.estado = estado;
    }
    
    public ModCategoria(){
    
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
}
