package com.proxima.elearning;

public class GetAttendance {

    String id,name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GetAttendance(String id, String name) {
        this.id = id;
        this.name = name;

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
