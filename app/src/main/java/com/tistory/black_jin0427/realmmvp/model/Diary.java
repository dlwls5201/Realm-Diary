package com.tistory.black_jin0427.realmmvp.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by ifamily on 2018-01-25.
 */

public class Diary extends RealmObject {


    @Required
    private String date;

    @Required
    private String title;

    @Required
    private String content;

    @Required
    private Integer personId;

    private String tag;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
