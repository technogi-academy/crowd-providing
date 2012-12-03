package models;

import java.util.Date;
import javax.persistence.Column;

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
        @Column(columnDefinition = "TEXT") // Si no columna se hace varchar(255) bye mensajes grandes =(
	public String message;
	public String attachements;
	@Temporal(TemporalType.TIMESTAMP)
	public Date date;
	@ManyToOne
	public Conversation conversation;
        public String sender;
	
	
}
