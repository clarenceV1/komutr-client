package com.komutr.client.been;

public class BuySellTicketDetails {


    /**
     * buy : {"content":"dsfdsfsf","created_at":"0000-00-00 00:00:00","id":1,"title":"dcvdsfsf"}
     * refund : {"content":"dsfdsfsf","created_at":"0000-00-00 00:00:00","id":1,"title":"dcvdsfsf"}
     *
     */

    private Buy buy;
    private Refund refund;

    public Buy getBuy() {
        return buy;
    }

    public void setBuy(Buy buy) {
        this.buy = buy;
    }

    public Refund getRefund() {
        return refund;
    }

    public void setRefund(Refund refund) {
        this.refund = refund;
    }

    public static class Buy {
        /**
         * content : dsfdsfsf
         * created_at : 0000-00-00 00:00:00
         * id : 1
         * title : dcvdsfsf
         */

        private String content;
        private String created_at;
        private int id;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class Refund {
        /**
         * content : dsfdsfsf
         * created_at : 0000-00-00 00:00:00
         * id : 1
         * title : dcvdsfsf
         */

        private String content;
        private String created_at;
        private int id;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

