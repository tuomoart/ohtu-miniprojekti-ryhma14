package library.domain;

import java.util.Objects;

/**
 *
 * @author emma
 */
public class WebLink extends Tip {

    private String URL;
    private String comment;

    public WebLink(String id, String URL, String comment, Boolean read) {
        super("", id, read);
        this.URL = URL;
        this.comment = comment;
    }

    public String getURL() {
        return URL;
    }

    public String getComment() {
        return comment;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        String result = "URL: " + URL + "\n";
        result += "Kommentti: " + comment + "\n";

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
        final WebLink other = (WebLink) obj;

        return Objects.equals(this.URL, other.getURL());
    }

}
