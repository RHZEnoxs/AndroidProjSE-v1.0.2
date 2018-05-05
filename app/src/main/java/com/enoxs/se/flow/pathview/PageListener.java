package com.enoxs.se.flow.pathview;

import android.app.Activity;
import android.view.Menu;

/**
 * Created by wu on 2015/08/26
 */
public interface PageListener {
    void onPageStart(Activity activity);

    void onCreateOptionsMenu(Activity activity, Menu menu);
}
