package edu.ntu.tolstoi.olx_scrapper.service;

import edu.ntu.tolstoi.olx_scrapper.model.Car;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class XlsExportService {

    public byte[] exportToXls(List<Car> cars) throws IOException {
        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Cars");
            
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Title");
            headerRow.createCell(1).setCellValue("Price");
            headerRow.createCell(2).setCellValue("Details");
            headerRow.createCell(3).setCellValue("URL");
            headerRow.createCell(4).setCellValue("Image URL");
            
            int rowNum = 1;
            for (Car car : cars) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(car.getTitle());
                row.createCell(1).setCellValue(car.getPrice());
                row.createCell(2).setCellValue(car.getDetails());
                row.createCell(3).setCellValue(car.getUrl());
                row.createCell(4).setCellValue(car.getImageUrl());
            }
            
            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
} 