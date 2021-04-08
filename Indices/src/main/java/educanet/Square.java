
package educanet;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Square {
    private final int[] indices = {
            0, 1, 3,
            1, 2, 3
    };
    private int squareVaoId;
    private int squareVboId;
    private int squareEboId;
    private int squareColorId;

    public Square(float x, float y, float length) {
        float ColorA = (float) Math.sqrt(((x + length) * (x + length)) + (y * y));
        float ColorB = (float) Math.sqrt(((x + length) * (x + length)) + ((y - length) * (y - length)));
        float ColorC = (float) Math.sqrt((x * x) + ((y - length) * (y - length)));
        float ColorD = (float) Math.sqrt((x * x) + (y * y));

        float shadowRadius = 3.14159265359f; // trollface
        float ColorA2 = (ColorA / (float) Math.sqrt(shadowRadius));
        float ColorB2 = (ColorB / (float) Math.sqrt(shadowRadius));
        float ColorC2 = (ColorC / (float) Math.sqrt(shadowRadius));
        float ColorD2 = (ColorD / (float) Math.sqrt(shadowRadius));

        float[] fuckingcolors = {
                (float) (ColorA2 + 0.0), (float) (ColorA2 + 0.0), ColorA2, 1f,
                (float) (ColorB2 + 0.0), (float) (ColorB2 + 0.0), ColorB2, 1f,
                (float) (ColorC2 + 0.0), (float) (ColorC2 + 0.0), ColorC2, 1f,
                (float) (ColorD2 + 0.0), (float) (ColorD2 + 0.0), ColorD2, 1f,
        };

        float[] vertices = {
                x + length, y, 1.0f, // 0 -> Top right
                x + length, y - length, 1.0f, // 1 -> Bottom right
                x, y - length, 1.0f, // 2 -> Bottom left
                x, y, 1.0f, // 3 -> Top left
        };

        // Generate all the ids
        squareVaoId = GL33.glGenVertexArrays();
        squareVboId = GL33.glGenBuffers();
        squareEboId = GL33.glGenBuffers();
        squareColorId = GL33.glGenBuffers();

        // Tell OpenGL we are currently using this object (vaoId)
        GL33.glBindVertexArray(squareVaoId);

        // Tell OpenGL we are currently writing to this buffer (eboId)
        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, squareEboId);
        IntBuffer ib = BufferUtils.createIntBuffer(indices.length)
                .put(indices)
                .flip();
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ib, GL33.GL_STATIC_DRAW);

        //colors yoyo oyyo yoy
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareColorId);
        FloatBuffer colorfb = BufferUtils.createFloatBuffer(fuckingcolors.length)
                .put(fuckingcolors)
                .flip();

        // Send the color buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, colorfb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1, 4, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);

        // Change to VBOs...
        // Tell OpenGL we are currently writing to this buffer (vboId)
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareVboId);
        FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                .put(vertices)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);

        // Clear the buffer from the memory (it's saved now on the GPU, no need for it here)
        MemoryUtil.memFree(fb);
        MemoryUtil.memFree(ib);
        MemoryUtil.memFree(colorfb);
    }

    public void render() {
        GL33.glUseProgram(educanet.Shaders.shaderProgramId);
        GL33.glBindVertexArray(squareVaoId);
        GL33.glDrawElements(GL33.GL_TRIANGLES, indices.length, GL33.GL_UNSIGNED_INT, 0);
    }
}