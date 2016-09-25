package com.macth.match.common.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.macth.match.R;


/**
 * Created by sks on 2016/2/17.
 */
public class PickPhotoDialog extends AlertDialog {

    public TextView textAlbum,textCamera,textCancel;
    public PickPhotoDialog(Context context, int theme) {
        super(context, theme);
    }

    public PickPhotoDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pick_photo);
        textAlbum = (TextView) findViewById(R.id.photozoom);
        textCamera = (TextView) findViewById(R.id.takephoto);
        textCancel = (TextView) findViewById(R.id.pop_cancel);
    }

}
