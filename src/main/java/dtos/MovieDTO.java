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
    private double priceDTO;
    private int yearDTO;
    private String[] actorsDTO;
    

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.titleDTO = movie.getTitle();
        this.descriptionDTO = movie.getDescription();
        this.priceDTO = movie.getPrice();
        this.yearDTO = movie.getYear();
        this.actorsDTO = movie.getActors();
    }

    public MovieDTO(String titleDTO, String descriptionDTO, double priceDTO, int yearDTO, String[] actorsDTO) {
        this.titleDTO = titleDTO;
        this.descriptionDTO = descriptionDTO;
        this.priceDTO = priceDTO;
        this.yearDTO = yearDTO;
        this.actorsDTO = actorsDTO;
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

    public double getPriceDTO() {
        return priceDTO;
    }

    public void setPriceDTO(double priceDTO) {
        this.priceDTO = priceDTO;
    }

    public int getYearDTO() {
        return yearDTO;
    }

    public void setYearDTO(int yearDTO) {
        this.yearDTO = yearDTO;
    }

    public String[] getActorsDTO() {
        return actorsDTO;
    }

    public void setActorsDTO(String[] actorsDTO) {
        this.actorsDTO = actorsDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieDTO)) {
            return false;
        }

        MovieDTO movieDTO = (MovieDTO) o;

        if (yearDTO != movieDTO.yearDTO) {
            return false;
        }
        return titleDTO != null ? titleDTO.equals(movieDTO.titleDTO) : movieDTO.titleDTO == null;
    }

    @Override
    public int hashCode() {
        int result = titleDTO != null ? titleDTO.hashCode() : 0;
        result = 31 * result + yearDTO;
        return result;
    }

    @Override
    public String toString() {
        return "MovieDTO{" + "id=" + id + ", titleDTO=" + titleDTO + ", descriptionDTO=" + descriptionDTO + ", priceDTO=" + priceDTO + ", yearDTO=" + yearDTO + ", actorsDTO=" + actorsDTO + '}';
    }

}
