package tests;

import graph.Edge;
import graph.Graph;
import org.neo4j.driver.v1.*;
import procedure.QueryBuilder;

import java.util.List;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * In this test some simple methods are called on a locally hosted database
 */
@SuppressWarnings("unused")
public class NeoTest {

    /**
     * Test method to start a new query on a locally hosted database
     *
     * @param args program parameters
     */
    public static void main(String[] args) {
        QueryBuilder qb = new QueryBuilder("MATCH (tom:Person { number:'10'},{name:'hans'})-[:DIRECTED {name :'10'}]->(m:Movie {director: 'Franz'})-[:DIRECTED {name :'333'}]->(m:Movie {director: 'Franz'}) RETURN tom");
        Graph graph = qb.build();
        List<Edge> list = graph.getEdges();
        for (Edge v: list
                ) {
            System.out.println(v.getLabel());
            System.out.println(v.getProperties().values());
        }

    }

    /**
     * Display of data sets from a locally hosted database
     */
    private static void createAndShow() {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("trump", "password"));
        Session session = driver.session();

        session.run("CREATE (a: Person {name: {name}, title: {title}})", parameters("name", "Arthur", "title", "King"));

        StatementResult result = session.run("MATCH (a:Person) WHERE a.name = {name} " +
                        "RETURN a.name AS name, a.title AS title",
                parameters("name", "Arthur"));
        while (result.hasNext()) {
            Record record = result.next();
            System.out.println(record.get("title").asString() + " " + record.get("name").asString());
        }
        session.close();
        driver.close();
    }
}
