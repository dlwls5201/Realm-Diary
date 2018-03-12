package com.tistory.black_jin0427.realmmvp.view.diary.detail;


/**
 * Created by iwedding on 2018. 3. 12..
 */

public interface DetailPresenter {

    void setView(DetailPresenter.View view);

    void onUpdateDiary(String date, String title, String content, String tag);

    interface View {
        void setUpdateDiary(String date, String title, String content, String tag);
    }
}
