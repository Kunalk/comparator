package com.waes.techinterview.comparator.service.storage;

import com.waes.techinterview.comparator.exception.ProcessingException;
import com.waes.techinterview.comparator.vo.ComparatorInputVO;
import com.waes.techinterview.comparator.vo.DocumentVO;

/**
 * Created by Kunal on 07-11-2018.
 */
public interface StorageService {

    /**
     * Store the data
     * @param comparatorInputVO input object containing all the fields.
     *
     */
    void store(ComparatorInputVO comparatorInputVO) throws ProcessingException;

    /**
     * Retrive the document
     * @param id
     * @return
     */
    DocumentVO getDocument(Long id) throws ProcessingException;

}
