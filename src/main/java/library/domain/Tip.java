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

    @lombok.Generated
    public String getTitle() {
        return title;
    }

    @lombok.Generated
    public void setTitle(String title) {
        this.title = title;
    }
    
    @lombok.Generated
    public Boolean isRead() {
        return read;
    }

    @lombok.Generated
    public void setRead(boolean read) {
        this.read = read;
    }
    
    @lombok.Generated
    public String getId() {
        return id;
    }
    
    
    @lombok.Generated
    @Override
    public String toString() {
        String result = "Otsikko: " + title + "\n";
        result += "Luettu: " + read;
        
        return result;
    }
}
