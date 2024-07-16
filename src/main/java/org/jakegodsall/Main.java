package org.jakegodsall;

import org.jakegodsall.view.cli.CommandLineInterface;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CommandLineInterface cli = new CommandLineInterface();
        cli.main();
    }
}