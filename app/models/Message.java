package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

@Entity
public class Message extends Model{

	public Long idTransmitter;
	public Long idReceiver;
	public String message;
	public String attachments;
	@Temporal(TemporalType.TIMESTAMP)
	public Date date;
	@ManyToOne
	public Conversation conversation;
        String type;
        String user;
        
    public Message(String user, String message, String type, Conversation conversation) {
        this.user = user;
        this.message = message;
        this.type = type;
        this.conversation = conversation;
        this.attachments = "";
        this.date = new Date();
    }   
        
}
