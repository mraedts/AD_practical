package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class InputParser {
    public AdjacencyList parse(String filename) {
        try {
            //System.out.println("Working Directory = " + System.getProperty("user.dir"));

            File file = new File(filename);
            Scanner reader = new Scanner(file);

            String myData = reader.nextLine();
            String[] mySplitStr = myData.split("\\s+");

            int nodes = Integer.parseInt(mySplitStr[0]);
            int edges = Integer.parseInt(mySplitStr[1]);
            int time = Integer.parseInt(mySplitStr[2]);

            AdjacencyList list = new AdjacencyList(nodes, time);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();

                // split input on spaces with regex
                String[] splitStr = data.split("\\s+");

                int from = Integer.parseInt(splitStr[0]);
                int to = Integer.parseInt(splitStr[1]);
                int length = Integer.parseInt(splitStr[2]);
                int capacity = Integer.parseInt(splitStr[3]);

                list.addEdge(from, to, length, capacity, false);

                // System.out.println(Arrays.toString(splitStr));
            }
            reader.close();

            return list;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }
}
