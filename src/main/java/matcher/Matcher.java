package matcher;

import graph.Graph;
import graph.Vertex;
import org.neo4j.graphdb.*;

import java.util.*;

public abstract class Matcher {
    GraphDatabaseService db;
    Graph graph;

    /**
     * Constructor um an die Datenbank zu kommen
     * @param database
     */
    public Matcher(GraphDatabaseService database, Graph graph) {
        this.db = database;
        this.graph = graph;
    }

    public Matcher() {
        this.db = null;
    }

    /**
     * Returns all predecessors of the given node.
     * @param node
     * @return
     */
    List<Node> previousNodes(Node node) {
        List<Node> result = new ArrayList<>();
        Iterable<Relationship> rel = node.getRelationships(Direction.INCOMING);
        for (Relationship r : rel
                ) {
            result.add(r.getStartNode());
        }
        return result;
    }

    /**
     * Returns all sucessors of the given node.
     * @param node
     * @return
     */
    List<Node> successingNodes(Node node) {
        List<Node> result = new ArrayList<>();

        Iterable<Relationship> rel = node.getRelationships(Direction.OUTGOING);
        for (Relationship r : rel
                ) {
            System.out.println(r.toString());
            result.add(r.getEndNode());
        }
        return result;
    }

    Boolean compare(Vertex a, Node b) {
        for (Label c : b.getLabels()
                ) {
            if (c.name().equals(a.getLabel())) {
                return true;
            }
        }
        return false;
    }

    Boolean compare(Node a, Node b) {
        return a.getLabels() == b.getLabels();
    }



    /**
     * Returns all nodes that are similar to the vertex from the query graph.
     * @param vertex
     * @return
     */
    List<Node> findeNodes(Vertex vertex) {


        Label lb =  Label.label(vertex.getIdentifier());
        System.out.println(lb.name());
        ResourceIterator<Node> iterator = db.findNodes(lb);
        List<Node> nodes = new ArrayList<>();
        while (iterator.hasNext()){
            try {
                nodes.add(iterator.next());
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return nodes;
    }

    public Set<Node> simulate() {
        Map<Integer, List<Node>> map = matchingAlgorithm();
        Set<Node> set = new HashSet<>();
        for (Map.Entry<Integer, List<Node>> entry : map.entrySet()) {
            set.addAll(entry.getValue());
        }
        return set;
    }

    public abstract Map<Integer, List<Node>> matchingAlgorithm();
}
