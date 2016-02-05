package com.gh.model;

/**
 * 地图坐标标记
 * 防止敌方坦克重叠
 * @author ganhang
 *
 */
public class Map {
	public int[][] location=new int[500][500];
	public Map() {
		for (int i = 0; i < 500; i++) {
			for (int j = 0; j <500; j++) {
				location[i][j]=0;
			}
		}
	}
}
