package com.github.alica.scrapy.command;

import com.github.alica.scrapy.command.util.CommandWithParameter;
import com.github.alica.scrapy.command.util.CommandWithoutParameter;
import java.util.ArrayList;

public class Switch {
    private CommandWithoutParameter commandV;
    private CommandWithoutParameter commandC;
    private CommandWithParameter commandW;
    private CommandWithParameter commandE;
    public Switch(CommandWithoutParameter commandV, CommandWithoutParameter commandC, CommandWithParameter commandW, CommandWithParameter commandE){
        this.commandV = commandV;
        this.commandC = commandC;
        this.commandW = commandW;
        this.commandE = commandE;
    }

    public void v(){
        commandV.execute();
    }

    public void c(){
        commandC.execute();
    }

    public void w(ArrayList<String> listWord){
        commandW.execute(listWord);
    }

    public void e(ArrayList<String> listWord){
        commandE.execute(listWord);
    }
}