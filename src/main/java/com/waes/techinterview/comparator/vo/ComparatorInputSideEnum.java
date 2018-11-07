package com.waes.techinterview.comparator.vo;

/**
 * Created by Kunal on 07-11-2018.
 */
public enum ComparatorInputSideEnum {

    LEFT("LEFT"), RIGHT("RIGHT");


    private ComparatorInputSideEnum(String inputSide){
        this.inputSide = inputSide;
    }

    public String getInputSide() {
        return inputSide;
    }

    private String inputSide;


}
