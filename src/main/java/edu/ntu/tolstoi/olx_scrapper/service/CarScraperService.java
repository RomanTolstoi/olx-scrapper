package edu.ntu.tolstoi.olx_scrapper.service;
import edu.ntu.tolstoi.olx_scrapper.model.Car;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarScraperService {


    @Value("https://www.olx.ua/")
    private String baseUrl;   
    
    @Value("https://www.olx.ua/uk/transport/legkovye-avtomobili/")
    private String carsListUrl;

    private List<Car> cachedCars = new ArrayList<>();

    public List<Car> getCars() {
        return cachedCars;
    }

    public void scrapeNewCars(String brend) {
        List<Car> newCars = scrapeOlxCars(brend);
        
        cachedCars = newCars;

    }
    
    public List<Car> scrapeOlxCars(String brend) {
        List<Car> allCars = new ArrayList<>();

            try {
                
                Document doc = Jsoup.connect(carsListUrl + brend).get();
                
                Elements carListings = doc.select("div[data-cy='l-card']");
                
                carListings.stream().forEach(listing -> {

                    String title = listing.select("h6").text();
                    String price = listing.select("p[data-testid='ad-price']").text();
                    String details = listing.select("p[data-testid='location-date']").text();
                    String url_link = baseUrl + listing.select("a").attr("href");;
                    String imageUrl = listing.select("img").attr("src");
                                        
                    allCars.add(new Car(title, price, details, url_link, imageUrl));
                });
                
            } catch (IOException e) {
                System.err.println("Error scraping: " + e.getMessage());
            }
        
        return allCars;
    }
} 