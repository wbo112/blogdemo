package com.wbo112.premain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Objects;

public class MyAgent {
    public static void premain(String args, Instrumentation inst) {
        //args 是命令行的入参
        inst.addTransformer(new AsmTransformer(args));
    }

    private static class AsmTransformer implements ClassFileTransformer {

        String pkg;

        public AsmTransformer(String pkg) {
            this.pkg = pkg.replace(".", "/");
        }


        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
           //转换指定包名开头的类
            if (Objects.nonNull(pkg) && className.startsWith(pkg)) {
                try {
                    ClassReader cr = new ClassReader(classfileBuffer);
                    ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
                    AddTimerAdapter addTimerAdapter = new AddTimerAdapter(cw);
                    cr.accept(addTimerAdapter, 0);
                    return cw.toByteArray();
                }catch (Throwable throwable){
                    throwable.printStackTrace();
                    return classfileBuffer;
                }
            } else {
                return classfileBuffer;
            }

        }
    }
}