package org.ultra.validator.demo1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SubProcessCPUTime {
    public static void main(String[] args) throws Exception {
        // 创建ProcessBuilder对象
        ProcessBuilder builder = new ProcessBuilder("java");
        
        // 将错误输出重定向到标准输出流
        builder.redirectErrorStream(true);
        
        // 启动子进程，并获得Process对象
        Process process = builder.start();
        
        // 等待子进程执行完毕
        int exitCode = process.waitFor();
        
        // 获取子进程的标准输出流
        InputStream inputStream = process.getInputStream();
        
        // 获取父进程的CPU时间
        String parentCpuTime = getCPUTime();
        
        // 获取子进程的CPU时间
        String subProcessCpuTime = getSubProcessCpuTime(inputStream);
        
        // 关闭子进程的标准输入流和标准输出流
        inputStream.close();
        
        // 输出结果
        System.out.println("Parent CPU time: " + parentCpuTime);
        System.out.println("Sub process CPU time: " + subProcessCpuTime);
    }
    
    // 通过执行"top"命令获取父进程的CPU时间
    private static String getCPUTime() throws Exception {
        String cpuTime = "";
        try {
            Process process = Runtime.getRuntime().exec("top");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            bufferedReader.readLine(); // 跳过第一行输出
            cpuTime = bufferedReader.readLine(); // 读取第二行输出
        } catch (Exception e) {
            throw new Exception("Failed to get CPU time.", e);
        }
        return cpuTime;
    }
    
    // 读取子进程的CPU时间
    private static String getSubProcessCpuTime(InputStream inputStream) throws Exception {
        String cpuTime = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                if (tokenizer.hasMoreTokens()) {
                    String task = tokenizer.nextToken();
                    if (task.startsWith("Image Size:")) {
                        cpuTime = tokenizer.nextToken();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Failed to get sub process CPU time.", e);
        }
        return cpuTime;
    }
}
