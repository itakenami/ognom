package ognom.db;

/**
 *
 * @author itakenami
 */
public class Config {
    
    protected String host;
    protected int port;
    protected String db;
    protected String login = null;
    protected String senha = null;
    
    public Config(String host, int port, String db){
        this.host = host;
        this.port = port;
        this.db = db;
    }
    
    public Config(String host, int port, String db, String login, String senha){
        this.host = host;
        this.port = port;
        this.db = db;
        this.login = login;
        this.senha = senha;
    }
    
}