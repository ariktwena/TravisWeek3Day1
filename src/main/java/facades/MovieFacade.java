package facades;

import dtos.MovieDTO;
import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private MovieFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public MovieDTO createFromMovieDTO(MovieDTO movieDTO){
        Movie movie = new Movie(movieDTO.getTitleDTO(), movieDTO.getDescriptionDTO());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new MovieDTO(movie);
    }
    
    public MovieDTO createMovie(Movie movie){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new MovieDTO(movie);
    }
    
    public MovieDTO getMovieDTOById(long id){
        EntityManager em = emf.createEntityManager();
        return new MovieDTO(em.find(Movie.class, id));
    }
    
    public MovieDTO getMovieDTOByYear(int year){
        EntityManager em = emf.createEntityManager();
        try{
            Movie movie = (Movie)em.createQuery("SELECT movie FROM Movie movie WHERE movie.year = :year").setParameter("year", year).getSingleResult();
            return new MovieDTO(movie);
        }finally{  
            em.close();
        }
    }
    
    //TODO Remove/Change this before use
    public long getMovieCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long movieCount = (long)em.createQuery("SELECT COUNT(movie) FROM Movie movie").getSingleResult();
            return movieCount;
        }finally{  
            em.close();
        }
    }
    
    public List<MovieDTO> getAllMovieDTOs(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Movie> query = em.createQuery("SELECT movie FROM Movie movie", Movie.class);
        List<Movie> movies = query.getResultList();
        return MovieDTO.convertMovieListToDTO(movies);
    }
    
    public List<Movie> getAllMovies(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Movie> query = em.createQuery("SELECT movie FROM Movie movie", Movie.class);
        List<Movie> movies = query.getResultList();
        return movies;
    }
    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        MovieFacade facade = getMovieFacade(emf);
        facade.getAllMovieDTOs().forEach(dto->System.out.println(dto));
    }

}
