package com.bruce.im;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruce on 2019/5/31 21:09
 */
@Slf4j
public class ClientSearcher {

    private static int LISTEN_PORT = 30202;

    public static void main(String[] args) {
        ServerInfo serverInfo = searchServer(3000);

        System.out.println(serverInfo.toString());
    }


    public static ServerInfo searchServer(int timeout) {
        log.info("DUP searcher started.");

        CountDownLatch receiveLatch = new CountDownLatch(1);

        SearcherListener listener = null;
        try {
            listener = listen(receiveLatch);

            sendBroadcast();

            receiveLatch.await(timeout, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("UDPSearcher finished.");

        if (listener == null) {
            return null;
        }

        List<ServerInfo> serverInfos = listener.getServerInfo();

        return serverInfos.size() > 0 ? serverInfos.get(0) : null;
    }

    /**
     * 开启新的线程监听
     */
    private static SearcherListener listen(CountDownLatch receiveLatch) throws InterruptedException {
        log.info("UDPSearcher start listen.");
        CountDownLatch threadStartLatch = new CountDownLatch(1);
        SearcherListener searcherListener = new SearcherListener(receiveLatch, threadStartLatch, LISTEN_PORT);
        searcherListener.start();
        threadStartLatch.await();
        return searcherListener;
    }

    /**
     * 向服务端发送广播,要求获取TCP连接信息,发送之前应该保证监听线程已经启动
     *
     * @throws SocketException
     */
    private static void sendBroadcast() throws IOException {
        log.info("UDPSearcher sendBroadcast started.");
        DatagramSocket udpSocket = new DatagramSocket();
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        byteBuffer.put(UDPConstants.HEADER);
        byteBuffer.putShort((short) 1);
        byteBuffer.putInt(LISTEN_PORT);
        DatagramPacket reqPacket = new DatagramPacket(byteBuffer.array(), byteBuffer.position() + 1);
        reqPacket.setAddress(InetAddress.getByName("255.255.255.255"));
        reqPacket.setPort(UDPConstants.SERVER_PORT);
        udpSocket.send(reqPacket);
        udpSocket.close();
        log.info("UDPSearcher sendBroadcast finished.");
    }

    /**
     * 等待服务端连接返回TCP连接信息
     */
    @Slf4j
    private static class SearcherListener extends Thread {
        private CountDownLatch receiveLatch;
        private CountDownLatch threadStartLatch;
        private int listen_port;
        private DatagramSocket udpSocket = null;

        private volatile boolean done = false;
        byte[] buffer = new byte[128];
        private List<ServerInfo> serverInfos = new ArrayList<>();
        private int minLen = UDPConstants.HEADER.length + 2 + 4;

        public SearcherListener(CountDownLatch receiveLatch, CountDownLatch threadStartLatch, int listen_port) {
            this.receiveLatch = receiveLatch;
            this.listen_port = listen_port;
            this.threadStartLatch = threadStartLatch;
        }

        @Override
        public void run() {
            try {
                udpSocket = new DatagramSocket(listen_port);
                DatagramPacket recPacket = new DatagramPacket(buffer, buffer.length);
                threadStartLatch.countDown();
                while (!done) {
                    udpSocket.receive(recPacket);
                    String hostAddress = recPacket.getAddress().getHostAddress();
                    int port = recPacket.getPort();
                    int length = recPacket.getLength();
                    byte[] data = recPacket.getData();

                    boolean valid = length >= minLen && ByteUtils.startWith(data, UDPConstants.HEADER);
//                    log.info("UDP Searcher receive from ip:{} port:{} dataValid:{}", hostAddress, port, valid);
//                    log.("UDP Searcher receive from ip:");
                    if (!valid) {
                        continue;
                    }
                    ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, UDPConstants.HEADER.length, length);
                    short cmd = byteBuffer.getShort();
                    int tcpServerPort = byteBuffer.getInt();

                    if (cmd != 2 || tcpServerPort <= 0) {
                        log.warn("UDPSearcher receive cmd:{},tcpServerPort:{}", cmd, tcpServerPort);
                        continue;
                    }
                    String sn = new String(buffer, minLen, length - minLen);
                    ServerInfo serverInfo = new ServerInfo(sn, tcpServerPort, hostAddress);
                    serverInfos.add(serverInfo);

                    done = true;
                    receiveLatch.countDown();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (udpSocket != null) {
                    udpSocket.close();
                    udpSocket = null;
                }
            }
            log.info("UDPSearcher listener finished.");
        }

        public List<ServerInfo> getServerInfo() {
            return serverInfos;
        }
    }


}
