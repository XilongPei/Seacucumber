package tests;

import graph.Edge;
import graph.Graph;
import org.neo4j.driver.v1.*;
import procedure.QueryBuilder;

import java.util.List;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * In diesem Test werden einfache Methoden auf einer lokal gehosteten Datenbank aufgerufen
 */
@SuppressWarnings("unused")
public class NeoTest {

    /**
     * Testmethode zum Starten einer neuen Abfrage auf einer lokal gehosteten Datenbank
     *
     * @param args Programmparameter
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
     * Testanzeige von Datensätzen aus einer lokal gehosteten Datenbank
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
