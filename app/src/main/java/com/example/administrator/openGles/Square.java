package com.example.administrator.openGles;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Square {
	// Our vertices.
	private float vertices[] = {
		      -1.0f,  1.0f, 0.0f,  // 0, Top Left
		      -1.0f, -1.0f, 0.0f,  // 1, Bottom Left
		       1.0f, -1.0f, 0.0f,  // 2, Bottom Right
		       1.0f,  1.0f, 0.0f,  // 3, Top Right
		};
	// The colors mapped to the vertices.

	// The order we like to connect them.
	private short[] indices = { 0, 1, 2, 0, 2, 3 };

	// Our vertex buffer.
	public FloatBuffer vertexBuffer;

	// Our index buffer.
	private ShortBuffer indexBuffer;

	public Square() {
		// a float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		// short is 2 bytes, therefore we multiply the number if
		// vertices with 2.
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
	}

	/**
	 * This function draws our square on screen.
	 * @param gl
	 */
	public void draw(GL10 gl) {
		gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);
		// Counter-clockwise winding.
		gl.glFrontFace(GL10.GL_CCW); // 设置逆时针方法为面的“前面”
		// Enable face culling.
		gl.glEnable(GL10.GL_CULL_FACE); // 打开 忽略“后面”设置
		// What faces to remove with the face culling. 明确指明“忽略“哪个面的代码如下：
		gl.glCullFace(GL10.GL_BACK); //

		// Enabled the vertices buffer for writing and to be used during
		// rendering.  打开
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, // OpenGL docs
                                 vertexBuffer);

		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,// OpenGL docs
				  GL10.GL_UNSIGNED_SHORT, indexBuffer);

		// Disable the vertices buffer.使用完毕后 关闭
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY); // OpenGL docs
		// Disable face culling. 关闭
		gl.glDisable(GL10.GL_CULL_FACE); // OpenGL docs
	}

}
