package com.rmit.sept.bk_loginservices.payload;
import javax.validation.constraints.NotNull;

public class UserID {

    @NotNull
    private long id;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
