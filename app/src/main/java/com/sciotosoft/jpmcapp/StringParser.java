package com.sciotosoft.jpmcapp;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringParser {
    //Max Range
    //Input
    //The input consists of integers on a line separated by spaces. The input contains N,
    //the number of days (0 < N < 10000), followed byN integers D (-10000 < D < 10000) indicating
    //the gain or loss on that day.

    public static List<Integer> parseProfitInput(String stringToSplit) {
        List<Integer> intList = new ArrayList<>();
        String[] tempArray = stringToSplit.split(" ");
        for(String s : tempArray) {
            try{
                int i = Integer.parseInt(s);
                intList.add(i);
            }catch(NumberFormatException e){
                //If ANY errors are encountered, invalidate the entire input!!
                System.out.println(e + "on StringParser parseInputStr");
                intList.clear();
                return intList;
            }
        }
        //May pass Empty strings into Empty Lists. That's fine, the list is later validated by length.
        return intList;
    }

    public static String parseDollarInput(String dollarInput){
        //TextDollar
        String dollarsInEnglish = "";
        List<ArrayList> listOfLists = new ArrayList<>();
        List<String> inputList = new ArrayList<String>(Arrays.asList(dollarInput.split("")));

        //I realize this is messy, but it seemed the most expedient way to organize them.
        List<String> tenthsDigits = Arrays.asList("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine");
        List<String> joiningDigits = Arrays.asList("Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen");
        List<String> hundredsthDigits = Arrays.asList("Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety");
        List<String> scalingDigits = Arrays.asList("Hundred", "Thousand", "Million");
        //build ListOfLists

        //formatting inputList with String.Split() causes a stray "" value to occupy index 0.
        if(inputList.get(0).equals("")){
            inputList.remove(0);
        }
        //Order inputList in reverse and segment into sets of 3 for numerical processing.
        //Requirement: Only the last list in listofLists may have less than three entries.
        for(int index = inputList.size()-1; index >= 0; index--){
            String s = inputList.get(index);
            int listOfListsTopIndex = listOfLists.size()-1;
            int topListLength;
            if(listOfListsTopIndex >= 0){
                topListLength = listOfLists.get(listOfListsTopIndex).size();
                if(topListLength < 3){
                   ArrayList currentList = listOfLists.get(listOfListsTopIndex);
                   currentList.add(s);
                }else{
                    ArrayList<String> newList = new ArrayList<>();
                    newList.add(s);
                    listOfLists.add(newList);
                }

            }else{
                //FIRST ITERATION
                ArrayList<String> newList = new ArrayList<>();
                newList.add(s);
                listOfLists.add(newList);
            }
        }
        for(List<String> list : listOfLists){
            //0 = hundreds
            //1 = thousands
            //3 = millions
            int listIndex = listOfLists.indexOf(list);
            for(String s : list){
                //0 = ones
                //1 = tens
                //2 = hundreds
                int stringIndex = list.indexOf(s);
                if(list.size() > 2){
                    int n = Integer.valueOf(list.get(2));
                    //Handle "Special Rules" Numbers 10-19.
                    if (n == 1){

                    }
                }

            }
        }
        dollarsInEnglish += "";
        return dollarsInEnglish;
    }
}
