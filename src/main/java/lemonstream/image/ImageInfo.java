package lemonstream.image;

public class ImageInfo {

    private String originalFileName;
    private String targetFileName;

    public ImageInfo() {
    }

    public ImageInfo(String originalFileName, String targetFileName) {
        this.originalFileName = originalFileName;
        this.targetFileName = targetFileName;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }
}
