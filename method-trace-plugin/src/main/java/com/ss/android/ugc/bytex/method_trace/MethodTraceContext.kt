package com.ss.android.ugc.bytex.method_trace

import com.android.build.gradle.AppExtension
import com.ss.android.ugc.bytex.common.BaseContext
import org.gradle.api.Project

/**
 * @author: archko 2021/8/28 :18:02
 */
class MethodTraceContext(project: Project, android: AppExtension, extension: MethodTraceExtension) :
    BaseContext<MethodTraceExtension?>(project, android, extension)