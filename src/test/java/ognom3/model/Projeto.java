package ognom3.model;

import java.util.Date;
import java.util.List;
import ognom.Model;
import ognom.annotation.Autoload;
import ognom.annotation.Entity;

/**
 *
 * @author itakenami
 */
@Entity(name="projetos")
public class Projeto extends Model {
    
    private String nome;
    private Date data_inicio;
    private Date data_fim;
    private List<Analista> analistas;

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
     * @return the data_inicio
     */
    public Date getData_inicio() {
        return data_inicio;
    }

    /**
     * @param data_inicio the data_inicio to set
     */
    public void setData_inicio(Date data_inicio) {
        this.data_inicio = data_inicio;
    }

    /**
     * @return the data_fim
     */
    public Date getData_fim() {
        return data_fim;
    }

    /**
     * @param data_fim the data_fim to set
     */
    public void setData_fim(Date data_fim) {
        this.data_fim = data_fim;
    }

    /**
     * @return the analistas
     */
    public List<Analista> getAnalistas() {
        return analistas;
    }

    /**
     * @param analistas the analistas to set
     */
    public void setAnalistas(List<Analista> analistas) {
        this.analistas = analistas;
    }
}
