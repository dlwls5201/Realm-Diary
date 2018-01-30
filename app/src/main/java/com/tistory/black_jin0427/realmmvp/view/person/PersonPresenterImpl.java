package com.tistory.black_jin0427.realmmvp.view.person;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.tistory.black_jin0427.realmmvp.Dlog;
import com.tistory.black_jin0427.realmmvp.model.Diary;
import com.tistory.black_jin0427.realmmvp.model.Person;
import com.tistory.black_jin0427.realmmvp.view.diary.list.DiaryListActivity;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ifamily on 2018-01-30.
 */

public class PersonPresenterImpl implements PersonPresenter {

    private Activity mActivity;
    private Realm mRealm;


    public PersonPresenterImpl(Activity activity, Realm realm) {
        mActivity = activity;
        mRealm = realm;
    }

    @Override
    public void onItemClick(Person person) {

        Intent intent = new Intent(mActivity, DiaryListActivity.class);
        intent.putExtra("id", person.getId());
        mActivity.startActivity(intent);

    }

    @Override
    public void onItemLongCLick(final Person person) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        alertDialogBuilder.setTitle("확인해주세요");
        alertDialogBuilder.setMessage("삭제하시겠습니까?")
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                RealmResults<Diary> diaries = mRealm.where(Diary.class).equalTo("personId", person.getId()).findAll();
                                Dlog.e(person.getId() + " delete diaries : " + diaries);
                                diaries.deleteAllFromRealm();

                                person.deleteFromRealm();

                            }
                        });

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();

    }

    @Override
    public void onAddPerson(final String name, final String gender, final String age) {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Add a person
                Number maxId = realm.where(Person.class).max("id");
                Log.d("MyTag","maxId : " + maxId);

                int nextId = maxId == null ? 1 : maxId.intValue() + 1;

                Person person = realm.createObject(Person.class, nextId);
                person.setName(name);
                person.setGender(gender);
                person.setAge(Integer.parseInt(age));

            }
        });

    }
}
