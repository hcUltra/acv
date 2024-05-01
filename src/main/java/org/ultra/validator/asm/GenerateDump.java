package org.ultra.validator.asm;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.ultra.validator.common.constant.CompileConstant;
import org.ultra.validator.common.util.FileUtil;
import org.ultra.validator.common.util.CommandUtil;

import java.io.*;
import java.nio.file.Files;

/**
 * @author hcUltra
 * @description 根据用户代码生成构造字节码的 dump 函数 -> 也就是SolutionDump类
 * @date 2024/4/28 13:44
 **/
@Slf4j
public class GenerateDump {
    /**
     * 将方法写入 SolutionDump.java
     */
    public static void writeSolutionDumpSourceCode() throws IOException {
        File file = new File(CompileConstant.SOLUTION_DUMP_JAVA_PATH);
        if (file.exists()) {
            boolean r = file.delete();
            if (!r) {
                log.error("删除 SolutionDump.java 文件失败");
            }
        }
        // 创建空的 SolutionDump.java 文件
        file = new File(CompileConstant.SOLUTION_DUMP_JAVA_PATH).getAbsoluteFile();
        boolean r = file.createNewFile();
        if (!r) {
            // 创建失败
            log.error("创建新 SolutionDump.java 文件失败");
            return;
        }
        // 向字节码文件写入流
        StringBuilder solutionDumpClassStr = new StringBuilder();
        solutionDumpClassStr.append(PrintASM.getSolutionDumpClass());
        for (int i = 8; i <= 11; i++) {
            solutionDumpClassStr.setCharAt(i, ' ');
        }
        // byte[] bytes = solutionDumpClassStr.getBytes();
        PrintWriter printWriter = new PrintWriter(new FileWriter(file));
        printWriter.write(solutionDumpClassStr.toString());
        printWriter.close();
    }


    private static void createRtJar() throws IOException {
        File file = new File(CompileConstant.RT_JAR_PATH);
        boolean ret = file.createNewFile();
        if (!ret) {
            log.error("创建 rt.jar 文件失败");
            return;
        }
        File rt = new File(CompileConstant.JAVA_HOME_JRE + "/lib/rt.jar");
        byte[] bytes = Files.readAllBytes(rt.toPath());
        FileUtil.writeFile(CompileConstant.RT_JAR_PATH, bytes);
    }


    private static byte[] compileAndGetByteCode() throws IOException {
        // 创建 tmp 目录
        File file = new File(CompileConstant.WORK_DIR);
        if (!file.exists()) {
            boolean ret = file.mkdirs();
            if (!ret) {
                log.error("创建 tmp 目录失败");
                return null;
            }
        }
        String javacCommand = CompileConstant.JAVA_HOME_JRE.substring(0, CompileConstant.JAVA_HOME_JRE.length() - 3);
        javacCommand += "bin/javac";

        // 如果依赖 rt.jar 文件不存在 拷贝一份到临时目录
        File rtJarFile = new File(CompileConstant.RT_JAR_PATH);
        if (!rtJarFile.exists()) {
            createRtJar();
        }

        String compileCmd = String.format(javacCommand + " -encoding utf8 -classpath  %s -d %s %s", CompileConstant.RT_JAR_PATH, CompileConstant.TARGET_POSITION, CompileConstant.SOLUTION_DUMP_JAVA_PATH);
        // 只关注 javac 的标准错误,一旦编译出错,错误信息就会写入标准错误
        CommandUtil.run(compileCmd, CompileConstant.STDOUT, CompileConstant.STDERR);
        // 如果编译错误,COMPILE_ERROR 中就会有错误信息
        String compileError = FileUtil.readFile(CompileConstant.STDERR);
        if (!"".equals(compileError)) {
            System.out.println("编译出错");
            throw new RuntimeException("编译错误 ..." + compileError);
        }
        // 获取字节码的字节流
        File byteCodeFile = new File(CompileConstant.SOLUTION_DUMP_CLASS_PATH);
        InputStream is = Files.newInputStream(byteCodeFile.toPath());
        byte[] bytes = new byte[(int) byteCodeFile.length()];
        int n = is.read(bytes);
        if(n < 0) {
            log.error("获取字节流失败");
            return null;
        }
        is.close();
        return bytes;
    }

    public static byte[] generate() throws IOException {
        writeSolutionDumpSourceCode();

        return compileAndGetByteCode();
    }
}
