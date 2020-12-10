package tech.blockchainers.ethereum.signer.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerificationDto {

    private String message;
    private String address;
    private boolean isValid;

}