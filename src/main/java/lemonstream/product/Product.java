package lemonstream.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


import lemonstream.image.ImageInfo;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column
    private String subject;

    @Column
    private String description;

    @OneToMany(mappedBy = "product",
            cascade = { CascadeType.ALL },
            targetEntity = ImageInfo.class,
            orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<ImageInfo> imageList = new ArrayList<>();

    @Column
    private Boolean published;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setImageList(List<ImageInfo> imageList) {
        this.imageList = imageList;
    }

    public List<ImageInfo> getImageList() {
        return imageList;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }
}
