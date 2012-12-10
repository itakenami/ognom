package ognom3.model;

import ognom.Model;
import ognom.Finder;
import ognom.annotation.Entity;

@Entity(name="cargos")
public class Cargo extends Model {
    
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void get(){
        Cargo c = Finder.findById(Cargo.class, get_id());
        setNome(c.getNome());
    }
    
    
}