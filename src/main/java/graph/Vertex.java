package graph;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

    /**
     * Label des Knoten
     */
    private String label;

    /**
     * Identifier des Knoten
     */
    private String identifier;

    /**
     * Id des Knoten
     */
    private Integer id;

    /**
     * Liste der ausgehenden bzw eingehenden Kanten
     */
    private List<Edge> incomingEdges = new ArrayList<>();

    /**
     * Liste der ausgehenden Kanten
     */
    private List<Edge> outgoingEdges = new ArrayList<>();

    /**
     * Erstellt einen Knoten
     *
     * @param label      Label des Knoten
     * @param identifier Identifier des Knoten
     * @param id         Id des Knoten
     */
    public Vertex(String label, String identifier, Integer id) {
        this.label = label;
        this.identifier = identifier;
        this.id = id;
    }

    /**
     * Ausgabe des Knotens als String
     * @return Label und Identifier
     */
    @Override
    public String toString() {
        return "" + label + ":" + identifier;
    }

    /**
     * Fügt eine Kante zum Knoten hinzu
     * @param vertex Zu welchem Knoten soll die Kante von diesem Knoten aus gehen?
     * @param relationLabel Label der Beziehung
     */
    public void addEdge(Vertex vertex, String relationLabel) {
        Edge edge = new Edge(this, vertex, relationLabel);
        outgoingEdges.add(edge);
        vertex.incomingEdges.add(edge);
    }

    /**
     * Gibt des Label des Knoten zurück
     * @return Knoten
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gibt den Identifier des Knoten zurück
     * @return Identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Gibt die eingehenden Kanten zurück
     * @return Alle Kanten die in den Knoten gehen
     */
    public List<Edge> getIncomingEdges() {
        return incomingEdges;
    }

    /**
     * Gibt alle ausgehenden Kanten zurück
     * @return Alle Kanten, die von diesem Knoten ausgehen
     */
    public List<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }

    /**
     * Gibt die ID des Knoten zurück
     * @return Id des Knoten
     */
    public Integer getId() {
        return id;
    }

    /**
     * Vergleicht Neo4J Knoten mit Vertex
     * @param b Knoten, mit dem verglichen werden soll
     * @return true wenn gleich, false wenn nicht gleich
     */
    Boolean equals(Node b) {
        for (Label c : b.getLabels()
                ) {
            if (c.name().equals(this.getLabel())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Baut ein Feld in dem die einzelnen Knoten und Kanten eingetragen werden.
     * Gibt auf der Konsole einen Knoten mit allen verbundenen Knoten graphisch aus
     */
    public void printVertex() { //TODO Besser programmieren
        String[][] field = new String[5][5];
        field[2][2] = "[" + this.identifier.charAt(0) + "]";
        String right = " → ";
        String left = " ← ";
        String up = " ↑ ";
        String down = " ↓ ";

        //geht durch die Liste der ausgehenden Kanten
        for (int x = 0; x < this.outgoingEdges.size(); x++) {
            if (this.outgoingEdges.size() == 0) break;
            if (field[2][3] == null) {
                field[2][3] = right;
                field[2][4] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][3] == null) {
                field[3][3] = " ↘ ";
                field[4][4] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][2] == null) {
                field[3][2] = down;
                field[4][2] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][3] == null) {
                field[1][3] = " ↗ ";
                field[0][4] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[2][1] == null) {
                field[2][1] = left;
                field[2][0] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][1] == null) {
                field[1][1] = " ↖ ";
                field[0][0] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][2] == null) {
                field[1][2] = up;
                field[0][2] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][1] == null) {
                field[3][1] = " ↙ ";
                field[4][0] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
            }
        }


        //----------------------------------alle eingehenden Kanten-----------------------------------
        for (int x = 0; x < this.incomingEdges.size(); x++) {
            if (this.incomingEdges.size() == 0) break;
            if (field[2][3] == null) {
                field[2][3] = left;
                field[2][4] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][3] == null) {
                field[3][3] = " ↖ ";
                field[4][4] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][2] == null) {
                field[3][2] = up;
                field[4][2] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][3] == null) {
                field[1][3] = " ↙ ";
                field[0][4] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[2][1] == null) {
                field[2][1] = right;
                field[2][0] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][1] == null) {
                field[1][1] = " ↘ ";
                field[0][0] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][2] == null) {
                field[1][2] = down;
                field[0][2] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][1] == null) {
                field[3][1] = " ↗ ";
                field[4][0] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
            }
        }
        printArray(field);
    }


    /**
     * Gibt ein quadratisches Array auf der Konsole aus
     * @param array Quadratisches Array ausgeben
     */
    private void printArray(String[][] array) { //TODO was soll das sein und wofür bitte?
        for (String[] single_array : array) {
            for (int y = 0; y < array.length; y++) {
                try {
                    if (single_array[y] != null) {
                        System.out.print(single_array[y]);
                    } else {
                        System.out.print("   ");
                    }
                } catch (Exception e) {
                    System.out.println("Größe des Array muss quadratisch sein");
                    break;
                }
            }
            System.out.println();
        }
    }
}