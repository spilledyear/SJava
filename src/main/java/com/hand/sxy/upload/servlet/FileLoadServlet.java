/**
 * @author spilledyear
 * @date 2017/11/13 13:33
 */

package com.hand.sxy.upload.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "FileLoadServlet", urlPatterns = {"/file/load"})
public class FileLoadServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger("FileLoadServlet");

    private static final long serialVersionUID = 1302377908285976972L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("------------ FileLoadServlet ------------");

        if (request.getContentLength() > 0) {
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            try {
                inputStream = request.getInputStream();
                // 给新文件拼上时间毫秒，防止重名
                long now = System.currentTimeMillis();
                File file = new File("D:/", "file-" + now + ".txt");
                file.createNewFile();

                outputStream = new FileOutputStream(file);

                byte temp[] = new byte[1024];
                int size = -1;
                while ((size = inputStream.read(temp)) != -1) { // 每次读取1KB，直至读完
                    outputStream.write(temp, 0, size);
                }
                logger.info("File load success.");
            } catch (IOException e) {
                logger.warning("File load fail."+ e);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } finally {
                outputStream.close();
                inputStream.close();
            }
        }
        request.getRequestDispatcher("/success.jsp").forward(request, response);
    }
}
