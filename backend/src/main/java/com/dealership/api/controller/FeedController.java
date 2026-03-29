package com.dealership.api.controller;

import com.dealership.api.service.FacebookFeedGenerator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Public feed endpoints.
 *
 * GET /api/feeds/facebook-marketplace.xml — Facebook / Meta Catalog XML feed
 * GET /api/feeds/products.xml             — alias used by some Meta integrations
 */
@RestController
@RequestMapping("/api/feeds")
public class FeedController {

    private final FacebookFeedGenerator facebookFeedGenerator;

    public FeedController(FacebookFeedGenerator facebookFeedGenerator) {
        this.facebookFeedGenerator = facebookFeedGenerator;
    }

    @GetMapping(path = "/facebook-marketplace.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> facebookMarketplaceFeed() {
        return buildXmlResponse();
    }

    /** Alias: GET /api/feeds/products.xml */
    @GetMapping(path = "/products.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> productsFeed() {
        return buildXmlResponse();
    }

    private ResponseEntity<String> buildXmlResponse() {
        String xml = facebookFeedGenerator.generateFeed();
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_XML)
            .header("Cache-Control", "public, max-age=3600")
            .body(xml);
    }
}
