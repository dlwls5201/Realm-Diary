package com.tistory.black_jin0427.realmmvp.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tistory.black_jin0427.realmmvp.R;
import com.tistory.black_jin0427.realmmvp.model.Diary;

import io.realm.RealmResults;

/**
 * Created by ifamily on 2018-01-25.
 */

public class DiaryAdapter extends BaseAdapter {

    private RealmResults<Diary> diaries;

    public DiaryAdapter(RealmResults<Diary> diaries) {

        this.diaries = diaries;
    }

    public void setDiaryTag(RealmResults<Diary> tagDiaries) {
        this.diaries = tagDiaries;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return diaries != null ? diaries.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return diaries != null ? diaries.get(position) : null;
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

            convertView = inflater.inflate(R.layout.item_diary, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.txtDate = convertView.findViewById(R.id.txt_date);
            viewHolder.txtTitle = convertView.findViewById(R.id.txt_title);
            viewHolder.txtContent = convertView.findViewById(R.id.txt_content);
            viewHolder.txtTag = convertView.findViewById(R.id.txt_tag);

            convertView.setTag(viewHolder);

    } else {

        viewHolder = (ViewHolder) convertView.getTag();

    }

        viewHolder.txtDate.setText(diaries.get(position).getDate());
        viewHolder.txtTitle.setText(diaries.get(position).getTitle());
        viewHolder.txtContent.setText(diaries.get(position).getContent());

        String tag = diaries.get(position).getTag();
        if(TextUtils.isEmpty(tag)) {
            viewHolder.txtTag.setVisibility(View.GONE);
        } else {
            viewHolder.txtTag.setVisibility(View.VISIBLE);
            viewHolder.txtTag.setText("#" + tag);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView txtDate;
        TextView txtTitle;
        TextView txtContent;
        TextView txtTag;
    }
}
