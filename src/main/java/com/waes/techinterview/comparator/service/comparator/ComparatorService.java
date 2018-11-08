package com.waes.techinterview.comparator.service.comparator;

import com.waes.techinterview.comparator.vo.ComparatorResultVO;
import com.waes.techinterview.comparator.vo.DocumentVO;

/** Functional interface for comparing the both the arguments
 * Created by Kunal on 07-11-2018.
 */
@FunctionalInterface
public interface ComparatorService {

    /**
     * Methdd to compare the data (for left and riht sides).
     * @param documentVO java VO object which has all the fields
     * @return ComparatorResultVO with comparatorResult = EQUAL when exact match <p>
     *     comparatorResult = LENGTH_MISMATCH when length doesnot match <p>
     *         comparatorResult = OFFSET_MISMATCH when content mismatch
     */
    ComparatorResultVO compare(DocumentVO documentVO);

}
