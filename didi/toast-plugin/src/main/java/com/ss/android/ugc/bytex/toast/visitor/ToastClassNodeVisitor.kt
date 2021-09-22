package com.ss.android.ugc.bytex.toast.visitor

import com.ss.android.ugc.bytex.toast.Context
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode

/**
 * @author: archko 2021/8/28 :18:02
 */
class ToastClassNodeVisitor(private var context: Context, private var relativePath: String) {

    fun transform(klass: ClassNode) {
        if (klass.name == SHADOW_TOAST) {
            return
        }

        klass.methods.forEach { method ->
            /*method.instructions?.filterIsInstance(MethodInsnNode::class.java)?.filter {
                it.opcode == Opcodes.INVOKEVIRTUAL && it.name == "show" && it.desc == "()V"
                        //&& (it.owner == TOAST || context.klassPool.get(TOAST).isAssignableFrom(it.owner))
                        && (it.owner == TOAST)
            }?.forEach {
                it.optimize(context, klass, method)
            }*/
            method.instructions?.iterator()?.forEach {
                if (it is MethodInsnNode) {
                    (it as MethodInsnNode)
                    if (it.opcode == Opcodes.INVOKEVIRTUAL && it.name == "show" && it.desc == "()V"
                        //&& (it.owner == TOAST || context.klassPool.get(TOAST).isAssignableFrom(it.owner))
                        && (it.owner == TOAST)
                    ) {
                        it.optimize(context, klass, method)
                    }
                }
            }
        }
    }

    private fun MethodInsnNode.optimize(context: Context, klass: ClassNode, method: MethodNode) {
        context.logger.i(" * ${this.owner}.${this.name}${this.desc} => $SHADOW_TOAST.apply(L$SHADOW_TOAST;)V: ${klass.name}.${method.name}${method.desc}")
        this.owner = SHADOW_TOAST
        this.name = "show"
        this.desc = "(L$TOAST;)V"
        this.opcode = Opcodes.INVOKESTATIC
        this.itf = false
    }

}

private const val TOAST = "android/widget/Toast"

private const val SHADOW_TOAST = "com/didiglobal/booster/instrument/ShadowToast"