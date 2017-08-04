package com.example.administrator.openGles;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import com.example.administrator.mesh.Cube;
import com.example.administrator.mesh.Group;
import com.example.administrator.mesh.Mesh;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements Renderer {
	private float angle =10f;
	// Initialize our square.
	Square square1 = new Square();
	Square square = new ColorSquare();
	private Mesh root;
	public OpenGLRenderer() {
		// Initialize our cube.
		Group group = new Group();
		Cube cube = new Cube(1, 1, 1);
		cube.rx = 45;
		cube.ry = 45;
		group.add(cube);
		root = group;

	}
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceCreated(javax.
         * microedition.khronos.opengles.GL10, javax.microedition.khronos.
         * egl.EGLConfig)
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);  // OpenGL docs.
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);// OpenGL docs.
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);// OpenGL docs.
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);// OpenGL docs.
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);// OpenGL docs.
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, // OpenGL docs.
                          GL10.GL_NICEST);
		
		

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.
         * microedition.khronos.opengles.GL10)
	 */
	public void onDrawFrame(GL10 gl) {
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | // OpenGL docs.
                           GL10.GL_DEPTH_BUFFER_BIT);
//		drawOneSqure(gl);
//		drawThreeSqure(gl);
		draw3DRoot(gl);
	}

	private void draw3DRoot(GL10 gl) {
		// Replace the current matrix with the identity matrix
		gl.glLoadIdentity();
		// Translates 4 units into the screen.
		gl.glTranslatef(0, 0, -4);
		// Draw our scene.
		root.draw(gl);
	}

	/**
	 *
	 *  坐标变换绘制动画，绘制3个正方形A,B,C。
	 *    进行缩放变换，使的B比A小50%，C比B小50%。
	 *       然后以屏幕中心逆时针旋转A，
	 *       B以A为中心顺时针旋转，
	 *  C以B为中心顺时针旋转同时以自己中心高速逆时针旋转。
	 * @param gl
	 */
   private void drawThreeSqure(GL10 gl){
	   // 用单位矩阵替换当前矩阵
	   gl.glLoadIdentity();
	   gl.glTranslatef(0, 0, -10);

	   // 正方形 A  逆时针旋转
	   // 保存当前矩阵。
	   gl.glPushMatrix();
	   // 逆时针旋转。
	   gl.glRotatef(angle, 0, 0, 1);
	   square.draw(gl);// 画矩阵A
	   // 恢复保存过的矩阵 A
	   gl.glPopMatrix();
	   // 正方形 B 顺时针旋转
	   gl.glPushMatrix();
	   // Rotate square B before moving it,
	   //making it rotate around A.
	   gl.glRotatef(-angle, 0, 0, 1);
	   // Move square B.
	   gl.glTranslatef(2, 0, 0);
	   // Scale it to 50% of square A
	   gl.glScalef(.5f, .5f, .5f);
	   square.draw(gl);//绘制正方行B

	   // Save the current matrix
	   gl.glPushMatrix();
	   // Make the rotation around B
	   gl.glRotatef(-angle, 0, 0, 1);
	   gl.glTranslatef(2, 0, 0);
	   // Scale it to 50% of square B
	   gl.glScalef(.5f, .5f, .5f);
	   // Rotate around it's own center.
	   gl.glRotatef(angle*10, 0, 0, 1);
	   // Draw square C.
	   square.draw(gl);
	   // Restore to the matrix as it was before C.
	   gl.glPopMatrix();
	   // Restore to the matrix as it was before B.
	   gl.glPopMatrix();


	   // Increse the angle.
	   angle++;
   }
	private void drawOneSqure(GL10 gl) {


		gl.glLoadIdentity();//保存形状，防止一闪而过
		gl.glTranslatef(0, 0, -10);
		gl.glPushMatrix();
		// Make the rotation around B 以屏幕为中心旋转
		gl.glRotatef(-angle, 0, 0, 1);
		gl.glTranslatef(2, 0, 0);
		// Scale it to 50% of square B
		gl.glScalef(.5f, .5f, .5f);
		// Rotate around it's own center.
		gl.glRotatef(angle*10, 0, 0, 1);//以自身为中心旋转
		//gl.glScalef(1.5f,1.5f,1.5f);//缩放
//		// Draw our square.
		square1.draw(gl); // 绘制正方行
		gl.glPopMatrix();//恢复保存的矩阵
		angle ++;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.
         * microedition.khronos.opengles.GL10, int, int)
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);// OpenGL docs.
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);// OpenGL docs.
		// Reset the projection matrix
		gl.glLoadIdentity();// OpenGL docs.
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 45.0f,
                                   (float) width / (float) height,
                                   0.1f, 100.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);// OpenGL docs.
		// Reset the modelview matrix
		gl.glLoadIdentity();// OpenGL docs.
	}
}
