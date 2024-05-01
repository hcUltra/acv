package org.ultra.validator.common.constant;

import java.io.File;

/**
 * @author hcUltra
 * @description TODO
 * @date 2024/4/28 10:28
 **/
public class CompileConstant {
    /* 工作目录 存放编译时产生的控制台标准输入、标准错误、编译时所需的依赖环境 */
    public static final String WORK_DIR = "compile/tmp";
    /* 标准输出、标准错误 */
    public static final String STDOUT = WORK_DIR + "stdout.txt";
    public static final String STDERR = WORK_DIR + "stderr.txt";
    /* 编译所需的依赖 */
    public static final String RT_JAR_PATH = new File("compile/tmp/rt.jar").getAbsolutePath();
    public static final String ACV_JAR_PATH = new File("compile/tmp/acv.jar").getAbsolutePath();
    /* SolutionDump */
    public static final String SOLUTION_DUMP_JAVA_PATH = "src/main/java/org/ultra/validator/main/SolutionDump.java";
    public static final String SOLUTION_DUMP_CLASS_PATH = "target/classes/org/ultra/validator/main/SolutionDump.class";
    public static final String SOLUTION_DUMP_CLASSNAME = "org.ultra.validator.main.SolutionDump";
    /* Solution */
    public static final String SOLUTION_JAVA_PATH = "src/main/java/org/ultra/validator/main/Solution.java";
    public static final String SOLUTION_CLASS_PATH = "target/classes/org/ultra/validator/main/Solution.class";
    public static final String SOLUTION_CLASSNAME = "org.ultra.validator.main.Solution";
    /* 存放字节码的路径 */
    public static final String TARGET_POSITION = "target/classes";
    /* JAVA_HOME jre 目录 */
    public static final String JAVA_HOME_JRE = System.getProperty("java.home");
    /* 给用户的代码增加包名、和内部依赖的包 */
    public static final String PACKAGE = "package org.ultra.validator.main;";
    public static final String IMPORT =
            "import com.fasterxml.jackson.core.JsonProcessingException;\n" +
                    "import org.ultra.validator.annotation.CorrectMethod;\n" +
                    "import org.ultra.validator.annotation.Validator;\n" +
                    "import org.ultra.validator.annotation.ValidatorMethod;\n" +
                    "import org.ultra.validator.config.ArgumentConfig;\n" +
                    "import org.ultra.validator.config.ArgumentsConfig;\n" +
                    "import org.ultra.validator.exception.UnableResolveTypeException;\n" +
                    "import org.ultra.validator.exception.UnknownTypeException;\n" +
                    "import org.ultra.validator.process.Active;\n" +
                    "import org.ultra.validator.range.Range;\n" +
                    "import java.lang.reflect.InvocationTargetException;\n" +
                    "import java.util.*;";
}
