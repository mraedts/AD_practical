package Main;

public class Main {

    public static void main(String[] args) {



        InputParser parser = new InputParser();
        AdjacencyList list = parser.parseStdInput();
        TimeExpandedNetwork time = new TimeExpandedNetwork(list);
        //System.out.println("done:");

        time.list.mergeDupes();

        FordFulkerson fulk = new FordFulkerson(time.list);


    }
}
