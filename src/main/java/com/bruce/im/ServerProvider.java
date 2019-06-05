package com.bruce.im;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Created by bruce on 2019/5/31 19:47
 */
//@Slf4j
public class ServerProvider {

    private static Logger log = LoggerFactory.getLogger(ServerProvider.class);

    public static void main(String[] args) throws IOException {
        Provider provider = start();

        System.in.read();

        provider.close();
    }

    private static Provider start() {
        String uuid = UUID.randomUUID().toString();
        Provider provider = new Provider(uuid);
        provider.start();
        return provider;
    }

    private static class Provider extends Thread {
        private byte[] sn;
        volatile boolean done;
        private DatagramSocket udpSocket;

        byte[] buffer = new byte[128];

        public Provider(String sn) {
            this.sn = sn.getBytes();
        }

        @Override
        public void run() {
            log.info("UDPProvider Started.");
            try {
                udpSocket = new DatagramSocket(UDPConstants.SERVER_PORT);
                DatagramPacket recPacket = new DatagramPacket(buffer, buffer.length);
                while (!done) {
                    //阻塞接收
                    udpSocket.receive(recPacket);
                    String clientIp = recPacket.getAddress().getHostAddress();
                    int clientPort = recPacket.getPort();
                    int clientDataLength = recPacket.getLength();
                    byte[] clientData = recPacket.getData();
                    boolean isValid = clientDataLength >= (UDPConstants.HEADER.length + 2 + 4) && ByteUtils.startWith(clientData, UDPConstants.HEADER);

                    System.out.println("ServerProvider receive form ip" + clientIp);
//                    log.info("ServerProvider receive form ip:{} port:{} dataValid:{}", clientIp, clientPort, isValid);

                    if (!isValid) {
                        continue;
                    }

                    int index = UDPConstants.HEADER.length;
                    short cmd = (short) ((clientData[index++] << 8) | (clientData[index++] & 0xff));

                    int recPort = (clientData[index++] << 24)
                            | ((clientData[index++] & 0xff) << 16)
                            | ((clientData[index++] & 0xff) << 8)
                            | (clientData[index] & 0xff);

                    if (cmd == 1 && recPort > 0) {
                        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
                        byteBuffer.put(UDPConstants.HEADER);
                        byteBuffer.putShort((short) 2);
                        byteBuffer.putInt(TCPConstants.SERVER_PORT);
                        byteBuffer.put(sn);
                        int len = byteBuffer.position();
                        DatagramPacket respPacket = new DatagramPacket(buffer, len, recPacket.getAddress(), recPort);
                        udpSocket.send(respPacket);
//                        log.info("server response to:{} port:{} dataLen:{}", clientIp, recPort, len);
                    } else {
//                        log.info("server receive nonsupport cmd:{} client:{} port:{}", cmd, clientIp, clientPort);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        private void close() {
            done = true;
            if (udpSocket != null) {
                udpSocket.close();
                udpSocket = null;
            }
        }


    }


}
