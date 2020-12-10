package tech.blockchainers.ethereum.signer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class EthereumSigner {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "ethereum-signer");
        SpringApplication.run(EthereumSigner.class, args);
    }

}
