package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Supplier extends Model{

    public boolean registeredKey;
    public String company;
    @Required
    public String password;
    @Email
    public String email; 
    /*Lo cambié porque no tenemos ni una categoria para desplegar*/
    public String[] categories;
    /* @ManyToMany
     * public List<Category> categories;
     */
    @OneToOne
    public Privacy privacy;
    @Temporal(TemporalType.TIMESTAMP)
    public Date registerDate=new Date();
    
    public void setRegisteredKey(boolean registeredKey) {
        this.registeredKey = registeredKey;
    }
}