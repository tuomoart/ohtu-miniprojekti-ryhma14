/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.domain;

/**
 *
 * @author tuomoart
 */
public class Podcast extends Tip {
    private String series;
    private String url;
    private String creator;
    
    public Podcast(String title, String series, String id, String creator, String url, Boolean isRead) {
        super(title, id, isRead);
    }
}
