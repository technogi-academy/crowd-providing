/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//POJO para obtener via AJAX solo datos necesarios de una conversacion
// No son parte del modelo de datos, no se guardan a BD
package models;

import java.util.Date;

/**
 *
 * @author Alvaro
 */
public class ConversationDetails {
    
        public Long id;

        public Date creationDate;
	public String details;
	public String open;
        
        public int messagesLength;
        
        
	//Supplier
	public String supplierCompany;
        public String supplierEmail;
        
        //Client
        public String clientEmail;
	public String clientName;
        public String clientUsername;
	public String clientLastname1;
	public String clientLastname2;
        
        //Request
        public String requestDesc;
        public Date requestDate;
        public String categoryName;
        
        public ConversationDetails(Conversation c)
        {
            Request r = Request.findById(c.idRequest);
            Supplier s = Supplier.findById(c.idSupplier);
            
            id = c.id;
            
            creationDate = c.creationDate;
            details = c.details;
            open = c.open;
            
            messagesLength = c.messages.size();
            
            supplierCompany = s.company;
            supplierEmail = s.email;
            
            clientUsername = c.client.username;
            clientEmail = c.client.email;
            clientName = c.client.name;
            clientLastname1 = c.client.lastname1;
            clientLastname2 = c.client.lastname2;
            
            requestDate = r.creationDate;
            requestDesc = r.description;
            categoryName = r.category.name;
        }
}
