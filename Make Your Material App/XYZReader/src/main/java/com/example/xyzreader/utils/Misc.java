package com.example.xyzreader.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.example.xyzreader.R;

public class Misc {

    public static void makeSnackBar(Context context, View view, String text, boolean error) {
        final Snackbar snackbar;
        snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, error ? R.color.colorError : R.color.colorInfo));
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(3);

        snackbar.show();
    }
}
