package com.project.ShopOnlineBE.services;

import com.project.ShopOnlineBE.components.LocalizationUtils;
import com.project.ShopOnlineBE.dtos.ProductDTO;
import com.project.ShopOnlineBE.dtos.ProductImageDTO;
import com.project.ShopOnlineBE.exceptions.DataNotFoundException;
import com.project.ShopOnlineBE.exceptions.InvalidParamException;
import com.project.ShopOnlineBE.models.Category;
import com.project.ShopOnlineBE.models.Product;
import com.project.ShopOnlineBE.models.ProductImage;
import com.project.ShopOnlineBE.repositories.CategoryRepository;
import com.project.ShopOnlineBE.repositories.ProductImageRepository;
import com.project.ShopOnlineBE.repositories.ProductRepository;
import com.project.ShopOnlineBE.responses.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final LocalizationUtils localizationUtils;
    @Override
    public Product createProduct(ProductDTO productDTO) throws Exception {
        Category existingCategory = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                        new Exception(
                                "Cannot find category with id: "+productDTO.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .thumbnail(productDTO.getThumbnail())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long id) throws Exception {
        return productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id = " + id));
    }
    @Override
    public List<Product> findProductsByIds(List<Long> productIds) {
        return productRepository.findProductsByIds(productIds);
    }

    @Override
    public Page<ProductResponse> getAllProducts(String keyword,
                                                Long categoryId, PageRequest pageRequest) {
        // Lấy danh sách sản phẩm theo trang (page), giới hạn (limit), và categoryId (nếu có)
        Page<Product> productsPage;
        productsPage = productRepository.searchProducts(categoryId, keyword, pageRequest);
        return productsPage.map(ProductResponse::fromProduct);
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(id);
        if(existingProduct != null) {
            Category existingCategory = categoryRepository
                    .findById(productDTO.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find category with id: "+productDTO.getCategoryId()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());

            List<ProductImage> productImages = productImageRepository.findByProductId(existingProduct.getId());
            if (!productImages.isEmpty()) {
                existingProduct.setThumbnail(productImages.get(0).getImageUrl());
            } else {
                existingProduct.setThumbnail(productDTO.getThumbnail());
            }

            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception {
        Product existingProduct = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find product with id: "+productImageDTO.getProductId()));
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        //Ko cho insert quá 5 ảnh cho 1 sản phẩm
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException("Number of images must be <= " + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }
    @Override
    public void deleteProductImages(Long Id, String imageName) {
        Optional<ProductImage> optionalProductImage = productImageRepository.findById(Id);
        if(optionalProductImage.isPresent()) {
            ProductImage productImage = optionalProductImage.get();
            if(productImage.getImageUrl().equals(imageName)) {
                productImageRepository.delete(productImage);
            }
        }
    }
}
