package com.example.administrator.mesh;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * Group可以用来管理多个空间几何形体，如果把Mesh比作Android的View ,
 * Group可以看作Android的ViewGroup，
 * Android的View的设计也是采用的“Composite Pattern”。
 * Group的主要功能是把针对Group的操作（如draw)分发到Group中的每个成员对应的操作（如draw)。
 * Created by LiuTao on 2017/8/3 0003.
 */

public class Group extends Mesh {
    private Vector<Mesh> children = new Vector<Mesh>();

    @Override
    public void draw(GL10 gl) {
        int size = children.size();
        for (int i = 0; i < size; i++)
            children.get(i).draw(gl);
    }

    /**
     * @param location
     * @param object
     * @see Vector#add(int, Object)
     */
    public void add(int location, Mesh object) {
        children.add(location, object);
    }

    /**
     * @param object
     * @return
     * @see Vector#add(Object)
     */
    public boolean add(Mesh object) {
        return children.add(object);
    }

    /**
     * @see Vector#clear()
     */
    public void clear() {
        children.clear();
    }

    /**
     * @param location
     * @return
     * @see Vector#get(int)
     */
    public Mesh get(int location) {
        return children.get(location);
    }

    /**
     * @param location
     * @return
     * @see Vector#remove(int)
     */
    public Mesh remove(int location) {
        return children.remove(location);
    }

    /**
     * @param object
     * @return
     * @see Vector#remove(Object)
     */
    public boolean remove(Object object) {
        return children.remove(object);
    }

    /**
     * @return
     * @see Vector#size()
     */
    public int size() {
        return children.size();
    }

}