package de.hftstuttgart.projectindoorweb.web.internal.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TransmissionHelper {
    public static  File convertMultipartFileToLocalFile(MultipartFile multipartFile) throws IOException {

        File convertedFile = new File(multipartFile.getOriginalFilename());
        convertedFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
        return convertedFile;

    }
}
