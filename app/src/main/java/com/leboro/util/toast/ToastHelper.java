package com.leboro.util.toast;

import com.leboro.MainActivity;

import android.widget.Toast;

public class ToastHelper {

    public static void showLongToast(int messageResourceId) {
        Toast.makeText(MainActivity.context, messageResourceId, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(int messageResourceId) {
        Toast.makeText(MainActivity.context, messageResourceId, Toast.LENGTH_LONG).show();
    }

}
