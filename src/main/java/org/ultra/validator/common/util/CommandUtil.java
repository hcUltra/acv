package org.ultra.validator.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 编译运行模块
 * 输入代码
 * 输出执行结果
 */
public class CommandUtil {
    /**
     * 1. 通过Runtime 类获取到Runtime实例,执行exec方法
     * 2. 获取标准输出,并写入到指定文件
     * 3. 获取标准错误,并写入到指定文件
     * 4. 等待子进程结束,拿到子进程的状态码并返回
     *
     * @return 子进程执行退出的状态码
     */
    public static int run(String cmd, String stdoutFile, String stderrFile) {
        Process process;
        try {
            process = Runtime.getRuntime().exec(cmd);
            // 获取标准输出和标准错误
            if (stdoutFile != null) {
                InputStream stdoutFrom = process.getInputStream();
                FileOutputStream stdoutTo = new FileOutputStream(stdoutFile);
                while (true) {
                    int ch = stdoutFrom.read();
                    if (ch == -1) {
                        break;
                    }
                    stdoutTo.write(ch);
                }
                stdoutFrom.close();
                stdoutTo.close();
            }

            if (stderrFile != null) {
                InputStream stderrFrom = process.getErrorStream();
                FileOutputStream stderrTo = new FileOutputStream(stderrFile);
                while (true) {
                    int ch = stderrFrom.read();
                    if (ch == -1) {
                        break;
                    }
                    stderrTo.write(ch);
                }
                stderrFrom.close();
                stderrTo.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int exitCode;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return exitCode;
    }

//    public static void main(String[] args) {
//
//        run(JAVAC + " ","/Users/yinger/Desktop/OS/project/acv/compile/tmp/stdout.txt","/Users/yinger/Desktop/OS/project/acv/compile/tmp/stderr.txt");
//    }
}
