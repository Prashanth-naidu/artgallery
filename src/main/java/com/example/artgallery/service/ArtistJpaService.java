package com.example.artgallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import com.example.artgallery.model.*;
import com.example.artgallery.repository.*;

@Service
public class ArtistJpaService implements ArtistRepository {

    @Autowired
    private ArtistJpaRepository ar;

    @Autowired
    private GalleryJpaRepository gr;

    @Autowired
    private ArtJpaRepository artr;

    @Override
    public ArrayList<Artist> getArtists() {
        return (ArrayList<Artist>) ar.findAll();
    }

    @Override
    public Artist getArtistById(int artistId) {
        try {
            Artist artist = ar.findById(artistId).get();
            return artist;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Artist addArtist(Artist artist) {
        List<Integer> galleryIds = new ArrayList<>();
        for (Gallery gallery : artist.getGalleries()) {
            galleryIds.add(gallery.getGalleryId());
        }

        List<Gallery> galleries = gr.findAllById(galleryIds);

        if (galleries.size() != galleryIds.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        artist.setGalleries(galleries);
        return ar.save(artist);
    }

    @Override
    public Artist updateArtist(int artistId, Artist artist) {
        try {
            Artist newArtist = ar.findById(artistId).get();

            if (artist.getArtistName() != null) {
                newArtist.setArtistName(artist.getArtistName());
            }
            if (artist.getGenre() != null) {
                newArtist.setGenre(artist.getGenre());
            }
            if (artist.getGalleries() != null) {
                List<Integer> galleryIds = new ArrayList<>();
                for (Gallery gallery : artist.getGalleries()) {
                    galleryIds.add(gallery.getGalleryId());
                }

                List<Gallery> galleries = gr.findAllById(galleryIds);
                if (galleries.size() != galleryIds.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                newArtist.setGalleries(galleries);
            }
            return ar.save(newArtist);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteArtist(int artistId) {
        try {
            ar.deleteById(artistId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Art> getArtistArts(int artistId) {
        try {
            Artist artist = ar.findById(artistId).get();
            return artr.findByArtist(artist);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Gallery> getArtistGalleries(int artistId) {
        try {
            Artist artist = ar.findById(artistId).get();
            return artist.getGalleries();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}