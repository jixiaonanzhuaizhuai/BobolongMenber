package com.lgmember.util;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.lgmember.activity.BaseActivity;

/**
 * Created by Yanan_Wu on 2017/2/10.
 */

public class MyUtils {


    public static void showToast(final Activity ctx, final String msg) {


        if ("main".equals(Thread.currentThread().getName())) {
            Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
        } else {
            ctx.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    //此处的代码会在主线程中执行
                    Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
                }
            });
        }

    }


}
