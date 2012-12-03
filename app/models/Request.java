package models;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

@Entity
public class Request extends Model{
	
	@ManyToOne
	public Client client;
	@OneToOne
	public Category category;
	@Temporal(TemporalType.TIMESTAMP)
	public Date creationDate;
	public String description;
}
