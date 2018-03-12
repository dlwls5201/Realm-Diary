package com.tistory.black_jin0427.realmmvp.view.diary.detail;

import android.app.Activity;

import io.realm.Realm;

/**
 * Created by iwedding on 2018. 3. 12..
 */

public class DetailPresenterImpl implements DetailPresenter {

    private Activity mActivity;
    private Realm mRealm;
    private View view;

    public DetailPresenterImpl(Activity activity) {
        mActivity = activity;
        mRealm = Realm.getDefaultInstance();
    }


    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void onUpdateDiary(String date, String title, String content, String tag) {
        if(view != null) {
            view.setUpdateDiary(date, title, content, tag);
        }
    }

}
