package org.ultra.validator.asm;

import org.ultra.validator.common.constant.CompileConstant;
import org.ultra.validator.common.util.FileUtil;
import org.ultra.validator.common.util.ReflectUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author hcUltra
 * @description TODO
 * @date 2024/4/28 12:42
 **/
public class ValidatorClassLoader extends ClassLoader {

    private static String getAbsolutePath(String path) {
        return new File(path).getAbsolutePath();
    }

    private static void write(String filepath, byte[] bytes) throws IOException {
        File file = new File(filepath);
        if (!file.exists()) {
            throw new IOException("org.ultra.validator.main.Solution file does not exist");
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.close();
    }

    public static byte[] invokeDump(byte[] bytes) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        final Class<?> clazz = new ValidatorClassLoader().defineClass(CompileConstant.SOLUTION_DUMP_CLASSNAME, bytes);
        final Object obj = clazz.newInstance();
        Method method = ReflectUtil.getMethod(clazz, "dump");
        method.setAccessible(true);
        Object byteCOde = method.invoke(obj);
        return (byte[]) byteCOde;
    }

//    @Override
//    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
//        System.out.println(name);
////        if("java.lang.Object".equals(name)) {
////            return null;
////        }
//        return super.loadClass(name, resolve);
//    }

    private Class<?> defineClass(String className, byte[] clazzFile) {
        return defineClass(className, clazzFile, 0, clazzFile.length);
    }


    public static void main(String[] args) throws Exception {
        String code = "/**\n" +
                " * 请编写对数器测试代码，请遵循一下规则，并且您可以参考如下leetcode第一题的对数器代码，\n" +
                " * 1. 请不要修改类名\n" +
                " * 2. @Validator(count=?)   注解用于标注测试次数\n" +
                " * 3. @ValidatorMethod      注解用于标注对数器方法\n" +
                " * 4. @CorrectMethod        注解用于标注正确方法\n" +
                " * 5. 请构造测试配置类 ArgumentsConfig 的对象，对于构造规则详见介绍文档\n" +
                " * 6. 启动测试 new Active().activateValidator(configs);\n" +
                " **/\n" +
                "@Validator(count = 1234)\n" +
                "public class Solution {\n" +
                "    /**\n" +
                "     * 示例：\n" +
                "     * O(n) 时间复杂度 对数器方法\n" +
                "     *\n" +
                "     * @param nums   数组\n" +
                "     * @param target 目标数字\n" +
                "     * @return 一个有序数对\n" +
                "     */\n" +
                "    @ValidatorMethod\n" +
                "    public int[] twoSum(int[] nums, int target) {\n" +
                "        Map<Integer, Integer> map = new HashMap<>();\n" +
                "        for (int i = 0; i < nums.length; i++) {\n" +
                "            int key = target - nums[i];\n" +
                "            if (map.containsKey(key)) {\n" +
                "                return new int[]{map.get(key), i};\n" +
                "            } else {\n" +
                "                map.put(nums[i], i);\n" +
                "            }\n" +
                "        }\n" +
                "        return new int[]{-1, -1};\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * O(n^2) 时间复杂度 正确方法 暴力破解\n" +
                "     *\n" +
                "     * @param nums   数组\n" +
                "     * @param target 目标数字\n" +
                "     * @return 一个有序数对\n" +
                "     */\n" +
                "    @CorrectMethod\n" +
                "    public int[] twoSumCorrect(int[] nums, int target) {\n" +
                "        for (int i = 0; i < nums.length; i++) {\n" +
                "            for (int j = i + 1; j < nums.length; j++) {\n" +
                "                if (nums[i] + nums[j] == target) {\n" +
                "                    return new int[]{i, j};\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "        return new int[]{-1, -1};\n" +
                "    }\n" +
                "\n" +
                "    public void start() {\n" +
                "        // -10^9 <= nums[i] <= 10^9\n" +
                "        ArgumentConfig innerIntConfig = new ArgumentConfig()\n" +
                "                .withValueRange(new Range(-Math.pow(10, 9), Math.pow(10, 9)));\n" +
                "\n" +
                "        // 2 <= nums.length <= 10^4\n" +
                "        ArgumentConfig numsConfig = new ArgumentConfig()\n" +
                "                .withSize(new Range(2, Math.pow(10, 4)))\n" +
                "                .withInnerConfig(new ArgumentConfig[]{innerIntConfig});\n" +
                "\n" +
                "        // -10^9 <= target <= 10^9\n" +
                "        ArgumentConfig targetConfig = new ArgumentConfig()\n" +
                "                .withValueRange(new Range(-Math.pow(10, 9), Math.pow(10, 9)));\n" +
                "\n" +
                "        List<ArgumentConfig> lpc = new ArrayList<>();\n" +
                "        lpc.add(numsConfig);\n" +
                "        lpc.add(targetConfig);\n" +
                "        ArgumentsConfig configs = new ArgumentsConfig(lpc, null);\n" +
                "        new Active().activateValidator(configs);\n" +
                "    }\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        new Solution().start();\n" +
                "    }\n" +
                "}";
        FileUtil.writeFile(new File(CompileConstant.SOLUTION_JAVA_PATH).getAbsolutePath(), CompileConstant.PACKAGE + CompileConstant.IMPORT + code);
        // 根据传入的 code 生成dump 类
        byte[] bytes = GenerateDump.generate();
        // 写入类 反射调用dump
        final byte[] byteCode = invokeDump(bytes);
        String filepath = getAbsolutePath(CompileConstant.SOLUTION_CLASS_PATH);
        write(filepath, byteCode);
        // 加载类
        final Class<?> clazz = new ValidatorClassLoader().defineClass(CompileConstant.SOLUTION_CLASSNAME, byteCode);
        final Object obj = clazz.newInstance();
        Method method = ReflectUtil.getMethod(clazz, "start");
        method.setAccessible(true);
        method.invoke(obj);
    }

}
