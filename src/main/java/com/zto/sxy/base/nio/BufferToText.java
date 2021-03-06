package com.zto.sxy.base.nio;

/**
 * Created by brian on 16/11/29.
 *
 * Converting text to and from ByteBuffers
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class BufferToText {
    private static final int BSIZE = 1024;

    public static void main(String[] args) throws Exception {
        String parent = BufferToText.class.getResource("/").getPath();
        String file = parent + "/buffer2text.txt";

        FileChannel fc = new FileOutputStream(file).getChannel();
        fc.write(ByteBuffer.wrap("Some text".getBytes()));
        fc.close();
        fc = new FileInputStream(file).getChannel();
        ByteBuffer buff = ByteBuffer.allocate(BSIZE);
        fc.read(buff);
        buff.flip();
        // Doesn't work:
        System.out.println(buff.asCharBuffer());

        // Decode using this system's default Charset:
        buff.rewind();
        String encoding = System.getProperty("file.encoding");
        System.out.println("Decoded using " + encoding + ": "
                + Charset.forName(encoding).decode(buff));

        // Or, we could encode with something that will print:
        fc = new FileOutputStream(file).getChannel();
        fc.write(ByteBuffer.wrap(
                "Some text".getBytes("UTF-16BE")));
        fc.close();
        // Now try reading again:
        fc = new FileInputStream(file).getChannel();
        buff.clear();
        fc.read(buff);
        buff.flip();
        System.out.println(buff.asCharBuffer());

        // Use a CharBuffer to write through:
        fc = new FileOutputStream(file).getChannel();
        buff = ByteBuffer.allocate(24); // More than needed
        buff.asCharBuffer().put("Some text");
        fc.write(buff);
        fc.close();
        // Read and display:
        fc = new FileInputStream(file).getChannel();
        buff.clear();
        fc.read(buff);
        buff.flip();
        System.out.println(buff.asCharBuffer());
    }
}
