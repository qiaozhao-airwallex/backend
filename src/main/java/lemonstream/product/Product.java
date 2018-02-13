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
import javax.validation.constraints.NotNull;


import lemonstream.image.ImageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column
    @NotNull
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
    @NotNull
    private Boolean published;

}
