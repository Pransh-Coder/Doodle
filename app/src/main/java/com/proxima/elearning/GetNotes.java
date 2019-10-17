package com.proxima.elearning;

public class GetNotes {
    String chapter,topic,note;
    public GetNotes(String chapter, String topic, String note) {
        this.chapter=chapter;
        this.note=note;
        this.topic=topic;
    }

    public String getChapter() {
        return chapter;
    }

    public String getTopic() {
        return topic;
    }

    public String getNote() {
        return note;
    }


}
