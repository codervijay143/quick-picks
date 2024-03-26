package com.ecommerce.portal.services.impl;

import com.ecommerce.portal.dtos.ProductDTO;
import com.ecommerce.portal.dtos.ProductRequest;
import com.ecommerce.portal.entities.Category;
import com.ecommerce.portal.entities.Product;
import com.ecommerce.portal.exceptions.ProductException;
import com.ecommerce.portal.repositories.CategoryRepository;
import com.ecommerce.portal.repositories.ProductRepository;
import com.ecommerce.portal.services.ProductService;

import com.ecommerce.portal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final ProductRepository productRepository;

    @Override
    public ProductDTO createProduct(ProductRequest productRequest) {
       Category topLevel= this.categoryRepository.findByName(productRequest.getTopLavelCategory());

       if(topLevel==null){
           Category topLavelCategory=new Category();
           topLavelCategory.setName(productRequest.getTopLavelCategory());
           topLavelCategory.setLevel(1);
           topLevel=this.categoryRepository.save(topLavelCategory);
       }
        Category secondLevel= this.categoryRepository.findByNameAndParent(productRequest.getSecondLavelCategory(),topLevel.getName());

        if(secondLevel==null){
            Category secondLevelCategory=new Category();
            secondLevelCategory.setName(productRequest.getSecondLavelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);
            secondLevel=this.categoryRepository.save(secondLevelCategory);
        }
       Category thirdLevel= this.categoryRepository.findByNameAndParent(productRequest.getThirdLavelCategory(),secondLevel.getName());

        if(thirdLevel==null){
            Category thirdLevelCategory=new Category();
            thirdLevelCategory.setName(productRequest.getThirdLavelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);
            thirdLevel=this.categoryRepository.save(thirdLevelCategory);
        }

        Product product=new Product();
        product.setTitle(productRequest.getTitle());
        product.setColor(productRequest.getColor());
        product.setDescription(productRequest.getDescription());
        product.setDiscountedPrice(productRequest.getDiscountedPrice());
        product.setDiscountPersent(productRequest.getDiscountPersent());
        product.setImageUrl(productRequest.getImageUrl());
        product.setBrand(productRequest.getBrand());
        product.setPrice(productRequest.getPrice());
        product.setSize(productRequest.getSize());
        product.setQuantity(productRequest.getQuantity());
        product.setCreatedAt(LocalDateTime.now());
        product.setCategory(thirdLevel);

        Product product1=this.productRepository.save(product);
        return this.entityToDTO(product1);
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
       Product product=findProductById(productId);
       product.getSize().clear();
       this.productRepository.delete(product);
        return "Product deleted Successfully";
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) throws ProductException {
        Product product=findProductById(productId);
        if(productDTO.getQuantity()!=0){
            product.setQuantity(productDTO.getQuantity());
        }
        Product product1=this.productRepository.save(product);
        return this.entityToDTO(product1);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> opt=this.productRepository.findById(id);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new ProductException("Product not found with id-"+id);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        return products;
    }

    @Override
    public List<ProductDTO> recentlyAddedProduct() {
        List<Product> productList=this.productRepository.findTop10ByOrderByCreatedAtDesc();
        List<ProductDTO> productDTOList=new ArrayList<>();
        for(Product product:productList){
            ProductDTO productDTO = this.entityToDTO(product);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    @Override
    public List<ProductDTO> getAllProducts() {

       List<Product> productList=this.productRepository.findAll();
       List<ProductDTO> productDTOList=new ArrayList<>();
       for(Product product:productList){
           ProductDTO productDTO = this.entityToDTO(product);
           productDTOList.add(productDTO);
       }
       return productDTOList;
    }

    @Override
    public List<ProductDTO> searchProduct(String query) {
        List<Product> products=this.productRepository.searchProduct(query);
        List<ProductDTO> productDTOList=new ArrayList<>();
        for(Product product:products){
            ProductDTO productDTO = this.entityToDTO(product);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }


    @Override
    public Page<ProductDTO> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice,
                                        Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

        Pageable pageable= PageRequest.of(pageNumber,pageSize);

        List<Product> products=this.productRepository.filterProducts(category,minPrice,maxPrice,minDiscount,sort);

        if(!colors.isEmpty()){
            products=products.stream().filter(p->colors.stream().
                    anyMatch(c-> c.equalsIgnoreCase(p.getColor())))
                    .toList();
        }

        if(stock!=null){
            if(stock.equals("in_stock")){
                products=products.stream().filter(p-> p.getQuantity()>0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products=products.stream().filter(p-> p.getQuantity()<1).collect(Collectors.toList());
            }
        }

        int startIndex= (int) pageable.getOffset();
        int endIndex=Math.min(startIndex+pageable.getPageSize(),products.size());

        List<Product> pageContent=products.subList(startIndex,endIndex);
        List<ProductDTO> productDTOs = pageContent.stream()
                .map(product -> {
                  return this.entityToDTO(product);
                })
                .collect(Collectors.toList());

        Page<ProductDTO> filteredProducts=new PageImpl<>(productDTOs,pageable,products.size());

        return filteredProducts;
    }

    public ProductDTO entityToDTO(Product product){

        ProductDTO productDTO=new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setTitle(product.getTitle());
        productDTO.setColor(product.getColor());
        productDTO.setDescription(product.getDescription());
        productDTO.setDiscountedPrice(product.getDiscountedPrice());
        productDTO.setDiscountPersent(product.getDiscountPersent());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setBrand(product.getBrand());
        productDTO.setPrice(product.getPrice());
        productDTO.setSize(product.getSize());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setCategory(product.getCategory());

        return productDTO;
    }

    public Product DTOToEntity(ProductDTO productDTO){

        Product product=new Product();
        product.setTitle(productDTO.getTitle());
        product.setColor(productDTO.getColor());
        product.setDescription(productDTO.getDescription());
        product.setDiscountedPrice(productDTO.getDiscountedPrice());
        product.setDiscountPersent(productDTO.getDiscountPersent());
        product.setImageUrl(productDTO.getImageUrl());
        product.setBrand(productDTO.getBrand());
        product.setPrice(productDTO.getPrice());
        product.setSize(productDTO.getSize());
        product.setQuantity(productDTO.getQuantity());
        product.setCreatedAt(productDTO.getCreatedAt());
        product.setCategory(productDTO.getCategory());
        return product;
    }
}
