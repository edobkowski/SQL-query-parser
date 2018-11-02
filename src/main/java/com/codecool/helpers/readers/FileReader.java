package com.codecool.helpers.readers;

import com.codecool.exceptions.DataReaderException;
import com.codecool.predicates.LessPredicate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FileReader implements DataReader {

    private String fileName;

    public FileReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<String> getHeader() throws DataReaderException {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))){
            return Arrays.asList(stream
                            .findFirst()
                            .get()
                            .split("\\s*,\\s*"));
        }
        catch (IOException e) {
            throw new DataReaderException("Cannot read file " + fileName);
        }
    }

    @Override
    public Stream<String> getDataStream() throws DataReaderException {
        try {
            return Files.lines(Paths.get(fileName)).skip(1);
        }
        catch (IOException e) {
            throw new DataReaderException("Cannot read file " + fileName);
        }
    }
}
