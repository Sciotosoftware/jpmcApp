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
        for (String s : tempArray) {
            try {
                int i = Integer.parseInt(s);
                intList.add(i);
            } catch (NumberFormatException e) {
                //If ANY errors are encountered, invalidate the entire input!!
                System.out.println(e + "on StringParser parseInputStr");
                intList.clear();
                return intList;
            }
        }
        //May pass Empty strings into Empty Lists. That's fine, the list is later validated by length.
        return intList;
    }

    public static String parseDollarInput(String dollarInput) {
        //TextDollar
        String dollarsInEnglish = "";
        List<ArrayList> listOfLists = new ArrayList<>();
        List<String> inputList = new ArrayList<String>(Arrays.asList(dollarInput.split("")));
        //formatting inputList with String.Split() causes a stray "" value to occupy index 0.
        if (inputList.get(0).equals("")) {
            inputList.remove(0);
        }
        if(inputList.size() > 9){
            return "Please enter a number less than 1,000,000,000";
        }

        //I realize this is messy, but it seemed the most expedient way to organize them.
        List<String> onesDigits = Arrays.asList("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine");
        List<String> tensDigits = Arrays.asList("Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen");
        List<String> hundredsDigits = Arrays.asList("NA", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety");
        List<String> scalingDigits = Arrays.asList("Hundred", "Thousand", "Million");
        //build ListOfLists


        //Order inputList in reverse and segment into sets of 3 for numerical processing.
        //Requirement: Only the last list in listofLists may have less than three entries.
        for (int index = inputList.size() - 1; index >= 0; index--) {
            String s = inputList.get(index);
            int listOfListsTopIndex = listOfLists.size() - 1;
            int topListLength;
            if (listOfListsTopIndex >= 0) {
                topListLength = listOfLists.get(listOfListsTopIndex).size();
                if (topListLength < 3) {
                    ArrayList currentList = listOfLists.get(listOfListsTopIndex);
                    currentList.add(s);
                } else {
                    ArrayList<String> newList = new ArrayList<>();
                    newList.add(s);
                    listOfLists.add(newList);
                }

            } else {
                //FIRST ITERATION
                ArrayList<String> newList = new ArrayList<>();
                newList.add(s);
                listOfLists.add(newList);
            }
        }
        for (int listIndex = 0; listIndex < listOfLists.size(); listIndex++) {
            //0 = hundreds
            //1 = thousands
            //3 = millions
            List<String> list = listOfLists.get(listIndex);
            Boolean caseFlag = false;
            String tempStr = "";
            if (list.size() >= 2) {
                int n = Integer.valueOf(list.get(1));
                //Handle "Special Rules" Numbers 10-19.
                if (n == 1) {
                    caseFlag = true;
                }
            }
            for (int stringIndex = 0; stringIndex < list.size(); stringIndex++) {
                //0 = ones
                //1 = tens
                //2 = hundreds
                String s = list.get(stringIndex);
                int intValue = Integer.valueOf(s);
                if (caseFlag) {
                    switch (stringIndex) {
                        case 2:
                            tempStr = onesDigits.get(intValue-1) + scalingDigits.get(0) + tempStr;
                            break;
                        case 1:
                            continue;
                        case 0:
                            tempStr = tensDigits.get(intValue) + tempStr;
                            break;
                    }
                } else {
                    switch (stringIndex) {
                        case 2:
                            tempStr = onesDigits.get(intValue-1)  + scalingDigits.get(0) + tempStr;
                            break;
                        case 1:
                            tempStr = hundredsDigits.get(intValue-1) + tempStr;
                            break;
                        case 0:
                            tempStr = onesDigits.get(intValue-1) + tempStr;
                            break;
                    }
                }
            }
            if(listIndex > 0){
                tempStr = tempStr + scalingDigits.get(listIndex);
            }
            dollarsInEnglish = tempStr + dollarsInEnglish;
        }
        if(dollarsInEnglish.equals("")){
            dollarsInEnglish = "Zero";
        }
        dollarsInEnglish += "Dollars";
        return dollarsInEnglish;
    }
}