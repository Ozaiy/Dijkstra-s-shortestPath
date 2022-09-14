package model;

import java.util.*;

public class TransportGraph {

    private int numberOfStations;
    private int numberOfConnections;
    private List<Station> stationList;
    private Map<String, Integer> stationIndices;

    private List<Integer>[] adjacencyLists;
    private Connection[][] connections;
    private ArrayList<Double[]> weightList;

    public TransportGraph (int size) {
        this.numberOfStations = size;
        stationList = new ArrayList<>(size);
        weightList = new ArrayList<>();
        stationIndices = new HashMap<>();
        connections = new Connection[size][size];
        adjacencyLists = (List<Integer>[]) new List[size];
        for (int vertex = 0; vertex < size; vertex++) {
            adjacencyLists[vertex] = new LinkedList<>();
        }
    }

    /**
     * @param vertex Station to be added to the stationList
     * The method also adds the station with it's index to the map stationIndices
     */
    public void addVertex(Station vertex) {
        // TODO

        stationList.add(vertex);
        for (int i = 0; i < stationList.size() ; i++) {

         stationIndices.put(stationList.get(i).getStationName(), i);

        }

    }

    public void addConnectionWeight(ArrayList<Double[]> weight){
        this.weightList.addAll(weight);
    }

    /**
     * Method to add an edge to a adjancencyList. The indexes of the vertices are used to define the edge.
     * Method also increments the number of edges, that is number of Connections.
     * The grap is bidirected, so edge(to, from) should also be added.
     * @param from
     * @param to
     */
    private void addEdge(int from, int to) {
        // TODO

        adjacencyLists[from].add(to);
        adjacencyLists[to].add(from);
        this.numberOfConnections++;

    }

    /**
     * Method to add an edge in the form of a connection between stations.
     * The method also adds the edge as an edge of indices by calling addEdge(int from, int to).
     * The method adds the connecion to the connections 2D-array.
     * The method also builds the reverse connection, Connection(To, From) and adds this to the connections 2D-array.
     * @param connection The edge as a connection between stations
     */
    public void addEdge(Connection connection) {
        // TODO

        int from = stationIndices.get(connection.getFrom().getStationName());
        int to = stationIndices.get(connection.getTo().getStationName());

        Connection reverserConnection = new Connection(connection.getTo(),connection.getFrom());
        reverserConnection.setLine(connection.getLine());
        reverserConnection.setWeight(connection.getWeight());

        connections[from][to] = connection;
        connections[to][from] = reverserConnection;

        addEdge(from,to);
    }

    public List<Integer> getAdjacentVertices(int index) {
        return adjacencyLists[index];
    }

    public Connection getConnection(int from, int to) {
        return connections[from][to];
    }

    public int getIndexOfStationByName(String stationName) {
        return stationIndices.get(stationName);
    }

    public Station getStation(int index) {
        return stationList.get(index);
    }

    public int getNumberOfStations() {
        return numberOfStations;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public int getNumberEdges() {
        return numberOfConnections;
    }

    public ArrayList<Double[]> getWeightList() { return weightList; }
    public int getNumberOfConnections() { return numberOfConnections; }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        resultString.append(String.format("Graph with %d vertices and %d edges: \n", numberOfStations, numberOfConnections));
        for (int indexVertex = 0; indexVertex < numberOfStations; indexVertex++) {
            resultString.append(stationList.get(indexVertex) + ": ");
            int loopsize = adjacencyLists[indexVertex].size() - 1;
            for (int indexAdjacent = 0; indexAdjacent < loopsize; indexAdjacent++) {
                resultString.append(stationList.get(adjacencyLists[indexVertex].get(indexAdjacent)).getStationName() + "-" );
            }
            resultString.append(stationList.get(adjacencyLists[indexVertex].get(loopsize)).getStationName() + "\n");
        }
        return resultString.toString();
    }

    /**
     * A Builder helper class to build a TransportGraph by adding lines and building sets of stations and connections from these lines.
     * Then build the graph from these sets.
     */
    public static class Builder {

        private Set<Station> stationSet;
        private List<Line> lineList;
        private Set<Connection> connectionSet;
        private ArrayList<Double[]> connectionWeights;


        public Builder() {
            lineList = new ArrayList<>();
            stationSet = new HashSet<>();
            connectionSet = new HashSet<>();
            connectionWeights = new ArrayList<>();

        }

