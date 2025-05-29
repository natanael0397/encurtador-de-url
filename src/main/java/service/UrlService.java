package service;

import model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import repository.UrlRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequestMapping
public class UrlService {
    @Autowired
    public UrlRepository urlRepository;

    public String shortenUrl(String originalUrl){
        String shortUrl = generateShortUrl();
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        url.setExpirationDate(LocalDateTime.now().plusDays(30));//prazo de expiração de 30 dias
        urlRepository.save(url);
        return shortUrl;
    }


    public Optional<Url> getOriginalUrl(String shortUrl){
        Optional<Url> urlOptional = urlRepository.findByShortUrl(shortUrl);
        if(urlOptional.isPresent()){
            Url url = urlOptional.get();
            if(url.getExpirationDate().isAfter(LocalDateTime.now())){
                return Optional.of(url);
            }else{
                urlRepository.delete(url);
            }
        }
        return Optional.empty();
    }

    public String generateShortUrl(){
        String characters = "ABCDEFGHIJLMNOPQRSTUVXZWYabcdefghijlmnopqrstuvyxzwy";
        StringBuilder shortUrl = new StringBuilder();
        Random random = new Random();
        int lenght = 5 + random.nextInt(6);
        for(int i = 0; i < lenght; i++){
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortUrl.toString();
    }
}
