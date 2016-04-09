package com.bupt.johnfrey.whisper.otto;

import com.squareup.otto.Bus;

/**
 *  Created by zhangfengyang on 16/4/5
 */
public class OttoManager {
    private static final Bus BUS = new Bus();

    public OttoManager() {
    }

    public static Bus getInstance() {
        return BUS;
    }

    public static void register(Object o) {
        getInstance().register(o);
    }

    public static void unregister(Object o) {
        getInstance().unregister(o);
    }
}
