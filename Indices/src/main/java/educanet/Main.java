package educanet;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

public class Main {

    public static void main(String[] args) throws Exception {

        String loadedmaze = MazeLoader.Maze1(); // can change to Maze1(), Maze2() or Maze3(), to load the others

        String[] mazeRows = loadedmaze.split("\n");
        int mazeVerticalLength = mazeRows.length;
        float mazeSize = 2 / (float) mazeVerticalLength;

        char[][] zeroOne /*zero two o_O*/ = new char[mazeVerticalLength][mazeVerticalLength]; // puts the 0 and 1 in char, 01101100 01101101 01100001 01101111
        for (int i = 0; i < mazeVerticalLength; i++) {
            for (int j = 0; j < mazeVerticalLength; j++) {
                zeroOne[i][j] = mazeRows[i].charAt(j);
            }
        }

        //region: Window init
        GLFW.glfwInit();
        // Tell GLFW what version of OpenGL we want to use.
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        // TODO: Imagine using a mac lmaomalalmao couldn't be me omegalul

        // Create the window...
        // We can set multiple options with glfwWindowHint ie. fullscreen, resizability etc.
        long window = GLFW.glfwCreateWindow(800, 600, "PAIN MAZE", 0, 0);
        if (window == 0) {
            GLFW.glfwTerminate();
            throw new Exception("Něco si hodně hodně hodně moc dosral nebo si se přepsal");
        }
        GLFW.glfwMakeContextCurrent(window);

        // Tell GLFW, that we are using OpenGL
        GL.createCapabilities();
        GL33.glViewport(0, 0, 800, 600);

        // Resize callback
        GLFW.glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            GL33.glViewport(0, 0, w, h);
        });
        //endregion

        // Main game loop
        //Game.init(window); // you just lost the game
        Shaders.initShaders();
        Square[][] epicSquares = new Square[mazeVerticalLength][mazeVerticalLength];
        for (int i = 0; i < zeroOne.length; i++) {
            for (int j = 0; j < mazeVerticalLength; j++) {
                if (zeroOne[i][j] == '1') {
                    epicSquares[i][j] = new Square((j * mazeSize - 1), 1 - (i * mazeSize), mazeSize);
                } else {
                    epicSquares[i][j] = null;
                }
            }
        }

        System.out.println(loadedmaze); // prints out the loadedmaze because why not

        // Draw in polygon mod
        //GL33.glPolygonMode(GL33.GL_FRONT_AND_BACK, GL33.GL_LINE);
        while (!GLFW.glfwWindowShouldClose(window)) {
            // Key input management
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS)
                GLFW.glfwSetWindowShouldClose(window, true); // Send a shutdown signal...

            // Change the background color
            GL33.glClearColor(0f, 0f, 0f, 1f);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);

            for (int i = 0; i < epicSquares.length; i++) {
                for (int j = 0; j < epicSquares.length; j++) {
                    if(epicSquares[i][j] != null) {
                        epicSquares[i][j].render();
                    }
                }
            }

            // Swap the color buffer -> screen tearing solution
            GLFW.glfwSwapBuffers(window);
            // Listen to input
            GLFW.glfwPollEvents();
        }

        // Don't forget to cleanup
        GLFW.glfwTerminate();
    }

}
