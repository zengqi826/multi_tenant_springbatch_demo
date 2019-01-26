package com.example.springbatch;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchDemoApplicationTests {

    @Autowired
    private StringEncryptor stringEncryptor;
	@Test
	public void contextLoads() {
	    
        System.out.println(stringEncryptor.encrypt("helloworld"));
        System.out.println(stringEncryptor.encrypt("jdbc:mysql://127.0.0.1:53306/docker_demo?useSSL=false&serverTimezone=GMT%2B8"));
        
	}

}
