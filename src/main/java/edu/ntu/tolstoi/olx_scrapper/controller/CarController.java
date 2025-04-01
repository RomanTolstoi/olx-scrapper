package edu.ntu.tolstoi.olx_scrapper.controller;

import edu.ntu.tolstoi.olx_scrapper.service.CarScraperService;
import edu.ntu.tolstoi.olx_scrapper.service.XlsExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class CarController {

    private final CarScraperService scraperService;
    private final XlsExportService xlsExportService;

    @Autowired
    public CarController(CarScraperService scraperService, XlsExportService xlsExportService) {
        this.scraperService = scraperService;
        this.xlsExportService = xlsExportService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("cars", scraperService.getCars());
        return "index";
    }

    @PostMapping("/scrape")
    public String scrape(@RequestParam() String brend, Model model) {
        scraperService.scrapeNewCars(brend);
        model.addAttribute("cars", scraperService.getCars());
        return "index";
    }
    
    @GetMapping("/download/xls")
    public ResponseEntity<byte[]> downloadXls() {
        try {
            byte[] xlsData = xlsExportService.exportToXls(scraperService.getCars());
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cars.xls")
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .contentLength(xlsData.length)
                    .body(xlsData);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 