
package model;

/**
 *
 * @author ALEX PC
 */
public class ModMarca {
   
    private int id_marca;
    private String descricao;
    private String estado;

    public ModMarca(int id_marca, String descricao, String estado) {
        this.id_marca = id_marca;
        this.descricao = descricao;
        this.estado = estado;
    }
    
    public ModMarca(){
        
    }

    public int getId_marca() {
        return id_marca;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
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
