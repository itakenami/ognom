/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ognom3;

import ognom.Finder;
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

public class ModelTestDelete {

 
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
    public void delete() {

        System.out.print("Testando delete de Cargo: ");

        String[] cargos = {"Analista de Sistemas", "Programador", "Arquiteto", "Digitador"};
        
        String nome_cargo = cargos[cargos.length - 1];

        Cargo c = new Cargo();
        c.setNome(nome_cargo);
        
        Cargo c1 = Finder.findOne(Cargo.class, c);
        c1.delete();

        Cargo c2 = Finder.findOne(Cargo.class, c);

        assertNull("Deveria ser null", c2);

        System.out.println("OK");
    }
}
