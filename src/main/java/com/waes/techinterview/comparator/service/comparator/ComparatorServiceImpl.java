package com.waes.techinterview.comparator.service.comparator;

import com.waes.techinterview.comparator.service.storage.StorageService;
import com.waes.techinterview.comparator.vo.ComparatorResultEnum;
import com.waes.techinterview.comparator.vo.ComparatorResultVO;
import com.waes.techinterview.comparator.vo.ContentDifference;
import com.waes.techinterview.comparator.vo.DocumentVO;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * This method actual compares the content of document's left and right side
     * @param id
     * @return
     */
    @Override
    public ComparatorResultVO compare(DocumentVO documentVO) {

        // if document is not present -> throw exception with details
        if(documentVO ==null ){
            //TODO throw exception - data not present for given id
        }

        // if either of left or right side data is not present -> throw exception with details
        if(StringUtils.isBlank(documentVO.getLeft()) || StringUtils.isBlank(documentVO.getRight())){
            //TODO throw exception - data not sufficient to perform comparison
        }

        ComparatorResultVO comparatorResultVO = null;

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


        return comparatorResultVO;
    }

    private static List<ContentDifference> calculateOffsetDifference(DocumentVO documentVO ){
        List<ContentDifference> contentDifferences = new ArrayList<>();

        // length is used for getting the total offset+length in tha data for the diff
        int length = 0;

        // offset field used for getting where the DIFF's are
        // initialize offset to -1 which will allow to track the chars (array starts from 0, so anything <0)
        int offset = -1;

        for (int i = 0; i <= documentVO.getLeft().length()-1; i++) {
            if(documentVO.getLeft().charAt(i) != documentVO.getRight().charAt(i)){

                // char mismatch, so we need to start both offset and length counters
                // if already started, increament the length only
                if(offset<0){
                    // offset <0 -> start of char difference, so set the offset to charAt position
                    offset= i;
                }

                length++;

            }else if(offset != -1){

                // char matches with offset <> -1 -> store the entry<offset,legth> of mismatched records
                contentDifferences.add(new ContentDifference(offset,length));

                // since matching chars identified, reinitialize the offset and length to default values
                length = 0;
                offset = -1;
            }
        }

        if(length != 0 && offset != -1){
            contentDifferences.add(new ContentDifference(offset,length));
        }

        return contentDifferences;
    }
}
