package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.validation.Email;
import play.data.validation.Password;
import play.db.jpa.Model;

@Entity
public class Supplier extends Model{

	public String company;
	@Email
	public String email; 
	@ManyToMany
	public List<Category> categories;
	@OneToOne
	public Privacy privacy;
        @Password
        public String password;
        
        public int registeredKey = 0;
        
        /*
        * Metodo que verifica si un usuario ya fue registrado
        */

        public static Supplier connect(String email, String password) {
            return find("byEmailAndPassword", email, password).first();
        }
}
