package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void client(Long id) {
        Client client = Client.findById(id);
        render(client);
    }

    public static void supplier(long id) {

        Supplier supplier = Supplier.findById(id);
        render(supplier);
    }
    
    public static void supplierMsg(long id){
        Supplier supplier = Supplier.findById(id);
        render(supplier);
    }

    public static void getConversations(long id, boolean isSupplier) {
        List<Conversation> lc;
        if (isSupplier) {
            lc = Conversation.find("byIdSupplier", id).fetch();
        } else {
            Client c = Client.findById(id);
            lc = Conversation.find("byClient", c).fetch();
        }
        List<ConversationDetails> cdl = new ArrayList<ConversationDetails>();
        
        for (Conversation c: lc)
        {
            ConversationDetails cd = new ConversationDetails(c);
            cdl.add(cd);
        }
        
        renderJSON(cdl);

    }
    
        public static void getNewConversations(long id, int convCount) {

        List<Conversation> lc;

            Client c = Client.findById(id);
            lc = Conversation.find("byClient", c).fetch();
        
        List<ConversationDetails> cdl = new ArrayList<ConversationDetails>();
       
        
            
        for (int i = lc.size()-1; i >= convCount; i --)
        {
            Conversation conv = lc.get(i);
            ConversationDetails cd = new ConversationDetails(conv);
            cdl.add(cd);
        }
        
        renderJSON(cdl);

    }

    public static void addMessage(long conversationId, String message, String attachments, boolean sentFromSupplier) {
        
        Conversation conv = Conversation.findById(conversationId);
        if(conv.open.equals("true")){
            Message m = new Message();
            m.idTransmitter = (sentFromSupplier) ? conv.idSupplier : conv.client.id;
            m.idReceiver = (sentFromSupplier) ? conv.client.id : conv.idSupplier;
            m.message = message;
            m.attachements = attachments;
            m.sender = (sentFromSupplier)? "Supplier": "Client";
            m.date = new Date();
            //m.conversation = conv; ///Esta referencia provoca errores de invocacion de metodos reflexivos 

            m.save();

            conv.messages.add(m);
            conv.save();

            renderJSON("{\"status\": 200}");
        }
        else{
            renderJSON("{\"status\": 409}");
        }
    }

    public static class dateComparator implements Comparator<Message> {

        public int compare(Message o1, Message o2) {
            return o1.date.compareTo(o2.date);
        }
    }

    public static void getNewMessages(long conversationId, int messageCount) {
        Conversation conv = Conversation.findById(conversationId);
        List<Message> lm = conv.messages;

        Collections.sort(lm, new dateComparator());

        List<Message> newMessages = new ArrayList<Message>();
        int size = lm.size();
        for (int i = size - 1; i >= messageCount; i--) {
            newMessages.add(lm.get(i));
        }

        renderJSON(newMessages);
    }
    
    public static void closeConversation(long conversationId){
        Conversation conv = Conversation.findById(conversationId);
        conv.open="false";
        conv.save();
        renderJSON("{\"status\": 200}");
    }

    public static void getSupplierRequests(long id) {

        Supplier s = Supplier.findById(id);
        List<Request> lr = new ArrayList<Request>();

        List<Category> lc = s.categories;
        for (Category c : lc) {
            List<Request> reqlist = Request.find("byCategory", c).fetch();
            for (Request r : reqlist) {
                Conversation conv = Conversation.find("byIdRequest", r.id).first();
                if (conv == null) {

                    lr.add(r);
                }

            }
        }
        renderJSON(lr);

    }

    public static void addNewConversation(long idSupplier, long idRequest,
            String message, String attachments, String convDetails) {

        Request r = Request.findById(idRequest);
        Message m = new Message();
        m.idTransmitter = idSupplier;
        m.idReceiver = r.client.id;
        m.message = message;
        m.attachements = attachments;
        m.sender = "Supplier";
        m.date = new Date();

        m.save();
        
        Supplier supp = Supplier.findById(idSupplier);
        
        Conversation conv = new Conversation();
        conv.creationDate = m.date;
        conv.open = "true";
        conv.client = r.client;
        conv.details = convDetails;
        conv.idRequest = r.id;
        conv.idSupplier = idSupplier;
        conv.messages = new ArrayList<Message>();        

        conv.save();

        Message om = Message.findById(m.id);

        Conversation oc = Conversation.findById(conv.id);

        oc.messages.add(om);
        oc.save();


        renderJSON("{\"status\": 200}");

    }

    public static void testConversations() {
        Client client = Client.find("byUsername",
                "alan").first();
        List<Supplier> suppliers = Supplier.findAll();
        Supplier sup = suppliers.get(0);

        Request r = Request.find("byClient", client).first();


        Message m = new Message();
        m.idTransmitter = sup.id;
        m.idReceiver = client.id;

        m.message = "Helllo";
        m.date = new Date();
        //m.conversation = conv;
        m.save();

        Conversation conv = new Conversation();
        conv.client = client;
        conv.details = "Conversas";
        conv.idSupplier = sup.id;
        conv.idRequest = r.id;

        conv.messages = new ArrayList<Message>();
        conv.messages.add(m);
        conv.save();

        renderJSON(conv);


    }
}