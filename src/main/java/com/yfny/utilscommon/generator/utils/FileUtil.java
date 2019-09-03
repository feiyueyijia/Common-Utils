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

    public static void addToJavaMapper(int type, Object data, String filePath) throws IOException, TemplateException {
        Template tpl = getTemplate(type); // 获取模板文件
        File file = new File(filePath);
        // 填充数据
        StringWriter writer = new StringWriter();
        tpl.process(data, writer);
        String addContents = writer.toString();
        String fileContents = readFileContent(filePath, 0);
        long content_length = fileContents.length();
        long file_length = file.length();

        //System.out.println("readLenth: " + fileContents.length());
        long position = fileContents.lastIndexOf("Condition\")") + (file_length - content_length);
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
        long content_length = fileContents.length();
        long file_length = file.length();
        //System.out.println("readLenth: " + fileContents.length());
        long position = fileContents.lastIndexOf("}") + (file_length - content_length);
        //System.out.println("position: " + position);
        addContainsToFile(filePath, position, addContents);
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
            case FreemarketConfigUtils.TYPE_ENTITY:
                return FreemarketConfigUtils.getInstance().getTemplate("Entity.ftl");
            case FreemarketConfigUtils.TYPE_PRODUCER_SQL_BUILDER:
                return FreemarketConfigUtils.getInstance().getTemplate("ProducerSqlBuilder.ftl");
            case FreemarketConfigUtils.TYPE_PRODUCER_MAPPER:
                return FreemarketConfigUtils.getInstance().getTemplate("ProducerMapper.ftl");
            case FreemarketConfigUtils.TYPE_PRODUCER_SERVICE:
                return FreemarketConfigUtils.getInstance().getTemplate("ProducerServiceImpl.ftl");
            case FreemarketConfigUtils.TYPE_PRODUCER_COMPOSITE:
                return FreemarketConfigUtils.getInstance().getTemplate("ProducerComposite.ftl");
            case FreemarketConfigUtils.TYPE_PRODUCER_BEFORE_SERVICE:
                return FreemarketConfigUtils.getInstance().getTemplate("ProducerBeforeServiceImpl.ftl");
            case FreemarketConfigUtils.TYPE_PRODUCER_FUTURE:
                return FreemarketConfigUtils.getInstance().getTemplate("ProducerFuture.ftl");
            case FreemarketConfigUtils.TYPE_PRODUCER_CONTROLLER:
                return FreemarketConfigUtils.getInstance().getTemplate("ProducerController.ftl");
            case FreemarketConfigUtils.TYPE_CONSUMER_CLIENT:
                return FreemarketConfigUtils.getInstance().getTemplate("ConsumerClient.ftl");
            case FreemarketConfigUtils.TYPE_CONSUMER_HYSTRIX:
                return FreemarketConfigUtils.getInstance().getTemplate("ConsumerHystrix.ftl");
            case FreemarketConfigUtils.TYPE_CONSUMER_FUTURE:
                return FreemarketConfigUtils.getInstance().getTemplate("ConsumerFuture.ftl");
            case FreemarketConfigUtils.TYPE_CONSUMER_CONTROLLER:
                return FreemarketConfigUtils.getInstance().getTemplate("ConsumerController.ftl");
            case FreemarketConfigUtils.TYPE_EXCEPTION_HANDLER:
                return FreemarketConfigUtils.getInstance().getTemplate("ExceptionHandler.ftl");
            case FreemarketConfigUtils.TYPE_ADD_ENTITY:
                return FreemarketConfigUtils.getInstance().getTemplate("EntityAdd.ftl");
            case FreemarketConfigUtils.TYPE_ADD_PRODUCER_MAPPER:
                return FreemarketConfigUtils.getInstance().getTemplate("ProducerMapperAdd.ftl");
            case FreemarketConfigUtils.TYPE_API_BASE_TEST:
                return FreemarketConfigUtils.getInstance().getTemplate("APIBaseTest.ftl");
            case FreemarketConfigUtils.TYPE_API_UNIT_TEST:
                return FreemarketConfigUtils.getInstance().getTemplate("APIUnitTest.ftl");
            default:
                return null;
        }
    }

    private static String getBasicProjectPath() {
        String path = new File(FileUtil.class.getClassLoader().getResource("").getFile()).getPath() + File.separator;
        StringBuilder sb = new StringBuilder();
        sb.append(path.substring(0, path.indexOf("target"))).append("src").append(File.separator).append("main").append(File.separator);
        return sb.toString();
    }

    private static String getTestProjectPath() {
        String path = new File(FileUtil.class.getClassLoader().getResource("").getFile()).getPath() + File.separator;
        StringBuilder sb = new StringBuilder();
        sb.append(path.substring(0, path.indexOf("target"))).append("src").append(File.separator).append("test").append(File.separator);
        return sb.toString();
    }

    /**
     * 获取源码路径
     *
     * @return
     */
    public static String getSourcePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getBasicProjectPath()).append("java").append(File.separator);
        return sb.toString();
    }

    /**
     * 获取资源文件路径
     *
     * @return
     */
    public static String getResourcePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getBasicProjectPath()).append("resources").append(File.separator);
        return sb.toString();
    }

    /**
     * 获取单元测试路径
     *
     * @return
     */
    public static String getTestPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTestProjectPath()).append("java").append(File.separator);
        return sb.toString();
    }

}
