
package net.chenxiy.lcnote.net.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProblemData {

    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("num_solved")
    @Expose
    private Integer numSolved;
    @SerializedName("num_total")
    @Expose
    private Integer numTotal;
    @SerializedName("ac_easy")
    @Expose
    private Integer acEasy;
    @SerializedName("ac_medium")
    @Expose
    private Integer acMedium;
    @SerializedName("ac_hard")
    @Expose
    private Integer acHard;
    @SerializedName("stat_status_pairs")
    @Expose
    private List<StatStatusPair> statStatusPairs = null;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getNumSolved() {
        return numSolved;
    }

    public void setNumSolved(Integer numSolved) {
        this.numSolved = numSolved;
    }

    public Integer getNumTotal() {
        return numTotal;
    }

    public void setNumTotal(Integer numTotal) {
        this.numTotal = numTotal;
    }

    public Integer getAcEasy() {
        return acEasy;
    }

    public void setAcEasy(Integer acEasy) {
        this.acEasy = acEasy;
    }

    public Integer getAcMedium() {
        return acMedium;
    }

    public void setAcMedium(Integer acMedium) {
        this.acMedium = acMedium;
    }

    public Integer getAcHard() {
        return acHard;
    }

    public void setAcHard(Integer acHard) {
        this.acHard = acHard;
    }

    public List<StatStatusPair> getStatStatusPairs() {
        return statStatusPairs;
    }

    public void setStatStatusPairs(List<StatStatusPair> statStatusPairs) {
        this.statStatusPairs = statStatusPairs;
    }

}
