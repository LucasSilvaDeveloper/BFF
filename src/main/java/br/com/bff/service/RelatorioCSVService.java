package br.com.bff.service;

import br.com.bff.config.FormatterLocalDateTimeToString;
import br.com.bff.config.FormatterStringToCPF;
import br.com.bff.model.Usuario;
import br.com.bff.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.Unique;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RelatorioCSVService {

    public void generateReportUsuarioToCSV(HttpServletResponse response, List<Usuario> usuarios, List<String> colunas, String formatoData) {
        ICsvBeanWriter csvWriter = null;

        try {
            csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        } catch (IOException e) {
            log.error(e.getMessage());
            return;
        }

        try {
            String fileName = "relatorio_usuario_data_".concat(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).concat("_horario_").concat(LocalTime.now().format(DateTimeFormatter.ofPattern("HH_mm_ss"))).concat(".csv");

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=" +fileName;

            response.setHeader(headerKey, headerValue);

//            String[] csvHeader = {"ID", "NOME", "CPF", "Email"};
//            csvWriter.writeHeader(csvHeader);

            String[] nameMapping = processColunas(colunas);

            //FIXME
            // proximo passo é fazer a formatação das datas de saida do arquivo csv

            final CellProcessor[] processors = getProcessors(nameMapping, formatoData);

            for (Usuario usuario : usuarios) {
                csvWriter.write(usuario, nameMapping, processors);
            }
            csvWriter.close();

        }catch (Exception ex){
            log.error(ex.getMessage());
        }finally {
            try {
                csvWriter.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    private String[] processColunas(List<String> colunas){

        if (colunas.contains(Constants.TODAS_COLUNAS)){
            return new String[]{Constants.ID, Constants.NOME, Constants.CPF, Constants.EMAIL, Constants.TELEFONE, Constants.DATA_ATUALIZACAO, Constants.DATA_CADASTRO};
        }else {
            colunas.remove(Constants.TODAS_COLUNAS);
            return colunas.toArray(new String[0]);
        }
    }

    private static CellProcessor[] getProcessors(String[] nameMapping, String formatoData){

        List<CellProcessor> cellProcessors = new ArrayList<>();

        for (String s : nameMapping) {

            switch (s){
                case Constants.ID:
                    cellProcessors.add(new Unique(new ParseLong()));
                    break;
                case Constants.NOME:
                    cellProcessors.add(new NotNull());
                    break;
                case Constants.CPF:
                    cellProcessors.add(new FormatterStringToCPF());
                    break;
                case Constants.EMAIL:
                case Constants.TELEFONE:
                    cellProcessors.add(new Optional());
                    break;
                case Constants.DATA_ATUALIZACAO:
                case Constants.DATA_CADASTRO:
                    cellProcessors.add(new FormatterLocalDateTimeToString(formatoData));
                    break;
            }
        }
        return cellProcessors.toArray(new CellProcessor[0]);
    }

}

