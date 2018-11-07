package com.waes.techinterview.comparator.repository;

import com.waes.techinterview.comparator.vo.DocumentVO;

/**
 * Created by Kunal on 07-11-2018.
 */
public interface DocumentDao {

    /**
     *
     * @param id
     * @return
     */
    DocumentVO findById(long id);

    /**
     *
     * @param documentVO
     */
    void saveDocument(DocumentVO documentVO);

}
