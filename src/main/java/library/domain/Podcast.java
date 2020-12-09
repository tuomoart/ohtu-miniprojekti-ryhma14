/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.domain;

import java.util.Objects;

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
        this.series = series;
        this.creator = creator;
        this.url = url;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        String result = "Nimi: " + super.getTitle() + " \n";
        result += "Sarja: " + series + "\n";
        result += "Tekijä: " + creator + "\n";
        result += "Url: " + url + "\n";
        result += "Luettu: " + super.getRead();
        return result;
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Podcast other = (Podcast) obj;
        if (!Objects.equals(this.series, other.series)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.creator, other.creator)) {
            return false;
        }
        return true;
    }
    
    
}
