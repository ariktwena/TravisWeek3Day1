package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import dtos.MovieDTO;
import entities.Movie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Assertions;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class MovieResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Movie r1, r2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        r1 = new Movie("TitleTest1", "Des1", 34, 1999, new String[]{"Actor1", "Actor2", "Actor3"}, "Private message1");
        r2 = new Movie("TitleTest2", "Des2", 50, 2010, new String[]{"Actor4", "Actor5", "Actor6"}, "Private message2");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(r1);
            em.persist(r2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testIfJSON() {
        given().when().get("/movie/all").then().assertThat().contentType(ContentType.JSON);
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/movie/all").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testCount() throws Exception {
        given()
                .contentType("application/json")
                .get("/movie/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(2));
    }

    @Test
    public void testSeLogYear() throws Exception {
        given().log().all().when().get("/movie/year/{year}", 1999).then().log().body();
    }

    @Test
    public void testYearData1() {
        given()
                .when()
                .get("/movie/year/{year}", 1999)
                .then()
                .assertThat()
                .body("titleDTO[0]", equalToIgnoringCase("TitleTest1"));
    }

    @Test
    public void testYearData2() throws Exception {
        given()
                .contentType("application/json")
                .get("/movie/year/{year}", 1999).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("titleDTO[0]", equalToIgnoringCase("TitleTest1"));
    }

    @Test
    public void testActorArray1() {
        given()
                .when()
                .get("/movie/year/{year}", 1999)
                .then()
                .assertThat()
                .body("actorsDTO[0][0]", equalToIgnoringCase("Actor1"));
    }
    
    @Test
    public void testActorArray2() {
        given()
                .when()
                .get("/movie/year/{year}", 1999)
                .then()
                .assertThat()
                .body("actorsDTO[0][0]", equalToIgnoringCase("Actor1"), 
                        "actorsDTO[0][1]", equalToIgnoringCase("Actor2"), 
                        "actorsDTO[0][2]", equalToIgnoringCase("Actor3"));
    }

    @Test
    public void testSeLog() throws Exception {
        given().log().all().when().get("/movie/all").then().log().body();
    }

    @Test
    public void testWithGson() {
        List<MovieDTO> expected = new ArrayList<>();
        expected.add(new MovieDTO(r1));

        String json = given().when().get("/movie/year/{year}", 1999).body().print();

        MovieDTO[] mov = GSON.fromJson(json,MovieDTO[].class);

        List<MovieDTO> movs = Arrays.asList(mov.clone());

        Assertions.assertIterableEquals(expected, movs);
    }
}
