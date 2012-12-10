package ognom3;

import ognom.Finder;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import ognom.db.Connection;
import ognom.db.Config;
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
public class ModelTest {

 
    @BeforeClass
    public static void setUpClass() throws Exception {
       // Connection.getInstance().newConnection(new Config("localhost", 27017, "gp"));
        
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
    public void testar(){
        
    }

    
    //@Test
    public void teste() {
        Cargo c = new Cargo();
        c.setNome("Cargo Atribuido");
        c.addField("descricao", "descricao do cargo atribuido");
        c.save();
        
        Cargo c1 = Finder.findOne(Cargo.class, "{nome:'Cargo Atribuido'}");
        assertEquals("Campo chave não é igual", c.getField("descricao"), c1.getField("descricao"));
        
    }
    
    //@Test
    public void testeautoload() {
        
       /*
        Analista a = new Analista();
        a.setNome("TAKENAO");
        a.setEspecialidade("TAKE");
        Cargo c = new Cargo();
        c.set_id(new ObjectId("4ec5ae5c8aa24096f0227aae"));
        a.setCargo(c);
        
        a.save();*/
        Projeto p = new Projeto();
        p.getNome();
        p.setData_inicio(new Date());
        p.setData_fim(new Date());
        
        List<Analista> list = new ArrayList<Analista>();
        Analista a1 = new Analista();
        a1.set_id(new ObjectId("4ec5ae9f8aa2ed4f3895720d"));
        list.add(a1);
        Analista a2 = new Analista();
        a2.set_id(new ObjectId("4ec5af978aa2db513699ec6a"));
        list.add(a2);
        
        
        p.setAnalistas(list);
        
        p.save();
       
        
    }

}
