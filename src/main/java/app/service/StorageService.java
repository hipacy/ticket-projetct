package app.service;

import app.exceptions.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service("storageService")
public class StorageService {

    @Autowired
    ServletContext context;

    @Value("${item.upload.path}")
    private String itemsImgPath;

    @Value("${user.upload.path}")
    private String usersImgPath;

    @Value("${varia.upload.path}")
    private String variaImgPath;

    private String path;

    public void uploadImage(MultipartFile file, String fileDomain, String identity) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }
        setPath(fileDomain, identity);

        try {
            if (!file.getOriginalFilename().contains(".png")) {
                throw new StorageException("Incorrect file format! Image has to be in .png format.");
            }
            Path uploadedFilePath = Paths.get(path);
            Files.copy(file.getInputStream(), uploadedFilePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new StorageException("Failed to store file: " + file.getName() + " " + e.toString(), e);
        }

    }

    private void setPath(String fileDomain, String identity) {
        if ("item".equals(fileDomain)) {
            this.path = itemsImgPath + identity + ".png";
        } else if ("user".equals(fileDomain)) {
            this.path = usersImgPath + identity + ".png";
        } else {
            this.path = variaImgPath + identity + ".png";
        }
    }


}