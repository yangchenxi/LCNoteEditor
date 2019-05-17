
package net.chenxiy.lcnote.net.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatStatusPair {

    @SerializedName("stat")
    @Expose
    private Stat stat;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("difficulty")
    @Expose
    private Difficulty difficulty;
    @SerializedName("paid_only")
    @Expose
    private Boolean paidOnly;
    @SerializedName("is_favor")
    @Expose
    private Boolean isFavor;
    @SerializedName("frequency")
    @Expose
    private Double frequency;
    @SerializedName("progress")
    @Expose
    private Double progress;

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Boolean getPaidOnly() {
        return paidOnly;
    }

    public void setPaidOnly(Boolean paidOnly) {
        this.paidOnly = paidOnly;
    }

    public Boolean getIsFavor() {
        return isFavor;
    }

    public void setIsFavor(Boolean isFavor) {
        this.isFavor = isFavor;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

}
