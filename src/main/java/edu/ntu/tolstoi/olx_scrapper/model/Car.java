package edu.ntu.tolstoi.olx_scrapper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private String title;
    private String price;
    private String details;
    private String url;
    private String imageUrl;
}
