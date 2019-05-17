
package net.chenxiy.lcnote.net.pojo.updatenote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateNote {

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("question")
    @Expose
    private Question question;
    @SerializedName("__typename")
    @Expose
    private String typename;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

}
