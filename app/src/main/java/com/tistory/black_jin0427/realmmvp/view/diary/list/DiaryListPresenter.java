package com.tistory.black_jin0427.realmmvp.view.diary.list;

import com.tistory.black_jin0427.realmmvp.model.Diary;

import io.realm.RealmResults;

/**
 * Created by ifamily on 2018-01-30.
 */

public interface DiaryListPresenter {

    void setView(DiaryListPresenter.View view);

    void onItemClick(Diary diary, int position);

    void onItemLongClick(Diary diary);

    void onSearchTag(int id, String tag);

    void onAddDiary(int id);

    interface View {
        void setSearchTag(RealmResults<Diary> tagDiaries);
    }
}
