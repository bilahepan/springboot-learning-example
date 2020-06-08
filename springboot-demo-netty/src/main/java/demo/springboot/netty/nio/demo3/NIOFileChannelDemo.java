package demo.springboot.netty.nio.demo3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NIOFileChannelDemo {

    public static void main(String[] args) throws  Exception{
        //创建流
        FileInputStream inputStream = new FileInputStream("1.txt");
        FileOutputStream outputStream = new FileOutputStream("1-copy.txt");

        //获取channel
        FileChannel sourceCh = inputStream.getChannel();
        FileChannel destCh = outputStream.getChannel();

        //copy完成
        destCh.transferFrom(sourceCh,0,sourceCh.size());



        //关闭流
        sourceCh.close();
        destCh.close();
        inputStream.close();
        outputStream.close();
    }
}
