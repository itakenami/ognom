package ognom.db;

import com.mongodb.DB;
import com.mongodb.Mongo;

/**
 *
 * @author itakenami
 */
public class Connection {
    
    private static Connection instance;
    
    private Mongo mongo;
    private DB db;
    
    private Connection() {
        mongo=null;
        db=null;
    }
    
    public void newConnection(Config conf){
        
        try{
            
            mongo = new Mongo(conf.host,conf.port); 
            db = mongo.getDB(conf.db);
            
            if(conf.login!=null && conf.senha!=null){
                boolean auth = db.authenticate(conf.login, conf.senha.toCharArray());
                if(!auth){
                    throw new RuntimeException("newConnection(Config conf) => Error trying to authenticate to the bank. Login or password invalid.");
                }
            }
            
        }catch(Exception ex){
            throw new RuntimeException("newConnection(Config conf) => Error when trying to get a connection: "+ex.getMessage());
        }
    }
    
    public static Connection getInstance(){
        if(instance==null){
            instance = new Connection();
        }
        return instance;
    }
    
    public DB getDB(){
        if(db==null){
            throw new RuntimeException("Connection getInstance() => The connection has not yet been created. Run the method newConnection(Config conf) before.");
        }
        return db;
    }
    
}
