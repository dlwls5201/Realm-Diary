package com.tistory.black_jin0427.realmmvp.view.person;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tistory.black_jin0427.realmmvp.Dlog;
import com.tistory.black_jin0427.realmmvp.R;
import com.tistory.black_jin0427.realmmvp.adapter.PersonAdapter;
import com.tistory.black_jin0427.realmmvp.model.Person;
import com.tistory.black_jin0427.realmmvp.view.dialog.PersonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by ifamily on 2018-01-18.
 */

public class PersonActivity extends AppCompatActivity {

    @BindView(R.id.list_view)
    ListView listView;

    private Realm realm;
    private RealmResults<Person> datas;

    private PersonPresenter personPresenter;
    private PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();
        personPresenter = new PersonPresenterImpl(PersonActivity.this, realm);

        initListView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        datas.removeAllChangeListeners();
        realm.close();

    }

    private void initListView() {

        datas = realm.where(Person.class).findAllSortedAsync("id");
        datas.addChangeListener(new RealmChangeListener<RealmResults<Person>>() {
            @Override
            public void onChange(RealmResults<Person> people) {
                Dlog.w("people : " + people);
                adapter.notifyDataSetChanged(); // UI를 갱신합니다.
            }
        });

        adapter = new PersonAdapter(datas);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Person person = (Person) parent.getAdapter().getItem(position);
                personPresenter.onItemClick(person);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Person person = (Person) parent.getAdapter().getItem(position);
                personPresenter.onItemLongCLick(person);

                return false;
            }
        });

    }

    @OnClick(R.id.fab) void addPerson() {
        new PersonDialog(PersonActivity.this, personPresenter).show();
    }

}