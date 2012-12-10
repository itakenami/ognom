/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ognom3;

import ognom.db.Config;
import ognom.db.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author itakenami
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ognom3.ModelTestSave.class, ognom3.ModelTestUpdate.class, ognom3.ModelTestDelete.class, ognom3.ModelTestFind.class, ognom3.ModelTestAutoload.class ,ognom3.ModelTest.class})
public class SuiteTestes {

    @BeforeClass
    public static void setUpClass() throws Exception {
        
        Connection.getInstance().newConnection(new Config("localhost", 27017, "gp"));
        
        if (Connection.getInstance().getDB().collectionExists("cargos")) {
            Connection.getInstance().getDB().getCollection("cargos").drop();
        }

        if (Connection.getInstance().getDB().collectionExists("analistas")) {
            Connection.getInstance().getDB().getCollection("analistas").drop();
        }

        if (Connection.getInstance().getDB().collectionExists("projetos")) {
            Connection.getInstance().getDB().getCollection("projetos").drop();
        }
        
        if (Connection.getInstance().getDB().collectionExists("pessoas")) {
            Connection.getInstance().getDB().getCollection("pessoas").drop();
        }
        
        if (Connection.getInstance().getDB().collectionExists("setores")) {
            Connection.getInstance().getDB().getCollection("setores").drop();
        }
        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
