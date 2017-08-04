package com.example.administrator.mesh;

/**
 * 正方形
 * 下面来定义一个正方体（Cube），
 * 为简单起见，这个四面体只可以设置宽度，高度，和深度，没有和Plane一样提供Segments支持。
 * Created by LiuTao on 2017/8/3 0003.
 */

public class Cube extends Mesh {
    public Cube(float width, float height, float depth) {
        width  /= 2;
        height /= 2;
        depth  /= 2;

        float vertices[] = { -width, -height, -depth, // 0
                width, -height, -depth, // 1
                width,  height, -depth, // 2
                -width,  height, -depth, // 3
                -width, -height,  depth, // 4
                width, -height,  depth, // 5
                width,  height,  depth, // 6
                -width,  height,  depth, // 7
        };

        short indices[] = { 0, 4, 5,
                0, 5, 1,
                1, 5, 6,
                1, 6, 2,
                2, 6, 7,
                2, 7, 3,
                3, 7, 4,
                3, 4, 0,
                4, 7, 6,
                4, 6, 5,
                3, 0, 1,
                3, 1, 2, };

        setIndices(indices);
        setVertices(vertices);
    }
}