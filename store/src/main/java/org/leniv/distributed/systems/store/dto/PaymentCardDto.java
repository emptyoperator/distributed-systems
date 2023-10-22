package org.leniv.distributed.systems.store.dto;

import lombok.Data;

@Data
public class PaymentCardDto {
    private String cardNumber;
    private String expirationDate;
    private String cvv;
}
