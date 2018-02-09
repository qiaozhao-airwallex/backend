package lemonstream.util;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

import org.assertj.core.util.Lists;
import org.junit.Test;

import lemonstream.image.ImageInfo;
import lemonstream.product.Product;

public class MyBeanUtilsTest {

    @Test
    public void nullAwareBeanCopy() throws Exception {
        Product existingObj = new Product();
        existingObj.setId(1L);
        existingObj.setSubject("subject");
        existingObj.setDescription("old");
        existingObj.setPublished(true);
        ImageInfo imageInfo1 = new ImageInfo();
        imageInfo1.setId(100L);
        imageInfo1.setDisplayOrder(1);
        imageInfo1.setTargetFileName("abc.def");
        imageInfo1.setProduct(existingObj);
        ImageInfo imageInfo2 = new ImageInfo();
        imageInfo2.setId(101L);
        imageInfo2.setDisplayOrder(2);
        imageInfo2.setTargetFileName("xyz.def");
        imageInfo2.setProduct(existingObj);
        existingObj.setImageList(Lists.newArrayList(imageInfo1, imageInfo2));

        Product updateRequest = new Product();
        updateRequest.setDescription("description");

        ImageInfo imageInfo3 = new ImageInfo();
        imageInfo3.setId(100L);
        imageInfo3.setDisplayOrder(5);
        ImageInfo imageInfo4 = new ImageInfo();
        imageInfo4.setTargetFileName("new target");
        imageInfo4.setDisplayOrder(100);
        updateRequest.setImageList(Lists.newArrayList(imageInfo3, imageInfo4));

        MyBeanUtils.nullAndArrayAwareBeanCopy(existingObj, updateRequest);
        assertThat(existingObj.getId(), is(1L));
        assertThat(existingObj.getSubject(), is("subject"));
        assertThat(existingObj.getDescription(), is("description"));
        assertThat(existingObj.getPublished(), is(true));
        assertThat(existingObj.getImageList().size(), is(2));
        assertThat(existingObj.getImageList().get(0), is(imageInfo1));
        assertThat(existingObj.getImageList().get(0).getId(), is(100L));
        assertThat(existingObj.getImageList().get(0).getDisplayOrder(), is(5));
        assertThat(existingObj.getImageList().get(0).getTargetFileName(), is("abc.def"));
        assertThat(existingObj.getImageList().get(0).getProduct(), is(existingObj));
        assertThat(existingObj.getImageList().get(1), is(imageInfo4));
        assertThat(existingObj.getImageList().get(1).getId(), nullValue());
        assertThat(existingObj.getImageList().get(1).getDisplayOrder(), is(100));
        assertThat(existingObj.getImageList().get(1).getTargetFileName(), is("new target"));
    }

}