/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ognom3.model;

import ognom.Model;
import ognom.annotation.Autoload;
import ognom.annotation.Entity;

/**
 *
 * @author itakenami
 */
@Entity(name="pessoas")
public class Pessoa extends Model{
    
    private Integer cpf;
    private String nome;
    private String email;
    @Autoload(model=Cargo.class)
    private Cargo cargo;

    /**
     * @return the cpg
     */
    public Integer getCpf() {
        return cpf;
    }

    /**
     * @param cpg the cpg to set
     */
    public void setCpf(Integer cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the cargo
     */
    public Cargo getCargo() {
        return cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
}
