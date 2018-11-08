package com.waes.techinterview.comparator.service.validator;

/**
 * Created by Kunal on 07-11-2018.
 */
@FunctionalInterface
public interface ValidationService {

    /**
     *  * <p>Validates that a string is a valid Base64 encoding. This uses a
     * regular expression to perform the check. The empty string is also
     * considered valid. All whitespace is ignored.</p>
     * @param data the string to validate
     * @return true if the string is a valid Base64 encoding.
     */
    boolean validateInputData(String data);


}
