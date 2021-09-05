package com.wbo112.premain;

import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class AddTimerMethodAdapter6 extends AdviceAdapter {
    private String owner;

    private int localVarIndex;

    public AddTimerMethodAdapter6(int access, String name, String desc,
                                  MethodVisitor mv, String owner) {
        super(ASM9, mv, access, name, desc);
        this.owner = owner;


    }

    @Override
    protected void onMethodEnter() {
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System",
                "currentTimeMillis", "()J", false);
        localVarIndex = newLocal(Type.LONG_TYPE);
        mv.visitVarInsn(LSTORE, localVarIndex);
    }

    @Override
    protected void onMethodExit(int opcode) {
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System",
                "currentTimeMillis", "()J", false);
        mv.visitVarInsn(LLOAD, localVarIndex);
        mv.visitInsn(LSUB);
        int localVarIndex1 = newLocal(Type.LONG_TYPE);
        mv.visitVarInsn(LSTORE, localVarIndex1);
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");


        Handle handle = new Handle(
                H_INVOKESTATIC,
                Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                "makeConcatWithConstants",
                MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                false);
        mv.visitVarInsn(LLOAD, localVarIndex1);

        mv.visitInvokeDynamicInsn("makeConcatWithConstants", "(J)Ljava/lang/String;", handle, this.getName()+"  time : \1");

        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }
}