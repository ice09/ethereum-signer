package tech.blockchainers.ethereum.signer.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {

    private String privateKey;
    private String address;

}