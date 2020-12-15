package tech.blockchainers.ethereum.signer.rest;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;
import tech.blockchainers.ethereum.signer.rest.dto.AccountDto;
import tech.blockchainers.ethereum.signer.rest.dto.SignatureDto;
import tech.blockchainers.ethereum.signer.rest.dto.VerificationDto;
import tech.blockchainers.ethereum.signer.service.SignatureService;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@RestController
public class SigningController {

    private final SignatureService signatureService;

    public SigningController(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @GetMapping("/createAccount")
    public AccountDto createAccount() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        Credentials signer = create();
        return new AccountDto("0x" + Hex.toHexString(signer.getEcKeyPair().getPrivateKey().toByteArray()), signer.getAddress());
    }

    @GetMapping("/signMessage")
    public SignatureDto signMessage(@RequestParam String privateKey, @RequestParam String message) {
        Credentials signer = Credentials.create(privateKey);
        String signature = signatureService.sign(message.getBytes(StandardCharsets.UTF_8), signer);
        return new SignatureDto(message, signature);
    }

    @GetMapping("/verifyMessage")
    public VerificationDto signMessage(@RequestParam String ethereumAddress, @RequestParam String message, @RequestParam String signature) {
        String recoveredAddress = "0x" +
            signatureService.ecrecoverAddress(
                Hash.sha3(signatureService.createProof(message.getBytes(StandardCharsets.UTF_8))),
                Numeric.hexStringToByteArray(signature),
                ethereumAddress
            );
        return new VerificationDto(message, recoveredAddress, ethereumAddress.equalsIgnoreCase(recoveredAddress));
    }

    public Credentials create() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        ECKeyPair keyPair = Keys.createEcKeyPair();
        BigInteger publicKey = keyPair.getPublicKey();
        BigInteger privateKey = keyPair.getPrivateKey();
        return Credentials.create(new ECKeyPair(privateKey, publicKey));
    }
}
