package com.bruce.study;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * @ClassName: HelloWorldClassloader
 * @Description:
 * @Author caoxunan | caoxunan121@163.com
 * @date 11/4/21 11:46 PM
 * @Version 1.0
 */
public class HelloWorldClassloader extends ClassLoader {

    public static void main(String[] args) throws Exception {

        // 定义ClassLoader
        HelloWorldClassloader classloader = new HelloWorldClassloader();

        // 加载class文件
        Class<?> helloWorld = classloader.loadClass("Hello");

        // 创建实例
        Object obj = helloWorld.newInstance();

        // 获得hello方法
        Method hello = helloWorld.getMethod("hello");

        // 执行方法 obj.hello();
        hello.invoke(obj);
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {

        // 加载文件
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("Hello.xlass");

        // 定义字节数组输出流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        // 读取到的字节长度
        int len;

        // buffer长度
        byte[] data = new byte[128];

        try {
            // 将输入流中的字节写到输出流中
            while ((len = is.read(data, 0, data.length)) != -1) {
                bos.write(data, 0, len);
            }
        } catch (IOException ex) {
            closeIoStream(is, bos);
        }

        // 读取字节数组输出流，得到字节码文件的字节数组
        byte[] classByteArr = bos.toByteArray();

        // 对数组中的字节做运算，因为 y = 255 - x, 所以x = 255 -y
        for (int i = 0; i < classByteArr.length; i++) {
            classByteArr[i] = (byte) (255 - classByteArr[i]);
        }

        //调用父类定义这个类
        return defineClass(name, classByteArr, 0, classByteArr.length);
    }

    private void closeIoStream(InputStream is, ByteArrayOutputStream bos) {

        // 关闭IO流
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (bos != null) {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
