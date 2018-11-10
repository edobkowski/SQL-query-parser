package com.codecool.helpers.readers;

import com.codecool.exceptions.DataReaderException;

import java.util.List;
import java.util.stream.Stream;

public interface DataReader {
    List<String> getHeader() throws DataReaderException;
    Stream<String> getDataStream() throws DataReaderException;
    void setSource(String source) throws DataReaderException;
}
