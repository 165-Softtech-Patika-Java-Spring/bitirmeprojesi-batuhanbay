package com.softtechbootcamp.bitirme.app.rpt.service;

import com.softtechbootcamp.bitirme.app.gen.exceptions.BusinessExceptions;
import com.softtechbootcamp.bitirme.app.rpt.dto.ExportReportDto;
import com.softtechbootcamp.bitirme.app.rpt.enums.ExportReportErrorMessage;
import com.softtechbootcamp.bitirme.app.set.service.SetSettingsService;
import com.softtechbootcamp.bitirme.app.utils.MySQLConnUtils;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class ExportReportService {
    private SetSettingsService setSettingsService;

    public void generateProductTypeDetailReport(ExportReportDto exportReportDto) throws FileNotFoundException, SQLException {

        // Check if the report to be generated has the file directory to be saved in
        String savedDirectoryPath = setSettingsService.findByKey("savedDirectoryPath").getValue();
        Path path = Paths.get(savedDirectoryPath);

        // Check with the directory obtained from the database whether there is a file to be saved in the certificate to be produced
        if (!Files.isDirectory(path)) {
            // If there is no file in the directory taken from the database, create this file in the given directory.
            File file = new File(savedDirectoryPath);
            // Create a file in the directory retrieved from the database and check if the operation is successful
            if (!file.mkdir()) {
                throw new BusinessExceptions(ExportReportErrorMessage.UNEXPECTED_ERROR_DURING_CREATE_FILE);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(exportReportDto.getName())
                .append("_")
                .append(new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()))
                .append(".pdf");

        String fileNamePdf = stringBuilder.toString();

        String patikaLogoPath = ResourceUtils.getFile("classpath:images/logo_patika.png").getAbsolutePath();
        String softtechLogoPath = ResourceUtils.getFile("classpath:images/logo_softtech.png").getAbsolutePath();


        if (patikaLogoPath.isEmpty() || softtechLogoPath.isEmpty()) {
            throw new BusinessExceptions(ExportReportErrorMessage.COULD_NOT_FIND_LOGO_PATH);
        }
        // Get mysql connection cause of query which is used for getting product type detail in jrxml.
        Connection conn = MySQLConnUtils.getMySQLConnection();

        try {
            File file = ResourceUtils.getFile("classpath:product_type_detail.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("product_type_id",  exportReportDto.getId().intValue());
            parameters.put("patika_logo_path", patikaLogoPath);
            parameters.put("softtech_logo_path", softtechLogoPath);

            JasperPrint jPrint =  JasperFillManager.fillReport(jasperReport, parameters, conn);
            JasperExportManager.exportReportToPdfFile(jPrint, savedDirectoryPath + fileNamePdf);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BusinessExceptions(ExportReportErrorMessage.UNEXPECTED_ERROR_DURING_GENERATING_REPORT);
        }
    }
}
