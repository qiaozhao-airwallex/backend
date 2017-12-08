package lemonstream.image;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class ImageService {

    private final Path rootLocation;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    public ImageService(String uploadRootLocation) {
        this.rootLocation = Paths.get(uploadRootLocation);
    }

    public ImageInfo create(MultipartFile imageFile) {
        String originalFileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        String targetFileName = UUID.randomUUID().toString() + ".webp";
        storeFile(imageFile, originalFileName, targetFileName);

        ImageInfo imageInfo = new ImageInfo(originalFileName, targetFileName);
        imageRepository.add(imageInfo);
        return imageInfo;
    }

    private void storeFile(MultipartFile imageFile, String originalFileName, String targetFileName) {
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

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }
}
