package com.waes.techinterview.comparator.vo;

import java.util.List;

/**
 * Created by Kunal on 07-11-2018.
 */
public class ComparatorResultVO {
    private ComparatorResultEnum comparatorResult;

    private List<ContentDifference> contentDifferences;

    public ComparatorResultVO(ComparatorResultEnum comparatorResultEnum, String resultMessage) {
        this.comparatorResult = comparatorResultEnum;

    }

    public ComparatorResultVO(ComparatorResultEnum comparatorResultEnum) {
        this.comparatorResult = comparatorResultEnum;
    }


    public ComparatorResultEnum getComparatorResult() {
        return comparatorResult;
    }


    public List<ContentDifference> getContentDifferences() {
        return contentDifferences;
    }

    public void setContentDifferences(List<ContentDifference> contentDifferences) {
        this.contentDifferences = contentDifferences;
    }



}
