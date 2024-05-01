package org.ultra.validator.asm;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.util.ASMifier;
import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;
import org.ultra.validator.common.constant.CompileConstant;
import org.ultra.validator.common.util.CommandUtil;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author hcUltra
 * @description TODO
 * @date 2024/4/28 13:16
 **/
public class PrintASM {

    public static String getSolutionDumpClass() {
        // 更新字节码 编译Solution.java
        String javacCommand = CompileConstant.JAVA_HOME_JRE.substring(0, CompileConstant.JAVA_HOME_JRE.length() - 3);
        javacCommand += "bin/javac";
        String compileCmd = String.format(javacCommand + " -encoding utf8 -classpath  %s -d %s %s",
                CompileConstant.ACV_JAR_PATH, new File(CompileConstant.TARGET_POSITION).getAbsoluteFile(),
                new File(CompileConstant.SOLUTION_JAVA_PATH).getAbsoluteFile());

        CommandUtil.run(compileCmd, CompileConstant.STDOUT, CompileConstant.STDERR);
        // 跳过调试信息
        int parsingOption = ClassReader.SKIP_DEBUG;
        ASMifier printer = new ASMifier();
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter, true);
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(null, printer, printWriter);
        try {
            new ClassReader(CompileConstant.SOLUTION_CLASSNAME).accept(traceClassVisitor, parsingOption);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }
        return stringWriter.toString();
    }

}
