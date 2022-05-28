package br.com.bff.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

@Slf4j
public class FormatterStringToCPF extends CellProcessorAdaptor {

    public FormatterStringToCPF() {
    }

    public FormatterStringToCPF(CellProcessor next, String datePattern) {
        super(next);
    }

    public Object execute(Object value, CsvContext context) {
        validateInputNotNull(value, context);

        String cpfFormatado = null;
        try {
            cpfFormatado = value.toString().substring(0,3).concat(".").concat(value.toString().substring(3,6)).concat(".").concat(value.toString().substring(6,9)).concat("-").concat(value.toString().substring(9,11));
        } catch (Exception e) {
           log.error("Parse Error: ", e.getMessage());
        }

        if (!StringUtils.hasText(cpfFormatado))
            throw new SuperCsvCellProcessorException("CPF could not be parsed", context, this);

        return cpfFormatado;
    }

    private static void checkPreconditions(String dateFormat) {
        if (dateFormat == null) {
            throw new NullPointerException("dateFormat should not be null");
        }
    }
}
