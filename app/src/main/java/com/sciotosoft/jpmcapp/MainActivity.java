package com.sciotosoft.jpmcapp;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //todo: In order to upload this project in a timely manner, I had to disable Git SSL verification. Later, I will need to enable and resolve SSL issues.
    //todo: Time Permitting, I would have created an additional Fragment for the Max Range problem, leaving MainActivity empty of its logic.

    static final int lowerLimit = 2;
    static final int upperLimit = 10000;
    private static final NullPointerException validationError = new NullPointerException();
    String inputString;
    //List<ActionCase> caseList = new ArrayList<ActionCase>();
    //List<Integer> dailyChanges = new ArrayList<>();

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextInputEditText rangeInputText = this.findViewById(R.id.rangeInputTxt);


        Button calculateBtn = this.findViewById(R.id.calculateMaxReturnBTN);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    inputString = rangeInputText.getText().toString();
                    rangeButtonClicked(inputString);
                } catch (NullPointerException e) {
                    System.out.println(e + "NullPointerException at MainActivity onClick");
                }
            }
        });
    }

    //with more time I would switch rangeButtonClicked to another method that runs when the user closes the
    //keyboard by tap out of licking "done".
    private void rangeButtonClicked(String inputStr) {
        List<Integer> inputList = StringParser.parseInputStr(inputStr);
        TextInputEditText rangeInputText = this.findViewById(R.id.rangeInputTxt);
        System.out.println("chk1");
        TextView rangeOutputText = this.findViewById(R.id.rangeOutputTxt);
        System.out.println("chk2");
        try {
            if (inputList.size() >= lowerLimit && inputList.size() <= upperLimit) {
                //rangeOutputText.setText(runTheNumbers(inputList).gain);
                ActionCase bestProfit = runTheNumbers(inputList);
                rangeInputText.setText("");
                if(bestProfit.run > 0){ //Protect against divide by 0 error.
                    TextView outputText = this.findViewById(R.id.rangeOutputTxt);
                    outputText.setText("Max Value Gain: " + bestProfit.gain +
                            "\nEntry Day: " + bestProfit.entryDay +
                            "\nExit Day: " + bestProfit.exitDay +
                            "\nDays to Fruition: " + bestProfit.run +
                            "\nMean Daily Profit: " + bestProfit.gain / bestProfit.run);
                }else{
                    System.out.println("NO INVESTMENT HAS BEEN MADE!");
                }
            } else {
                rangeInputText.setText(""); //Clear interface to allow user to enter a fresh set
                Toast toast = Toast.makeText(getApplicationContext(), "Input Error: Enter between (2) and (10,000) Integers." +
                        "\nSeparate each integer with a single space. " +
                        "\n1 5 -3 15 -26 8+ 1 13", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 10);
                toast.show();
                //"Input Error: Enter between (2) and (10,000) Integers." +
                //                        "\nSeparate each integer with a single space. " +
                //                        "\n1 5 -3 15 -26 8+ 1 13"
            }
        } catch (NullPointerException e) {
            System.out.println(e + "Exception on MainActivity.rangeButtonClicked");
        }
        System.out.println("chk3");

    }

    private ActionCase runTheNumbers(@NotNull List<Integer> input) {
        //I kind of wanted to use a linked list here just for the sake of it,
        // but it really seemed like it would bog this down since there will be many iterations.
        //todo: Time permitting I would replace highestGain variable with a sorting algorithm to order caseList by Gains.
        List<ActionCase> caseList = new ArrayList<>(); //debug var - check actual highest.
        ActionCase highestGain = new ActionCase(0,0,0);
        int totalGain = 0;
        int meanProfit = 0;
        int entryDayGain = 0;
        int exitDayGain = 0;
        //int entryDay = 0;
        //int exitDay = 0;
        try{
            for (int entryDay = 0; entryDay < input.size(); entryDay++) {
                //ActionCase bestROI = new ActionCase(0,0,0); //ROI = return on investment
                //entryDay = input.get(exteriorIndex); //debug var
                entryDayGain = input.get(entryDay);
                totalGain += entryDayGain;
                System.out.println("Entry day gain " + entryDayGain);
                for (int exitDay = 0; exitDay < input.size(); exitDay++) {
                    //We cannot sell our stock before we've bought it!! Ergo . . .
                    if (exitDay > entryDay) {
                        //exitDay = input.get(exitDay); //debug var
                        exitDayGain = input.get(exitDay);
                        totalGain += exitDayGain;
                        System.out.println("Exit day gain " + exitDayGain);
                        ActionCase ac = new ActionCase(totalGain, entryDay, exitDay);
                        caseList.add(ac);
                        //If gain is equal, the existing case will be retained on account of requiring fewer days to fulfillment,
                        // resulting in greater value when making multiple investments back to back..
                        if(ac.gain > highestGain.gain){
                            highestGain = ac;
                            //meanProfit = ac.gain / ac.run;
                        }
                    }
                }
            }
        }catch(IndexOutOfBoundsException e){
            System.out.println(e + " on MainActivity.runTheNumbers");
        }finally {
            return  highestGain;
        }
    }
}