package br.com.bff.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class FormatterLocalDateTimeToString extends CellProcessorAdaptor {

    private final String datePattern;

    public FormatterLocalDateTimeToString(String datePattern) {
        checkPreconditions(datePattern);
        this.datePattern = datePattern;
    }

    public FormatterLocalDateTimeToString(CellProcessor next, String datePattern) {
        super(next);
        checkPreconditions(datePattern);
        this.datePattern = datePattern;
    }

    public Object execute(Object value, CsvContext context) {
        validateInputNotNull(value, context);

        String dataFormatada = null;
        try {
            dataFormatada = LocalDateTime.parse(value.toString()).format(DateTimeFormatter.ofPattern(datePattern));
        } catch (Exception e) {
           log.error("Parse Error: ", e.getMessage());
        }

        if (!StringUtils.hasText(dataFormatada))
            throw new SuperCsvCellProcessorException("Date could not be parsed", context, this);

        return dataFormatada;
    }

    private static void checkPreconditions(String dateFormat) {
        if (dateFormat == null) {
            throw new NullPointerException("dateFormat should not be null");
        }
    }
}
