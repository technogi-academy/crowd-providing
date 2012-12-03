package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Unique;
import play.db.jpa.Model;

/**
 * Para la clase de Cliente el id es el campo id de la clase model, el cual
 * es un autonumérico. El username no se utilizará como id, ya que los 
 * queries sobre cadenas son mas lentos.
 * 
 * De cualquier forma con la anotación de Unique se evita que el campo se duplique 
 * @author dev
 *
 */
@Entity
public class Client extends Model {

	@Unique
	public String username;
	@Password
	public String password;
	@Email
	public String email;
	public String name;
	public String lastname1;
	public String lastname2;
	@Temporal(TemporalType.DATE)
	public Date dateOfBirth;
	@Temporal(TemporalType.TIMESTAMP)
	public Date registerDate;
	
	@OneToOne
	public Privacy privacy;
        
        public int registeredKey = 0;
	
        /*
        * Metodo que verifica si un usuario ya fue registrado
        */

        public static Client connect(String username, String password) {
            return find("byUsernameAndPassword", username, password).first();
        }
}
