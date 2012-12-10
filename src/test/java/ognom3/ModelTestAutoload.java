package ognom3;

import ognom.Finder;
import java.util.List;
import java.util.ArrayList;
import ognom3.model.Cargo;
import ognom3.model.Pessoa;
import ognom3.model.Setor;
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
public class ModelTestAutoload {

 
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
    public void testar(){
        List<Cargo> lc = Finder.findAll(Cargo.class);
        ObjectId id = lc.get(0).get_id();
        
        Pessoa p = new Pessoa();
        p.setCpf(123);
        p.setNome("Igor Takenami");
        p.setEmail("itakenami@gmail.com");
        Cargo c = new Cargo();
        c.set_id(id);
        p.setCargo(c);
        
        p.save();
        
        Pessoa pr = Finder.findOne(Pessoa.class, "{cpf:123}");
        assertEquals("Cargo diferente", pr.getCargo().getNome(), lc.get(0).getNome());
        
        Pessoa p2 = new Pessoa();
        p2.setCpf(456);
        p2.setNome("Maria Souza");
        p2.setEmail("maria@gmail.com");
        p2.save();
        
        
        List<Pessoa> pessoas = new ArrayList<Pessoa>();
        List<Pessoa> ps = Finder.findAll(Pessoa.class);
        
        for (Pessoa pes : ps) {
            Pessoa pn = new Pessoa();
            pn.set_id(pes.get_id());
            pessoas.add(pn);
        }
        
        Setor s = new Setor();
        s.setNome("COSI2");
        s.setDescricao("Setor de Teste");
        s.setPessoas(pessoas);
        
        s.save();
        
        Setor se = Finder.findOne(Setor.class, "{nome:'COSI2'}");
        assertEquals("Quanidade de Pessoas não é igual",2, se.getPessoas().size());
        assertEquals(se.getPessoas().get(0).getNome(), ps.get(0).getNome());
        
    }

    
   

}
