package com.macth.match.mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.entity.BaseEntity;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.DialogUtils;
import com.macth.match.common.utils.ImageLoaderUtils;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.mine.dto.CloseDTO;
import com.macth.match.mine.dto.DeleteNewDTO;
import com.macth.match.mine.entity.MineProjectsEntity;
import com.macth.match.mine.entity.NewsEvent;
import com.macth.match.recommend.RecommendUiGoto;
import com.macth.match.recommend.entity.MilDetailsResult;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/9/18.
 */
public class MineProjectsAdapter extends BaseSimpleRecyclerAdapter<MineProjectsEntity> {

    private String flag;
    private final Context context;
    TextView tv01,tv02,tv03;
    List<MineProjectsEntity> list =  new ArrayList<>();

    public MineProjectsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_mine_projects;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, MineProjectsEntity mineProjectsEntity, final int position) {

        tv01 = holder.getView(R.id.tv_mine_projects_close);
        tv02 = holder.getView(R.id.tv_mine_projects_update);
        tv03 = holder.getView(R.id.tv_mine_projects_modify);
        holder.setText(R.id.tv_mine_project_company_name, mineProjectsEntity.getCompanyname());
        holder.setText(R.id.tv_mine_project_price, mineProjectsEntity.getPrice());
        holder.setText(R.id.tv_mine_project_project_type, "项目类型："+mineProjectsEntity.getProject_type());
        holder.setText(R.id.tv_mine_project_ctime, mineProjectsEntity.getCtime());

        if (mineProjectsEntity.getProject_termunit().equals("1")) {
            holder.setText(R.id.tv_mine_project_project_termunit, mineProjectsEntity.getProject_termvalue()+"年");
        } else {
            holder.setText(R.id.tv_mine_project_project_termunit, mineProjectsEntity.getProject_termvalue()+"个月");
        }

        if (mineProjectsEntity.getProject_status().equals("1")) {
            holder.setText(R.id.tv_mine_project_project_status, "进行中");
        } else {
            holder.setText(R.id.tv_mine_project_project_status, "关闭");
        }

        if(mineProjectsEntity.getOwn().equals("1")){
            tv01.setVisibility(View.VISIBLE);
            tv02.setVisibility(View.VISIBLE);
            tv03.setVisibility(View.VISIBLE);
        }else {
//            tv01.setVisibility(View.VISIBLE);
//            tv02.setVisibility(View.VISIBLE);
//            tv03.setVisibility(View.VISIBLE);

            tv01.setVisibility(View.INVISIBLE);
            tv02.setVisibility(View.VISIBLE);
            tv03.setVisibility(View.INVISIBLE);
        }

        list.add(position,mineProjectsEntity);

        ImageView mImg = holder.getView(R.id.img_mine_project);
        ImageLoaderUtils.displayImage(mineProjectsEntity.getImage(), mImg);

        tv01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.confirm(context, "是否关闭？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CloseDTO dto = new CloseDTO();
                        dto.setPid(list.get(position).getPid());
                        CommonApiClient.close((Activity) context, dto, new CallBack<MilDetailsResult>() {
                            @Override
                            public void onSuccess(MilDetailsResult result) {
                                if(AppConfig.SUCCESS.equals(result.getCode())){
                                    LogUtils.e("申请关闭成功");
                                    DialogUtils.showPrompt(context, "提示","申请成功，请等待审核！", "知道了");
                                }
                                if(AppConfig.CODE.equals(result.getCode())){
                                    LogUtils.e("AppConfig.CODE");
                                    DialogUtils.showPrompt(context, "提示",result.getMsg(), "知道了");
                                }
                            }
                        });
                    }
                });


            }
        });

        tv02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("url", AppConfig.MYMILEPOST_H5_URL+ list.get(position).getPid()+"&userid="+AppContext.get("usertoken",""));
                LogUtils.e("url---",""+AppConfig.MYMILEPOST_H5_URL+ list.get(position).getPid()+"&userid="+AppContext.get("usertoken",""));
                RecommendUiGoto.gotoMyMilePost(context, bundle);


            }
        });

        tv03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("pid",list.get(position).getPid());
//                bundle.putString("name",list.get(position).getCompanyname());
//                bundle.putString("price",list.get(position).getPrice());
//                bundle.putString("value",list.get(position).getProject_termvalue());
//                bundle.putString("unit",list.get(position).getProject_termunit());
//                MineUIGoto.gotoModification(context,bundle);//修改项目
                if(AppContext.get("useridentity","").equals("内部用户")){
                    flag ="1";
                }else {
                    flag ="2";
                }
                Bundle bundle = new Bundle();
                bundle.putString("url", AppConfig.MDPROJECT_H5_URL+ AppContext.get("usertoken","")+"&flag="+flag+"&pid="+list.get(position).getPid());
                LogUtils.e("url---",""+AppConfig.MDPROJECT_H5_URL+ AppContext.get("usertoken","")+"&flag="+flag+"&pid="+list.get(position).getPid());
                RecommendUiGoto.gotoModificationPj(context, bundle);

            }
        });

    }


}
