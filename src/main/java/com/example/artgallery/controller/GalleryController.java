package com.example.artgallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.example.artgallery.model.*;
import com.example.artgallery.service.GalleryJpaService;

@RestController
public class GalleryController {

    @Autowired
    private GalleryJpaService gs;

    @GetMapping("/galleries")
    public ArrayList<Gallery> getGalleries() {
        return gs.getGalleries();
    }

    @GetMapping("/galleries/{galleryId}")
    public Gallery getGalleryById(@PathVariable("galleryId") int galleryId) {
        return gs.getGalleryById(galleryId);
    }

    @PostMapping("/galleries")
    public Gallery addGallery(@RequestBody Gallery gallery) {
        return gs.addGallery(gallery);
    }

    @PutMapping("/galleries/{galleryId}")
    public Gallery updateGallery(@PathVariable("galleryId") int galleryId, @RequestBody Gallery gallery) {
        return gs.updateGallery(galleryId, gallery);
    }

    @DeleteMapping("/galleries/{galleryId}")
    public void deleteGallery(@PathVariable("galleryId") int galleryId) {
        gs.deleteGallery(galleryId);
    }

    @GetMapping("/galleries/{galleryId}/artists")
    public List<Artist> getGalleryArtists(@PathVariable("galleryId") int galleryId) {
        return gs.getGalleryArtists(galleryId);
    }
}
