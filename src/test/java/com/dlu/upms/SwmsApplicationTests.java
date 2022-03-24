package com.dlu.upms;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SwmsApplicationTests {

    @Test
    void contextLoads() {
        Object tokenCredentials = new SimpleHash("md5", "222222", "555555", 6).toHex();
        System.out.println(tokenCredentials.toString());

    }

}
