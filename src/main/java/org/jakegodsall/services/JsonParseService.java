package org.jakegodsall.services;

import java.io.IOException;
import java.util.List;

public interface JsonParseService {
    List<String> parseModels(String json) throws IOException;
    String parseSentence(String json) throws IOException;
}
