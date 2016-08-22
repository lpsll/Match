package com.macth.match.common.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.macth.match.common.base.SimpleActivity;
import com.macth.match.common.base.SimplePage;


/**
 * Created by John_Libo on 2016/8/18.
 */
public class UIHelper {

    public static void showFragment(Context context, SimplePage page) {
        Intent intent = new Intent(context, SimpleActivity.class);
        intent.putExtra(SimpleActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

    public static void showFragment(Context context, SimplePage page,
                                    Bundle args) {
        Intent intent = new Intent(context, SimpleActivity.class);
        intent.putExtra(SimpleActivity.BUNDLE_KEY_PAGE, page.getValue());
        intent.putExtra(SimpleActivity.BUNDLE_KEY_ARGS, args);
        context.startActivity(intent);
    }
}
