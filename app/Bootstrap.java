import play.*;
import play.jobs.*;
import play.test.*;
 
import models.*;

/**
 *
 * @author Alvaro
 */
@OnApplicationStart
public class Bootstrap extends Job{
    
    @Override
     public void doJob() {
        // Check if the database is empty
        if(Client.count() == 0) {
            Fixtures.deleteDatabase();
            Fixtures.loadModels("test-data.yml");
            System.out.println("Usuarios creados");
        }
        else
        {
            System.out.println("Ya hay usuarios");
        }
    }
}
