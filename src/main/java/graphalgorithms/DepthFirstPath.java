package graphalgorithms;

import model.TransportGraph;

public class DepthFirstPath extends AbstractPathSearch {

    public DepthFirstPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    @Override
    public void search() {
        int startIndex = this.startIndex;
        this.dfs(this.graph, startIndex);
    }

    private void dfs(TransportGraph graph, int index) {

        nodesVisited.add(graph.getStation(index));
        this.marked[index] = true;
        for (int w : this.graph.getAdjacentVertices(index)) {
            if (index == endIndex) {
                pathTo(endIndex);
                break;
            }
            if (!this.marked[w]) {
                this.edgeTo[w] = index;
                this.dfs(graph, w);
            }
        }
    }
}
