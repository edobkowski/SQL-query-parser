package com.codecool.helpers.readers;

import com.codecool.exceptions.DataReaderException;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoogleSheetReaderTest {

    private static GoogleSheetReader googleSheetReader;

    @BeforeAll
    static void init() throws DataReaderException {
        googleSheetReader = new GoogleSheetReader("Arkusz1");
    }

    @Test
    void getHeader() throws DataReaderException {
        List<String> header = googleSheetReader.getHeader();

        System.out.println(header);
    }

    @Test
    void getDataStream() throws DataReaderException {
        googleSheetReader.getDataStream().forEach(System.out::println);
    }
}