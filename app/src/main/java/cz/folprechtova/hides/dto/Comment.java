package cz.folprechtova.hides.dto;

import java.io.Serializable;


public class Comment implements Serializable {

    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
