package com.tistory.black_jin0427.realmmvp.view.diary.write;

/**
 * Created by ifamily on 2018-01-30.
 */

public interface DiaryWritePresenter {

    void onAddDiary(String date, String title, String content, String tag, int personId);

}
