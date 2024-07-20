package org.jakegodsall;

import org.jakegodsall.config.ApiKeyConfig;
import org.jakegodsall.view.cli.CommandLineInterface;

public class Main {
    public static void main(String[] args) throws Exception {
        CommandLineInterface cli = new CommandLineInterface();
//        cli.main();
//        Application.launch(GuiInterface.class, args);

        System.out.println(ApiKeyConfig.getApiKeyFromJsonFile());
    }
}