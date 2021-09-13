package com.ss.android.ugc.bytex.method_trace

import com.android.build.gradle.AppExtension
import com.ss.android.ugc.bytex.common.CommonPlugin
import com.ss.android.ugc.bytex.common.visitor.ClassVisitorChain
import com.ss.android.ugc.bytex.pluginconfig.anno.PluginConfig
import org.gradle.api.Project

/**
 * @author: archko 2021/8/28 :18:00
 */
@PluginConfig("bytex.method_trace")
class MethodTracePlugin : CommonPlugin<MethodTraceExtension, MethodTraceContext>() {
    override fun getContext(
        project: Project,
        android: AppExtension,
        extension: MethodTraceExtension
    ): MethodTraceContext {
        return MethodTraceContext(project, android, extension)
    }

    override fun transform(relativePath: String, chain: ClassVisitorChain): Boolean {
        chain.connect(MethodTraceVisitor(context))
        return super.transform(relativePath, chain)
    }

}