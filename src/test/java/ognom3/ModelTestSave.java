/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ognom3;

import ognom.Finder;
import java.util.List;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import ognom3.model.Analista;
import ognom3.model.Cargo;
import ognom3.model.Projeto;
import org.bson.types.ObjectId;
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

public class ModelTestSave {

 
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
    public void saveCargo() {

        System.out.print("Testando save de Cargo: ");

        String[] cargos = {"Analista de Sistemas", "Programador", "Arquiteto", "Digitador"};

        ObjectId oid = null;

        for (String nm_cargo : cargos) {

            Cargo c = new Cargo();
            c.setNome(nm_cargo);
            Cargo ca = c.save();
            oid = ca.get_id();
            assertNotNull("id Nullo", oid);
        }

        Cargo cv = Finder.findById(Cargo.class, oid);
        assertNotNull(cv);
        assertEquals("Cargo diferente", cargos[cargos.length - 1], cv.getNome());
        System.out.println("OK");
    }

    @Test
    public void saveAnalista() {

        String[] cargos = {"Analista de Sistemas", "Programador", "Arquiteto", "Digitador"};
        String nome_analista = "Igor Takenami";
        String especialidade_analista = "JAVA";
        Cargo cargo_analista = new Cargo();


        System.out.print("Testando save de Analista: ");

        cargo_analista.setNome("Cargo Novo");

        Analista a = new Analista();
        a.setNome(nome_analista);
        a.setEspecialidade(especialidade_analista);
        a.setCargo(cargo_analista);

        Analista a1 = a.save();

        assertNotNull("Esperado id NÃO nullo", a1.get_id());
        Analista av = Finder.findById(Analista.class, a1.get_id());
        assertNotNull("Esperado retornar algum valor", av);
        assertEquals("Nome Diferente", nome_analista, av.getNome());


        nome_analista = "Joao Paulo";
        especialidade_analista = "JAVA";
        cargo_analista = new Cargo();
        cargo_analista.setNome(cargos[0]);

        a = new Analista();
        a.setNome(nome_analista);
        a.setEspecialidade(especialidade_analista);
        a.setCargo(Finder.findOne(Cargo.class, cargo_analista));

        a1 = a.save();

        assertNotNull("Esperado id NÃO nullo", a1.get_id());
        av = Finder.findById(Analista.class, a1.get_id());
        assertNotNull("Esperado retornar algum valor", av);
        assertEquals("Nome Diferente", nome_analista, av.getNome());

        System.out.println("OK");
    }

    @Test
    public void saveProjeto() throws ParseException {

        System.out.print("Testando save de Projeto: ");


        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String nome_projeto = "Projeto 1";
        Date data1 = new java.sql.Date(format.parse("10/11/2011").getTime());
        Date data2 = new java.sql.Date(format.parse("30/11/2011").getTime());
        List<Analista> lista_analistas = Finder.findAll(Analista.class);

        Projeto p = new Projeto();
        p.setNome(nome_projeto);
        p.setData_inicio(data1);
        p.setData_fim(data2);
        p.setAnalistas(lista_analistas);

        Projeto p1 = p.save();

        assertNotNull("Esperado id NÃO nullo", p1.get_id());
        Projeto pv = Finder.findById(Projeto.class, p1.get_id());
        assertNotNull("Esperado retornar algum valor", pv);
        assertEquals("Nome Diferente", nome_projeto, pv.getNome());

        System.out.println("OK");

    }

}
