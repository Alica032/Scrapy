package com.github.alica.scrapy;

import com.github.alica.scrapy.command.util.CommandWithParameter;
import com.github.alica.scrapy.command.util.CommandWithoutParameter;
import java.util.ArrayList;

public class Switch {
    private CommandWithoutParameter commandV;
    private CommandWithParameter commandW;

    public Switch(CommandWithoutParameter commandV, CommandWithParameter commandW){
        this.commandV = commandV;
        this.commandW = commandW;
    }

    public void v(){
        commandV.execute();
    }

    public void w(ArrayList<String> listWord){
        commandW.execute(listWord);
    }
}