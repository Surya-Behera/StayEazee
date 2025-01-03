package Authentication.SpringAuthJwt.FileStorageHelper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileStorageHelper {

    private final Path uploadDir;

    public FileStorageHelper() {
        // Constructor no longer throws IOException
        this.uploadDir = Paths.get("static/image/");
    }

    // @PostConstruct ensures that this method is called after bean initialization

    public void init() throws IOException {
        // Ensure the upload directory exists after the object is instantiated
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    public String storeFile(org.springframework.web.multipart.MultipartFile file) throws IOException {
        // Generate a unique file name
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(fileName);

        // Copy file to the directory
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return the relative URL to be used by the application
        return "/uploads/" + fileName;
    }
}
