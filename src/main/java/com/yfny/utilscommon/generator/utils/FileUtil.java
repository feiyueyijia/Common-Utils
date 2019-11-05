package com.yfny.utilscommon.generator.utils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;

/**
 * 代码生成器文件生成
 * Created by jisongZhou on 2019/3/5.
 **/
public class FileUtil {

    /**
     * @param type     使用模板类型
     * @param data     填充数据
     * @param filePath 输出文件
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateToJava(int type, Object data, String filePath) throws IOException, TemplateException {
        File file = new File(filePath);
        if (file.exists()) {
            System.err.println("ERROR: " + file.getPath().substring(file.getPath().lastIndexOf("\\") + 1, file.getPath().length()) + " 已存在，请手动修改");
            return;
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Template tpl = getTemplate(type); // 获取模板文件
        // 填充数据
        StringWriter writer = new StringWriter();
        tpl.process(data, writer);
        writer.flush();
        // 写入文件
        FileOutputStream fos = new FileOutputStream(filePath);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw = new BufferedWriter(osw, 1024);
        tpl.process(data, bw);
        fos.close();
    }

    public static String constructAddContent(int type, Object data, String content) throws IOException, TemplateException {
        Template tpl = getTemplate(type); // 获取模板文件
        // 填充数据
        StringWriter writer = new StringWriter();
        tpl.process(data, writer);
        String addContents = writer.toString();
        if (!content.contains(addContents)) {
            content = content + addContents;
        }
        return content;
    }

    public static void addToJavaEnd(String addContents, String filePath) throws IOException {
        File file = new File(filePath);
        String fileContents = readFileContent(filePath, 0);
        long content_length = fileContents.length();
        long file_length = file.length();
        //System.out.println("readLenth: " + fileContents.length());
        long position = fileContents.lastIndexOf("}") + (file_length - content_length);
        //System.out.println("position: " + position);
        addContainsToFile(filePath, position, addContents);
    }

    public static void addToJavaEnd(int type, Object data, String filePath) throws IOException, TemplateException {
        Template tpl = getTemplate(type); // 获取模板文件
        File file = new File(filePath);
        // 填充数据
        StringWriter writer = new StringWriter();
        tpl.process(data, writer);
        String addContents = writer.toString();
        String fileContents = readFileContent(filePath, 0);
        if (!fileContents.contains(addContents)) {
            long content_length = fileContents.length();
            long file_length = file.length();
            //System.out.println("readLenth: " + fileContents.length());
            long position = fileContents.lastIndexOf("}") + (file_length - content_length);
            //System.out.println("position: " + position);
            addContainsToFile(filePath, position, addContents);
        }
    }

    /**
     * 对一个文件的任意位置可以插入任何内容
     *
     * @param filePath 文件路径
     * @param position 追加内容添加位置
     * @param contents 追加内容
     * @throws IOException
     */
    public static void addContainsToFile(String filePath, long position, String contents) throws IOException {
        //1、参数校验
        File file = new File(filePath);
        //System.out.println(file);
        //判断文件是否存在
        if (!(file.exists() && file.isFile())) {
            System.out.println("文件不存在  ~ ");
            return;
        }
        //判断position是否合法
        if ((position < 0) || (position > file.length())) {
            System.out.println("position不合法 ~ ");
            return;
        }

        //2、创建临时文件
        File tempFile = File.createTempFile("sss", ".temp", new File("/"));
        //File tempFile = new File("d:/wwwww.txt");
        //3、用文件输入流、文件输出流对文件进行操作
        FileOutputStream outputStream = new FileOutputStream(tempFile);
        FileInputStream inputStream = new FileInputStream(tempFile);
        //在退出JVM退出时自动删除
        tempFile.deleteOnExit();

        //4、创建RandomAccessFile流
        RandomAccessFile rw = new RandomAccessFile(file, "rw");
        //System.out.println(rw.getFilePointer());
        //文件指定位置到 position
        rw.seek(position);
        //System.out.println(rw.getFilePointer());

        int tmp;
        //5、将position位置后的内容写入临时文件
        while ((tmp = rw.read()) != -1) {
            outputStream.write(tmp);
        }
        //6、将追加内容 contents 写入 position 位置
        rw.seek(position);
        rw.write(contents.getBytes());

        //7、将临时文件写回文件，并将创建的流关闭
        while ((tmp = inputStream.read()) != -1) {
            rw.write(tmp);
        }
        rw.close();
        outputStream.close();
        inputStream.close();
    }


