package com.ecommerce.portal.services;

import com.ecommerce.portal.dtos.ProductDTO;
import com.ecommerce.portal.dtos.ProductRequest;
import com.ecommerce.portal.entities.Product;
import com.ecommerce.portal.exceptions.ProductException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public ProductDTO createProduct(ProductRequest productRequest);

    public String deleteProduct(Long productId)throws ProductException;

    public ProductDTO updateProduct(Long productId,ProductDTO productDTO)throws ProductException;

    public Product findProductById(Long id)throws ProductException;

    public List<Product> findProductByCategory(String category);

    public Page<ProductDTO> getAllProduct(String category,List<String> colors,List<String> sizes,
                                        Integer minPrice,Integer maxPrice,Integer minDiscount,
                                        String sort,String stock,Integer pageNumber,Integer pageSize);

    public List<ProductDTO> recentlyAddedProduct();

    public List<ProductDTO> getAllProducts();

    public List<ProductDTO> searchProduct(String query);
}
