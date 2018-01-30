package com.tistory.black_jin0427.realmmvp.view.diary.write;

import android.app.Activity;
import android.text.TextUtils;

import com.tistory.black_jin0427.realmmvp.model.Diary;
import com.tistory.black_jin0427.realmmvp.model.Person;

import io.realm.Realm;

/**
 * Created by ifamily on 2018-01-30.
 */

public class DiaryWritePresenterImpl implements DiaryWritePresenter {

    private Activity mActivity;
    private Realm mRealm;

    public DiaryWritePresenterImpl(Activity activity, Realm realm) {
        mActivity = activity;
        mRealm = realm;
    }

    @Override
    public void onAddDiary(final String date, final String title, final String content, final String tag, final int personId) {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Add a diary
                Diary diary = realm.createObject(Diary.class);
                diary.setDate(date);
                diary.setTitle(title);
                diary.setContent(content);
                diary.setPersonId(personId);

                if(!TextUtils.isEmpty(tag)) diary.setTag(tag);

                Person person = realm.where(Person.class).equalTo("id", personId).findFirst();
                person.getDiaries().add(diary);

                mActivity.finish();

            }
        });

    }

}
