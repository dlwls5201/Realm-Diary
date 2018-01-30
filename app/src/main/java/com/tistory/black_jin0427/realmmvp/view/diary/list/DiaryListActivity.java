package com.tistory.black_jin0427.realmmvp.view.diary.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tistory.black_jin0427.realmmvp.Dlog;
import com.tistory.black_jin0427.realmmvp.R;
import com.tistory.black_jin0427.realmmvp.adapter.DiaryAdapter;
import com.tistory.black_jin0427.realmmvp.model.Diary;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by ifamily on 2018-01-26.
 */

public class DiaryListActivity extends AppCompatActivity implements DiaryListPresenter.View {

    private Realm realm;
    private DiaryAdapter adapter;
    private DiaryListPresenter diaryListPresenter;

    private int id;
    private RealmResults<Diary> diaries;

    @BindView(R.id.edit_tag)
    EditText editTag;
    @BindView(R.id.list_diary)
    ListView listDiary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);
        ButterKnife.bind(this);

        if(getIntent() != null) {
            id = getIntent().getIntExtra("id",-1);

            if(id > 0) {

                realm = Realm.getDefaultInstance();
                diaryListPresenter = new DiaryListPresenterImpl(DiaryListActivity.this, realm);
                diaryListPresenter.setView(this);

                initListView();

            } else {
                Dlog.e("data error");
                finish();
            }
        } else {
            Dlog.e("data error");
            finish();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        diaries.removeAllChangeListeners();
        realm.close();
    }


    private void initListView() {

        diaries = realm.where(Diary.class).equalTo("personId",id).findAllSorted("date");
        diaries.addChangeListener(new RealmChangeListener<RealmResults<Diary>>() {
            @Override
            public void onChange(RealmResults<Diary> diaries) {
                Dlog.v("diaries : " + diaries);
                adapter.notifyDataSetChanged(); // UI를 갱신합니다.
            }
        });

        adapter = new DiaryAdapter(diaries);
        listDiary.setAdapter(adapter);

        listDiary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Diary diary = (Diary) parent.getAdapter().getItem(position);
                diaryListPresenter.onItemClick(diary);

            }
        });

        listDiary.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Diary diary = (Diary) parent.getAdapter().getItem(position);
                diaryListPresenter.onItemLongClick(diary);

                return false;
            }
        });

    }

    @OnClick(R.id.btn_tag) void onBtnTag() {
        String tag = editTag.getText().toString();
        diaryListPresenter.onSearchTag(id, tag);
    }

    @OnClick(R.id.fab_diary) void addDiary() {
        diaryListPresenter.onAddDiary(id);
    }

    @Override
    public void setSearchTag(RealmResults<Diary> tagDiaries) {

        if(tagDiaries.size() > 0) {
            adapter.setDiaryTag(tagDiaries);
        } else {
            Toast.makeText(getApplicationContext(), "해당 색인이 없습니다.", Toast.LENGTH_LONG).show();
        }

        editTag.setText(null);
    }
}
