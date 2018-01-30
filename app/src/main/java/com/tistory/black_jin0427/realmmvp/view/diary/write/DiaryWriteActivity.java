package com.tistory.black_jin0427.realmmvp.view.diary.write;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tistory.black_jin0427.realmmvp.Dlog;
import com.tistory.black_jin0427.realmmvp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by ifamily on 2018-01-29.
 */

public class DiaryWriteActivity extends AppCompatActivity {

    private DiaryWritePresenter diaryWritePresenter;

    private InputMethodManager imm;
    private Realm realm;

    private int personId;

    @BindView(R.id.edit_date)
    EditText editDate;

    @BindView(R.id.edit_title)
    EditText editTitle;

    @BindView(R.id.edit_content)
    EditText editContent;

    @BindView(R.id.edit_tag)
    EditText editTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_write);
        ButterKnife.bind(this);

        if(getIntent() != null) {
            personId = getIntent().getIntExtra("personId",-1);

            if(personId > 0) {

                initView();

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
        realm.close();
    }

    private void initView() {

        realm = Realm.getDefaultInstance();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowTime = sdf.format(date);
        editDate.setText(nowTime);

        diaryWritePresenter = new DiaryWritePresenterImpl(DiaryWriteActivity.this, realm);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imm.showSoftInput(editTitle, 0);
            }
        },100);

    }

    @OnClick(R.id.btn_ok) void onBtnOK() {

        String date = editDate.getText().toString();
        String title = editTitle.getText().toString();
        String content = editContent.getText().toString();
        String tag = editTag.getText().toString();

        if(TextUtils.isEmpty(date) || TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {

            Toast.makeText(getApplicationContext(), "내용을 입력해 주세요", Toast.LENGTH_LONG).show();

        } else {

            diaryWritePresenter.onAddDiary(date, title, content, tag, personId);

        }

    }

}
