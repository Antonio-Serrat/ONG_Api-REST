package com.alkemy.ong.service.Interface;

import java.io.IOException;

public interface IEmailService {
    void send(String sendTo, String subject_email, String template) throws IOException;
}
