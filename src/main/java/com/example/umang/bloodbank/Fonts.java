package com.example.umang.bloodbank;

/**
 * Created by UMANG on 4/22/2016.
 */
import android.content.Context;
import android.graphics.Typeface;

public class Fonts {

    public static Typeface getPacifico(Context c, String name) {

        Typeface fontPacifico = Typeface.createFromAsset(c.getAssets(),
                "fonts/" + name);

        return fontPacifico;
    }

    public static Typeface getFont(Context c, String name) {
        Typeface font = Typeface.DEFAULT;
        try {
            font = Typeface.createFromAsset(c.getAssets(), "fonts/" + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return font;
    }

}
