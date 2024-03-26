package com.ecommerce.portal.dtos;

import com.ecommerce.portal.entities.Address;
import com.ecommerce.portal.entities.PaymentInformation;
import com.ecommerce.portal.entities.Rating;
import com.ecommerce.portal.entities.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class UserDTO {

    private Long id;
    @NotBlank(message = "firstName is mandatory")
    private String firstName;
    @NotBlank(message = "lastName is mandatory")
    private String lastName;
    @NotBlank(message = "password is mandatory")
    private String password;
    @NotBlank(message = "email is mandatory")
    private String email;
    private UserRole role;
    private String mobile;
    private List<Address> address=new ArrayList<>();
    private List<PaymentInformation> paymentInformationList=new ArrayList<>();
    private LocalDateTime createdAt;
}
