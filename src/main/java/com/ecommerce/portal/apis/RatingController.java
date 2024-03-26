package com.ecommerce.portal.apis;

import java.util.ArrayList;
import java.util.List;

import com.ecommerce.portal.dtos.RatingDTO;
import com.ecommerce.portal.dtos.RatingRequest;
import com.ecommerce.portal.entities.Rating;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.ProductException;
import com.ecommerce.portal.exceptions.UserException;
import com.ecommerce.portal.services.RatingService;
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
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {
	
	private final UserService userService;
	private final RatingService ratingServices;

	@PostMapping("/create")
	public ResponseEntity<RatingDTO> createRatingHandler(@RequestBody RatingRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException, UserException, ProductException {
		User user=userService.findUserProfileByJwt(jwt);
		Rating rating=ratingServices.createRating(req, user);
		RatingDTO ratingDTO=this.entityToDTO(rating);
		return new ResponseEntity<>(ratingDTO,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<RatingDTO>> getProductsReviewHandler(@PathVariable Long productId){
	
		List<Rating> ratings=ratingServices.getProductsRating(productId);
		List<RatingDTO> ratingDTOList=new ArrayList<>();
		for (Rating rating:ratings){
			RatingDTO ratingDTO=this.entityToDTO(rating);
			ratingDTOList.add(ratingDTO);
		}
		return new ResponseEntity<>(ratingDTOList,HttpStatus.OK);
	}

	private RatingDTO entityToDTO(Rating rating){

	    RatingDTO ratingDTO = new RatingDTO();
	    ratingDTO.setId(rating.getId());
	    ratingDTO.setUser(rating.getUser());
	    ratingDTO.setProduct(rating.getProduct());
	    ratingDTO.setRating(rating.getRating());
	    ratingDTO.setCreatedAt(rating.getCreatedAt());
	    return ratingDTO;

	}
}
