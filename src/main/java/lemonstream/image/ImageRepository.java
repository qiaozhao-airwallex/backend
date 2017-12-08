package lemonstream.image;

import java.util.HashMap;
import java.util.Map;

public class ImageRepository {
    private Map<String, ImageInfo> images = new HashMap<>();

    public void add(ImageInfo imageInfo) {
        images.put(imageInfo.getTargetFileName(), imageInfo);
    }

    public ImageInfo findOne(String fileName) {
        return images.get(fileName);
    }
}
