package org.jakegodsall;

import org.jakegodsall.config.impl.ApiKeyConfigImpl;
import org.jakegodsall.view.cli.ApiKeyHandler;
import org.jakegodsall.view.cli.CommandLineInterface;

public class Main {
    public static void main(String[] args) throws Exception {
        CommandLineInterface cli = new CommandLineInterface(new ApiKeyHandler(new ApiKeyConfigImpl()));
        cli.main();
//        Application.launch(GuiInterface.class, args);

    }
}