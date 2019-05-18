
package net.chenxiy.lcnote.net.pojo.github.upload;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadResultInfo {

    @SerializedName("content")
    @Expose
    private Content content;
    @SerializedName("commit")
    @Expose
    private Commit commit;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

}
