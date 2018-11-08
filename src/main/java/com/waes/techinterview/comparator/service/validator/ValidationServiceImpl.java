package com.waes.techinterview.comparator.service.validator;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Created by Kunal on 08-11-2018.
 */
@Service
public class ValidationServiceImpl implements ValidationService{

    /** Whitespace regular expression. */
    private static final String WHITESPACE_REGEX = "\\s";

    /** Base64 validation regular expression. */
    private static final Pattern BASE64_PATTERN = Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");

    /**
     *  * <p>Validates that a string is a valid Base64 encoding. This uses a
     * regular expression to perform the check. The empty string is also
     * considered valid. All whitespace is ignored.</p>
     * @param data the string to validate
     * @return true if the string is a valid Base64 encoding.
     */
    @Override
    public boolean validateInputData(String data) {
        final String sanitized = data.replaceAll(WHITESPACE_REGEX, "");

        return BASE64_PATTERN.matcher(sanitized).matches();
    }
}
