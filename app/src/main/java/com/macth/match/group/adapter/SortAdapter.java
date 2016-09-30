package com.macth.match.group.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.macth.match.R;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.group.entity.GroupEntity;

import java.util.List;

/**
 * Created by John_Libo on 2016/9/29.
 */
public class SortAdapter extends BaseAdapter {
    private final List<GroupEntity> persons;
    private Context context;
    private LayoutInflater inflater;

    public SortAdapter(Context context, List<GroupEntity> persons) {
        this.context = context;
        this.persons = persons;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_group, null);
            viewholder.group_tv = (TextView) convertView
                    .findViewById(R.id.group_tv);
            viewholder.group_img = (ImageView) convertView
                    .findViewById(R.id.group_img);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.group_tv.setText(persons.get(position).getGroupname());
        ImageLoaderUtils.displayAvatarImage(persons.get(position).getGroupimg(),viewholder.group_img);

        return convertView;
    }



    class ViewHolder {
        TextView group_tv;
        ImageView group_img;
    }


}
