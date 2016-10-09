package com.macth.match.group.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseApplication;
import com.macth.match.common.base.BaseTitleActivity;
import com.macth.match.common.base.SimplePage;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.UIHelper;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.group.adapter.ListAdapter;
import com.macth.match.group.dto.MembersDTO;
import com.macth.match.group.entity.GroupEntity;
import com.macth.match.group.entity.GroupResult;
import com.macth.match.group.entity.MembersEntity;
import com.macth.match.group.entity.MembersResult;
import com.macth.match.group.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.mention.RongMentionManager;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * 聊天页
 */
public class ConversationActivity extends BaseTitleActivity {
    TextView tv;
    /**
     * 目标 Id
     */
    private String mTargetId;

    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String mTargetIds;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;
    PopupWindow popWindow;

    List<MembersEntity> data;

    List<io.rong.imlib.model.UserInfo> list ;

    @Override
    protected int getContentResId() {
        return R.layout.conversation;
    }

    @Override
    public void initView() {

        RongMentionManager.getInstance().setMentionedInputListener((io.rong.imkit.mention.IMentionedInputListener) listener);


        tv = (TextView) findViewById(R.id.base_titlebar_ensure);
        Intent intent = getIntent();
        mTargetId = intent.getData().getQueryParameter("targetId");
        mTargetIds = intent.getData().getQueryParameter("title");
        setTitleText(mTargetIds);
        LogUtils.e("intent-----",""+mTargetIds);
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));
        String string = mConversationType.getName();
        LogUtils.e("mConversationType----",""+mConversationType);
        if(mTargetIds.equals("群组聊天")||string.equals("group")){
            tv.setVisibility(View.VISIBLE);
            tv.setText("群成员");
        }else {
            tv.setVisibility(View.GONE);
        }
        enterFragment(mConversationType, mTargetId);
    }

    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType
     * @param mTargetId
     */
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {

        ConversationFragment fragment = new ConversationFragment();

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();
        LogUtils.e("uri----",""+uri);
        fragment.setUri(uri);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_conversation, fragment);
        transaction.commit();
        //扩展功能自定义
        InputProvider.ExtendProvider[] provider = {
                new ImageInputProvider(RongContext.getInstance()),//图片
//                new CameraInputProvider(RongContext.getInstance()),//相机
//                new LocationInputProvider(RongContext.getInstance()),//地理位置
//                new VoIPInputProvider(RongContext.getInstance()),// 语音通话
//                new ContactsProvider(RongContext.getInstance())//自定义通讯录
        };

        RongIM.resetInputExtensionProvider(mConversationType, provider);

//        reqGroup();



    }



    io.rong.imkit.mention.IMentionedInputListener listener = new io.rong.imkit.mention.IMentionedInputListener() {
        @Override
        public boolean onMentionedInput(Conversation.ConversationType conversationType, String targetId) {
            LogUtils.e("targetId----",""+targetId);
            AppContext.set("groupname",mTargetIds);
            LogUtils.e("mTargetIds----",""+mTargetIds);
            Bundle bundle = new Bundle();
            bundle.putString("IMenType","1");
            UIHelper.showBundleFragment(ConversationActivity.this, SimplePage.GROUP_MEMBERS,bundle);//群成员
            return true;
        }
    };

    private void reqGroup() {
        BaseDTO dto = new BaseDTO();
        dto.setUserid(AppContext.get("usertoken", ""));
        CommonApiClient.group(this, dto, new CallBack<GroupResult>() {
            @Override
            public void onSuccess(GroupResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("获取用户群成功");
                    initDotice();

                }
            }
        });
    }

    private void initDotice() {
        RongIM.getInstance().setGroupMembersProvider(new RongIM.IGroupMembersProvider() {
            @Override
            public void getGroupMembers(String groupId, RongIM.IGroupMemberCallback callback) {
                //获取群组成员信息列表
                LogUtils.e("list----",""+list);
                callback.onGetGroupMembersResult(list); // 调用 callback 的 onGetGroupMembersResult 回传群组信息
//                RongMentionManager.getInstance().mentionMember(item.userInfo);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_titlebar_back:
                finish();
                break;
            case R.id.base_titlebar_ensure:
                AppContext.set("groupname",mTargetIds);
                UIHelper.showFragment(ConversationActivity.this, SimplePage.GROUP_MEMBERS);//群成员
//                reqMembers();
                break;
            default:
                break;
        }
        super.onClick(v);
    }

    private void reqMembers() {
        MembersDTO dto = new MembersDTO();
        dto.setName(AppContext.get("groupname", ""));
        CommonApiClient.members(this, dto, new CallBack<MembersResult>() {
            @Override
            public void onSuccess(MembersResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("获取群成员成功");
                    data = result.getData();

//                    showPopNm(data);
                }
            }
        });
    }

    private void showPopNm(List<MembersEntity> result) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.from(this).inflate(R.layout.item_pop_list, null);
        popWindow = new PopupWindow(view, WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

        int width = AppContext.get("screenWidth", 0);
        popWindow.setWidth(width * 7 / 10);

        backgroundAlpha(0.7f);
        // 需要设置一下此参数，点击外边可消失
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        popWindow.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popWindow.setFocusable(false);
        ListView listView = (ListView) view.findViewById(R.id.pop_list);
        listView.setAdapter(new ListAdapter(this, result));
        View parent = getWindow().getDecorView();//高度为手机实际的像素高度

        popWindow.showAtLocation(tv, Gravity.CENTER_VERTICAL, 0, 0);
        //添加pop窗口关闭事件
        popWindow.setOnDismissListener(new poponDismissListener());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RongIM.getInstance().startPrivateChat(ConversationActivity.this, data.get(position).getId(), "标题");
            }
        });
    }


    public class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            LogUtils.e("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
            popWindow.dismiss();
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }


}
