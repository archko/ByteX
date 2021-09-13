package com.ss.android.ugc.bytex.thread_transform.visitor;

import com.ss.android.ugc.bytex.common.visitor.BaseClassVisitor;
import com.ss.android.ugc.bytex.thread_transform.Context;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ThreadClassVisitor extends BaseClassVisitor {
    private static String TAG;
    private final Context context;
    private String className;

    public ThreadClassVisitor(Context context) {
        this.context = context;
        TAG = context.extension.getName();
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv;
        mv = super.visitMethod(access, name, desc, signature, exceptions);

        if ("hello".equals(name) || "hello2".equals(name)) {
            StringBuilder sb = new StringBuilder();
            sb.append("name:").append(name)
                    .append(" desc;").append(desc);
            mv.visitFieldInsn(
                    Opcodes.GETSTATIC,
                    "java/lang/System",
                    "out",
                    "Ljava/io/PrintStream;"
            );
            mv.visitLdcInsn("----thread----" + sb);
            mv.visitMethodInsn(
                    Opcodes.INVOKEVIRTUAL,
                    "java/io/PrintStream",
                    "println",
                    "(Ljava/lang/String;)V",
                    false
            );
        }

        //mv = new InlineGetterSetterMethodVisitor(context, mv, className, name, desc);
        return mv;
    }
}
