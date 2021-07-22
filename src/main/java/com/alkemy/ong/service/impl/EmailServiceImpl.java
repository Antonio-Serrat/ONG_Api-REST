package com.alkemy.ong.service.impl;

import com.alkemy.ong.service.Interface.IEmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import com.alkemy.ong.util.EmailConstants;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final MessageSource messageSource;

    public void contactEmail(String sendTo) throws IOException {
        send(sendTo, EmailConstants.EMAIL_SUBJECT_CONTACT, EmailConstants.EMAIL_TEMPLATE_CONTACT);
    }

    public void registerEmail(String sendTo) throws IOException {
        send(sendTo, EmailConstants.EMAIL_SUBJECT_REGISTER, EmailConstants.EMAIL_TEMPLATE_REGISTER);
    }

    @Override
    public void send(String sendTo, String subject_email, String template) throws IOException {
        Email from = new Email(EmailConstants.EMAIL_FROM);
        Email to = new Email(sendTo);
        String subject = subject_email;
        Content content = new Content(EmailConstants.EMAIL_TYPE, getEmailFromResources(template));
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(EmailConstants.API_KEY);
        Request request = new Request();
        try{
            request.setMethod(Method.POST);
            request.setEndpoint(EmailConstants.EMAIL_ENDPOINT);
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        }catch (IOException ex){
            throw new IOException(messageSource.getMessage("email.error.cant.send", null, Locale.getDefault()));
        }
    }

    private String getEmailFromResources(String template) throws IOException {
        String email = "";
        String line;
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            InputStream inputStream = classLoader.getResourceAsStream(template);
            assert inputStream != null;
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            while ((line = reader.readLine()) != null) {
                email = email.concat(line);
            }
        } catch (IOException e) {
           throw new IOException(messageSource.getMessage("email.error.cant.get.email", null, Locale.getDefault()));
        }
        return email;

    }

}
