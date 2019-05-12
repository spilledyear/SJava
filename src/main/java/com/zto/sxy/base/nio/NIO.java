package com.zto.sxy.base.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;


/**
 * @author spilledyear
 * @date 2019-02-26 17:27
 */
public class NIO {
    public static void main(String[] args) {
    }


    /**
     * capacity：Buffer有一个固定的大小值，capacity；
     * <p>
     * position：初始值为0；写数据到Buffer中时表示当前的位置，读取数据从某个特定位置读；当将Buffer从写模式切换到读模式，position会被重置为0；
     * <p>
     * limit：写模式下表示最多能往Buffer里写多少数据，此时limit等于capacity；当切换到读模式时，limit会被设置成写模式下的position值，表示最多能读到多少数据；
     */
    public static void buffer() {
        ByteBuffer buf = ByteBuffer.allocate(100);

        // 100 0 100
        System.out.println(String.format("capacity= %s, position= %s, limit= %s", buf.capacity(), buf.position(), buf.limit()));

        // 100 10 100
        for (int i = 0; i < 10; i++) {
            buf.put((byte) 'a');
        }
        System.out.println(String.format("capacity= %s, position= %s, limit= %s", buf.capacity(), buf.position(), buf.limit()));

        // 100 0 10 读模式切换到写模式，同时limit设置为之前position的值，position重置为0
        buf.flip();
        System.out.println(String.format("capacity= %s, position= %s, limit= %s", buf.capacity(), buf.position(), buf.limit()));


        // 100 1 10
        buf.put((byte) 'a');
        System.out.println(String.format("capacity= %s, position= %s, limit= %s", buf.capacity(), buf.position(), buf.limit()));


        // 100 0 10 不改变limit的值，position重置为0
        buf.rewind();
        System.out.println(String.format("capacity= %s, position= %s, limit= %s", buf.capacity(), buf.position(), buf.limit()));


        // 100 20 20 limit代表buffer最多能装多少
        buf.limit(20);
        for (int i = 0; i < 20; i++) {
            buf.put((byte) 'a');
        }
        System.out.println(String.format("capacity= %s, position= %s, limit= %s", buf.capacity(), buf.position(), buf.limit()));


        // 100 80 100
        buf.limit(100);
        for (int i = 0; i < 60; i++) {
            buf.put((byte) 'a');
        }
        System.out.println(String.format("capacity= %s, position= %s, limit= %s", buf.capacity(), buf.position(), buf.limit()));
    }

    public static void channel() throws IOException {
        String inFile = "D:\\Conclusion\\data.txt";
        String outFile = "D:\\Conclusion\\data2.txt";

        FileChannel in = new FileInputStream(inFile).getChannel();
        FileChannel out = new FileInputStream(outFile).getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (in.read(buffer) != -1) {
            // 切换到读模式
            buffer.flip();
            out.write(buffer);

            buffer.clear();
        }
    }

    public static void selector() throws IOException {
        TimeServer ts = new TimeServer(9999);
    }

}


class TimeServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel serverChannel;

    private volatile boolean stop;


    public TimeServer(int port) {
        try {
            // 创建一个Selector
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port));

            // 注册channel到Selector上
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Time Server Start in port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (IOException e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // 多路复用器件关闭后，所有注册在上面的Channel和Pipe等资源都会自动去注册并关闭，所以不需要重复释放资源
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            }


            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer buf = ByteBuffer.allocate(1024);
                while (sc.read(buf) != -1) {
                    buf.flip();
                    byte[] bytes = new byte[buf.remaining()];
                    buf.get();
                    System.out.println(new String(bytes, "UTF-8"));

                    sc.write(ByteBuffer.wrap("response".getBytes()));
                }

                key.cancel();
                sc.close();
            }
        }
    }
}