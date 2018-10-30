package com.codecool;

import com.codecool.exceptions.FileHandlerException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileHandler {

    public String getHeader(String fileName) throws FileHandlerException {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))){

            return stream.findFirst().get();
        }
        catch (IOException e) {
            throw new FileHandlerException("Cannot read file " + fileName);
        }

    }

    public Stream<String> getDataStream(String fileName) throws FileHandlerException {
        try {
            return Files.lines(Paths.get(fileName));
        }
        catch (IOException e) {
            throw new FileHandlerException("Cannot read file " + fileName);
        }
    }
}
