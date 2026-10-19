package com.networkpro.app;

import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.LinearLayout;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;

public class KeyboardService extends InputMethodService {

    private LinearLayout keyboardLayout;
    private boolean isArabic = true;

    // الصفوف العربية
    private final String[] arRow1 = {"ض", "ص", "ث", "ق", "ف", "غ", "ع", "ه", "خ", "ح", "ج", "د"};
    private final String[] arRow2 = {"ش", "س", "ي", "ب", "ل", "ا", "ت", "ن", "م", "ك", "ط"};
    private final String[] arRow3 = {"ئ", "ء", "ؤ", "ر", "لا", "ى", "ة", "و", "ز", "ظ"};

    // الصفوف الإنجليزية
    private final String[] enRow1 = {"q", "w", "e", "r", "t", "y", "u", "i", "o", "p"};
    private final String[] enRow2 = {"a", "s", "d", "f", "g", "h", "j", "k", "l"};
    private final String[] enRow3 = {"z", "x", "c", "v", "b", "n", "m"};

    @Override
    public View onCreateInputView() {
        keyboardLayout = new LinearLayout(this);
        keyboardLayout.setOrientation(LinearLayout.VERTICAL);
        keyboardLayout.setBackgroundColor(Color.rgb(15, 23, 42)); // خلفية داكنة أنيقة
        keyboardLayout.setPadding(8, 12, 8, 12);

        buildKeyboard();
        return keyboardLayout;
    }

    private void buildKeyboard() {
        keyboardLayout.removeAllViews();

        String[] row1 = isArabic ? arRow1 : enRow1;
        String[] row2 = isArabic ? arRow2 : enRow2;
        String[] row3 = isArabic ? arRow3 : enRow3;

        // الصف الأول
        keyboardLayout.addView(createRow(row1));
        // الصف الثاني
        keyboardLayout.addView(createRow(row2));
        // الصف الثالث
        keyboardLayout.addView(createRow(row3));

        // الصف الرابع (الأزرار الخاصة: مسح، لغة، مسافة، إنتر)
        LinearLayout bottomRow = new LinearLayout(this);
        bottomRow.setOrientation(LinearLayout.HORIZONTAL);
        bottomRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // زر المسح (Backspace)
        Button btnDelete = createSpecialButton("⌫", Color.rgb(225, 29, 72));
        btnDelete.setOnClickListener(v -> {
            InputConnection ic = getCurrentInputConnection();
            if (ic != null) {
                ic.deleteSurroundingText(1, 0);
            }
        });
        bottomRow.addView(btnDelete);

        // زر التبديل بين اللغات
        Button btnLang = createSpecialButton(isArabic ? "EN" : "عربي", Color.rgb(79, 70, 229));
        btnLang.setOnClickListener(v -> {
            isArabic = !isArabic;
            buildKeyboard();
        });
        bottomRow.addView(btnLang);

        // زر المسافة (Space)
        Button btnSpace = createSpecialButton("مسافة / Space", Color.rgb(51, 65, 85));
        LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 3f);
        spaceParams.setMargins(4, 4, 4, 4);
        btnSpace.setLayoutParams(spaceParams);
        btnSpace.setOnClickListener(v -> {
            InputConnection ic = getCurrentInputConnection();
            if (ic != null) {
                ic.commitText(" ", 1);
            }
        });
        bottomRow.addView(btnSpace);

        // زر الإدخال (Enter)
        Button btnEnter = createSpecialButton("↵", Color.rgb(16, 185, 129));
        btnEnter.setOnClickListener(v -> {
            InputConnection ic = getCurrentInputConnection();
            if (ic != null) {
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
            }
        });
        bottomRow.addView(btnEnter);

        keyboardLayout.addView(bottomRow);
    }

    private LinearLayout createRow(String[] keys) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        for (String key : keys) {
            Button button = new Button(this);
            button.setText(key);
            button.setTextSize(18);
            button.setTextColor(Color.WHITE);
            button.setBackgroundColor(Color.rgb(30, 41, 59));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            params.setMargins(3, 4, 3, 4);
            button.setLayoutParams(params);

            button.setOnClickListener(v -> {
                InputConnection ic = getCurrentInputConnection();
                if (ic != null) {
                    ic.commitText(key, 1);
                }
            });

            row.addView(button);
        }
        return row;
    }

    private Button createSpecialButton(String text, int bgColor) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(14);
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(bgColor);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        params.setMargins(3, 4, 3, 4);
        button.setLayoutParams(params);

        return button;
    }
}
