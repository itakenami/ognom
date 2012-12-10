/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ognom3.model;

import java.util.List;
import ognom.Model;
import ognom.annotation.Autoload;
import ognom.annotation.Entity;

/**
 *
 * @author itakenami
 */
@Entity(name="setores")
public class Setor extends Model {
    private String nome;
    private String descricao;
    @Autoload(fields={"nome"},model=Pessoa.class)
    private List<Pessoa> pessoas;

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
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the pessoas
     */
    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    /**
     * @param pessoas the pessoas to set
     */
    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
    
}
