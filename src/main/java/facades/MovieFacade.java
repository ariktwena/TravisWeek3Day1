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
    private MovieFacade() {
    }

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

    public MovieDTO createFromMovieDTO(MovieDTO movieDTO) {
        Movie movie = new Movie(movieDTO.getTitleDTO(), movieDTO.getDescriptionDTO(), movieDTO.getPriceDTO(), movieDTO.getYearDTO(), movieDTO.getActorsDTO());
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

    public MovieDTO createMovie(Movie movie) {
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

    public MovieDTO getMovieDTOById(long id) {
        EntityManager em = emf.createEntityManager();
        return new MovieDTO(em.find(Movie.class, id));
    }

    public List<MovieDTO> getMovieDTOByYear(int year) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Movie> movies = (List<Movie>) em.createQuery("SELECT movie FROM Movie movie WHERE movie.year = :year").setParameter("year", year).getResultList();
            return MovieDTO.convertMovieListToDTO(movies);
        } finally {
            em.close();
        }
    }

    public List<MovieDTO> getMovieDTOByPrice(double price) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Movie> movies = (List<Movie>) em.createQuery("SELECT movie FROM Movie movie WHERE movie.price = :price").setParameter("price", price).getResultList();
            return MovieDTO.convertMovieListToDTO(movies);
        } finally {
            em.close();
        }
    }

    //TODO Remove/Change this before use
    public long getMovieCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long movieCount = (long) em.createQuery("SELECT COUNT(movie) FROM Movie movie").getSingleResult();
            return movieCount;
        } finally {
            em.close();
        }
    }

    public List<MovieDTO> getAllMovieDTOs() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Movie> query = em.createQuery("SELECT movie FROM Movie movie", Movie.class);
        List<Movie> movies = query.getResultList();
        return MovieDTO.convertMovieListToDTO(movies);
    }

    public List<Movie> getAllMovies() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Movie> query = em.createQuery("SELECT movie FROM Movie movie", Movie.class);
        List<Movie> movies = query.getResultList();
        return movies;
    }

    public void createDB() {
        Movie movie1 = new Movie("Title1", "Description1", 34, 1980, new String[]{"John", "Jim", "Simon"}, "Private message1");
        Movie movie2 = new Movie("Title2", "Description2", 45, 1999, new String[]{"Sam", "Bill", "Jo"}, "Private message2");
        Movie movie3 = new Movie("Title3", "Description3", 14, 2000, new String[]{"Aaron", "Mike", "Bob"}, "Private message3");
        Movie movie4 = new Movie("Title4", "Description4", 45, 1980, new String[]{"Herry", "Henry", "Billy"}, "Private message4");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(movie1);
            em.persist(movie2);
            em.persist(movie3);
            em.persist(movie4);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        MovieFacade facade = getMovieFacade(emf);
        facade.getAllMovieDTOs().forEach(dto -> System.out.println(dto));
    }

}
