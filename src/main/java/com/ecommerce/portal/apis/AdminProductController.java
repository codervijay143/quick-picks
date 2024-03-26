package com.ecommerce.portal.apis;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.ecommerce.portal.dtos.ApiResponse;
import com.ecommerce.portal.dtos.CreateProductRequest;
import com.ecommerce.portal.dtos.ProductDTO;
import com.ecommerce.portal.dtos.ProductRequest;
import com.ecommerce.portal.entities.Product;
import com.ecommerce.portal.exceptions.ProductException;
import com.ecommerce.portal.repositories.CategoryRepository;
import com.ecommerce.portal.repositories.ProductRepository;
import com.ecommerce.portal.services.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

	private final ProductService productService;

	@PostMapping("/")
	public ResponseEntity<ProductDTO> createProductHandler(@RequestBody ProductRequest req) throws ProductException {

		ProductDTO productDTO = this.productService.createProduct(req);

		return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.ACCEPTED);
		
	}

	@PostMapping("/data")
	public String testing(@RequestBody List<ProductRequest> productRequests){

		for (ProductRequest productRequest:productRequests){
			this.productService.createProduct(productRequest);
		}
		return "data";
	}
	
	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProductHandler(@PathVariable Long productId) throws ProductException{
		
		System.out.println("dlete product controller .... ");
		String msg=this.productService.deleteProduct(productId);
		System.out.println("dlete product controller .... msg "+msg);
		ApiResponse res=new ApiResponse(msg,true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<ProductDTO>> findAllProduct(){
		
		List<ProductDTO> productDTOS =this.productService.getAllProducts();
		
		return new ResponseEntity<List<ProductDTO>>(productDTOS,HttpStatus.OK);
	}
	
	@GetMapping("/recent")
	public ResponseEntity<List<ProductDTO>> recentlyAddedProduct(){
		
		List<ProductDTO> productDTOList = this.productService.recentlyAddedProduct();
		
		return new ResponseEntity<List<ProductDTO>>(productDTOList,HttpStatus.OK);
	}
	
	
	@PutMapping("/{productId}/update")
	public ResponseEntity<ProductDTO> updateProductHandler(@RequestBody ProductDTO productDTO,@PathVariable Long productId) throws ProductException{

		ProductDTO updatedProduct=this.productService.updateProduct(productId, productDTO);
		
		return new ResponseEntity<ProductDTO>(updatedProduct,HttpStatus.OK);
	}
	
	@PostMapping("/creates")
	public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody ProductRequest[] reqs) throws ProductException{
		
		for(ProductRequest product:reqs) {
			this.productService.createProduct(product);
		}
		
		ApiResponse res=new ApiResponse("products created successfully",true);
		return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
	}

}
