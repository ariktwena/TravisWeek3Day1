/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Movie;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author tha
 */
public class MovieDTO {
    private long id;
    private String titleDTO;
    private String descriptionDTO;

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.titleDTO = movie.getTitle();
        this.descriptionDTO = movie.getDescription();
    }

    public MovieDTO(long id, String titleDTO, String descriptionDTO) {
        this.id = id;
        this.titleDTO = titleDTO;
        this.descriptionDTO = descriptionDTO;
    }

    public MovieDTO(String titleDTO, String descriptionDTO) {
        this.titleDTO = titleDTO;
        this.descriptionDTO = descriptionDTO;
    }
    
    public static List<MovieDTO> convertMovieListToDTO(List<Movie> movies){
        List<MovieDTO> movieDTOs = movies.stream().map(currentMovie -> new MovieDTO(currentMovie)).collect(Collectors.toList());
        return movieDTOs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitleDTO() {
        return titleDTO;
    }

    public void setTitleDTO(String titleDTO) {
        this.titleDTO = titleDTO;
    }

    public String getDescriptionDTO() {
        return descriptionDTO;
    }

    public void setDescriptionDTO(String descriptionDTO) {
        this.descriptionDTO = descriptionDTO;
    }

    @Override
    public String toString() {
        return "MovieDTO{" + "id=" + id + ", titleDTO=" + titleDTO + ", descriptionDTO=" + descriptionDTO + '}';
    }
  
}
