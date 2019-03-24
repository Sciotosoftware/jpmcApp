package com.sciotosoft.jpmcapp;

public class ActionCase {
    //This class feels poorly named, but I can't be buggered to come up with something more descriptive for a demo app.
    //A simple data structure for evaluation of the optimal date range in which to invest.
    public int gain;
    int entryDay;
    int exitDay;
    int run; //# of days. Presently unused, but possibly important for potential future expansions including day-per-dollar efficiency analysis.

    public ActionCase (int totalGain, int entryD, int exitD)
    {
        setGain(totalGain);
        setEntryDay(entryD);
        setExitDay(exitD);
        setRun();

    }

    private void setGain(int gain) {
        this.gain = gain;
    }

    public int getGain(){
        return this.gain;
    }

    private void setEntryDay(int day){
        this.entryDay = day;
    }
    public int getEntryDay(){
        return this.entryDay;
    }

    private void setExitDay(int day){
        this.exitDay = day;
    }
    public int getExitDay(){
        return this.exitDay;
    }

    private void setRun(){
        this.run = this.exitDay - this.entryDay;
    }
    public int getRun(){
        return this.run;
    }



}
