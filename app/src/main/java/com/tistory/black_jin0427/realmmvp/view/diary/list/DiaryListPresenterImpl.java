package com.tistory.black_jin0427.realmmvp.view.diary.list;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.tistory.black_jin0427.realmmvp.model.Diary;
import com.tistory.black_jin0427.realmmvp.view.diary.write.DiaryWriteActivity;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ifamily on 2018-01-30.
 */

public class DiaryListPresenterImpl implements DiaryListPresenter{

    private Activity mActivity;
    private Realm mRealm;
    private View view;

    public DiaryListPresenterImpl(Activity activity, Realm realm) {
        mActivity = activity;
        mRealm = realm;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void onItemClick(Diary diary) {
        String text = "날짜 : " + diary.getDate() + "  제목 : " + diary.getTitle() + "   내용 : " + diary.getContent() + "   테그 : " + diary.getTitle();
        Toast.makeText(mActivity, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemLongClick(final Diary diary) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        alertDialogBuilder.setTitle("확인해주세요");
        alertDialogBuilder.setMessage("삭제하시겠습니까?")
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                diary.deleteFromRealm();
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
    public void onSearchTag(int id, String tag) {
        if(view != null) {
            RealmResults<Diary> tagDiaries = mRealm.where(Diary.class)
                    .equalTo("personId",id)
                    .equalTo("tag",tag)
                    .findAllSorted("date");

            view.setSearchTag(tagDiaries);
        }
    }

    @Override
    public void onAddDiary(int id) {
        Intent intent = new Intent(mActivity, DiaryWriteActivity.class);
        intent.putExtra("personId", id);
        mActivity.startActivity(intent);
    }
}
