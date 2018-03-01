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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lemonstream.exception.AuthorizationFailureException;
import lemonstream.user.User;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PhysicalImageRepository physicalImageRepository;

    public ImageInfo create(User user, MultipartFile imageFile) {
        String originalFileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        String targetFileName = UUID.randomUUID().toString() + ".webp";
        physicalImageRepository.save(imageFile, originalFileName, targetFileName);

        ImageInfo imageInfo = new ImageInfo(originalFileName, targetFileName);
        imageInfo.setCreatedBy(user);
        imageRepository.save(imageInfo);
        return imageInfo;
    }

    public void delete(User user, Long id) {
        ImageInfo imageInfo = imageRepository.findById(id);
        if (imageInfo == null) {
            int a = 0;
        }
        String fileName = imageInfo.getTargetFileName();
        if (!imageInfo.isCreatedBy(user)) {
            throw new AuthorizationFailureException();
        }
        imageRepository.delete(id);
    }
}
