package com.waes.techinterview.comparator.vo;

import java.util.List;

/**
 * Created by Kunal on 07-11-2018.
 */
public class ComparatorResultVO {
    private ComparatorResultEnum comparatorResultEnum;

    private List<ContentDifference> contentDifferences;

    public ComparatorResultVO(ComparatorResultEnum comparatorResultEnum, String resultMessage) {
        this.comparatorResultEnum = comparatorResultEnum;

    }

    public ComparatorResultVO(ComparatorResultEnum comparatorResultEnum) {
        this.comparatorResultEnum = comparatorResultEnum;
    }


    public ComparatorResultEnum getComparatorResultEnum() {
        return comparatorResultEnum;
    }


    public List<ContentDifference> getContentDifferences() {
        return contentDifferences;
    }

    public void setContentDifferences(List<ContentDifference> contentDifferences) {
        this.contentDifferences = contentDifferences;
    }



}
