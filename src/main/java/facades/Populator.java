/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.MovieDTO;
import entities.Movie;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        MovieFacade facade = MovieFacade.getMovieFacade(emf);
        facade.createMovie(new Movie("Title1", "Description1", 34, 1980, new String[] {"John", "Jim", "Simon"}, "Private message1"));
        facade.createMovie(new Movie("Title2", "Description2", 45, 1999, new String[] {"Sam", "Bill", "Jo"}, "Private message2"));
        facade.createMovie(new Movie("Title3", "Description3", 14, 2000, new String[] {"Aaron", "Mike", "Bob"}, "Private message3"));
        facade.createMovie(new Movie("Title4", "Description4", 45, 1980, new String[] {"Herry", "Henry", "Billy"}, "Private message4"));
    }
    
    public static void main(String[] args) {
        populate();
    }
}
