/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ognom3;

import ognom.Finder;
import ognom3.model.Analista;
import ognom3.model.Cargo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author itakenami
 */

public class ModelTestUpdate {

 
    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void update() {

        System.out.print("Testando update de Cargo e Analista: ");

        String[] cargos = {"Analista de Sistemas", "Programador", "Arquiteto", "Digitador"};
        String nome_analista = "Igor Takenami";

        Cargo c = new Cargo();
        c.setNome(cargos[0]);
        Cargo c1 = Finder.findOne(Cargo.class, c);
        c1.setNome("Cargo 1 Alterado");
        Cargo cv = c1.save();
        assertNotSame("Cargo igual", cargos[0], cv.getNome());

        Analista a = new Analista();
        a.setNome(nome_analista);
        
        
        Analista a1 = Finder.findOne(Analista.class, a);

        a1.setNome("Analista Alterado");
        Analista av = a1.save();
        assertNotSame("Analista igual", nome_analista, av.getNome());

        System.out.println("OK");
    }

}
