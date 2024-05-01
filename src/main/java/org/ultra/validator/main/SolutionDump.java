package org.ultra.validator.main;

import java.util.*;

import jdk.internal.org.objectweb.asm.*;

public class SolutionDump implements Opcodes {

    public static byte[] dump() throws Exception {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(52, ACC_PUBLIC + ACC_SUPER, "org/ultra/validator/main/Solution", null, "java/lang/Object", null);

        {
            av0 = cw.visitAnnotation("Lorg/ultra/validator/annotation/Validator;", true);
            av0.visit("count", new Integer(1234));
            av0.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "twoSum", "([II)[I", null, null);
            {
                av0 = mv.visitAnnotation("Lorg/ultra/validator/annotation/ValidatorMethod;", true);
                av0.visitEnd();
            }
            mv.visitCode();
            mv.visitTypeInsn(NEW, "java/util/HashMap");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/util/HashMap", "<init>", "()V", false);
            mv.visitVarInsn(ASTORE, 3);
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ISTORE, 4);
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitFrame(Opcodes.F_APPEND, 2, new Object[]{"java/util/Map", Opcodes.INTEGER}, 0, null);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitInsn(ARRAYLENGTH);
            Label l1 = new Label();
            mv.visitJumpInsn(IF_ICMPGE, l1);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitInsn(IALOAD);
            mv.visitInsn(ISUB);
            mv.visitVarInsn(ISTORE, 5);
            mv.visitVarInsn(ALOAD, 3);
            mv.visitVarInsn(ILOAD, 5);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "containsKey", "(Ljava/lang/Object;)Z", true);
            Label l2 = new Label();
            mv.visitJumpInsn(IFEQ, l2);
            mv.visitInsn(ICONST_2);
            mv.visitIntInsn(NEWARRAY, T_INT);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ALOAD, 3);
            mv.visitVarInsn(ILOAD, 5);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
            mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            mv.visitInsn(IASTORE);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_1);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitInsn(IASTORE);
            mv.visitInsn(ARETURN);
            mv.visitLabel(l2);
            mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{Opcodes.INTEGER}, 0, null);
            mv.visitVarInsn(ALOAD, 3);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitInsn(IALOAD);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true);
            mv.visitInsn(POP);
            mv.visitIincInsn(4, 1);
            mv.visitJumpInsn(GOTO, l0);
            mv.visitLabel(l1);
            mv.visitFrame(Opcodes.F_CHOP, 2, null, 0, null);
            mv.visitInsn(ICONST_2);
            mv.visitIntInsn(NEWARRAY, T_INT);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);
            mv.visitInsn(ICONST_M1);
            mv.visitInsn(IASTORE);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_1);
            mv.visitInsn(ICONST_M1);
            mv.visitInsn(IASTORE);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(5, 6);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "twoSumCorrect", "([II)[I", null, null);
            {
                av0 = mv.visitAnnotation("Lorg/ultra/validator/annotation/CorrectMethod;", true);
                av0.visitEnd();
            }
            mv.visitCode();
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ISTORE, 3);
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{Opcodes.INTEGER}, 0, null);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitInsn(ARRAYLENGTH);
            Label l1 = new Label();
            mv.visitJumpInsn(IF_ICMPGE, l1);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitInsn(ICONST_1);
            mv.visitInsn(IADD);
            mv.visitVarInsn(ISTORE, 4);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{Opcodes.INTEGER}, 0, null);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitInsn(ARRAYLENGTH);
            Label l3 = new Label();
            mv.visitJumpInsn(IF_ICMPGE, l3);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitInsn(IALOAD);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitInsn(IALOAD);
            mv.visitInsn(IADD);
            mv.visitVarInsn(ILOAD, 2);
            Label l4 = new Label();
            mv.visitJumpInsn(IF_ICMPNE, l4);
            mv.visitInsn(ICONST_2);
            mv.visitIntInsn(NEWARRAY, T_INT);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitInsn(IASTORE);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_1);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitInsn(IASTORE);
            mv.visitInsn(ARETURN);
            mv.visitLabel(l4);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitIincInsn(4, 1);
            mv.visitJumpInsn(GOTO, l2);
            mv.visitLabel(l3);
            mv.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
            mv.visitIincInsn(3, 1);
            mv.visitJumpInsn(GOTO, l0);
            mv.visitLabel(l1);
            mv.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
            mv.visitInsn(ICONST_2);
            mv.visitIntInsn(NEWARRAY, T_INT);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);
            mv.visitInsn(ICONST_M1);
            mv.visitInsn(IASTORE);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_1);
            mv.visitInsn(ICONST_M1);
            mv.visitInsn(IASTORE);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(4, 5);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "start", "()V", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, "org/ultra/validator/config/ArgumentConfig");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "org/ultra/validator/config/ArgumentConfig", "<init>", "()V", false);
            mv.visitTypeInsn(NEW, "org/ultra/validator/range/Range");
            mv.visitInsn(DUP);
            mv.visitLdcInsn(new Double("10.0"));
            mv.visitLdcInsn(new Double("9.0"));
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "pow", "(DD)D", false);
            mv.visitInsn(DNEG);
            mv.visitLdcInsn(new Double("10.0"));
            mv.visitLdcInsn(new Double("9.0"));
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "pow", "(DD)D", false);
            mv.visitMethodInsn(INVOKESPECIAL, "org/ultra/validator/range/Range", "<init>", "(DD)V", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "org/ultra/validator/config/ArgumentConfig", "withValueRange", "(Lorg/ultra/validator/range/Range;)Lorg/ultra/validator/config/ArgumentConfig;", false);
            mv.visitVarInsn(ASTORE, 1);
            mv.visitTypeInsn(NEW, "org/ultra/validator/config/ArgumentConfig");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "org/ultra/validator/config/ArgumentConfig", "<init>", "()V", false);
            mv.visitTypeInsn(NEW, "org/ultra/validator/range/Range");
            mv.visitInsn(DUP);
            mv.visitLdcInsn(new Double("2.0"));
            mv.visitLdcInsn(new Double("10.0"));
            mv.visitLdcInsn(new Double("4.0"));
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "pow", "(DD)D", false);
            mv.visitMethodInsn(INVOKESPECIAL, "org/ultra/validator/range/Range", "<init>", "(DD)V", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "org/ultra/validator/config/ArgumentConfig", "withSize", "(Lorg/ultra/validator/range/Range;)Lorg/ultra/validator/config/ArgumentConfig;", false);
            mv.visitInsn(ICONST_1);
            mv.visitTypeInsn(ANEWARRAY, "org/ultra/validator/config/ArgumentConfig");
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitInsn(AASTORE);
            mv.visitMethodInsn(INVOKEVIRTUAL, "org/ultra/validator/config/ArgumentConfig", "withInnerConfig", "([Lorg/ultra/validator/config/ArgumentConfig;)Lorg/ultra/validator/config/ArgumentConfig;", false);
            mv.visitVarInsn(ASTORE, 2);
            mv.visitTypeInsn(NEW, "org/ultra/validator/config/ArgumentConfig");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "org/ultra/validator/config/ArgumentConfig", "<init>", "()V", false);
            mv.visitTypeInsn(NEW, "org/ultra/validator/range/Range");
            mv.visitInsn(DUP);
            mv.visitLdcInsn(new Double("10.0"));
            mv.visitLdcInsn(new Double("9.0"));
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "pow", "(DD)D", false);
            mv.visitInsn(DNEG);
            mv.visitLdcInsn(new Double("10.0"));
            mv.visitLdcInsn(new Double("9.0"));
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "pow", "(DD)D", false);
            mv.visitMethodInsn(INVOKESPECIAL, "org/ultra/validator/range/Range", "<init>", "(DD)V", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "org/ultra/validator/config/ArgumentConfig", "withValueRange", "(Lorg/ultra/validator/range/Range;)Lorg/ultra/validator/config/ArgumentConfig;", false);
            mv.visitVarInsn(ASTORE, 3);
            mv.visitTypeInsn(NEW, "java/util/ArrayList");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
            mv.visitVarInsn(ASTORE, 4);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
            mv.visitInsn(POP);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitVarInsn(ALOAD, 3);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
            mv.visitInsn(POP);
            mv.visitTypeInsn(NEW, "org/ultra/validator/config/ArgumentsConfig");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitInsn(ACONST_NULL);
            mv.visitMethodInsn(INVOKESPECIAL, "org/ultra/validator/config/ArgumentsConfig", "<init>", "(Ljava/util/List;Ljava/util/List;)V", false);
            mv.visitVarInsn(ASTORE, 5);
            mv.visitTypeInsn(NEW, "org/ultra/validator/process/Active");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "org/ultra/validator/process/Active", "<init>", "()V", false);
            mv.visitVarInsn(ALOAD, 5);
            mv.visitMethodInsn(INVOKEVIRTUAL, "org/ultra/validator/process/Active", "activateValidator", "(Lorg/ultra/validator/config/ArgumentsConfig;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(9, 6);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, "org/ultra/validator/main/Solution");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "org/ultra/validator/main/Solution", "<init>", "()V", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "org/ultra/validator/main/Solution", "start", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}
