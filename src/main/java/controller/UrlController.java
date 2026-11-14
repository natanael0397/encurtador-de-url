package controller;

import model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.UrlService;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Url")
@CrossOrigin(origins = "*")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("Url");
        if (originalUrl == null || originalUrl.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        String shortCode = urlService.shortenUrl(originalUrl);
        String fullShortUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/Url/{shortUrl}")
                .buildAndExpand(shortCode)
                .toUriString();

        Map<String, String> response = new HashMap<>();
        response.put("Url", fullShortUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Object> redirectToOriginalUrl(@PathVariable String shortUrl) {
        Optional<Url> urlOptional = urlService.getOriginalUrl(shortUrl);
        if (urlOptional.isPresent()) {
            Url url = urlOptional.get();
            return ResponseEntity.status(302).location(URI.create(url.getOriginalUrl())).build();
        }
        return ResponseEntity.notFound().build();
    }
}