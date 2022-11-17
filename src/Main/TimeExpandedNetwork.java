package Main;

public class TimeExpandedNetwork {
    AdjacencyList list;


    public TimeExpandedNetwork( AdjacencyList originalGraph) {
        this.list = originalGraph;

        list.expandEntries(list.entries.length * list.T);
        connectSubgraphs();
        list.filterOriginalGraph();
        connectSourcesAndSinks();
    }

    public void connectSourcesAndSinks() {
        for (int t = 0; t < list.T-1; t++) {
            int last = list.nodesInOgGraph-1;
            list.addEdge(t * list.nodesInOgGraph,(t+1) * list.nodesInOgGraph, 0, Integer.MAX_VALUE, false);
            list.addEdge(last + t * list.nodesInOgGraph,(t+1) * list.nodesInOgGraph + last, 0, Integer.MAX_VALUE, false);
        }
    }

    public void connectSubgraphs() {
        int index = 0;
        // exec for all nodes in og graph
        for (int i = 0; i < list.nodesInOgGraph; i++ ) {
            if (list.entries[i] != null) {
                int size = list.entries[i].toEdges.size();
                for (int j = 0; j < size; j++) {

                    AdjacencyList.Entry.EdgeData data = list.entries[i].toEdges.get(j);
                    int offset = list.getOffset(data.length);
                    offset = offset + data.to;

                    for (int t = 0; t < list.T; t++) {
                        int to =  t * list.nodesInOgGraph + offset;
                        if (t * list.nodesInOgGraph + offset < list.entries.length && to >= list.nodesInOgGraph ) {
                            list.addEdge(index + t * list.nodesInOgGraph ,  t * list.nodesInOgGraph + offset  , -1,data.capacity, false);
                        }
                    }
                }
            }
        index++;
        }
    }
}
