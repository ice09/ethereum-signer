package tech.blockchainers.ethereum.signer.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignatureDto {

    private String message;
    private String signature;

}