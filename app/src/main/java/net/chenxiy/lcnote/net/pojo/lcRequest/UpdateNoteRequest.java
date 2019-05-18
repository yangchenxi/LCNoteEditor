
package net.chenxiy.lcnote.net.pojo.lcRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateNoteRequest {

    @SerializedName("operationName")
    @Expose
    private String operationName;
    @SerializedName("variables")
    @Expose
    private Variables variables;
    @SerializedName("query")
    @Expose
    private String query;

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public Variables getVariables() {
        return variables;
    }

    public void setVariables(Variables variables) {
        this.variables = variables;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

}
