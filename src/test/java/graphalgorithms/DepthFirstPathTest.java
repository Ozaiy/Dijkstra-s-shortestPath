package graphalgorithms;

import model.TransportGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepthFirstPathTest {

    String[] redLine = {"red", "metro", "A", "B", "C", "D"};
    String[] blueLine = {"blue", "metro", "E", "B", "F", "G"};
    String[] greenLine = {"green", "metro", "H", "I", "C", "G", "J"};
    String[] yellowLine = {"yellow", "bus", "A", "E", "H", "D", "G", "A"};

    TransportGraph.Builder builder = new TransportGraph.Builder();



    @Test
    void search() {








//        BreadthFirstPath bfsTest = new BreadthFirstPath(builder.build(), "E", "J");
//        bfsTest.search();
//        System.out.println(bfsTest);
//        bfsTest.printNodesInVisitedOrder();

    }
}
