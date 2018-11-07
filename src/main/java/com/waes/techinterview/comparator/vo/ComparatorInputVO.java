package com.waes.techinterview.comparator.vo;

/**
 * Input object for store service which supports encapsulation (by hiding the params)
 * and service does not need to change the signature in case of changes in arguments.
 * Created by Kunal on 07-11-2018.
 */
public class ComparatorInputVO {

    Long id;

    String data;

    ComparatorInputSideEnum comparatorInputSideEnum;

    public ComparatorInputVO(Long id, String data, ComparatorInputSideEnum comparatorInputSideEnum) {
        this.id = id;
        this.data = data;
        this.comparatorInputSideEnum = comparatorInputSideEnum;
    }

    public Long getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public ComparatorInputSideEnum getComparatorInputSideEnum() {
        return comparatorInputSideEnum;
    }
}
