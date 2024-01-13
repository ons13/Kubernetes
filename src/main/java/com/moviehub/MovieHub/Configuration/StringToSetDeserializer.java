package com.moviehub.MovieHub.Configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class StringToSetDeserializer extends StdDeserializer<Set<String>> {

    public StringToSetDeserializer() {
        this(null);
    }

    public StringToSetDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Set<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String[] elements = node.asText().split(",\\s*");
        Set<String> set = new HashSet<>();
        for (String element : elements) {
            set.add(element.trim());
        }
        return set;
    }
}