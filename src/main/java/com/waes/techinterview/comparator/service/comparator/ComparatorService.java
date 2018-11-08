package com.waes.techinterview.comparator.service.comparator;

import com.waes.techinterview.comparator.vo.ComparatorResultVO;
import com.waes.techinterview.comparator.vo.DocumentVO;

/**
 * Created by Kunal on 07-11-2018.
 */
public interface ComparatorService {

    ComparatorResultVO compare(DocumentVO documentVO);

}
