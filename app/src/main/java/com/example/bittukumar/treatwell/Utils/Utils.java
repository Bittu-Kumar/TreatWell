package com.example.bittukumar.treatwell.Utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bittukumar.treatwell.R;

/**
 * Created by Bittu Kumar on 04-11-2017.
 */

public class Utils {

    private static View successToastView;

    public static void showSuccessToast(Context context, String message) {
        if (context != null) {
            if (successToastView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                successToastView = inflater.inflate(R.layout.message_toast_view, null);
            }
            TextView textView = (TextView) successToastView.findViewById(R.id.messageTV);
            textView.setText(message);
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setView(successToastView);
            toast.show();
        }
    }
}
