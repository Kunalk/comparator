package com.waes.techinterview.comparator.service.storage.h2;

import com.waes.techinterview.comparator.service.storage.StorageService;
import com.waes.techinterview.comparator.vo.ComparatorInputSideEnum;
import com.waes.techinterview.comparator.vo.ComparatorInputVO;
import com.waes.techinterview.comparator.vo.DocumentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Kunal on 08-11-2018.
 */
@Service
public class H2DBStorageService implements StorageService{

    @Autowired
    private H2DatabaseDao h2DatabaseDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    public void store(ComparatorInputVO comparatorInputVO) {
        Optional<DocumentEntity> documentEntity = h2DatabaseDao.findById(comparatorInputVO.getId());

        // check if existing document present for given {id}.
        // if present, check the CmparatorInputSide and update data
        // otherwise, create new entity
        if(documentEntity.isPresent()){
            if(ComparatorInputSideEnum.LEFT == comparatorInputVO.getComparatorInputSideEnum()){
                documentEntity.get().setLeft(comparatorInputVO.getData());
            }else{
                documentEntity.get().setRight(comparatorInputVO.getData());
            }
            h2DatabaseDao.save(documentEntity.get());
        }else{
            DocumentEntity newDocumentEntity = new DocumentEntity();
            newDocumentEntity.setId(comparatorInputVO.getId());
            if(ComparatorInputSideEnum.LEFT == comparatorInputVO.getComparatorInputSideEnum()){
                newDocumentEntity.setLeft(comparatorInputVO.getData());
            }else{
                newDocumentEntity.setRight(comparatorInputVO.getData());
            }
            h2DatabaseDao.save(newDocumentEntity);
        }


    }

    @Override
    @Transactional(readOnly = true)
    public DocumentVO getDocument(Long id) {
        DocumentVO documentVO = null;
        Optional<DocumentEntity> documentEntity = h2DatabaseDao.findById(id);
        if(documentEntity.isPresent()){
            documentVO = new DocumentVO();
            documentVO.setId(documentEntity.get().getId());
            documentVO.setLeft(documentEntity.get().getLeft());
            documentVO.setRight(documentEntity.get().getRight());
        }
        return documentVO;
    }
}
