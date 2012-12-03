package controllers;

import models.*;

public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
        Client cli = Client.connect(username, password);
        if(cli != null)
            return cli.registeredKey == 1;
        else {
            Supplier sup = Supplier.connect(username, password);
            if(sup != null)
                return sup.registeredKey == 1;
            else return false;
        }
    }

    static void onDisconnected() {
        Application.index();
    }

    static void onAuthenticated() {
        Conversations.index();
    }
    
    static boolean check(String profile) {
        System.out.println(connected());
        Supplier sup = Supplier.find("byEmail", connected()).first();
        System.out.println(sup!=null);
        return sup != null;
    } 
}