package graphalgorithms;

import model.TransportGraph;

import java.util.LinkedList;

public class BreadthFirstPath extends AbstractPathSearch {

    public BreadthFirstPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    @Override
    public void search() {
        LinkedList<Integer> queue = new LinkedList<>();
        marked[startIndex] = true;
        queue.add(startIndex);
        nodesVisited.add(graph.getStation(startIndex));

        while (!queue.isEmpty()) {
            int v = queue.removeFirst();
            for (int w: graph.getAdjacentVertices(v))
                if (!marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    queue.add(w);
                    nodesVisited.add(graph.getStation(w));
                    if (w == endIndex) {
                        pathTo(endIndex);
                        break;
                    }
                }
        }
    }
}
