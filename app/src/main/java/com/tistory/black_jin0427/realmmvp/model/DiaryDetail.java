package com.tistory.black_jin0427.realmmvp.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 *  사용하지 않음
 *  Realm Migration 예제 데이터
 */
public class DiaryDetail extends RealmObject {

    @Required
    private String date;

    @Required
    private String title;

    private String description;
    private int number;

}
