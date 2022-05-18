package com.noah.pws.addon.addons;

import com.noah.pws.addon.AddonAbstract;
import com.noah.pws.suite.Suite;

public class TestAddon extends AddonAbstract {

    @Override
    public void onEnable(Suite suite) {
        System.out.println("test addon loaded in suite: " + suite);
    }
}
