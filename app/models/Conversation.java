package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Conversation extends Model{
	
	public Date creationDate;
	public String details;
	public String open;
	public Long idSupplier;
	public Long idRequest;
	
	@ManyToOne
	public Client client;
	@OneToMany
	public List<Message> messages;
	
}
