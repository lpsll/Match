package com.macth.match.receiver;

import android.content.Context;
import android.view.View;

import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.utils.LogUtils;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * 融云的消息推送接收
 */
public class NotificationReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage message) {

        String reciver = AppContext.get("receiver","");
        LogUtils.e("reciver---",""+reciver);
        if(reciver.equals("0")){
            return false;
        }else {
            return true;
        }

    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage message) {
        return false;
    }
}
