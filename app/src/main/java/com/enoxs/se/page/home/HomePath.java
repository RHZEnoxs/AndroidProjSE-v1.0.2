package com.enoxs.se.page.home;


import com.enoxs.se.R;
import com.enoxs.se.flow.pathview.Layout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flow.path.Path;

/**
 * Created by wu on 2015/08/25
 */
@Layout(R.layout.page_home)
public class HomePath extends Path {
    private static Logger log = LoggerFactory.getLogger(HomePath.class);
    public HomePath(){
        log.info("init HomePath.");
    }
}
