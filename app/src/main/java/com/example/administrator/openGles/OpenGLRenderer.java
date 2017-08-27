package com.example.administrator.openGles;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.Matrix;

import com.example.administrator.fragment.fragment2.activity.GLBitmap;
import com.example.administrator.mesh.Mesh;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements Renderer {
	private final Context context;
	private float angle =10f;
	// Initialize our square.
	Square square1 = new Square();
	Square square = new ColorSquare();
	private Mesh root;
	private final GLBitmap getBitMap = new GLBitmap();

	// mMVPMatrix是"Model View Projection Matrix"的缩写 模型视图投影矩阵
	private final float[] mMVPMatrix = new float[16];
	private final float[] mProjectionMatrix = new float[16];//定义投影矩阵变量
	private final float[] mViewMatrix = new float[16];//定义相机视图矩阵变量
	public OpenGLRenderer(Context context) {
		this.context = context;


		// Initialize our cube.
//		Group group = new Group();
//		Cube cube = new Cube(1, 1, 1);
//		cube.rx = 45;
//		cube.ry = 45;
//		group.add(cube);
//		root = group;

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
		getBitMap.loadGLTexture(gl, this.context);
		gl.glEnable(GL10.GL_TEXTURE_2D); // Enable Texture Mapping ( NEW )
		gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
		gl.glClearDepthf(1.0f); // Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); // Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); // The Type Of Depth Testing To Do

		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.
         * microedition.khronos.opengles.GL10)
	 */
	public void onDrawFrame(GL10 gl) {
		// 设置相机的位置(视图矩阵)
		Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

		// 将mProjectionMatrix和mViewMatrix矩阵相乘并赋给mMVPMatrix
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// Reset the Modelview Matrix
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -5.0f); // move 5 units INTO the screen is
		gl.glScalef(.3f, .3f, .3f);
		// the same as moving the camera 5
		// units away
		// square.draw(gl);

		getBitMap.draw(gl);
		changeGLViewport(gl);
		//drawOneSqure(gl);
//		drawThreeSqure(gl);
		//draw3DRoot(gl);
	}
	/**
	 * 通过改变gl的视角获取
	 *
	 * @param gl
	 */
	private long frameSeq = 0;
	private int viewportOffset = 0;
	private int maxOffset = 400;
	private void changeGLViewport(GL10 gl) {
		System.out.println("time=" + System.currentTimeMillis());
		frameSeq++;
		viewportOffset++;
		// The
		// Current
		if (frameSeq % 100 == 0) {// 每隔100帧，重置
			gl.glViewport(0, 0, width, height);
			viewportOffset = 0;
		} else {
			int k = 4;
			gl.glViewport(-maxOffset + viewportOffset * k, -maxOffset
					+ viewportOffset * k, this.width - viewportOffset * 2 * k
					+ maxOffset * 2, this.height - viewportOffset * 2 * k
					+ maxOffset * 2);
		}
	}
	private void draw3DRoot(GL10 gl) {
		// Replace the current matrix with the identity matrix
		gl.glLoadIdentity();
		// Translates 4 units into the screen.
		//在屏幕的位置
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
	private int width = 0;
	private int height = 0;
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if (height == 0) { // Prevent A Divide By Zero By
			height = 1; // Making Height Equal One
		}
		this.width = width;
		this.height = height;
		float ratio = (float) width / height;//GLSurfaceView的宽高比

		// 根据六个面定义投影矩阵  frustumM(float[] m, int offset, float left, float right, float bottom, float top, float near, float far)
		Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
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
