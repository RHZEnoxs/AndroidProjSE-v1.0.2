package com.enoxs.se.page.sample;

import com.enoxs.se.R;
import com.enoxs.se.flow.pathview.Layout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flow.path.Path;

/**
 * Created by enoxs on 2018/5/4.
 */
@Layout(R.layout.page_sample)
public class SamplePath extends Path{
    private static Logger log = LoggerFactory.getLogger(SamplePath.class);
    public SamplePath(){
        log.info("init SamplePath.");
    }
}
