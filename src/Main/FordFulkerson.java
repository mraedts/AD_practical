package Main;

import java.util.ArrayList;
import java.util.LinkedList;

public class FordFulkerson {

    AdjacencyList list;
    boolean[] visited;


    public FordFulkerson(AdjacencyList list) {
        this.list = list;

        visited = new boolean[list.entries.length];
        for (Boolean v : visited) {
            v = false;
        }

        addReverseEdges();
        //System.out.println("Flow: " + mainLoop());
        mainLoop();
    }

    public void addReverseEdges() {
        for (int n = 0; n < list.entries.length; n++) {
            AdjacencyList.Entry entry = list.entries[n];
            if (entry != null) {
                for (int i = 0; i < entry.toEdges.size(); i++) {
                    AdjacencyList.Entry.EdgeData edge = entry.toEdges.get(i);
                    if (!edge.residual) {



                        AdjacencyList.Entry.EdgeData reverse = new AdjacencyList.Entry.EdgeData(n, 0,0,true,edge.to);


                        // Set pointer from each edge "a" to
                        // its reverse edge "b" and vice versa
                        edge.setReverse(reverse);
                        reverse.setReverse(edge);


                        if (list.entries[edge.to] == null) {
                            list.entries[edge.to] = new AdjacencyList.Entry(reverse);
                        } else {
                            list.entries[edge.to].toEdges.add(reverse);
                        }
                    }
                }
            }
        }
    }

    public void mainLoop() {
        int flow = 0;





        int t = list.entries.length-1;

        int maxFlow = 0;

        while (true) {

            AdjacencyList.Entry.EdgeData[] parent = new AdjacencyList.Entry.EdgeData[list.entries.length];

            ArrayList<Integer> q = new ArrayList<>();
            q.add(0);

            // BFS finding shortest augmenting path
            while (!q.isEmpty()) {
                int curr = q.remove(0);

                // Checks that edge has not yet been visited, and it doesn't
                // point to the source, and it is possible to send flow through it.

                if (list.entries[curr] != null) {
                    for (AdjacencyList.Entry.EdgeData e : list.entries[curr].toEdges)
                        if (parent[e.to] == null && e.to != 0 && e.capacity > e.flow) {
                            parent[e.to] = e;
                            q.add(e.to);
                        }
                }

            }

            // If sink was NOT reached, no augmenting path was found.
            // Algorithm terminates and prints out max flow.
            if (parent[t] == null)
                break;

            // If sink WAS reached, we will push more flow through the path
            int pushFlow = Integer.MAX_VALUE;

            // Finds maximum flow that can be pushed through given path
            // by finding the minimum residual flow of every edge in the path
            for (AdjacencyList.Entry.EdgeData e = parent[t]; e != null; e = parent[e.from])
                pushFlow = Math.min(pushFlow , e.capacity - e.flow);

            // Adds to flow values and subtracts from reverse flow values in path
            for (AdjacencyList.Entry.EdgeData e = parent[t]; e != null; e = parent[e.from]) {
                e.flow += pushFlow;
                e.reverse.flow -= pushFlow;
            }

            maxFlow += pushFlow;
        }

        System.out.println(maxFlow);
    }


}
