package lemonstream.eventListener;

import java.awt.*;
import java.io.IOException;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lemonstream.image.ImageInfo;
import lemonstream.image.PhysicalImageRepository;

@Component
public class ImageDeleteEventListener implements PostDeleteEventListener {

    @Autowired
    private PhysicalImageRepository physicalImageRepository;

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        Object entity = event.getEntity();
        if (entity instanceof ImageInfo) {
            ImageInfo imageInfo = (ImageInfo) entity;
            physicalImageRepository.delete(imageInfo.getTargetFileName());
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return true;
    }
}
