package com.enoxs.se.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.enoxs.se.R;
import com.enoxs.se.flow.GsonParceler;
import com.enoxs.se.flow.pathview.HandlesBack;
import com.enoxs.se.flow.pathview.PageListener;
import com.enoxs.se.manager.SettingManager;
import com.enoxs.se.model.central.DataCenter;
import com.enoxs.se.model.central.MessageCenter;
import com.enoxs.se.page.home.HomePath;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flow.Flow;
import flow.FlowDelegate;
import flow.History;
import flow.path.Path;
import flow.path.PathContainerView;

public class MainActivity extends AppCompatActivity implements Flow.Dispatcher {
    private static Logger log = LoggerFactory.getLogger(MainActivity.class);
    /**
     * Android System
     */
    private static Context context;

    /**
     * Central
     */
    private static DataCenter dataCenter;
    private static MessageCenter messageCenter;

    /**
     * flowPath MVP
     */

    private static FlowDelegate flowSupport;
    private static PathContainerView container;
    private static PageListener pageListener;
    private static HandlesBack containerAsBackTarget;

    /**
     * Setting Configuration (Thread).
     */

    private SettingManager mSettingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        initCentral();
        initSetting();
        initView(savedInstanceState);
        log.info("MainActivity -> init.");
    }
    public void initCentral(){
//        dataCenter = new DataCenter();
        messageCenter = new MessageCenter();
    }
    public void initSetting(){
        mSettingManager = new SettingManager(context);
    }
    public void initView(Bundle savedInstanceState){
        container = (PathContainerView) findViewById(R.id.container);
        containerAsBackTarget = (HandlesBack) container;
        pageListener = (PageListener) container;
        FlowDelegate.NonConfigurationInstance nonConfig = (FlowDelegate.NonConfigurationInstance) getLastCustomNonConfigurationInstance();
        GsonParceler parceler = new GsonParceler(new Gson());
        History history = History.single(getFirstPage());
        flowSupport = FlowDelegate.onCreate(nonConfig, getIntent(), savedInstanceState, parceler, history, this);
    }




    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        flowSupport.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        flowSupport.onResume();
    }

    @Override
    protected void onPause() {
        flowSupport.onPause();
        super.onPause();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return flowSupport.onRetainNonConfigurationInstance();
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        Object service = null;
        if (flowSupport != null) {
            service = flowSupport.getSystemService(name);
        }
        return service != null ? service : super.getSystemService(name);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        flowSupport.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (containerAsBackTarget.onBackPressed()) return;
        if (flowSupport.onBackPressed()) return;
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        pageListener.onCreateOptionsMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void dispatch(Flow.Traversal traversal, final Flow.TraversalCallback callback) {
        boolean canGoBack = traversal.destination.size() > 1;
        setActionBarHomeEnabled(canGoBack);
        invalidateOptionsMenu();
        container.dispatch(traversal, new Flow.TraversalCallback() {
            @Override
            public void onTraversalCompleted() {
                callback.onTraversalCompleted();
            }
        });
    }

    private void setActionBarHomeEnabled(boolean enabled){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(enabled);
        }
    }

    public Path getFirstPage() {
        //TODO Use your own path.
        return new HomePath();
    }

    /**
     * Back Button with no action
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        log.debug("onKeyDown()");
        if(keyCode== KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0)
            return false;
        return super.onKeyDown(keyCode, event);
    }
}
