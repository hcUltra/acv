package org.ultra.validator.common.util;

import java.io.*;

/**
 * 封装文件操作(使用字符流)
 */
public class FileUtil {
    // 将 filePath 文件读取成 字符串
    public static String readFile(String filePath) {
        // try() with resource 执行结束自动调用 close方法
        StringBuilder result = new StringBuilder();
        try (FileReader fileReader = new FileReader(filePath)) {
            while (true) {
                int ch = fileReader.read();
                if (ch == -1) {
                    break;
                }
                result.append((char) ch);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result.toString();
    }

    // 将 content 字符串写到 filePath 对应的文件中
    // /Users/yinger/Desktop/OS/project/acv/src/main/java/org/ultra/validator/main/Solution.java
    // /Users/yinger/Desktop/OS/project/acv/src/main/java/org/ultra/validator/main/SolutionDump.java
    public static void writeFile(String filePath, String content) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // 将字节流写入 filePath
    public static void writeFile(String filePath, byte[] content) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(content);
            fos.flush(); // 确保所有缓冲的数据都被写入文件
        } catch (IOException e) {
            System.err.println("写入文件时发生错误: " + e.getMessage());
        }
    }
}
