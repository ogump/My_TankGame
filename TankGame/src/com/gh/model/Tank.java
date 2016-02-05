package com.gh.model;

import java.util.Vector;
/**
 * 坦克类
 * 每个坦克就是一个线程，
 * 这里自己坦克并没有启动线程
 * @author ganhang
 *
 */
public class Tank implements Runnable {
	private int x = 0;
	private int y = 0;// 坐标
	private int drect = 0;// 方向 0向上，1向右，2向下，3向左；
	private int type = 0;// 坦克类型 0表示自己
	private int speed = 3;// 速度
	public Vector<Bullet> mybs = new Vector<Bullet>();// 子弹集
	private Bullet myBullet;// 子弹
	public boolean islive = true;
	private Map map;
	public boolean start = true;
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Tank(int x, int y, int drect, int type) {
		super();
		this.x = x;
		this.y = y;
		this.drect = drect;
		this.type = type;
	}

	public Tank() {
		super();
	}

	public Bullet getMyBullet() {
		return myBullet;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getX() {
		return x;
	}

	public int getDrect() {
		return drect;
	}

	public void setDrect(int drect) {
		this.drect = drect;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void moveUp() {
		if (y - speed < 0)
			y = 0;
		else {
			y -= speed;
			 map.location[x][y]=1;//标记此坦克坐标在地图上防止其他坦克过来占用导致重叠
//			 这里只标记了坦克坐标那一个点，会有bug，部分坦克还是有重叠现象，
//			 这里可以遍历整个坦克坐标（x到x+20，y到y+30）设置标记。
//			 for(int i=x;i<x+20;i++){
//				 for (int j = y; j < y+30; j++) {
//					 map.location[x][y]=1;
//				}
//			 }
		}
	}

	public void moveDown() {
		if (y + speed > 470)
			y = 470;
		else {
			y += speed;
			 map.location[x][y]=1;
		}
	}

	public void moveRight() {
		if (x + speed > 470)
			x = 470;
		else {
			x += speed;
			 map.location[x][y]=1;
		}
	}

	public void moveLeft() {
		if (x - speed < 0)
			x = 0;
		else {
			x -= speed;
			 map.location[x][y]=1;
		}
	}

	public void shot() {
		switch (drect) {
		case 0:
			myBullet = new Bullet(x + 10, y, 5, 0);
			mybs.add(myBullet);
			break;
		case 1:
			myBullet = new Bullet(x + 30, y + 10, 5, 1);
			mybs.add(myBullet);
			break;
		case 2:
			myBullet = new Bullet(x + 10, y + 30, 5, 2);
			mybs.add(myBullet);
			break;
		case 3:
			myBullet = new Bullet(x, y + 10, 5, 3);
			mybs.add(myBullet);
			break;
		}
	}

	@Override
	public void run() {
		while (islive) {
			if (start) {
				int step;
				int s;
				try {
					switch (drect) {
					case 0:
						step = (int) (Math.random() * 30);
						for (int i = 0; i < step; i++) {
							moveUp();
							if (y <= 0)
								break;// 撞墙跳出循环
							if (y >= 30)// 仿数组越界
								if (map.location[x][y - 30] == 1 || map.location[x][y - 20] == 1) {
									map.location[x][y - 30] = 0;//这里没分开判断
									map.location[x][y - 20] = 0;
									break;
								}
							Thread.sleep(80);
						}
						break;
					case 1:
						step = (int) (Math.random() * 30);
						for (int i = 0; i < step; i++) {
							moveRight();
							if (x >= 500)
								break;
							if (x < 470)
								if (map.location[x + 20][y] == 1 || map.location[x + 30][y] == 1) {
									map.location[x + 20][y] = 0;
									map.location[x + 30][y] = 0;
									break;
								}
							Thread.sleep(80);
						}
						break;
					case 2:
						step = (int) (Math.random() * 30);
						for (int i = 0; i < step; i++) {
							moveDown();
							if (y >= 500)
								break;
							if (y < 470)
								if (map.location[x][y + 30] == 1 || map.location[x][y + 20] == 1) {
									map.location[x][y + 30] = 0;
									map.location[x][y + 20] = 0;
									break;
								}
							Thread.sleep(80);
						}
						break;
					case 3:
						step = (int) (Math.random() * 30);
						for (int i = 0; i < step; i++) {
							moveLeft();
							if (x <= 0)
								break;
							if (x >= 30)
								if (map.location[x - 20][y] == 1 || map.location[x - 30][y] == 1) {
									map.location[x - 20][y] = 0;
									map.location[x - 30][y] = 0;
									break;
								}
							Thread.sleep(80);
						}
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				drect = (int) (Math.random() * 4);// 随机方向
				s = (int) (Math.random() * 10);
				if (s > 8) {
					shot();
				}
			}
		}
	}
}
