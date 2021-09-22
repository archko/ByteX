package com.ss.android.ugc.bytex.toast;

import com.android.build.gradle.AppExtension;
import com.ss.android.ugc.bytex.common.BaseContext;

import org.gradle.api.Project;

public final class Context extends BaseContext<ToastExtension> {

    public boolean optimizationEnabled = true;

    Context(Project project, AppExtension android, ToastExtension extension) {
        super(project, android, extension);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void releaseContext() {
        super.releaseContext();
    }
}
