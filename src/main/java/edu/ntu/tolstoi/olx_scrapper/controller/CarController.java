package edu.ntu.tolstoi.olx_scrapper.controller;

import edu.ntu.tolstoi.olx_scrapper.service.CarScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarController {

    private final CarScraperService scraperService;

    @Autowired
    public CarController(CarScraperService scraperService) {
        this.scraperService = scraperService;
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
} 