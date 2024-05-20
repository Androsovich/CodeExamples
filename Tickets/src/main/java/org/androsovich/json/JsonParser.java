package org.androsovich.json;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import org.androsovich.json.exception.JsonParseFileException;
import org.androsovich.models.Ticket;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.androsovich.constants.Constants.JSON_TICKETS_HEADER;

public class JsonParser {
    private final Gson gson;


    public JsonParser(Gson gson) {
        this.gson = gson;
    }

    public List<Ticket> parse(String filePath) {
        Type ticketsList = new TypeToken<HashMap<String, ArrayList<Ticket>>>() {}.getType();

        List<Ticket> tickets;
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)) {
            tickets = ((Map<String, List<Ticket>>)gson.fromJson(reader, ticketsList)).get(JSON_TICKETS_HEADER);
        } catch (IOException e) {
            final String MESSAGE_EXCEPTION = "exception in method parse in JsonParser";
            throw new JsonParseFileException(MESSAGE_EXCEPTION, e);
        }

        return tickets;
    }
}
