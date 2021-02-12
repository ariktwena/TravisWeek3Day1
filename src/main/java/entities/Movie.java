package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "movies")
@NamedQuery(name = "Movie.deleteAllRows", query = "DELETE from Movie")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    public Movie() {
    }  
    
    // TODO, delete this class, or rename to an Entity class that makes sense for what you are about to do
    // Delete EVERYTHING below if you decide to use this class, it's dummy data used for the initial demo
    @Column(name = "title", length = 175, nullable = false, unique = false)
    private String title;
    @Column(name = "description", length = 175, nullable = false, unique = false)
    private String description;
    @Column(name = "price", length = 25, nullable = false, unique = false)
    private double price;
    @Column(name = "year", length = 5, nullable = false, unique = false)
    private int year;

    public Movie(Long id, String title, String description, double price, int year) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.year = year;
    }

    public Movie(String title, String description, double price, int year) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.year = year;
    }

    public Movie(String title, String description) {
        this.title = title;
        this.description = description;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Movie{" + "id=" + id + ", title=" + title + ", description=" + description + ", price=" + price + ", year=" + year + '}';
    }
    
}
