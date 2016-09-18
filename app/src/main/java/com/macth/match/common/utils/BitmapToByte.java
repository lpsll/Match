package com.macth.match.common.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/9/12.
 */
public class BitmapToByte {

    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

}
