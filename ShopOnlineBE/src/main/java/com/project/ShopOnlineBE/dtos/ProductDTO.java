package com.project.ShopOnlineBE.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ShopOnlineBE.models.Product;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotBlank(message = "Name must not blank")
    @Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
    private String name;
    @Min(value = 0, message = "Price must be greater than 0")
    @Max(value = 10000000, message = "Price must be less than 10000000")
    private float price;
    private String thumbnail;
    private String description;
    @JsonProperty("category_id")
    private long  categoryId;
    private List<MultipartFile> files;
    public static ProductDTO fromProduct(Product product) {
        return ProductDTO.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build();
    }
}
