package com.codecool.helpers.readers;

import com.codecool.exceptions.DataReaderException;
import com.codecool.google.SheetsServiceUtil;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GoogleSheetReader implements DataReader {

    private static final String SPREADSHEET_ID = "1B2s_Wv4t85yenqO9pnG-BlBrRI2O4VLMkpQFmfYko68";
    private static Sheets sheetsService;
    private static ValueRange valueRange;

    public GoogleSheetReader() throws DataReaderException {
        try {
            sheetsService = SheetsServiceUtil.getSheetsService();
            valueRange = initValueRange();
        } catch (IOException | GeneralSecurityException e) {
            throw new DataReaderException("Cannot get header: " + e.getMessage());
        }
    }

    @Override
    public List<String> getHeader() throws DataReaderException {
        return valueRange
                .getValues()
                .get(0)
                .stream()
                .map(o -> o.toString())
                .collect(Collectors.toList());
    }

    @Override
    public Stream<String> getDataStream() throws DataReaderException {
        return valueRange
                .getValues()
                .stream()
                .skip(1)
                .map(list -> list.toString())
                .map(str -> str.substring(1,str.length()-1));
    }

    private ValueRange initValueRange() throws IOException {
        return sheetsService.spreadsheets().values()
                .batchGet(SPREADSHEET_ID)
                .setRanges(Arrays.asList("Arkusz1"))
                .execute()
                .getValueRanges()
                .get(0);
    }
}
