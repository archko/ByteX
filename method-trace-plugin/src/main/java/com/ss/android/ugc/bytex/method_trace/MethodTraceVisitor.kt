package com.ss.android.ugc.bytex.method_trace

import com.ss.android.ugc.bytex.common.Constants
import com.ss.android.ugc.bytex.common.visitor.BaseClassVisitor
import org.objectweb.asm.Label
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
            /*val sb = StringBuilder();
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
            context.logger.i("MethodTraceVisitor", "----插桩----className: $desc  methodName: ${name}------")*/
            return InlineMethodTraceVisitor(context, mv, name, desc)
        }

        return mv
    }

    private class InlineMethodTraceVisitor internal constructor(
        private val context: MethodTraceContext,
        private val methodVisitor: MethodVisitor,
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
        }

        /*override fun visitCode() {
            super.visitCode()

            context.logger.i("TraceMethodVisitor", "start methodName: ${name}------")
        }

        override fun visitInsn(opcode: Int) {
            if (opcode == Opcodes.RETURN) {
                context.logger.i("TraceMethodVisitor", "end methodName: ${name}------")
            }
            super.visitInsn(opcode)
        }*/
        lateinit var l0: Label
        override fun visitCode() {
            super.visitCode()
            l0 = Label()
            methodVisitor.visitLabel(l0)
            methodVisitor.visitLineNumber(14, l0)
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
            methodVisitor.visitVarInsn(Opcodes.LSTORE, 1)
        }

        override fun visitInsn(opcode: Int) {
            if (opcode == Opcodes.RETURN) {
                context.logger.i("TraceMethodVisitor", "end methodName: ${name}------")

                val l2 = Label()
                methodVisitor.visitLabel(l2)
                methodVisitor.visitLineNumber(16, l2)
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
                methodVisitor.visitVarInsn(Opcodes.LSTORE, 3)
                val l3 = Label()
                methodVisitor.visitLabel(l3)
                methodVisitor.visitLineNumber(17, l3)
                methodVisitor.visitLdcInsn("time:")
                methodVisitor.visitVarInsn(Opcodes.LLOAD, 3)
                methodVisitor.visitVarInsn(Opcodes.LLOAD, 1)
                methodVisitor.visitInsn(Opcodes.LSUB)
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false)
                methodVisitor.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "kotlin/jvm/internal/Intrinsics",
                    "stringPlus",
                    "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;",
                    false
                )
                methodVisitor.visitVarInsn(Opcodes.ASTORE, 5)
                methodVisitor.visitInsn(Opcodes.ICONST_0)
                methodVisitor.visitVarInsn(Opcodes.ISTORE, 6)
                methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
                methodVisitor.visitVarInsn(Opcodes.ALOAD, 5)
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false)
                val l4 = Label()
                methodVisitor.visitLabel(l4)
                methodVisitor.visitLineNumber(18, l4)
                methodVisitor.visitInsn(Opcodes.RETURN)
                val l5 = Label()
                methodVisitor.visitLabel(l5)
                methodVisitor.visitLocalVariable("start", "J", null, l0, l5, 1)
                mv.visitLocalVariable("end", "J", null, l3, l5, 3)
                //mv.visitLocalVariable("this", "Lasm/MyMethodClassVisitor;", null, l0, l5, 0)
            }
            super.visitInsn(opcode)
        }
    }
}