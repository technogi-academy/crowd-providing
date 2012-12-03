package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import play.mvc.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void dashboard(String begin, String end, String category) {
        setCookie();//creamos la cookie manuelmente, pero realmente ya debería de estar hecha
        if (!session.contains("user")) {
            index();
        } else {
            Client client = findClient(Long.parseLong(session.get("user")));
            if (begin != null && end != null) {
                if (category != null) {
                    try {
                        List<Category> categoryList = Category.findAll();

                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd H:m");
                        Date beginDate = formater.parse(begin + " 00:00");

                        Date endDate = formater.parse(end + " 23:59");
                        
                        Category cat=Category.find("byName", category).first();
                        List<Request> requestList = Request.find("creationDate>=? and creationDate<=? and client_id=? and category_id=?", beginDate, endDate, client.id,cat.id).fetch();
                        String user = session.get("user");
                        render(categoryList, requestList, user,client);
                    } catch (ParseException ex) {
                        Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        List<Category> categoryList = Category.findAll();

                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd H:m");
                        Date beginDate = formater.parse(begin + " 00:00");

                        Date endDate = formater.parse(end + " 23:59");
                        List<Request> requestList = Request.find("creationDate>=? and creationDate<=? and client_id=?", beginDate, endDate, client.id).fetch();
                        String user = session.get("user");
                        render(categoryList, requestList, user,client);
                    } catch (ParseException ex) {
                        Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                if (category != null) {
                    List<Category> categoryList = Category.findAll();
                    Category cat=Category.find("byName", category).first();
                    List<Request> requestList = Request.find("client_id=? and category_id=?",client.id,cat.id).fetch();
                    String user = session.get("user");
                    render(categoryList, requestList, user,client);
                } else {
                    List<Category> categoryList = Category.findAll();
                    List<Request> requestList = Request.find("byClient", findClient(Long.parseLong(session.get("user")))).fetch();
                    String user = session.get("user");
                    render(categoryList, requestList, user,client);
                }
            }
        }
    }
    
    public static void filter(String begin, String end, String category) {
        if (begin.length()==0 || end.length()==0) {
            if (category.equals("All")) {
                dashboard(null,null,null);
            }else{
                dashboard(null,null,category);
            }
        }else{
            if (category.equals("All")) {
                dashboard(begin,end,null);
            }else{
                dashboard(begin,end,category);
            }
        }
    }
    
    public static void deleteRequest(long id){
        Request req=Request.findById(id);
        req.delete();
        dashboard(null,null,null);
    }

    @Util
    private static void setCookie() {
        session.put("user", 2);
        response.setCookie("user", "1");
    }

    public static void updateCategory(long id, String description) {
        Request newCat = Request.findById(id);
        newCat.description = description;
        newCat.save();
        dashboard(null, null, null);
    }

    public static Client findClient(long id) {
        Client client = Client.findById(id);
        return client;

    }

    public static Category findCategory(long id) {
        Category cat = Category.findById(id);
        return cat;

    }

    public static void AltaPeticion(long id) {
        render();
    }

    public static void GetRequest(Request newRequest, long idClient, long idCategory) {
        newRequest.client = findClient(idClient);
        newRequest.category = findCategory(idCategory);
        newRequest.creationDate = new Date();
        newRequest.validateAndSave();
        validation.keep();
        dashboard(null, null, null);
    }
}