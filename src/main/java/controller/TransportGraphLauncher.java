package controller;

import graphalgorithms.A_Star;
import graphalgorithms.BreadthFirstPath;
import graphalgorithms.DepthFirstPath;
import graphalgorithms.DijkstraShortesPath;
import model.Connection;
import model.TransportGraph;

public class TransportGraphLauncher {

    public static void main(String[] args) {
        String[] redLine = {"red", "metro", "A", "B", "C", "D"};
        String[] blueLine = {"blue", "metro", "E", "B", "F", "G"};
        String[] greenLine = {"green", "metro", "H", "I", "C", "G", "J"};
        String[] yellowLine = {"yellow", "bus", "A", "E", "H", "D", "G", "A"};

        String[] line1 = {"metro", "red", "Haven", "Marken", "Steigerplein", "Centrum", "Meridiaan", "Dukdalf", "Oostvaarders"};
        Double[] line1Weights = {4.5, 4.7, 6.1, 3.5, 5.4, 5.6};
        Integer[] line1Location = {14,1,12,3,10,5,8,8,6,9,3,10,0,11};
        String[] line2 = {"metro", "blue", "Trojelaan", "Coltrane Cirkel", "Meridiaan", "Robijnpark", "Violetplantsoen"};
        Double[] line2Weights = {6.0, 5.3, 5.1, 3.3};
        Integer[] line2Location = {9,3,7,6,6,9,6,12,5,14};
        String[] line3 = {"metro", "purple", "Grote Sluis", "Grootzeil", "Coltrane Cirkel", "Centrum", "Swingstraat"};
        Double[] line3Weights = {6.2, 5.2, 3.8, 3.6};
        Integer[] line3Location = {2,3,4,6,7,6,8,8,10,9};
        String[] line4 = {"metro", "green", "Ymeerdijk", "Trojelaan", "Steigerplein", "Swingstraat", "Bachgracht", "Nobelplein"};
        Double[] line4Weights = {5.0, 3.7, 6.9, 3.9, 3.4};
        Integer[] line4Location = {9,0,9,3,10,5,10,9,11,11,12,13};
        String[] line5 = {"bus", "yellow", "Grote Sluis", "Ymeerdijk", "Haven", "Nobelplein", "Violetplantsoen", "Oostvaarders", "Grote Sluis"};
        Double[] line5Weights = {26.0, 19.0, 37.0, 25.0, 27.0, 28.0};
        Integer[] line5Location = {2,3,9,0,14,1,12,13,5,14,0,11,2,3};


        // TODO Use the builder to build the graph from the String array.

        TransportGraph.Builder builder = new TransportGraph.Builder();

        //Here we build the Graph for the Dijkstra search alghoritms

        builder.addLine(line1,line1Location);
        builder.addWeights(line1Weights);

        builder.addLine(line2,line2Location);
        builder.addWeights(line2Weights);

        builder.addLine(line3,line3Location);
        builder.addWeights(line3Weights);

        builder.addLine(line4,line4Location);
        builder.addWeights(line4Weights);

        builder.addLine(line5,line5Location);
        builder.addWeights(line5Weights);

        builder.buildStationSet();
        builder.addLinesToStations();
        builder.buildConnections();
        builder.build();

        // the DFS and BFS arrays have to be implemented after the Dijkstra methods because of our datastructure setup

        builder.addLine(redLine);
        builder.addLine(blueLine);
        builder.addLine(greenLine);
        builder.addLine(yellowLine);

        builder.buildStationSet();
        builder.addLinesToStations();
        builder.buildConnectionsWithoutWeights();
        builder.build();

        System.out.println(builder.build().toString());


        System.out.println("DepthFirst");
        DepthFirstPath dfpTest = new DepthFirstPath(builder.build(), "E", "J");
        dfpTest.search();
        System.out.println(dfpTest);
        dfpTest.printNodesInVisitedOrder();
        System.out.println();

        System.out.println("BreadthFirst");
        BreadthFirstPath bfsTest = new BreadthFirstPath(builder.build(), "E", "J");
        bfsTest.search();
        System.out.println(bfsTest);
        bfsTest.printNodesInVisitedOrder();
        System.out.println();

        System.out.println("A-Star");
        A_Star a_star = new A_Star(builder.build(), "Nobelplein", "Dukdalf");
        a_star.search();
        System.out.println(a_star);
        a_star.printNodesInVisitedOrder();
        System.out.println();

        System.out.println("Dijkstra");
        DijkstraShortesPath dksTest = new DijkstraShortesPath(builder.build(), "Nobelplein", "Dukdalf");
        dksTest.search();
        System.out.println(dksTest);
        dksTest.printNodesInVisitedOrder();
        System.out.println("Path Weight: " + dksTest.getTotalWeight());
        System.out.println();







    }
}
