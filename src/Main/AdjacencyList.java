package Main;

import java.util.ArrayList;


public class AdjacencyList {
    Entry[] entries;
    int nodesInOgGraph;
    int T;

    public AdjacencyList(int nodes, int t) {
        entries = new Entry[nodes];
        nodesInOgGraph = nodes;
        this.T = t + 1;
    }




    public void expandEntries(int newLength) {
        Entry[] copy = new Entry[newLength];

        for(int i = 0; i < newLength; i++) {
            if (i < entries.length) {
                copy[i] = entries[i];
            }
        }
        this.entries = copy;
    }


    public void filterOriginalGraph() {
        for (int i = 0; i < nodesInOgGraph; i++) {
            Entry entry = entries[i];

            if (entry != null) {
                ArrayList<Entry.EdgeData> edges = entry.toEdges;
                edges.removeIf(edge -> edge.length > 0);
            }
        }
    }


    public int getOffset(int cost) {
        return  cost * nodesInOgGraph;
    }

    public void mergeDupes() {
        for (int i = 0 ; i < entries.length; i++) {
            if (entries[i] != null) {
                for (int j = 0; j < entries[i].toEdges.size(); j++) {
                    for (int k = j + 1; k < entries[i].toEdges.size(); k++) {
                        if (entries[i].toEdges.get(k).to == entries[i].toEdges.get(j).to ) {
                            //merge
                            int capacity = entries[i].toEdges.get(k).capacity + entries[i].toEdges.get(j).capacity;
                            int from = i;
                            int to = entries[i].toEdges.get(k).to;

                            int capA = entries[i].toEdges.get(k).capacity;
                            int capB = entries[i].toEdges.get(j).capacity;

                            Entry.EdgeData edge1 = entries[i].toEdges.get(j);
                            Entry.EdgeData edge2 = entries[i].toEdges.get(k);

                            entries[i].toEdges.remove(edge1);
                            entries[i].toEdges.remove(edge2);
                            addEdge(i, to, 0, capacity, false);
                        }
                    }
                }

            }

        }
    }



    public void addEdge(int from, int to, int length, int capacity, boolean residual) {
        if (entries[from] == null) {
            entries[from] = new Entry(from, to, length, capacity, residual);
        } else {
            entries[from].addEdge(to, length, capacity, residual, from);
        }
    }





    public void removeEdge(int from, int to, int length, int capacity) {
        Entry entry = entries[from];

        int i = 0;
        for (Entry.EdgeData edge : entry.toEdges) {
            if (edge.to == to && edge.capacity == capacity && edge.length == length) {
               entries[from].toEdges.remove(i);
               return;
            }
            i++;
        }
    }


    public static class Entry {
        int from;
        ArrayList<EdgeData> toEdges = new ArrayList<>();

        public String toString() {
            StringBuilder str = new StringBuilder(from + " | ");
            for (EdgeData edge : toEdges) {
                str.append(edge.to  + ": "+  edge.capacity + ", ");
            }

            return str.toString();
        }

        public Entry(int from, int to, int length, int capacity, boolean residual) {
            this.from = from;
            this.toEdges.add(new EdgeData(to, length, capacity, residual, from));
        }

        public Entry(EdgeData edge) {
            this.from = edge.from;
            this.toEdges.add(edge);
        }

        public static class EdgeData {
            int from;
            int to;
            int length;
            int capacity;
            int flow;
            boolean residual;
            EdgeData reverse;

            public EdgeData(int to, int length, int capacity, boolean residual, int from) {
                this.from = from;
                this.to = to;
                this.length = length;
                this.capacity = capacity;
                this.flow = 0;
                this.residual = residual;
            }

            public void setReverse(EdgeData e) {
                this.reverse = e;
            }

        }

        public void addEdge(int to, int length, int capacity, boolean residual, int from) {
            toEdges.add(new EdgeData(to, length, capacity, residual, from));
        }

    }
}
