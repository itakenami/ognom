/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ognom3;

import ognom.Finder;
import org.bson.types.ObjectId;
import java.util.List;
import ognom3.model.Analista;
import ognom3.model.Cargo;
import ognom3.model.Projeto;
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

public class ModelTestFind {

 
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
    public void findAll() {
        
        System.out.print("Testando findAll pata todos os Models: ");
        
        List<Cargo> cs = Finder.findAll(Cargo.class);
        assertEquals("Nao retornou a quantidade de Cargos", 3, cs.size());
        
        List<Analista> as = Finder.findAll(Analista.class);
        assertEquals("Nao retornou a quantidade de Analistas", 2, as.size());
        
        List<Projeto> ps = Finder.findAll(Projeto.class);
        assertEquals("Nao retornou a quantidade de Projetos", 1, ps.size());
        
        assertEquals("Nao retornou a quantidade de Analistas dentro do Projetos", 2, ps.get(0).getAnalistas().size());
        
        System.out.println("OK");
    }
    
    @Test
    public void findById() {
        
        System.out.print("Testando findById para Cargos e Analistas: ");
        
        List<Cargo> cs = Finder.findAll(Cargo.class);
        
        ObjectId i1 = cs.get(0).get_id();
        ObjectId i2 = cs.get(1).get_id();
        
        Cargo c1 = Finder.findById(Cargo.class, i1);
        Cargo c2 = Finder.findById(Cargo.class, i2);
        
        assertEquals("Objetos não parecidos", cs.get(0).getNome(), c1.getNome());
        assertEquals("Objetos não parecidos", cs.get(1).getNome(), c2.getNome());
        
        
        List<Analista> as = Finder.findAll(Analista.class);
        
        i1 = as.get(0).get_id();
        i2 = as.get(1).get_id();
        
        Analista a1 = Finder.findById(Analista.class, i1);
        Analista a2 = Finder.findById(Analista.class, i2);
        
        assertEquals("Objetos não parecidos", as.get(0).getNome(), a1.getNome());
        assertEquals("Objetos não parecidos", as.get(1).getNome(), a2.getNome());
        
        
        System.out.println("OK");

        
    }
    
    @Test
    public void findModel() {
        
        System.out.print("Testando findAll pata todos os Models: ");
        
        Cargo c = new Cargo();
        c.setNome("Programador");
        List<Cargo> cs = Finder.find(Cargo.class,c);
        assertEquals("Nao retornou a quantidade de Cargos", 1, cs.size());
        
        Analista a = new Analista();
        a.setEspecialidade("JAVA");
        List<Analista> as = Finder.find(Analista.class,a);
        assertEquals("Nao retornou a quantidade de Analistas", 2, as.size());
        
        Projeto p = new Projeto();
        p.setNome("Projeto 1");
        List<Projeto> ps = Finder.find(Projeto.class,p);
        assertEquals("Nao retornou a quantidade de Projetos", 1, ps.size());
        
        assertEquals("Nao retornou a quantidade de Analistas dentro do Projetos", 2, ps.get(0).getAnalistas().size());
        
        System.out.println("OK");
        
    }
            
    @Test
    public void findJson() {
        System.out.print("Testando findAll pata todos os Models: ");
        
        List<Cargo> cs = Finder.find(Cargo.class,"{nome:'Programador'}");
        assertEquals("Nao retornou a quantidade de Cargos", 1, cs.size());
        
        List<Analista> as = Finder.find(Analista.class,"{especialidade:'JAVA'}");
        assertEquals("Nao retornou a quantidade de Analistas", 2, as.size());
        
        List<Projeto> ps = Finder.find(Projeto.class,"{nome:'Projeto 1'}");
        assertEquals("Nao retornou a quantidade de Projetos", 1, ps.size());
        
        assertEquals("Nao retornou a quantidade de Analistas dentro do Projetos", 2, ps.get(0).getAnalistas().size());
        
        System.out.println("OK");
    }
            
    @Test
    public void findOneModel() {

        System.out.print("Testando findOneModel para Cargos e Analistas: ");
        
        List<Cargo> cs = Finder.findAll(Cargo.class);
        
        Cargo p1 = new Cargo();
        p1.setNome(cs.get(0).getNome());
        
        Cargo p2 = new Cargo();
        p2.set_id(cs.get(1).get_id());
        
        Cargo c1 = Finder.findOne(Cargo.class, p1);
        Cargo c2 = Finder.findOne(Cargo.class, p2);
        
        assertEquals("Objetos não parecidos", cs.get(0).get_id(), c1.get_id());
        assertEquals("Objetos não parecidos", cs.get(1).getNome(), c2.getNome());
        
        
        List<Analista> as = Finder.findAll(Analista.class);
        
        Analista ap1 = new Analista();
        ap1.setNome(as.get(0).getNome());
        
        Analista ap2 = new Analista();
        ap2.set_id(as.get(1).get_id());
        
        Analista a1 = Finder.findOne(Analista.class, ap1);
        Analista a2 = Finder.findOne(Analista.class, ap2);
        
        assertEquals("Objetos não parecidos", as.get(0).get_id(), a1.get_id());
        assertEquals("Objetos não parecidos", as.get(1).getNome(), a2.getNome());
        
        
        System.out.println("OK");
        
    }
            
    @Test
    public void findOneJson() {
        
        System.out.print("Testando findOneJson para Cargos e Analistas: ");
        
        List<Cargo> cs = Finder.findAll(Cargo.class);
        
        
        Cargo c1 = Finder.findOne(Cargo.class, "{nome:'"+cs.get(0).getNome()+"'}");
        Cargo c2 = Finder.findOne(Cargo.class, "{_id:{$oid:'"+cs.get(1).get_id().toString()+"'}}");
        
        assertEquals("Objetos não parecidos", cs.get(0).get_id(), c1.get_id());
        assertEquals("Objetos não parecidos", cs.get(1).getNome(), c2.getNome());
        
        
        List<Analista> as = Finder.findAll(Analista.class);
        
        Analista a1 = Finder.findOne(Analista.class, "{nome:'"+as.get(0).getNome()+"'}");
        Analista a2 = Finder.findOne(Analista.class, "{_id:{$oid:'"+as.get(1).get_id().toString()+"'}}");
        
        assertEquals("Objetos não parecidos", as.get(0).get_id(), a1.get_id());
        assertEquals("Objetos não parecidos", as.get(1).getNome(), a2.getNome());
        
        
        System.out.println("OK");
        
    }
    
    

}
