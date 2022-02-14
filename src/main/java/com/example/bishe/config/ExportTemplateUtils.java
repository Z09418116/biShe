package com.example.bishe.config;

import com.google.common.io.Closer;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @ClassName ExportTemplateUtils
 * @Description 导出模板工具类
 * @Author Matt.Lu
 * @DATE 2022/2/11
 */
public final class ExportTemplateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportTemplateUtils.class);

    public ExportTemplateUtils() {
    }

    /**
     * 获取导入模板
     * @param response
     * @param url 文件地址
     */
    public void getTemplate(HttpServletResponse response, String url) {
        Closer closer = Closer.create();
//        LOGGER.info("this",this.getClass().getClassLoader().getResource("").getPath());
        File file = new File(this.getClass().getClassLoader().getResource("").getPath() + url);
        String filenames = file.getName();
        try (
                InputStream inputStream = closer.register(new BufferedInputStream(new FileInputStream(file)));
                OutputStream outputStream = closer.register(new BufferedOutputStream(response.getOutputStream()))) {
            byte[] buffer = new byte[inputStream.available()];
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filenames.replace(" ", "")
                    .getBytes("utf-8"), "iso8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream");
            Integer a = -1;
            if (!a.equals(inputStream.read(buffer))) {
                outputStream.write(buffer);
            }
        } catch (IOException e) {
            LOGGER.error("下载异常", e);
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                LOGGER.error("流关闭异常:{}", e);
            }
        }
    }


}