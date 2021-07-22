package com.alkemy.ong.config;

import com.alkemy.ong.util.EmailConstants;
import com.sendgrid.SendGrid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {

    @Bean
    public SendGrid getSengrid(){
        return new SendGrid(EmailConstants.API_KEY);
    }

}
