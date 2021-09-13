package com.ss.android.ugc.bytex.method_trace

import com.ss.android.ugc.bytex.common.Constants
import com.ss.android.ugc.bytex.common.visitor.BaseClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * @author: archko 2021/8/28 :18:02
 */
class MethodTraceVisitor(
    private var context: MethodTraceContext
) : BaseClassVisitor() {

    private lateinit var className: String

    override fun visit(
        version: Int,
        access: Int,
        name: String,
        signature: String?,
        superName: String?,
        interfaces: Array<String?>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name
    }

    override fun visitMethod(
        access: Int,
        name: String,
        desc: String,
        signature: String?,
        exceptions: Array<String?>?
    ): MethodVisitor? {
        var mv: MethodVisitor
        mv = super.visitMethod(access, name, desc, signature, exceptions)

        if ("hello" == name || "hello2" == name) {
            val sb = StringBuilder();
            sb.append("name:").append(name)
                .append(" desc;").append(desc);
            mv.visitFieldInsn(
                Opcodes.GETSTATIC,
                "java/lang/System",
                "out",
                "Ljava/io/PrintStream;"
            );
            mv.visitLdcInsn("-------trace-------$sb");
            mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "println",
                "(Ljava/lang/String;)V",
                false
            );
            context.logger.i("MethodTraceVisitor", "----插桩----className: $desc  methodName: ${name}------")
        }

        return mv
    }

    private class InlineMethodTraceVisitor internal constructor(
        private val context: MethodTraceContext,
        private val methodVisitor: MethodVisitor,
        private val owner: String,
        private val name: String,
        private val desc: String
    ) :
        MethodVisitor(Constants.ASM_API, methodVisitor) {

        override fun visitMethodInsn(
            opcode: Int,
            owner: String?,
            name: String?,
            descriptor: String?,
            isInterface: Boolean
        ) {
            super.visitMethodInsn(opcode, owner, name, descriptor, false)
            methodVisitor.visitFieldInsn(
                Opcodes.GETSTATIC,
                "java/lang/System",
                "out",
                "Ljava/io/PrintStream;"
            )
            //methodVisitor.visitLdcInsn("hello!-------=====-------hello")
            methodVisitor.visitLdcInsn(
                String.format(
                    "-------===== name:%s,descriptor:%s",
                    name,
                    descriptor
                )
            )
            methodVisitor.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "println",
                "(Ljava/lang/String;)V",
                false
            )
            methodVisitor.visitMaxs(2, 2)
        }

        override fun visitCode() {
            super.visitCode()
            methodVisitor.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "println",
                "(Ljava/lang/String;)V",
                false
            )
            context.logger.i("TraceMethodVisitor", "start methodName: ${name}------")
        }

        override fun visitInsn(opcode: Int) {
            if (opcode == Opcodes.RETURN) {
                methodVisitor.visitMethodInsn(
                    Opcodes.INVOKEVIRTUAL,
                    "java/io/PrintStream",
                    "println",
                    "(Ljava/lang/String;)V",
                    false
                )
                context.logger.i("TraceMethodVisitor", "end methodName: ${name}------")
            }
            super.visitInsn(opcode)
        }
    }
}