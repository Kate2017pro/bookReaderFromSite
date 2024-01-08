package dto;

import lombok.Data;

@Data
public class BookCommonInfo { //todo заменить передачу через мапу инфы о книжку на эту дто
    private String annotation;
    private String author;
    private String title;
    private String image;
    private String srcUrl;
}
