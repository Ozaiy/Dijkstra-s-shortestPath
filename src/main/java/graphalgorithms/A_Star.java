package graphalgorithms;

import model.IndexMinPQ;
import model.Station;
import model.TransportGraph;

public class A_Star extends AbstractPathSearch{


    private Station end;
    private IndexMinPQ<Double> pq;

    private double[] distTo;


    public A_Star(TransportGraph graph, String start, String end) {
        super(graph, start, end);
        this.end = graph.getStation(graph.getIndexOfStationByName(end));
        distTo = new double[graph.getNumberOfStations()];
        int startIndex = graph.getIndexOfStationByName(start);
        for (int i = 0; i < graph.getNumberOfStations() ; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[startIndex] = 0.0;
        pq = new IndexMinPQ<Double>(graph.getNumberOfStations());
    }


    @Override
    public boolean hasPathTo(int vertex) {
        return distTo[vertex] < Double.POSITIVE_INFINITY;
    }

    @Override
    public void search() {
        pq.insert(startIndex, 0.0);
        while (!pq.isEmpty()){
            relax(graph, pq.delMin());
        }
    }

    public void relax(TransportGraph graph, int index){
        nodesVisited.add(graph.getStation(index));
        for(int w: graph.getAdjacentVertices(index)){
            if (index == endIndex){
                pathTo(endIndex);
                break;
            }
            if (distTo[w] > distTo[index] + graph.getConnection(index,w).getWeight() +
            graph.getStation(index).getLocation().travelTime(end.getLocation().getFirstCo(), end.getLocation().getSecondCo())){

                distTo[w] = distTo[index] + graph.getConnection(index,w).getWeight() +
                        graph.getStation(index).getLocation().travelTime(end.getLocation().getFirstCo(), end.getLocation().getSecondCo());

                edgeTo[w] = index;
                if (pq.contains(w)){
                    pq.changeKey(w,distTo[w]);
                }else {
                    pq.insert(w,distTo[w]);

                }
            }
        }
    }



}
