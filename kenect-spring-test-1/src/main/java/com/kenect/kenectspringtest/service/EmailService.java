package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.constants.Constants;
import com.kenect.kenectspringtest.exception.InvalidInputException;
import com.kenect.kenectspringtest.model.EmailAddress;
import com.kenect.kenectspringtest.repository.EmailRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>EmailService class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@Service
public class EmailService extends BaseService<EmailAddress, Long> {

    private static Pattern emailPattern = Pattern.compile("^[_A-Za-z]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    /**
     * <p>Constructor for EmailService.</p>
     *
     * @param emailRepository a {@link com.kenect.kenectspringtest.repository.EmailRepository} object.
     */
    @Autowired
    public EmailService(EmailRepository emailRepository) {
        this.repository = emailRepository;
    }

    /** {@inheritDoc} */
    @Override
    public EmailAddress save(EmailAddress emailAddress) {
        if (emailAddress == null)
            throw new InvalidInputException(Constants.MSG_EMAIL_OBJECT_REQUIRED);
        if (emailAddress.getEmail() == null || StringUtils.isEmpty(emailAddress.getEmail()))
            throw new InvalidInputException(Constants.MSG_EMAIL_REQUIRED);
        if (!validateEmailAddress(emailAddress.getEmail()))
            throw new InvalidInputException(Constants.MSG_EMAIL_FORMAT);
        return super.save(emailAddress);
    }

    /**
     * <p>validateEmailAddress.</p>
     *
     * @param email a {@link java.lang.String} object.
     * @return a boolean.
     */
    public static boolean validateEmailAddress(String email) {
        Matcher matcher = emailPattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
