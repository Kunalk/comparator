package com.waes.techinterview.comparator.unit;

import com.waes.techinterview.comparator.exception.ValidationException;
import com.waes.techinterview.comparator.service.comparator.ComparatorService;
import com.waes.techinterview.comparator.service.comparator.ComparatorServiceImpl;
import com.waes.techinterview.comparator.vo.ComparatorResultEnum;
import com.waes.techinterview.comparator.vo.ComparatorResultVO;
import com.waes.techinterview.comparator.vo.DocumentVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/** Unit test for CompratorService - ComparatorServiceImpl#compare(com.waes.techinterview.comparator.vo.DocumentVO)
 * Created by Kunal on 08-11-2018.
 */
public class ComparatorServiceTest {

    private ComparatorService comparatorService=null;

    @Before
    public void before(){
        comparatorService = new ComparatorServiceImpl();
    }

    /**
     * Test 1: test when input data is NULL
     * Assert that ValidationException is thrown
     */
    @Test(expected = ValidationException.class)
    public void testNullInputData(){
        comparatorService.compare(null);

    }

    /**
     * Test 2: test when left argument is not present
     * Assert that ValidationException is thrown
     */
    @Test(expected = ValidationException.class)
    public void testLeftArgumentNotPresent(){
        DocumentVO documentVO = new DocumentVO(1L, null, "abc");
        comparatorService.compare(documentVO);
    }

    /**
     * Test 3: test when right argument is not present
     * Assert that ValidationException is thrown
     */
    @Test(expected = ValidationException.class)
    public void testRightArgumentNotPresent(){
        DocumentVO documentVO = new DocumentVO(1L, "abc",null);
        comparatorService.compare(documentVO);
    }

    /**
     * Test 4: test when arugments length mismatch
     * Assert that getComparatorResult() return ComparatorResultEnum.LENGTH_MISMATCH
     */
    @Test
    public void testArgumentLengthMismatch(){
        DocumentVO documentVO = new DocumentVO(1L, "aGVycm8=","aGVycm8==");
        ComparatorResultVO comparatorResultVO = comparatorService.compare(documentVO);
        Assert.assertNotNull(comparatorResultVO);
        Assert.assertEquals(comparatorResultVO.getComparatorResult(), ComparatorResultEnum.LENGTH_MISMATCH);
    }

    /**
     * Test 5: test when arugments match exactly same
     * Assert that getComparatorResult() return ComparatorResultEnum.EQUAL
     */
    @Test
    public void testArgumentsMatch(){
        DocumentVO documentVO = new DocumentVO(1L, "aGVycm8=","aGVycm8=");
        ComparatorResultVO comparatorResultVO = comparatorService.compare(documentVO);
        Assert.assertNotNull(comparatorResultVO);
        Assert.assertEquals(comparatorResultVO.getComparatorResult(), ComparatorResultEnum.EQUAL);
    }

    /**
     * Test 6: test when arugments length is equal but contain mismatch
     * Assert that getComparatorResult() return ComparatorResultEnum.OFFSET_MISMATCH
     */
    @Test
    public void testArgumentOffsetMismatch(){
        DocumentVO documentVO = new DocumentVO(1L, "aGVycm8=","aGVycm81");
        ComparatorResultVO comparatorResultVO = comparatorService.compare(documentVO);
        Assert.assertNotNull(comparatorResultVO);
        Assert.assertEquals(comparatorResultVO.getComparatorResult(), ComparatorResultEnum.OFFSET_MISMATCH);
    }

    /**
     * Test 7: test when arugments length is equal but first char  mismatch
     * Assert that getComparatorResult() return ComparatorResultEnum.OFFSET_MISMATCH
     */
    @Test
    public void testArgumentDiffAtFirstChar(){
        DocumentVO documentVO = new DocumentVO(1L, "aGVycm8=","AGVycm8=");
        ComparatorResultVO comparatorResultVO = comparatorService.compare(documentVO);
        Assert.assertNotNull(comparatorResultVO);
        Assert.assertEquals(comparatorResultVO.getComparatorResult(), ComparatorResultEnum.OFFSET_MISMATCH);
        Assert.assertNotNull(comparatorResultVO.getContentDifferences());
        Assert.assertEquals(comparatorResultVO.getContentDifferences().size(),1);
    }

    /**
     * Test 8: test when arugments length is equal but middle char  mismatch
     * Assert that getComparatorResult() return ComparatorResultEnum.OFFSET_MISMATCH
     */
    @Test
    public void testArgumentDiffAtMiddleChar(){
        DocumentVO documentVO = new DocumentVO(1L, "aGVycm8=","aGVYcm8=");
        ComparatorResultVO comparatorResultVO = comparatorService.compare(documentVO);
        Assert.assertNotNull(comparatorResultVO);
        Assert.assertEquals(comparatorResultVO.getComparatorResult(), ComparatorResultEnum.OFFSET_MISMATCH);
        Assert.assertNotNull(comparatorResultVO.getContentDifferences());
        Assert.assertEquals(comparatorResultVO.getContentDifferences().size(),1);
    }

    /**
     * Test 9: test when arugments length is equal but last char  mismatch
     * Assert that getComparatorResult() return ComparatorResultEnum.OFFSET_MISMATCH
     */
    @Test
    public void testArgumentDiffAtLastChar(){
        DocumentVO documentVO = new DocumentVO(1L, "aGVycm8=","aGVycm99");
        ComparatorResultVO comparatorResultVO = comparatorService.compare(documentVO);
        Assert.assertNotNull(comparatorResultVO);
        Assert.assertEquals(comparatorResultVO.getComparatorResult(), ComparatorResultEnum.OFFSET_MISMATCH);
        Assert.assertNotNull(comparatorResultVO.getContentDifferences());
        Assert.assertEquals(comparatorResultVO.getContentDifferences().size(),1);
    }

    /**
     * /**
     * Test 10: test when arugments length is equal but all char mismatch
     * Assert that getComparatorResult() return ComparatorResultEnum.OFFSET_MISMATCH
     */

    @Test
    public void testArgumentsCompletelyDiff(){
        DocumentVO documentVO = new DocumentVO(1L, "aGVycm8=","YWNkYwo=");
        ComparatorResultVO comparatorResultVO = comparatorService.compare(documentVO);
        Assert.assertNotNull(comparatorResultVO);
        Assert.assertEquals(comparatorResultVO.getComparatorResult(), ComparatorResultEnum.OFFSET_MISMATCH);
        Assert.assertNotNull(comparatorResultVO.getContentDifferences());
        Assert.assertEquals(comparatorResultVO.getContentDifferences().size(),1);
    }

    /**
     * Test 11: test when arugments length is equal but in between char mismatch
     * Assert that getComparatorResult() return ComparatorResultEnum.OFFSET_MISMATCH
     */
    @Test
    public void testArgumentsMultipleDiff(){
        DocumentVO documentVO = new DocumentVO(1L, "aGVycm8=","AGvyCm9=");
        ComparatorResultVO comparatorResultVO = comparatorService.compare(documentVO);
        Assert.assertNotNull(comparatorResultVO);
        Assert.assertEquals(comparatorResultVO.getComparatorResult(), ComparatorResultEnum.OFFSET_MISMATCH);
        Assert.assertNotNull(comparatorResultVO.getContentDifferences());
        Assert.assertEquals(comparatorResultVO.getContentDifferences().size(),4);
    }


}
