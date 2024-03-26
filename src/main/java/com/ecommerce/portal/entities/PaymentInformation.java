package com.ecommerce.portal.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentInformation {

    private String cardHolderName;
    private String cardNumber;
    private String cvv;
    private LocalDate expirationDate;

}
