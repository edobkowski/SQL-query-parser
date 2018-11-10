package com.codecool.helpers.readers;

import com.codecool.exceptions.DataReaderException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FileReader implements DataReader {

    private String source;

    @Override
    public void setSource(String source) {
        if (source.contains(".csv")) this.source = "src/main/resources/" + source;
        else this.source = "src/main/resources/" + source + ".csv";
    }

    @Override
    public List<String> getHeader() throws DataReaderException {
        try (Stream<String> stream = Files.lines(Paths.get(source))){
            return Arrays.asList(stream
                            .findFirst()
                            .get()
                            .split("\\s*,\\s*"));
        }
        catch (IOException e) {
            throw new DataReaderException("Cannot read file " + source);
        }
    }

    @Override
    public Stream<String> getDataStream() throws DataReaderException {
        try {
            return Files.lines(Paths.get(source)).skip(1);
        }
        catch (IOException e) {
            throw new DataReaderException("Cannot read file " + source);
        }
    }
}
