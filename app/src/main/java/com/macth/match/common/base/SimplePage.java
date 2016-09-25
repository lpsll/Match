package com.macth.match.common.base;


import com.macth.match.R;
import com.macth.match.group.fragment.GroupFragment;
import com.macth.match.group.fragment.GroupMembersFragment;
import com.macth.match.mine.fragment.UpdateMilestoneFragment;
import com.macth.match.recommend.fragment.AddUseFragment;
import com.macth.match.recommend.fragment.AttachmentsFragment;
import com.macth.match.recommend.fragment.MilestoneDetailsFragment;

/**
 * Created by John_Libo on 2016/8/18.
 */
public enum SimplePage {

//        SELECT_ADDRESS(1, R.string.common, SelectAddressFragment.class),

          MILESTONE_DETAILS(1, R.string.milepost_details, MilestoneDetailsFragment.class),
          ATTACHMENTS(2, R.string.look, AttachmentsFragment.class),
          UPDATA_MILESTONE(3, R.string.updata, UpdateMilestoneFragment.class),
          ADD_USE(4, R.string.add_use_money, AddUseFragment.class),
          GROUP(5, R.string.tab_group, GroupFragment.class),
          GROUP_MEMBERS(6, R.string.group_members, GroupMembersFragment.class),
    ;

    private int title;
    private Class<?> clz;
    private int value;

    SimplePage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SimplePage getPageByValue(int val) {
        for (SimplePage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }
}
