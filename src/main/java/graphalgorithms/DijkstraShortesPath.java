package graphalgorithms;

import model.Connection;
import model.IndexMinPQ;
import model.Station;
import model.TransportGraph;

import java.util.*;

public class DijkstraShortesPath extends AbstractPathSearch {


    private IndexMinPQ<Double> pq;
    private double[] distTo;


    public DijkstraShortesPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
        distTo = new double[graph.getNumberOfStations()];
        int startIndex = graph.getIndexOfStationByName(start);
        for (int i = 0; i < graph.getNumberOfStations() ; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[startIndex] = 0.0;
        pq = new IndexMinPQ<Double>(graph.getNumberOfStations());
    }

    @Override
    public void search() {
        pq.insert(startIndex, 0.0);
        while (!pq.isEmpty()){
            relax(graph, pq.delMin());
        }
    }

    @Override
    public boolean hasPathTo(int vertex) {
        return distTo[vertex] < Double.POSITIVE_INFINITY;
    }

    private void relax(TransportGraph graph, int index){
        nodesVisited.add(graph.getStation(index));
        for(int w: graph.getAdjacentVertices(index)){
            if (index == endIndex){
                pathTo(endIndex);
                getTotalWeight();
                break;
            }
            if (distTo[w] > distTo[index] + graph.getConnection(index,w).getWeight()){
                distTo[w] = distTo[index] + graph.getConnection(index,w).getWeight();
                edgeTo[w] = index;
                if (pq.contains(w)){
                    pq.changeKey(w,distTo[w]);
                }else {
                    pq.insert(w,distTo[w]);

                }
            }
        }
    }

    public Double getTotalWeight(){
        double totalWeight = 0;

        int j = -1;
        for (int i = 1; i < verticesInPath.size() ; i++) {
            j++;
            totalWeight += graph.getConnection(verticesInPath.get(j),verticesInPath.get(i)).getWeight();
        }
        return totalWeight;

    }


}