        /**
         * Method to add a line to the list of lines and add stations to the line.
         * @param lineDefinition String array that defines the line. The array should start with the name of the line,
         * followed by the type of the line and the stations on the line in order.
         * @return this
         */
        public Builder addLine(String[] lineDefinition, Integer[] locations) {
            // TODO
            ArrayList<Location> stationLocations = new ArrayList<>();
            Location location;

            int l = -2;
            for (int j = 1; j < locations.length ; j+=2) {
                l+=2;
                location = new Location(locations[l], locations[j]);
                stationLocations.add(location);
            }

            Line line = new Line(lineDefinition[1], lineDefinition[0]);
            for (int i = 2; i < lineDefinition.length ; i++) {

                Station station = new Station(lineDefinition[i]);
                line.addStation(station);

            }

            for (int i = 0; i < line.getStationsOnLine().size() ; i++) {
                line.getStationsOnLine().get(i).setLocation(stationLocations.get(i));
            }


            lineList.add(line);
            return this;
        }

        public Builder addLine(String[] lineDefinition) {
            // TODO
            Line line = new Line(lineDefinition[1], lineDefinition[0]);
            for (int i = 2; i < lineDefinition.length ; i++) {

                Station station = new Station(lineDefinition[i]);
                line.addStation(station);

            }


            lineList.add(line);
            return this;
        }

        public Builder addWeights(Double[] weights){

            connectionWeights.add(weights);

            return this;
        }

        /**
         * Method that reads all the lines and their stations to build a set of stations.
         * Stations that are on more than one line will only appear once in the set.
         * @return
         */
        public Builder buildStationSet() {
            // TODO
            for (int i = 0; i < lineList.size() ; i++) {
                for (Station s: lineList.get(i).getStationsOnLine()) {
                    stationSet.add(s);
                }
            }

            return this;
        }

        /**
         * For every station on the set of station add the lines of that station to the lineList in the station
         * @return
         */
        public Builder addLinesToStations() {
            // TODO
            //check if the station can be found in the station List of the line List then add that station
            for (Station s : stationSet){

                for (Line line: lineList){

                    for (int i = 0; i < line.getStationsOnLine().size(); i++) {

                        if (s.equals(line.getStationsOnLine().get(i))){

                            s.addLine(line);

                        }
                    }
                }
            }
            return this;
        }



        public Builder buildConnectionsWithoutWeights() {
            // TODO
            Station from;
            Station to;
            Connection connection;

            for (int z = 0; z < lineList.size(); z++) {

                int j = -1;

                for (int i = 1; i < lineList.get(z).getStationsOnLine().size(); i++) {
                    j++;


                    from = lineList.get(z).getStationsOnLine().get(j);
                    to = lineList.get(z).getStationsOnLine().get(i);

                    connection = new Connection(from, to);
                    connection.setLine(lineList.get(z));


                    connectionSet.add(connection);

                }
            }

            return this;
        }



        /**
         * Method that uses the list of Lines to build connections from the consecutive stations in the stationList of a line.
         * @return
         */
        public Builder buildConnections() {
            // TODO
            Station from;
            Station to;
            Connection connection;

            for (int z = 0; z < lineList.size(); z++) {

                int j = -1;
                int g = -1;


                for (int i = 1; i < lineList.get(z).getStationsOnLine().size(); i++) {
                    j++;
                    g++;

                    from = lineList.get(z).getStationsOnLine().get(j);
                    to = lineList.get(z).getStationsOnLine().get(i);

                    connection = new Connection(from, to);
                    connection.setLine(lineList.get(z));
                    connection.setWeight(connectionWeights.get(z)[g]);

                    connectionSet.add(connection);

                }
            }

            return this;
        }

        /**
         * Method that builds the graph.
         * All stations of the stationSet are addes as vertices to the graph.
         * All connections of the connectionSet are addes as edges to the graph.
         * @return
         */
        public TransportGraph build() {
            TransportGraph graph = new TransportGraph(stationSet.size());
            // TODO

            for (Station station : stationSet){
                graph.addVertex(station);
            }

            for (Connection connection: connectionSet) {
                graph.addEdge(connection);

            }

            graph.addConnectionWeight(connectionWeights);

            return graph;
        }

    }
}
