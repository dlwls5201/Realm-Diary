package com.tistory.black_jin0427.realmmvp.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tistory.black_jin0427.realmmvp.R;
import com.tistory.black_jin0427.realmmvp.view.person.PersonPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ifamily on 2018-01-25.
 */

public class PersonDialog extends Dialog {

    private Activity mActivity;
    private PersonPresenter mPersonPresenter;

    private InputMethodManager imm;
    private String gender = "남자";

    @BindView(R.id.edit_name)
    EditText editName;

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @BindView(R.id.edit_age)
    EditText editAge;

    public PersonDialog(@NonNull Activity activity, PersonPresenter personPresenter) {
        super(activity);
        mActivity = activity;
        mPersonPresenter = personPresenter;

        imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        setContentView(R.layout.dialog_person);
        ButterKnife.bind(this);

        //show keyboard
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imm.showSoftInput(editName, 0);
            }
        },100);

        //init radio
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_man:
                        gender = "남자";
                        break;
                    case R.id.radio_woman:
                        gender = "여자";
                        break;
                }
            }
        });

    }

    @OnClick(R.id.btn_ok) void onOk() {
        final String name = editName.getText().toString();
        final String age = editAge.getText().toString();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(age)) {

            Toast.makeText(mActivity, "내용을 입력해 주세요",Toast.LENGTH_LONG).show();

        } else {

            if(mPersonPresenter != null) {
                mPersonPresenter.onAddPerson(name, gender, age);
            }

            dismiss();
        }
    }
}
