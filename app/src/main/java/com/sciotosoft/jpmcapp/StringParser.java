package com.sciotosoft.jpmcapp;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
//test

public class StringParser {
    //Max Range
    //Input
    //The input consists of integers on a line separated by spaces. The input contains N,
    //the number of days (0 < N < 10000), followed byN integers D (-10000 < D < 10000) indicating
    //the gain or loss on that day.

    public static List<Integer> parseInputStr(@Nullable String stringToSplit) {
        List<Integer> intList = new ArrayList<>();
        if(!StringUtils.isNumericSpace(stringToSplit)){
            return intList;
        }
        String delimiter = ""; //NOT related to delimiter
        String[] tempArray = stringToSplit.split(delimiter);
        for(String s : tempArray) {//Potential failure point; try-catch?
            if(StringUtils.isNumeric(s)){
                int i = Integer.parseInt(s);
                intList.add(i);
            }
        }
        //May pass Empty strings into Empty Lists. That's fine, the list is later validated by length.
        return intList;
    }
}
