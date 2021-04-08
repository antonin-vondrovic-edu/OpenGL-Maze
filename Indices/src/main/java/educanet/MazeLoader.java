package educanet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MazeLoader {

    public static String Maze1() {
        String loadedmaze = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("maze1.txt"));

            String readLine = br.readLine();

            for (int i = 0; readLine != null; i++) {
                loadedmaze += readLine + "\n";
                readLine = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return loadedmaze;
    }

    public static String Maze2() {
        String loadedmaze = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("maze2.txt"));

            String readLine = br.readLine();

            for (int i = 0; readLine != null; i++) {
                loadedmaze += readLine + "\n";
                readLine = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return loadedmaze;
    }

    public static String Maze3() {
        String loadedmaze = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("maze3.txt"));

            String readLine = br.readLine();

            for (int i = 0; readLine != null; i++) {
                loadedmaze += readLine + "\n";
                readLine = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return loadedmaze;
    }

}
