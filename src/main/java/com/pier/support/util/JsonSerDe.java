package com.pier.support.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

public class JsonSerDe {
    private final ObjectMapper mapper = new ObjectMapper();

    @Nonnull
    public <T> String prettySerialize(@Nullable final T data) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    @Nonnull
    public <T> String serialize(@Nullable final T data) throws JsonProcessingException {
        return mapper.writeValueAsString(data);
    }

    @Nonnull
    public <T> void serialize(@Nullable final T data, final OutputStream outputStream) throws IOException {
        mapper.writeValue(outputStream, data);
    }

    @Nullable
    public <T> T deserialize(@Nonnull final String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    @Nullable
    public <T> T deserialize(@Nonnull final File file, Class<T> clazz) throws IOException {
        return mapper.readValue(file, clazz);
    }

    @Nullable
    public <T> T deserialize(@Nonnull final InputStream inputStream, Class<T> clazz) throws IOException {
        return mapper.readValue(inputStream, clazz);
    }

    @Nullable
    public <T> Iterator<T> getIterator(@Nonnull final InputStream inputStream, Class<T> clazz) throws IOException {
        return mapper.readValues(mapper.getFactory().createParser(inputStream), clazz);
    }

    @Nullable
    public <T> Iterator<T> getIterator(@Nonnull final File file, Class<T> clazz) throws IOException {
        return mapper.readValues(mapper.getFactory().createParser(file), clazz);
    }
}
