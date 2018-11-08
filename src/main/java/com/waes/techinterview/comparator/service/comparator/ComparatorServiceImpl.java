package com.waes.techinterview.comparator.service.comparator;

import com.waes.techinterview.comparator.exception.ErrorCodeEnum;
import com.waes.techinterview.comparator.exception.ProcessingException;
import com.waes.techinterview.comparator.exception.ValidationException;
import com.waes.techinterview.comparator.service.storage.StorageService;
import com.waes.techinterview.comparator.vo.ComparatorResultEnum;
import com.waes.techinterview.comparator.vo.ComparatorResultVO;
import com.waes.techinterview.comparator.vo.ContentDifference;
import com.waes.techinterview.comparator.vo.DocumentVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Kunal on 08-11-2018.
 */
@Service
public class ComparatorServiceImpl implements ComparatorService {

    private static final Logger LOG = LoggerFactory.getLogger(ComparatorServiceImpl.class);

    @Override
    public ComparatorResultVO compare(DocumentVO documentVO) {

        // if document is not present -> throw exception with details
        if(documentVO ==null ){
            //throw exception - data not present for given id
            throw new ValidationException(ErrorCodeEnum.DATA_NOT_PRESENT);
        }

        // if either of left or right side data is not present -> throw exception with details
        if(StringUtils.isBlank(documentVO.getLeft()) || StringUtils.isBlank(documentVO.getRight())){
            // throw exception - data not sufficient to perform comparison
            throw new ValidationException(ErrorCodeEnum.DATA_NOT_PRESENT);
        }

        ComparatorResultVO comparatorResultVO = null;
        try{

            byte[] bytesLeft = documentVO.getLeft().getBytes();
            byte[] bytesRight = documentVO.getRight().getBytes();

            if(bytesLeft.length != bytesRight.length){
                comparatorResultVO = new ComparatorResultVO(ComparatorResultEnum.LENGTH_MISMATCH);
            }else if(Arrays.equals(bytesLeft, bytesRight)){
                comparatorResultVO = new ComparatorResultVO(ComparatorResultEnum.EQUAL);
            }else{
                comparatorResultVO = new ComparatorResultVO(ComparatorResultEnum.OFFSET_MISMATCH);
                comparatorResultVO.setContentDifferences(calculateOffsetDifference(documentVO));
            }
        }catch(Exception e){
            LOG.error("Exception identified while comparing left and right side information for ID -" + documentVO.getId() + " due to exception -"+e.getMessage(), e );
            throw new ProcessingException(e.getMessage(), e, ErrorCodeEnum.COMPARISON_ERROR);
        }

        return comparatorResultVO;
    }

    /**
     *
     * @param documentVO
     * @return
     */
    private static List<ContentDifference> calculateOffsetDifference(DocumentVO documentVO ){
        List<ContentDifference> contentDifferences = new ArrayList<>();

        // length is used for getting the total length in tha data for the diff from the offset position
        int length = 0;

        // offset field used for getting where the DIFF's start at any position
        // initialize offset to -1 which will allow to track the chars (array starts from 0, so anything <0 should be assigned)
        int offset = -1;

        for (int i = 0; i <= documentVO.getLeft().length()-1; i++) {
            if(documentVO.getLeft().charAt(i) != documentVO.getRight().charAt(i)){

                // char mismatch, so we need to start both offset and length counters
                // if already started, increament the length only
                if(offset<0){
                    // offset <0 -> start of char difference, so set the offset to charAt position
                    offset= i;
                }
                // increment the length of offset differences
                length++;

            }else if(offset != -1){

                // char matches with offset <> -1 -> store the entry<offset,legth> of mismatched records
                contentDifferences.add(new ContentDifference(offset,length));

                // since matching chars identified, reinitialize the offset and length to default values
                length = 0;
                offset = -1;
            }
        }

        // handle scenario when difference is at last section of string and no further character present
        if(length != 0 && offset != -1){
            contentDifferences.add(new ContentDifference(offset,length));
        }

        return contentDifferences;
    }
}
