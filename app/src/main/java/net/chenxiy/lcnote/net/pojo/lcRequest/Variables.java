
package net.chenxiy.lcnote.net.pojo.lcRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Variables {

    @SerializedName("titleSlug")
    @Expose
    private String titleSlug;
    @SerializedName("content")
    @Expose
    private String content;

    public String getTitleSlug() {
        return titleSlug;
    }

    public void setTitleSlug(String titleSlug) {
        this.titleSlug = titleSlug;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
