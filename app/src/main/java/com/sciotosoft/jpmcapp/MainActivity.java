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
    //todo: Time Permitting, I would have created two Fragments - one for each problem - abstracting the logic for Parts 1 and 2 into their respective controlling fragments. This would be a simple button switch, toggling displayed fragments.
    //todo: Time Permitting, I would install a variety of virtual devices and test that this application works and looks as intended on machines other than my Samsung J7 Sky Pro.

    static final int lowerLimit = 2;
    static final int upperLimit = 10000;
    String inputString;
    String inputDollar;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextInputEditText profitInputText = this.findViewById(R.id.profitInputTxt);
        final TextInputEditText dollarInputText = this.findViewById(R.id.dollarInputTxt);

        Button calculateBtn = this.findViewById(R.id.calculateMaxReturnBTN);
        Button dollarTranslateBtn = this.findViewById(R.id.dollarToTextTranslateBTN);

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    inputString = profitInputText.getText().toString();
                    calculateButtonClicked(inputString);
                } catch (NullPointerException e) {
                    System.out.println(e + "NullPointerException at MainActivity onClick");
                }
            }
        });

        dollarTranslateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                try {
                    inputDollar = dollarInputText.getText().toString();
                    translateButtonButtonClicked(inputDollar);
                } catch (NullPointerException e) {
                    System.out.println(e + "NullPointerException at MainActivity onClick");
                }
            }
        });
    }

    private void calculateButtonClicked(String inputStr) {
        List<Integer> inputList = StringParser.parseProfitInput(inputStr);
        TextInputEditText profitInputText = this.findViewById(R.id.profitInputTxt);

        try {
            if (inputList.size() >= lowerLimit && inputList.size() <= upperLimit) {
                ActionCase bestProfit = runTheNumbers(inputList);
                profitInputText.setText("");
                if(bestProfit.run > 0){ //Protect against divide by 0 error.
                    TextView outputText = this.findViewById(R.id.dualOutputTxt);
                    outputText.setText("Max Value Gain: " + bestProfit.getGain() +
                            "\nEntry Day: " + bestProfit.getEntryDay() +
                            "\nExit Day: " + bestProfit.getExitDay() +
                            "\nDays to Fruition: " + bestProfit.getRun() +
                            "\nMean Daily Profit: " + bestProfit.getGain() / bestProfit.getRun());
                }else{
                    System.out.println("NO INVESTMENT HAS BEEN MADE!");
                }
            } else {
                profitInputText.setText(""); //Clear interface to allow user to enter a fresh set
                Toast toast = Toast.makeText(getApplicationContext(), "Input Error: Enter between (2) and (10,000) Integers." +
                        "\nSeparate each integer with a single space. " +
                        "\n1 5 -3 15 -26 8+ 1 13", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 10);
                toast.show();
            }
        } catch (NullPointerException e) {
            System.out.println(e + "Exception on MainActivity.calculateButtonClicked");
        }
    }

    private void translateButtonButtonClicked(String inputDollar){
        System.out.println("InputDollar: " + inputDollar);
        TextView output = this.findViewById(R.id.dualOutputTxt);
        String outputStr = StringParser.parseDollarInput(inputDollar);
        output.setText(outputStr);
    }

    private ActionCase runTheNumbers(@NotNull List<Integer> input) {
        //todo: Time permitting I would replace highestGain variable with a sorting algorithm to order caseList by Gains.
        List<ActionCase> caseList = new ArrayList<>(); //debug var - check actual highest.
        ActionCase highestGain = new ActionCase(0,0,0);
        int totalGain;
        int entryDayGain;
        int exitDayGain;
        try{
            for (int entryDay = 0; entryDay < input.size(); entryDay++) {
                //ActionCase bestROI = new ActionCase(0,0,0); //ROI = return on investment
                 entryDayGain = input.get(entryDay);
                totalGain = entryDayGain;
                System.out.println("Entry day gain " + entryDayGain);
                for (int exitDay = entryDay+1; exitDay < input.size(); exitDay++) {
                    exitDayGain = input.get(exitDay);
                    totalGain += exitDayGain;
                    System.out.println("Exit day gain " + exitDayGain);
                    ActionCase ac = new ActionCase(totalGain, entryDay, exitDay);
                    caseList.add(ac);
                    //If gain is equal, the existing case will be retained on account of requiring fewer days to fulfillment,
                    // resulting in greater value when making multiple investments back to back..
                    if(ac.gain > highestGain.gain){
                        highestGain = ac;
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