package controllers;

import java.util.List;
import models.Conversation;
import play.mvc.With;

@With(Secure.class)
public class Conversations extends CRUD {
    @Check("Supplier")
    public static void index() {
        String user = Security.connected();
        room(user);
        Conversation.get().join(user);
    }
    
    public static void room(String user) {
        List events = Conversation.get().archive();
        render(user, events);
    }
    
    public static void say(String message) {
        String user = Security.connected();
        Conversation.get().say(user, message);
        room(user);
    }
    
    public static void leave() {
        Conversation.get().leave(Security.connected());
        Application.index();
    }
}
