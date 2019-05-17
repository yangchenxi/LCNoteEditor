
package net.chenxiy.lcnote.net.pojo.updatenote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("questionId")
    @Expose
    private String questionId;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("__typename")
    @Expose
    private String typename;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

}
