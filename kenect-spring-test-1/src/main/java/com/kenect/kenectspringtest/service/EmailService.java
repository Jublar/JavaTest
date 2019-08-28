package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.exception.InvalidInputException;
import com.kenect.kenectspringtest.model.EmailAddress;
import com.kenect.kenectspringtest.repository.EmailRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailService extends BaseService<EmailAddress, Long> {

    private static Pattern emailPattern = Pattern.compile("^[_A-Za-z]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    @Autowired
    public EmailService(EmailRepository emailRepository) {
        this.repository = emailRepository;
    }

    @Override
    public EmailAddress save(EmailAddress emailAddress) {
        if (emailAddress == null)
            throw new InvalidInputException("Email object is required");
        if (emailAddress.getEmail() == null || StringUtils.isEmpty(emailAddress.getEmail()))
            throw new InvalidInputException("Email is required");
        if (!validateEmailAddress(emailAddress.getEmail()))
            throw new InvalidInputException("Email format not correct");
        return super.save(emailAddress);
    }

    public static boolean validateEmailAddress(String email) {
        Matcher matcher = emailPattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
