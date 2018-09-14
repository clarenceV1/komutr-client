package com.komutr.client.been;

public class AboutUs {
    /**
     * content : {"id":1,"title":"dcvdsfsf","content":"dsfdsfsf","link":null,"image":null,"thumbnail_image_1":null,"created_at":"0000-00-00 00:00:00"}
     * service_phone : 2223333
     */

    private Content content;
    private String service_phone;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getService_phone() {
        return service_phone;
    }

    public void setService_phone(String service_phone) {
        this.service_phone = service_phone;
    }

    public static class Content {
        /**
         * id : 1
         * title : dcvdsfsf
         * content : dsfdsfsf
         * link : null
         * image : null
         * thumbnail_image_1 : null
         * created_at : 0000-00-00 00:00:00
         */

        private int id;
        private String title;
        private String content;
        private Object link;
        private Object image;
        private Object thumbnail_image_1;
        private String created_at;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getLink() {
            return link;
        }

        public void setLink(Object link) {
            this.link = link;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public Object getThumbnail_image_1() {
            return thumbnail_image_1;
        }

        public void setThumbnail_image_1(Object thumbnail_image_1) {
            this.thumbnail_image_1 = thumbnail_image_1;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
    //  {"content":"dsfdsfsf","created_at":"0000-00-00 00:00:00","id":1,"title":"dcvdsfsf"}



}
