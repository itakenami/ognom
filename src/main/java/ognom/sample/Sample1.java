package ognom.sample;

import ognom.Model;
import ognom.annotation.Entity;
import ognom.db.Config;
import ognom.db.Connection;

@Entity(name="users")
class User extends Model {
    
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    
}
/**
 *
 * @author itakenami
 */
public class Sample1 {
    public static void main(String[] args) {
        
        //Make connection
        Connection.getInstance().newConnection(new Config("alex.mongohq.com", 10017, "ognom-test", "test", "test"));
        
        //Create object and save
        User u = new User();
        u.setName("Igor Takenami");
        u.setAge(32);
        u.save();
    }
}
