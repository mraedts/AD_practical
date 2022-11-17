package Main;

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

        addResidualEdges();
        //System.out.println("Flow: " + mainLoop());
        mainLoop();
    }

    public void addResidualEdges() {
        for (int n = 0; n < list.entries.length; n++) {
            AdjacencyList.Entry entry = list.entries[n];
            if (entry != null) {
                for (AdjacencyList.Entry.EdgeData edge: entry.toEdges) {
                    if (!edge.residual) {
                        edge.setReverse(edge.to, n, 0);
                    }
                }
            }
        }
    }

    public void mainLoop() {
        int flow = 0;

        LinkedList<Integer> q = new LinkedList<>();
        // add source
        q.add(0);

        AdjacencyList.Entry.EdgeData[] predecessors = new AdjacencyList.Entry.EdgeData[list.entries.length];

        int t = list.entries.length-1;

        while (true) {
            System.out.println("flow:" + flow);
            while (q.size() > 0) {
                System.out.println("flow:" + flow);
                int currentNode = q.remove();
                if (list.entries[currentNode] != null) {
                    for (AdjacencyList.Entry.EdgeData edge : list.entries[currentNode].toEdges) {

                        if (predecessors[edge.to] == null && edge.to != 0 && edge.capacity > edge.flow) {
                            predecessors[edge.to] = edge;
                            q.add(edge.to);
                        }
                        System.out.println("flow:" + flow);
                    }
                }

                if (predecessors[t] != null) {
                    int df = Integer.MAX_VALUE;

                    System.out.println("flow:" + flow);
                    for (AdjacencyList.Entry.EdgeData edge = predecessors[t]; edge != null; edge = predecessors[edge.from]) {
                        df = Math.min(df, edge.capacity - edge.flow);
                    }

                    for (AdjacencyList.Entry.EdgeData edge = predecessors[t]; edge != null; edge = predecessors[edge.from]) {
                        edge.flow = edge.flow + df;
                        edge.reverse.flow = edge.reverse.flow - df;
                    }

                    flow += df;
                    System.out.println("flow:" + flow);
                }
            }
        }
    }
}
