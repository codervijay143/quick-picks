package com.ecommerce.portal.apis;

import java.util.ArrayList;
import java.util.List;

import com.ecommerce.portal.dtos.ReviewDTO;
import com.ecommerce.portal.dtos.ReviewRequest;
import com.ecommerce.portal.entities.Review;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.ProductException;
import com.ecommerce.portal.exceptions.UserException;
import com.ecommerce.portal.services.ReviewService;
import com.ecommerce.portal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
	
	private final ReviewService reviewService;
	private final UserService userService;

	@PostMapping("/create")
	public ResponseEntity<Review> createReviewHandler(@RequestBody ReviewRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException, UserException, ProductException {
		User user=userService.findUserProfileByJwt(jwt);
		System.out.println("product id "+req.getProductId()+" - "+req.getReview());
		Review review=reviewService.createReview(req, user);
		System.out.println("product review "+req.getReview());
		return new ResponseEntity<Review>(review,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<ReviewDTO>> getProductsReviewHandler(@PathVariable Long productId){
		List<Review> reviews=reviewService.getProductsReview(productId);
		List<ReviewDTO> reviewDTOList=new ArrayList<>();

		for (Review review:reviews){
			ReviewDTO reviewDTO = this.entityToDTO(review);
			reviewDTOList.add(reviewDTO);
		}
		return new ResponseEntity<List<ReviewDTO>>(reviewDTOList,HttpStatus.OK);
	}

	private ReviewDTO entityToDTO(Review review){
	    ReviewDTO reviewDTO = new ReviewDTO();
	    reviewDTO.setId(review.getId());
	    reviewDTO.setReview(review.getReview());
	    reviewDTO.setCreatedAt(review.getCreatedAt());
	    reviewDTO.setProduct(review.getProduct());
	    reviewDTO.setUser(review.getUser());
	    return reviewDTO;

	}
}
