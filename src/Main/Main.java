package Main;

public class Main {

    public static void main(String[] args) {



        InputParser parser = new InputParser();
        AdjacencyList list = parser.parse("tests/1.in");
        TimeExpandedNetwork time = new TimeExpandedNetwork(list);
        System.out.println("done:");

        time.list.mergeDupes();

        for (AdjacencyList.Entry entry : time.list.entries) {
            System.out.println(entry);
        }

        FordFulkerson fulk = new FordFulkerson(time.list);


    }
}
