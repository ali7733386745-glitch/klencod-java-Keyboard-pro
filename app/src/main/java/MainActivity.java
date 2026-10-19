package com.networkpro.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.Settings;
import android.widget.*;
import android.view.Gravity;
import android.graphics.Color;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 40, 30, 30);
        layout.setBackgroundColor(Color.rgb(15, 23, 42));

        TextView title = new TextView(this);
        title.setText("Smart Keyboard");
        title.setTextSize(26);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 20, 0, 30);
        layout.addView(title);

        TextView info = new TextView(this);
        info.setText("تفعيل لوحة المفاتيح:\n\n1. اضغط على الزر أدناه لفتح إعدادات اللغات.\n2. قم بتفعيل (Smart Keyboard).\n3. اخترها كلوحة مفاتيح افتراضية.");
        info.setTextSize(16);
        info.setTextColor(Color.LTGRAY);
        info.setPadding(10, 20, 10, 30);
        layout.addView(info);

        Button btnEnable = new Button(this);
        btnEnable.setText("تفعيل لوحة المفاتيح من الإعدادات");
        btnEnable.setTextColor(Color.WHITE);
        btnEnable.setBackgroundColor(Color.rgb(79, 70, 229));
        layout.addView(btnEnable);

        btnEnable.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
            startActivity(intent);
        });

        setContentView(layout);
    }
}
