package lemonstream.image;

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

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Column(name = "dis_order")
    private Integer displayOrder;

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
}
