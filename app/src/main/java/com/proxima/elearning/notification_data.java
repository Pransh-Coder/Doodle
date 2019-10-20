package com.proxima.elearning;

public class notification_data {
    String id,created_at,title,message;
    public notification_data(String id, String created_at, String title, String message) {
        this.id=id;
        this.created_at=created_at;
        this.title=title;
        this.message=message;
    }

    public String getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
