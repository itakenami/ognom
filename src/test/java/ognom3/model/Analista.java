/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ognom3.model;

import ognom.annotation.Entity;
import ognom.Model;
import ognom.annotation.Autoload;

@Entity(name="analistas")
public class Analista extends Model {
    
    private String nome;
    private String especialidade;
    
    private Cargo cargo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String epecialidade) {
        this.especialidade = epecialidade;
    }

    
    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
}
