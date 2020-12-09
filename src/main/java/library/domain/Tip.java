/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.domain;

/**
 *
 * @author hiira
 */
public class Tip {
    private String title;
    private boolean read;
    private final String id;
    
    public Tip(String title, String id, Boolean read) {
        this.title = title;
        this.id = id;
        this.read = read;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public Boolean isRead() {
        return read;
    }
    
    public void setAsRead() {
        this.read = true;
    }
    
    public String getId() {
        return id;
    }
    
    @Override
    public String toString() {
        String result = "Otsikko: " + title;
        
        return result;
    }
}
