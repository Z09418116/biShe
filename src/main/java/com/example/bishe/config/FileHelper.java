package com.example.bishe.config;


import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


import static com.google.common.io.Files.write;
import static com.google.common.io.Files.createParentDirs;
import static com.google.common.io.Files.getFileExtension;
import static org.springframework.util.StringUtils.cleanPath;
import static org.springframework.util.StringUtils.getFilename;
import java.util.UUID;


public class FileHelper {



    /**
     * 将文件写入文件系统
     *
     * @param file 附件
     * @return 文件的uri
     * @throws java.io.IOException
     */
    public static String saveFile(MultipartFile file, String rootPath, String relativeParentPath) throws IOException {
        String fileName = file.getOriginalFilename();
        if (!org.springframework.util.StringUtils.isEmpty(fileName)) {
            final String randomName = UUID.randomUUID().toString();
            final String uri = relativeParentPath + randomName + "."
                    + getFileExtension(fileName);
            final String fileFullPath = rootPath + uri;
            final File saveFile = new File(fileFullPath);
            createParentDirs(saveFile);
            write(file.getBytes(), saveFile);
            return cleanPath(uri);
        } else {
            return "";
        }
    }
}
