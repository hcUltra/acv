package org.ultra.validator.demo1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DecimalFormat;

public class GetSubProcessCPUTime {
    public static void main(String[] args) throws Exception {
        // javac /Users/yinger/Desktop/OS/project/acv-server/src/test/java/org/zhc/acvserver/Main1.java
        ProcessBuilder builder = new ProcessBuilder("java", "-cp", ".", "Main1");
        builder.redirectErrorStream(true);
        Process process = builder.start();
        int exitCode = process.waitFor();
        InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String output =new String();
        while ((line = bufferedReader.readLine()) != null) {
            output += line + "\n";

        }
        bufferedReader.close();
        inputStream.close();
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long totalRunningTime = runtimeMXBean.getUptime();
        long cpuTime = 0;
        for (String lineCPU : output.split("\n")) {
            if (lineCPU.matches(".*\\s+(\\d+)\\.\\d+\\s+.*")) {
                cpuTime += Long.parseLong(lineCPU.split(" ")[1]);
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double cpuUsage = (cpuTime / (double)totalRunningTime * 100.0) ;
        System.out.println(decimalFormat.format(cpuUsage) + " s");
    }
}
