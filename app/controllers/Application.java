package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import play.data.validation.Required;
import play.data.validation.Validation;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    public static void chat(@Required String user, @Required String demo) { 
        Conversations.index();      
    }
}