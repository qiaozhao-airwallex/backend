package lemonstream.image;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lemonstream.product.Product;

@Entity
@Table(name = "image")
public class ImageInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Transient
    private String originalFileName;

    @Column(name = "filename")
    private String targetFileName;

    @Column(name = "dis_order")
    private Integer displayOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

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

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ImageInfo)) {
            return false;
        }
        ImageInfo other = (ImageInfo) obj;
        return Objects.equals(getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
