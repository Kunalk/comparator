package com.waes.techinterview.comparator.vo;

/**
 * Created by Kunal on 07-11-2018.
 */
public class ComparatorResultVO {
    private ComparatorResultEnum comparatorResultEnum;
    private String resultMessage;

    public ComparatorResultVO(ComparatorResultEnum comparatorResultEnum, String resultMessage) {
        this.comparatorResultEnum = comparatorResultEnum;
        this.resultMessage = resultMessage;
    }

    public ComparatorResultVO(ComparatorResultEnum comparatorResultEnum) {
        this.comparatorResultEnum = comparatorResultEnum;
    }

    public ComparatorResultEnum getComparatorResultEnum() {
        return comparatorResultEnum;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
