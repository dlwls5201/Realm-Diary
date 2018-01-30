package com.tistory.black_jin0427.realmmvp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tistory.black_jin0427.realmmvp.R;
import com.tistory.black_jin0427.realmmvp.model.Person;

import io.realm.RealmResults;

/**
 * Created by ifamily on 2018-01-25.
 */

public class PersonAdapter extends BaseAdapter {

    private RealmResults<Person> datas;

    public PersonAdapter(RealmResults<Person> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return datas != null ? datas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            convertView = inflater.inflate(R.layout.item_person, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.txtName = convertView.findViewById(R.id.txt_name);
            viewHolder.txtAge = convertView.findViewById(R.id.txt_age);
            viewHolder.txtDiary = convertView.findViewById(R.id.txt_diary);

            convertView.setTag(viewHolder);

    } else {

        viewHolder = (ViewHolder) convertView.getTag();

    }

        viewHolder.txtName.setText(datas.get(position).getName());
        viewHolder.txtAge.setText("(" + datas.get(position).getAge() + ")");
        viewHolder.txtDiary.setText("일기 : " + datas.get(position).getDiaries().size());

        return convertView;
    }

    class ViewHolder {
        TextView txtName;
        TextView txtAge;
        TextView txtDiary;
    }
}
