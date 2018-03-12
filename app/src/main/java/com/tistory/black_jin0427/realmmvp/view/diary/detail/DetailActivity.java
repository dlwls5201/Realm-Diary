package com.tistory.black_jin0427.realmmvp.view.diary.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.tistory.black_jin0427.realmmvp.Dlog;
import com.tistory.black_jin0427.realmmvp.R;
import com.tistory.black_jin0427.realmmvp.model.Diary;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by iwedding on 2018. 3. 12..
 */

public class DetailActivity extends AppCompatActivity implements DetailPresenter.View {

    private DetailPresenter detailPresenter;

    private Realm realm;
    private Diary diary;
    private int personId, position;

    @BindView(R.id.edit_date)
    EditText editDate;

    @BindView(R.id.edit_title)
    EditText editTitle;

    @BindView(R.id.edit_content)
    EditText editContent;

    @BindView(R.id.edit_tag)
    EditText editTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent != null) {
            detailPresenter = new DetailPresenterImpl(this);
            detailPresenter.setView(this);

            realm = Realm.getDefaultInstance();

            personId = intent.getIntExtra("personId", -1);
            position = intent.getIntExtra("position", -1);
            Dlog.d("personId : " + personId + " , position : " + position);

            RealmResults<Diary> diaries = realm.where(Diary.class).equalTo("personId", personId).findAllSorted("date");
            Dlog.d("diaries : " + diaries);

            diary = diaries.get(position);
            Dlog.d("diary : " + diary);

            initView();


        }

    }

    private void initView() {

        editDate.setText(diary.getDate());
        editTitle.setText(diary.getTitle());
        editContent.setText(diary.getContent());
        editTag.setText(diary.getTag());

    }

    @OnClick(R.id.btn_update) void onBtnUpdate() {

        final String date = editDate.getText().toString();
        final String title = editTitle.getText().toString();
        final String content = editContent.getText().toString();
        final String tag = editTag.getText().toString();

        if(TextUtils.isEmpty(date) || TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {

            Toast.makeText(getApplicationContext(), "내용을 입력해 주세요", Toast.LENGTH_LONG).show();

        } else {

            //diaryWritePresenter.onAddDiary(date, title, content, tag, personId);
            //detailPresenter.onUpdateDiary(date, title, content, tag, personId);

            detailPresenter.onUpdateDiary(date, title, content, tag);

        }

    }

    @Override
    public void setUpdateDiary(final String date, final String title, final String content, final String tag) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                diary.setDate(date);
                diary.setTitle(title);
                diary.setContent(content);

                if (!TextUtils.isEmpty(tag)) diary.setTag(tag);

                //diary.setPersonId(personId);

                finish();
            }
        });

    }
}
