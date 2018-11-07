package com.waes.techinterview.comparator.vo;

/** Result enum defines difference results of comparison result
 * Created by Kunal on 07-11-2018.
 */
public enum ComparatorResultEnum {
    //If both left and right side arguments are equal
    EQUAL,
    //If length of arguments mismatch
    LENGTH_MISMATCH,
    //If length matches but difference in offset or values
    OFFSET_MISMATCH;

}
