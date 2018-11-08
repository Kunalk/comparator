package com.waes.techinterview.comparator.unit;

import com.waes.techinterview.comparator.exception.ProcessingException;
import com.waes.techinterview.comparator.service.storage.StorageService;
import com.waes.techinterview.comparator.service.storage.h2.DocumentEntity;
import com.waes.techinterview.comparator.service.storage.h2.H2DBStorageService;
import com.waes.techinterview.comparator.service.storage.h2.H2DatabaseDao;
import com.waes.techinterview.comparator.vo.DocumentVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


/** Mockito based unit testing of H2 DB storage service.
 * Created by Kunal on 08-11-2018.
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class H2DBStorageServiceMockTest {

    @InjectMocks
    private H2DBStorageService h2DBStorageService;

    @Mock
    H2DatabaseDao h2DatabaseDao;

    /**
     * Test 1: test when dao returns null
     */
    @Test
    public void testValidDocumentNotExists(){
        Mockito.when(h2DatabaseDao.findById(1L)).thenReturn(null);
        DocumentVO documentVO = h2DBStorageService.getDocument(1L);
        Assert.assertNull(documentVO);
    }

    /**
     * Test 2: test when dao returns valid entity
     */
    @Test
    public void testValidDocumentExists(){
        DocumentEntity  documentEntity = new DocumentEntity();
        documentEntity.setId(1L);

        Mockito.when(h2DatabaseDao.getOne(1L)).thenReturn(documentEntity);
        DocumentVO documentVO = h2DBStorageService.getDocument(1L);
        Assert.assertNotNull(documentVO);
        Assert.assertEquals(documentVO.getId(), 1L);

    }

    /**
     * Test 3: test when dao return valid entity with all mapped fields
     */
    @Test
    public void testValidDocumentWithProperFields(){
        DocumentEntity  documentEntity = new DocumentEntity();
        documentEntity.setId(1L);
        documentEntity.setLeft("left");
        documentEntity.setRight("right");

        Mockito.when(h2DatabaseDao.getOne(1L)).thenReturn(documentEntity);
        DocumentVO documentVO = h2DBStorageService.getDocument(1L);
        Assert.assertNotNull(documentVO);
        Assert.assertEquals(documentVO.getId(), 1L);
        Assert.assertEquals(documentVO.getLeft(), documentEntity.getLeft());
        Assert.assertEquals(documentVO.getRight(), documentEntity.getRight());
    }

    /**
     * Test 4: test when dao throws runtime exception
     */
    @Test(expected = ProcessingException.class)
    public void testGetDocumentThrowsException(){
        Mockito.when(h2DatabaseDao.getOne(1L)).thenThrow(new RuntimeException("test reason"));
        DocumentVO documentVO = h2DBStorageService.getDocument(1L);

    }
}
