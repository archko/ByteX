package com.ss.android.ugc.bytex.toast;

import com.android.build.gradle.AppExtension;
import com.ss.android.ugc.bytex.common.CommonPlugin;
import com.ss.android.ugc.bytex.common.TransformConfiguration;
import com.ss.android.ugc.bytex.common.visitor.ClassVisitorChain;
import com.ss.android.ugc.bytex.pluginconfig.anno.PluginConfig;
import com.ss.android.ugc.bytex.toast.visitor.ToastClassNodeVisitor;
import com.ss.android.ugc.bytex.transformer.TransformEngine;

import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;

import javax.annotation.Nonnull;

@PluginConfig("bytex.toast")
public class ToastPlugin extends CommonPlugin<ToastExtension, Context> {
    @Override
    protected Context getContext(Project project, AppExtension android, ToastExtension extension) {
        return new Context(project, android, extension);
    }

    @Override
    public void traverse(@NotNull String relativePath, @NotNull ClassVisitorChain chain) {
        super.traverse(relativePath, chain);
    }

    @Override
    public void beforeTransform(@NotNull TransformEngine engine) {
        super.beforeTransform(engine);
    }

    @Override
    public boolean transform(@NotNull String relativePath, @NotNull ClassVisitorChain chain) {
        return super.transform(relativePath, chain);
    }

    @Override
    public boolean transform(@NotNull String relativePath, @NotNull ClassNode node) {
        new ToastClassNodeVisitor(context, relativePath).transform(node);
        return super.transform(relativePath, node);
    }

    @Nonnull
    @Override
    public TransformConfiguration transformConfiguration() {
        return new TransformConfiguration() {
            @Override
            public boolean isIncremental() {
                return false;
            }
        };
    }
}
