package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MovieDTO;
import utils.EMF_Creator;
import facades.MovieFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("movie")
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final MovieFacade FACADE =  MovieFacade.getMovieFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
     
    @Path("init")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String createDB() {
        FACADE.createDB();
        return "{\"msg\":\"DB created :)\"}";
    }
    
    @Path("message")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getMovieCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }
    
    @Path("alldto")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getallMovieDTOs() {
//        List<MovieDTO> allMovieDTO = FACADE.getAllMovieDTOs();
//        String JSONString = GSON.toJson(allMovieDTO);
//        return JSONString;
        return GSON.toJson(FACADE.getAllMovieDTOs());
    }
    
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getallMovies() {
        return GSON.toJson(FACADE.getAllMovies());
    }
    
    @Path("id/{id}") //VIGTIGT
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieById(@PathParam("id") long id) {
        MovieDTO movieDTO = FACADE.getMovieDTOById(id);
        return new Gson().toJson(movieDTO);
    }
    
    @Path("year/{year}") //VIGTIGT
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieByYear(@PathParam("year") int year) {
        List<MovieDTO> movieDTOs = FACADE.getMovieDTOByYear(year);
        return new Gson().toJson(movieDTOs);
    }
    
    @Path("price/{price}") //VIGTIGT
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieByPrice(@PathParam("price") long price) {
        List<MovieDTO> movieDTOs = FACADE.getMovieDTOByPrice(price);
        return new Gson().toJson(movieDTOs);
    }
}
