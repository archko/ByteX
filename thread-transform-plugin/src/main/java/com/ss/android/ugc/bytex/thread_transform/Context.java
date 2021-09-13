package com.ss.android.ugc.bytex.thread_transform;

import com.android.build.gradle.AppExtension;
import com.ss.android.ugc.bytex.common.BaseContext;

import org.gradle.api.Project;

public final class Context extends BaseContext<ThreadTransformExtension> {

    Context(Project project, AppExtension android, ThreadTransformExtension extension) {
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
