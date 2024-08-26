package org.jakegodsall;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.impl.client.HttpClients;
import org.jakegodsall.config.impl.ApiKeyConfigImpl;
import org.jakegodsall.services.impl.FlashcardServiceGPTImpl;
import org.jakegodsall.services.impl.HttpClientServiceGPTImpl;
import org.jakegodsall.services.impl.JsonParseServiceGPTImpl;
import org.jakegodsall.services.impl.PromptServiceGPTImpl;
import org.jakegodsall.view.cli.ApiKeyHandler;
import org.jakegodsall.view.cli.CommandLineInterface;

public class Main {
    public static void main(String[] args) throws Exception {
        CommandLineInterface cli = new CommandLineInterface(
                new FlashcardServiceGPTImpl(
                        new HttpClientServiceGPTImpl(HttpClients.createDefault()),
                        new JsonParseServiceGPTImpl(new ObjectMapper()), new PromptServiceGPTImpl()
                ),
                new ApiKeyHandler(new ApiKeyConfigImpl())
        );
        cli.main();
//        Application.launch(GuiInterface.class, args);

    }
}