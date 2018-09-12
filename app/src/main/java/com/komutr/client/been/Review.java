package com.komutr.client.been;

/**
 *订单详情里的评论
 */
public class Review {


    /**
     * id : 3
     * status : 2
     * review_score : 3
     * content : dsfdsfdsf
     */

    private int id;
    private int status;
    private Integer review_score;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getReview_score() {
        return review_score;
    }

    public void setReview_score(Integer review_score) {
        this.review_score = review_score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
