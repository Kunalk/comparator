package com.waes.techinterview.comparator.service.storage.h2;

import com.waes.techinterview.comparator.exception.ErrorCodeEnum;
import com.waes.techinterview.comparator.exception.ProcessingException;
import com.waes.techinterview.comparator.service.storage.StorageService;
import com.waes.techinterview.comparator.vo.ComparatorInputSideEnum;
import com.waes.techinterview.comparator.vo.ComparatorInputVO;
import com.waes.techinterview.comparator.vo.DocumentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(H2DBStorageService.class);


    @Autowired
    private H2DatabaseDao h2DatabaseDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    public void store(ComparatorInputVO comparatorInputVO) throws ProcessingException{
        Optional<DocumentEntity> documentEntity = h2DatabaseDao.findById(comparatorInputVO.getId());

        // check if existing document present for given {id}.
        // if present, check the CmparatorInputSide and update data
        // otherwise, create new entity
        try{
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

        }catch(Exception e){
            LOG.error("Exception identified while persisting information for ID -" + comparatorInputVO.getId() + " due to exception -"+e.getMessage(), e );
            throw new ProcessingException(e.getMessage(), e, ErrorCodeEnum.PERSIST_ERROR);
        }


    }

    @Override
    @Transactional(readOnly = true)
    public DocumentVO getDocument(Long id) throws ProcessingException{
        DocumentVO documentVO = null;
        try{
            DocumentEntity documentEntity = h2DatabaseDao.getOne(id);
            if(documentEntity!=null){
                documentVO = new DocumentVO();
                documentVO.setId(documentEntity.getId());
                documentVO.setLeft(documentEntity.getLeft());
                documentVO.setRight(documentEntity.getRight());
            }
            return documentVO;
        }catch(Exception e){
            LOG.error("Exception identified while retrieving information for ID -" + id + " due to exception -"+e.getMessage(), e );
            throw new ProcessingException(e.getMessage(), e, ErrorCodeEnum.RETRIEVE_ERROR);
        }
    }
}
