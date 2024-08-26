package org.jakegodsall;

import org.jakegodsall.view.cli.CommandLineInterface;
import org.jakegodsall.view.cli.CommandLineInterfaceFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        CommandLineInterface cli = CommandLineInterfaceFactory.createGPTCommandLineInterface();
        cli.main();
    }
}