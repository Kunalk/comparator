package com.waes.techinterview.comparator.vo;

/**
 * Created by Kunal on 08-11-2018.
 */
public class ContentDifference {

    int offset;

    int length;

    public ContentDifference(int offset, int length) {
        this.offset = offset;
        this.length = length;
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }
}
