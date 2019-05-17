
package net.chenxiy.lcnote.net.pojo.updatenote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("updateNote")
    @Expose
    private UpdateNote updateNote;

    public UpdateNote getUpdateNote() {
        return updateNote;
    }

    public void setUpdateNote(UpdateNote updateNote) {
        this.updateNote = updateNote;
    }

}
