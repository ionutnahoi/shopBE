package com.endava.shopbe.sendEmailTest;

import com.endava.shopbe.email.SendEmail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ExtendWith(MockitoExtension.class)
public class SendEmailTest {

    SendEmail sendEmail;

    @Test
    void testSendEmail() {
        String toEmail="nahoi.ionut@yahoo.com";
        String subject = "Test";
        String text = " this is a test message";
        sendEmail.SendAnEmail(toEmail, subject, text);

    }
}
