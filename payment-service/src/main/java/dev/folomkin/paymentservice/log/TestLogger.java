package dev.folomkin.paymentservice.log;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestLogger {

    @PostConstruct
    void test() {
        log.info("PAYMENT SERVICE STARTED");
        log.debug("Debug log works");
    }
}