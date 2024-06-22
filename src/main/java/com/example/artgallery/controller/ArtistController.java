package com.example.artgallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import com.example.artgallery.model.*;
import com.example.artgallery.service.ArtistJpaService;

@RestController
public class ArtistController {

    @Autowired
    private ArtistJpaService as;

    @GetMapping("/galleries/artists")
    public ArrayList<Artist> getArtists() {
        return as.getArtists();
    }

    @GetMapping("/galleries/artists/{artistId}")
    public Artist getArtistById(@PathVariable("artistId") int artistId) {
        return as.getArtistById(artistId);
    }

    @PostMapping("/galleries/artists")
    public Artist addArtist(@RequestBody Artist artist) {
        return as.addArtist(artist);
    }

    @PutMapping("/galleries/artists/{artistId}")
    public Artist updateArtist(@PathVariable("artistId") int artistId, @RequestBody Artist artist) {
        return as.updateArtist(artistId, artist);
    }

    @DeleteMapping("/galleries/artists/{artistId}")
    public void deleteArtist(@PathVariable("artistId") int artistId) {
        as.deleteArtist(artistId);
    }

    @GetMapping("/artists/{artistId}/arts")
    public List<Art> getArtistArts(@PathVariable("artistId") int artistId) {
        return as.getArtistArts(artistId);
    }

    @GetMapping("/artists/{artistId}/galleries")
    public List<Gallery> getArtistGalleries(@PathVariable("artistId") int artistId) {
        return as.getArtistGalleries(artistId);
    }
}