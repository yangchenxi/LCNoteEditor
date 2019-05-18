
package net.chenxiy.lcnote.net.pojo.github.upload.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadRequest {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("committer")
    @Expose
    private Committer committer;
    @SerializedName("content")
    @Expose
    private String content;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Committer getCommitter() {
        return committer;
    }

    public void setCommitter(Committer committer) {
        this.committer = committer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
