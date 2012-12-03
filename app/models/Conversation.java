package models;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import play.db.jpa.Model;
import play.libs.F;
import play.libs.F.ArchivedEventStream;

@Entity
public class Conversation extends Model {
	
    public Date creationDate;
    public String details;
    public String open;
    public Long idSupplier;
    public Long idRequest;
    
    @Lob
    public final ArchivedEventStream<Message> chatEvents = new F.ArchivedEventStream<Message>(100);
	
    @ManyToOne
    public Client client;
    @OneToMany
    public List<Message> messages;
    
    
	
    Conversation() {
        creationDate = new Date();
    }
    static class ConversationSingleton {
        public static final Conversation INSTANCE = new Conversation();
    }
    
    public static Conversation get() {
        return ConversationSingleton.INSTANCE;
    }
    
    
    
    public F.EventStream<Message> join(String user) {
        chatEvents.publish(new Message(user, user + "join", "join", this));
        return chatEvents.eventStream();
    }
    
    public void leave(String user) {
        chatEvents.publish(new Message(user, "leave", "leave", this));
    }
    
    public void say(String user, String text) {
        if(text != null && !text.trim().equals(""))
            chatEvents.publish(new Message(user, text, "message", this));
    }
    
    public List<Message> archive() {
        messages = chatEvents.archive();
        return chatEvents.archive();
    }
}
