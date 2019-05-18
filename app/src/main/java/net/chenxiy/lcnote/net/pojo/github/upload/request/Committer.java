
package net.chenxiy.lcnote.net.pojo.github.upload.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Committer {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
