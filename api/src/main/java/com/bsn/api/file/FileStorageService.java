package com.bsn.api.file;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static java.io.File.separator;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${application.file.uploads.photos-output-path}")
    private String fileUploadPath;

    public String saveFile(@Nonnull MultipartFile sourceFile, @Nonnull String userId) {
        final String subPath = "users" + separator + userId;
        return uploadFile(sourceFile, subPath);
    }

    private String uploadFile(@Nonnull MultipartFile sourceFile, @Nonnull String subPath) {
        final String finalUploadPath = fileUploadPath + separator + subPath;
        File targetFolder = new File(finalUploadPath);

        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdirs();
            if (!folderCreated) {
                log.warn("Failed to create the target folder: " + targetFolder);
                return null;
            }
        }

        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pathString = finalUploadPath + separator + currentTime + "." + fileExtension;
        Path targetPath = Paths.get(pathString);

        try {
            Files.write(targetPath, sourceFile.getBytes());
            log.info("File saved to: {}", pathString);
            return pathString;
        } catch (IOException e) {
            log.error("File was not saved successfully", e);
        }

        return null;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }
}
