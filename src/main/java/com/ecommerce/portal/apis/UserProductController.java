package com.ecommerce.portal.apis;

import java.util.List;

import com.ecommerce.portal.dtos.ProductDTO;
import com.ecommerce.portal.entities.Product;
import com.ecommerce.portal.exceptions.ProductException;
import com.ecommerce.portal.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class UserProductController {
	@Autowired
	private  ProductService productService;

	
	
	@GetMapping("/products")
	public ResponseEntity<Page<ProductDTO>> findProductByCategoryHandler(@RequestParam String category,
																		 @RequestParam List<String>color, @RequestParam List<String> size, @RequestParam Integer minPrice,
																		 @RequestParam Integer maxPrice, @RequestParam Integer minDiscount, @RequestParam String sort,
																		 @RequestParam String stock, @RequestParam Integer pageNumber, @RequestParam Integer pageSize){

		
		Page<ProductDTO> res= productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort,stock,pageNumber,pageSize);
		
		System.out.println("complete products");
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
		
	}
	

	
	@GetMapping("/products/id/{productId}")
	public ResponseEntity<ProductDTO> findProductByIdHandler(@PathVariable Long productId) throws ProductException {
		Product product=productService.findProductById(productId);
		ProductDTO productDTO=this.entityToDTO(product);
		return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.ACCEPTED);
	}

	@GetMapping("/products/search")
	public ResponseEntity<List<ProductDTO>> searchProductHandler(@RequestParam String q){
		
		List<ProductDTO> products=productService.searchProduct(q);
		
		return new ResponseEntity<List<ProductDTO>>(products,HttpStatus.OK);
		
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

}