    /**
     * 读取文件内容
     *
     * @param filePath 文件路径
     * @param position 指针位置
     **/
    public static String readFileContent(String filePath, int position) {
        String content = "";
        try {
            //RandomAccessFile raf=new RandomAccessFile(new File("D:\\3\\test.txt"), "r");
            /**
             * model各个参数详解
             * r 代表以只读方式打开指定文件
             * rw 以读写方式打开指定文件
             * rws 读写方式打开，并对内容或元数据都同步写入底层存储设备
             * rwd 读写方式打开，对文件内容的更新同步更新至底层存储设备
             *
             * **/
            RandomAccessFile raf = new RandomAccessFile(filePath, "r");
            //获取RandomAccessFile对象文件指针的位置，初始位置是0
            //System.out.println("RandomAccessFile文件指针的初始位置:" + raf.getFilePointer());
            raf.seek(position);//移动文件指针位置
            byte[] buff = new byte[1024];
            //用于保存实际读取的字节数
            int hasRead = 0;
            //循环读取
            while ((hasRead = raf.read(buff)) > 0) {
                //打印读取的内容,并将字节转为字符串输入
                content = content + new String(buff, 0, hasRead, "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(content);
        return content;
    }

    /**
     * 获取模板
     *
     * @param type 模板类型
     * @return
     * @throws IOException
     */
    private static Template getTemplate(int type) throws IOException {
        switch (type) {
            case SchemeFreemarkerConfigUtils.TYPE_ENTITY:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("Entity.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_CONSTANT:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("Constant.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_SQL_BUILDER:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("SqlBuilder.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_MAPPER:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("Mapper.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_SERVICE:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("Service.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_SERVICE_IMPL:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("ServiceImpl.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_COMPOSITE:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("Composite.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_BEFORE_SERVICE:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("BeforeServiceImpl.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_VALID:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("Valid.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_CONTROLLER:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("Controller.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_CLIENT:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("Client.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_HYSTRIX:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("Hystrix.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_EXCEPTION_HANDLER:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("ExceptionHandler.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_ADD_ENTITY:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("EntityAdd.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_ADD_MAPPER:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("MapperAdd.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_API_BASE_TEST:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("APIBaseTest.ftl");
            case SchemeFreemarkerConfigUtils.TYPE_API_UNIT_TEST:
                return SchemeFreemarkerConfigUtils.getInstance().getTemplate("APIUnitTest.ftl");
            case FrameFreemarkerConfigUtils.TYPE_MAIN_APPLICATION:
                return FrameFreemarkerConfigUtils.getInstance().getTemplate("MainApplication.ftl");
            case FrameFreemarkerConfigUtils.TYPE_MAIN_YAML:
                return FrameFreemarkerConfigUtils.getInstance().getTemplate("MainYaml.ftl");
            case FrameFreemarkerConfigUtils.TYPE_MAIN_BOOTSTRAP:
                return FrameFreemarkerConfigUtils.getInstance().getTemplate("MainBootstrap.ftl");
            case FrameFreemarkerConfigUtils.TYPE_TEST_APPLICATION:
                return FrameFreemarkerConfigUtils.getInstance().getTemplate("TestApplication.ftl");
            case FrameFreemarkerConfigUtils.TYPE_TEST_YAML:
                return FrameFreemarkerConfigUtils.getInstance().getTemplate("TestYaml.ftl");
            case FrameFreemarkerConfigUtils.TYPE_GIT_IGNORE:
                return FrameFreemarkerConfigUtils.getInstance().getTemplate("GitIgnore.ftl");
            case FrameFreemarkerConfigUtils.TYPE_PRODUCER_POM:
                return FrameFreemarkerConfigUtils.getInstance().getTemplate("ProducerPom.ftl");
            case FrameFreemarkerConfigUtils.TYPE_CONSUMER_POM:
                return FrameFreemarkerConfigUtils.getInstance().getTemplate("ConsumerPom.ftl");
            case FrameFreemarkerConfigUtils.TYPE_README:
                return FrameFreemarkerConfigUtils.getInstance().getTemplate("ReadMe.ftl");
            default:
                return null;
        }
    }

    public final static int PRODUCER = 0;//服务生产者
    public final static int CONSUMER = 1;//服务消费者API

    private static String getBasicPath() {
        return "/WorkSpace/Auto/";
    }

    private static String getProducerPath(String projectName) {
        String path = getBasicPath() + "Service-" + projectName + "/";
        return path;
    }

    private static String getConsumerPath(String projectName) {
        String path = getBasicPath() + "Service-" + projectName + "-api/";
        return path;
    }

    public static String getPath(String projectName, int type) {
        String path = "";
        switch (type) {
            case PRODUCER:
                path = getProducerPath(projectName);
                break;
            case CONSUMER:
                path = getConsumerPath(projectName);
                break;
            default:
                break;
        }
        return path;
    }

    private static String getBasicProjectPath(String projectName, int type) {
//        String path = new File(FileUtil.class.getClassLoader().getResource("").getFile()).getPath() + File.separator;
//        StringBuilder sb = new StringBuilder();
//        sb.append(path.substring(0, path.indexOf("target"))).append("src").append(File.separator).append("main").append(File.separator);
//        return sb.toString();
        String path = getPath(projectName, type) + "src/main/";
        return path;
    }

    private static String getTestProjectPath(String projectName, int type) {
//        String path = new File(FileUtil.class.getClassLoader().getResource("").getFile()).getPath() + File.separator;
//        StringBuilder sb = new StringBuilder();
//        sb.append(path.substring(0, path.indexOf("target"))).append("src").append(File.separator).append("test").append(File.separator);
//        return sb.toString();
        String path = getPath(projectName, type) + "src/test/";
        return path;
    }

    /**
     * 获取源码路径
     *
     * @return
     */
    public static String getSourcePath(String projectName, int type) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(getBasicProjectPath()).append("java").append(File.separator);
//        return sb.toString();
        String path = getBasicProjectPath(projectName, type) + "java/";
        return path;
    }

    /**
     * 获取资源文件路径
     *
     * @return
     */
    public static String getResourcePath(String projectName, int type) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(getBasicProjectPath()).append("resources").append(File.separator);
//        return sb.toString();
        String path = getBasicProjectPath(projectName, type) + "resources/";
        return path;
    }

    /**
     * 获取单元测试路径
     *
     * @return
     */
    public static String getTestPath(String projectName, int type) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(getTestProjectPath()).append("java").append(File.separator);
//        return sb.toString();
        String path = getTestProjectPath(projectName, type) + "java/";
        return path;
    }

    /**
     * 获取单元测试资源文件路径
     *
     * @return
     */
    public static String getTestResourcePath(String projectName, int type) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(getTestProjectPath()).append("java").append(File.separator);
//        return sb.toString();
        String path = getTestProjectPath(projectName, type) + "resources/";
        return path;
    }

}
