package com.project.ShopOnlineBE.services;

import com.project.ShopOnlineBE.dtos.ProductDTO;
import com.project.ShopOnlineBE.dtos.ProductImageDTO;
import com.project.ShopOnlineBE.models.Product;
import com.project.ShopOnlineBE.models.ProductImage;
import com.project.ShopOnlineBE.responses.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(long id) throws Exception;

    List<Product> findProductsByIds(List<Long> productIds);

    Page<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest);
    Product updateProduct(long id, ProductDTO productDTO) throws Exception;
    void deleteProduct(long id);
    boolean existsByName(String name);
    ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception;

    void deleteProductImages(Long Id, String imageName);
}
