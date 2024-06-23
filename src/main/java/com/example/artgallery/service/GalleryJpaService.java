package com.example.artgallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import com.example.artgallery.model.*;
import com.example.artgallery.repository.*;

@Service
public class GalleryJpaService implements GalleryRepository {

    @Autowired
    private GalleryJpaRepository gr;

    @Autowired
    private ArtistJpaRepository ar;

    public ArrayList<Gallery> getGalleries() {
        return (ArrayList<Gallery>) gr.findAll();
    }

    public Gallery getGalleryById(int galleryId) {
        try {
            Gallery gallery = gr.findById(galleryId).get();
            return gallery;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Gallery addGallery(Gallery gallery) {
        List<Integer> artistIds = new ArrayList<>();
        for (Artist artist : gallery.getArtists()) {
            artistIds.add(artist.getArtistId());
        }
        List<Artist> artists = ar.findAllById(artistIds);
        gallery.setArtists(artists);

        for (Artist artist : artists) {
            artist.getGalleries().add(gallery);
        }
        Gallery savedGallery = gr.save(gallery);
        ar.saveAll(artists);

        return savedGallery;
    }

    public Gallery updateGallery(int galleryId, Gallery gallery) {
        try {
            Gallery newGallery = gr.findById(galleryId).get();
            if (gallery.getGalleryName() != null) {
                newGallery.setGalleryName(gallery.getGalleryName());
            }
            if (gallery.getLocation() != null) {
                newGallery.setLocation(gallery.getLocation());
            }
            if (gallery.getArtists() != null) {
                List<Artist> artists = newGallery.getArtists();
                for (Artist artist : artists) {
                    artist.getGalleries().remove(newGallery);
                }
                ar.saveAll(artists);
                List<Integer> newArtistIds = new ArrayList<>();
                for (Artist artist : gallery.getArtists()) {
                    newArtistIds.add(artist.getArtistId());
                }

                List<Artist> newArtists = ar.findAllById(newArtistIds);
                for (Artist artist : newArtists) {
                    artist.getGalleries().add(newGallery);
                }
                ar.saveAll(newArtists);
                newGallery.setArtists(newArtists);
            }
            return gr.save(newGallery);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteGallery(int galleryId) {
        try {
            Gallery gallery = gr.findById(galleryId).get();
            List<Artist> artists = gallery.getArtists();
            for (Artist artist : artists) {
                artist.getGalleries().remove(gallery);
            }
            ar.saveAll(artists);
            gr.deleteById(galleryId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public List<Artist> getGalleryArtists(int galleryId) {
        try {
            Gallery gallery = gr.findById(galleryId).get();
            return gallery.getArtists();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
