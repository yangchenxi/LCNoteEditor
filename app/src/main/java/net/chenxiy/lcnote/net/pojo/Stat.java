
package net.chenxiy.lcnote.net.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stat {

    @SerializedName("question_id")
    @Expose
    private Integer questionId;
    @SerializedName("question__article__live")
    @Expose
    private Boolean questionArticleLive;
    @SerializedName("question__article__slug")
    @Expose
    private String questionArticleSlug;
    @SerializedName("question__title")
    @Expose
    private String questionTitle;
    @SerializedName("question__title_slug")
    @Expose
    private String questionTitleSlug;
    @SerializedName("question__hide")
    @Expose
    private Boolean questionHide;
    @SerializedName("total_acs")
    @Expose
    private Integer totalAcs;
    @SerializedName("total_submitted")
    @Expose
    private Integer totalSubmitted;
    @SerializedName("frontend_question_id")
    @Expose
    private Integer frontendQuestionId;
    @SerializedName("is_new_question")
    @Expose
    private Boolean isNewQuestion;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Boolean getQuestionArticleLive() {
        return questionArticleLive;
    }

    public void setQuestionArticleLive(Boolean questionArticleLive) {
        this.questionArticleLive = questionArticleLive;
    }

    public String getQuestionArticleSlug() {
        return questionArticleSlug;
    }

    public void setQuestionArticleSlug(String questionArticleSlug) {
        this.questionArticleSlug = questionArticleSlug;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionTitleSlug() {
        return questionTitleSlug;
    }

    public void setQuestionTitleSlug(String questionTitleSlug) {
        this.questionTitleSlug = questionTitleSlug;
    }

    public Boolean getQuestionHide() {
        return questionHide;
    }

    public void setQuestionHide(Boolean questionHide) {
        this.questionHide = questionHide;
    }

    public Integer getTotalAcs() {
        return totalAcs;
    }

    public void setTotalAcs(Integer totalAcs) {
        this.totalAcs = totalAcs;
    }

    public Integer getTotalSubmitted() {
        return totalSubmitted;
    }

    public void setTotalSubmitted(Integer totalSubmitted) {
        this.totalSubmitted = totalSubmitted;
    }

    public Integer getFrontendQuestionId() {
        return frontendQuestionId;
    }

    public void setFrontendQuestionId(Integer frontendQuestionId) {
        this.frontendQuestionId = frontendQuestionId;
    }

    public Boolean getIsNewQuestion() {
        return isNewQuestion;
    }

    public void setIsNewQuestion(Boolean isNewQuestion) {
        this.isNewQuestion = isNewQuestion;
    }

}
