package com.tistory.black_jin0427.realmmvp.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by ifamily on 2018-01-25.
 */

public class Person extends RealmObject {

    /**
     *  PrimaryKey -> 기본키, 암묵적 @Index 포함
     *  Required -> non null
     *  Index -> 색인 기능
     */

    @PrimaryKey
    private int id;

    @Required
    private String name;
    private String gender;
    private int age;

    private RealmList<Diary> diaries;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public RealmList<Diary> getDiaries() {
        return diaries;
    }

    public void setDiaries(RealmList<Diary> diaries) {
        this.diaries = diaries;
    }
}
