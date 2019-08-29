package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.exception.InvalidInputException;
import com.kenect.kenectspringtest.model.Phone;
import com.kenect.kenectspringtest.repository.PhoneRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>PhoneService class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@Service
public class PhoneService extends BaseService<Phone, Long> {

    private static Pattern phonePattern = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");

    /**
     * <p>Constructor for PhoneService.</p>
     *
     * @param phoneRepository a {@link com.kenect.kenectspringtest.repository.PhoneRepository} object.
     */
    @Autowired
    public PhoneService(PhoneRepository phoneRepository) {
        this.repository = phoneRepository;
    }

    /** {@inheritDoc} */
    @Override
    public Phone save(Phone phone) {
        if (phone == null)
            throw new InvalidInputException("Phone object is required");
        if (phone.getNumber() == null || StringUtils.isEmpty(phone.getNumber()))
            throw new InvalidInputException("Phone number is required");
        if (!validatePhone(phone.getNumber()))
            throw new InvalidInputException("Phone number format not correct");
        return super.save(phone);
    }

    /**
     * <p>validatePhone.</p>
     *
     * @param phone a {@link java.lang.String} object.
     * @return a boolean.
     */
    public static boolean validatePhone(String phone) {
        Matcher matcher = phonePattern.matcher(phone);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}

