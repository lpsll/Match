package com.macth.match.group.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BaseFragment;
import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.CharacterParser;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.PinyinComparator;
import com.macth.match.common.utils.PinyinUtils;
import com.macth.match.common.widget.SideBar;
import com.macth.match.group.adapter.SortAdapter;
import com.macth.match.group.entity.PersonBean;
import com.macth.match.recommend.entity.AddItemListResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imageloader.utils.L;

/**
 * 群组
 */
public class GroupFragment extends BaseFragment {
    @Bind(R.id.listview)
    ListView groupList;
    @Bind(R.id.dialog)
    TextView dialog;
    @Bind(R.id.sidebar)
    SideBar sidebar;
    @Bind(R.id.et)
    EditText et;
    private SortAdapter sortadapter;
    private List<PersonBean> data;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;


    @Override
    protected void retry() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_group;
    }

    @Override
    public void initView(View view) {
        LogUtils.e("initView---", "initView");
        characterParser = CharacterParser.getInstance();
        sidebar.setTextView(dialog);
        // 设置字母导航触摸监听
        sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = sortadapter.getPositionForSelection(s.charAt(0));

                if (position != -1) {
//                    groupList.scrollToPosition(position);
                    groupList.setSelection(position);
                }
            }
        });

        //根据输入框输入值的改变来过滤搜索
        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
//                Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        data = getData(getResources().getStringArray(R.array.listpersons));
        // 数据在放在adapter之前需要排序
        Collections.sort(data, new PinyinComparator());
        sortadapter = new SortAdapter(getActivity(), data);
        groupList.setAdapter(sortadapter);

    }

    /**
     * 为ListView填充数据
     */
    private List<PersonBean> getData(String[] data) {
        List<PersonBean> listarray = new ArrayList<PersonBean>();
        for (int i = 0; i < data.length; i++) {
            String pinyin = PinyinUtils.getPingYin(data[i]);
            String Fpinyin = pinyin.substring(0, 1).toUpperCase();

            PersonBean person = new PersonBean();
            person.setName(data[i]);
            person.setPinYin(pinyin);
            // 正则表达式，判断首字母是否是英文字母
            if (Fpinyin.matches("[A-Z]")) {
                person.setFirstPinYin(Fpinyin);
            } else {
                person.setFirstPinYin("#");
            }

            listarray.add(person);
        }
        return listarray;

    }


    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<PersonBean> filterDateList = new ArrayList<PersonBean>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = data;
        } else {
            filterDateList.clear();
//            String result = "";
//            for (PersonBean sortModel : data) {
//                // 先将输入的字符串转换为拼音
//                characterParser.setResource(filterStr);
//                result = characterParser.getSpelling();
//                LogUtils.e("contains----",""+contains(sortModel, result));
//                if (contains(sortModel, result)) {
//                    filterDateList.add(sortModel);
//                } else if (sortModel.getName().contains(filterStr)) {
////                    sortModel.setGroup(str);
//                    filterDateList.add(sortModel);
//                }
//            }

            for (PersonBean sortModel : data) {
                String name = sortModel.getName();
                LogUtils.e("name---",""+name);
                LogUtils.e("name---getSelling---",""+characterParser.getSelling(name).toUpperCase());
                LogUtils.e("filterStr---getSelling---",""+characterParser.getSelling(filterStr.toString()));


                if (characterParser.getSelling(name).toUpperCase()
                        .startsWith(characterParser.getSelling(filterStr.toString()).toUpperCase())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, new PinyinComparator());
        sortadapter.updateListView(filterDateList);
    }


    /**
     * 根据拼音搜索
     *
     * @return
     */
    public  boolean contains(PersonBean contact, String search) {
        if (TextUtils.isEmpty(contact.getName())) {
            return false;
        }

        boolean flag = false;

        if (!flag) { // 如果简拼已经找到了，就不使用全拼了
            // 全拼匹配
            characterParser.setResource(contact.getName());
            // 不区分大小写
            Pattern pattern2 = Pattern
                    .compile(search, Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = pattern2.matcher(characterParser.getSpelling());
            flag = matcher2.find();
        }

        return flag;
    }

    private void reqGroup() {
        BaseDTO dto = new BaseDTO();
        dto.setUserid(AppContext.get("usertoken", ""));
        CommonApiClient.group(getActivity(), dto, new CallBack<AddItemListResult>() {
            @Override
            public void onSuccess(AddItemListResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("获取用户群成功");
                }
            }
        });
    }

    @Override
    public void initData() {
        LogUtils.e("initData---", "initData");
        reqGroup();
    }



}
