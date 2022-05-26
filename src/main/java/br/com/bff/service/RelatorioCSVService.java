package br.com.bff.service;

import br.com.bff.model.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RelatorioCSVService {

    public void generateReportUsuarioToCSV(HttpServletResponse response, List<Usuario> usuarios, List<String> colunas) {
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

            for (Usuario usuario : usuarios) {
                csvWriter.write(usuario, nameMapping);
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

        if (colunas.contains("Todas as colunas")){
            return new String[]{"id", "nome", "cpf", "email", "telefone", "dataAtualizacao", "dataCadastro"};
        }else {
            colunas.remove("Todas as colunas");
            return colunas.toArray(new String[0]);
        }
    }
}

