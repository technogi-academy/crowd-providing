package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {
    
    public static void index() {
        render();
    }
    
    public static void message(){
        render();
    }
    
    public static void signupClient() {
        render();
    }
    
    public static void saveNewClient(Client client){
        boolean exist= false;
        if(Client.count()>0){
            List<Client> clients = Client.findAll();
            for (int i = 0; i < clients.size(); i++) {
                if((clients.get(i).email.equals(client.email)) || (clients.get(i).username.equals(client.username))){
                    if(clients.get(i).registeredKey){
                        exist=true;
                    }else{
                        clients.get(i).delete();
                    }
                    break;
                }
            }
        }
        if(exist){
            message();
        }else{
            Date today = client.registerDate;
            Date birthDate = client.dateOfBirth;
            int age = today.getYear() - birthDate.getYear();
            int m = today.getMonth() - birthDate.getMonth();
            if (m < 0 || (m == 0 && today.getDate() < birthDate.getDate())) {
                age--;
            }
            client.age=age;
            client.registeredKey=false;
            client.validateAndSave();
            validation.keep();
            validatingClient(client.getId());
        }
    }
    
    public static void validatingClient(Long id){
        Client client= Client.findById(id);
        new SendMail(client.email,"Hi "+client.username+" <br/> Thanks for registering on 'Crowd Providing' <br/> Please Save your registration <br/>", "http://localhost:9000/confirmSignup?id="+id);
        render(client);
    }
    
    public static void confirmSignup(Long id){
        Client client= Client.findById(id);
        client.registeredKey=true;
        client.save();
        render(client);
    }
    
    public static void profileClient(Long id){
        Client client= Client.findById(id);
        /*Cambiar render proveedor al método que hace render del dashboard del Cliente*/
        render(client);        
    }
    
    public static void viewRegisteredClients(){
        if(Client.count()>0){
            List<Client> clients = Client.findAll();
            render(clients);
        }
    }
    
    public static void signupSupplier() {
        render();
    }
    
    public static void saveNewSupplier(Supplier supplier){
        boolean exist= false;
        if(Supplier.count()>0){
            List<Supplier> suppliers = Supplier.findAll();
            for (int i = 0; i < suppliers.size(); i++) {
                if(suppliers.get(i).company.equals(supplier.company)){
                    if(suppliers.get(i).registeredKey){
                        exist=true;
                    }else{
                        suppliers.get(i)._delete();
                    }
                    break;
                }
            }
        }
        if(exist){
            message();
        }else{
            supplier.registeredKey=false;
            supplier.validateAndSave();
            validation.keep();
            validatingSupplier(supplier.getId());
        }
    }
    
    public static void validatingSupplier(Long id){
        Supplier supplier= Supplier.findById(id);
        render(supplier);
    }
    
    public static void profileSupplier(Long id){
        Supplier supplier= Supplier.findById(id);
        supplier.registeredKey=true;
        supplier.save();
        /*Cambiar render proveedor al método que hace render del dashboard del Proveedor*/
        render(supplier);
    }
    
    public static void viewRegisteredSuppliers(){
        if(Supplier.count()>0){
            List<Supplier> suppliers = Supplier.findAll();
            render(suppliers);
        }
    }

}