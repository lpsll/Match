package com.macth.match.group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.group.entity.MembersEntity;
import com.macth.match.group.entity.MembersResult;

import java.util.List;

/**
 * Created by John_Libo on 2016/9/25.
 */
public class ListAdapter extends BaseAdapter {
    private final List<MembersEntity> mEntity;
    private Context mContext;
    public ListAdapter(Context context, List<MembersEntity> mLanderInEntity) {
        this.mContext = context;
        this.mEntity = mLanderInEntity;

    }

    @Override
    public int getCount() {
        return mEntity.size();
    }

    @Override
    public Object getItem(int position) {
        return mEntity.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        private LinearLayout lin;
        private ImageView img;
        private TextView text;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.pop_list_item,
                    null);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.pop_item_img);
            viewHolder.text = (TextView) convertView.findViewById(R.id.pop_item_tv);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(null==mEntity.get(position).getName()){
            viewHolder.text.setText("null");
        }
//        viewHolder.text.setText(mEntity.get(position).getName());
//        ImageLoaderUtils.displayAvatarImage(mEntity.get(position).getImg(), viewHolder.img);
        return convertView;
    }
}
