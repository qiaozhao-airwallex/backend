package lemonstream.image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.web.multipart.MultipartFile;

public class PhysicalImageRepository {
    private final Path rootLocation;

    public PhysicalImageRepository(String uploadRootLocation) {
        this.rootLocation = Paths.get(uploadRootLocation);
    }

    public void save(MultipartFile imageFile, String originalFileName, String targetFileName) {
        try {
            if (imageFile.isEmpty()) {
                throw new StorageException("Failed to store empty file " + originalFileName);
            }
            if (originalFileName.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + originalFileName);
            }
            Files.copy(imageFile.getInputStream(), this.rootLocation.resolve(targetFileName),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + originalFileName, e);
        }
    }

    public void delete(String fileName) {
        try {
            Files.delete(this.rootLocation.resolve(fileName));
        } catch (IOException e) {
            throw new StorageFileNotFoundException("Could not delete file: " + fileName, e);
        }
    }
}
