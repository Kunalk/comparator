package com.waes.techinterview.comparator.unit;

import com.waes.techinterview.comparator.service.validator.ValidationService;
import com.waes.techinterview.comparator.service.validator.ValidationServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** Unit test for validation service - ValidationServiceImpl#validateInputData(java.lang.String)
 * Created by Kunal on 08-11-2018.
 */

public class ValidationServiceTest {

    ValidationService validationService= null;

    @Before
    public void before(){
        // initialize the validation service
        validationService = new ValidationServiceImpl();
    }

    /**
     * Test 1: test when input is in proper base 64 encoded format.
     * Assert that method should return true
     */
    @Test
    public void validInputData(){
        Assert.assertEquals(true, validationService.validateInputData("aGVycm8="));
    }

    /**
     * Test 2: test when input is not in prper base 64 encoded format.
     * Assert that method should return false
     */
    @Test
    public void invalidInputData(){
        Assert.assertEquals(false, validationService.validateInputData("aGVycm8=="));
    }

    /**
     * Test 3: test when input is blank
     * Assert that method should return false
     */
    @Test
    public void blankInputata(){
        Assert.assertEquals(false, validationService.validateInputData(" "));
    }

    /**
     * Test 4: test when input is NULL
     * Assert tha method should return false
     */
    @Test
    public void nullInputata(){
        Assert.assertEquals(false, validationService.validateInputData(null));
    }

}
