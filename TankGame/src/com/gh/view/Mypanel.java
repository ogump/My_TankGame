package com.gh.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.gh.model.Bomb;
import com.gh.model.Bullet;
import com.gh.model.Map;
import com.gh.model.Tank;

/**
 * 游戏显示面板
 * 
 * @author ganhang
 *
 */

public class Mypanel extends JPanel implements Runnable {
	public Tank mytank = null;// 我的坦克
	Tank ek = null;
	Image img;
	Vector<Tank> eks = new Vector<Tank>();//地方坦克集
	Vector<Bomb> bs = new Vector<Bomb>();//爆炸集合
	Map map = new Map();

	public Mypanel() {
		mytank = new Tank(200, 200, 0, 0);
		mytank.setMap(map);
		// 创建敌人坦克
		for (int i = 0; i < 17; i++) {
			ek = new Tank(i * 30, 10, 2, 1);
			eks.add(ek);
			ek.setMap(map);
			new Thread(ek).start();
		}
		img = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/1.png"));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// 画背景
		g.fillRect(0, 0, 500, 500);
		// 画自己的坦克
		if (mytank.islive)
			drawTank(mytank.getX(), mytank.getY(), g, mytank.getDrect(), mytank.getType());
		// 画自己的子弹
		for (int i = 0; i < mytank.mybs.size(); i++) {// 循环时删除集合时，不要用foreach，用for
			Bullet b = new Bullet();
			b = mytank.mybs.get(i);
			if (b.islive) {
				g.setColor(Color.white);
				g.fill3DRect(b.getX(), b.getY(), 2, 2, false);
			} else
				mytank.mybs.remove(b);
		}
		// 画敌人坦克
		for (int i = 0; i < eks.size(); i++) {
			Tank ek = new Tank();
			ek = eks.get(i);
			if (ek.islive)
				drawEnemyTank(ek.getX(), ek.getY(), ek.getDrect(), g);
			// 画敌人子弹
			for (int j = 0; j < ek.mybs.size(); j++) {
				Bullet eb = new Bullet();
				eb = ek.mybs.get(j);
				if (eb.islive) {
					g.setColor(Color.green);
					g.fill3DRect(eb.getX(), eb.getY(), 2, 2, false);
				} else
					ek.mybs.remove(eb);
			}
		}
		// 画爆炸,这里有个bug第一次爆炸没有爆炸效果图出来，检查原因是只一闪而过
		// 添加休眠好了点，不过影响后面爆炸效果，不明白为什么第一次画得快些
		for (int i = 0; i < bs.size(); i++) {
			// System.out.println(bs.size());
			Bomb bb = bs.get(i);
			if (bb.islive) {
				if (bb.getTime() > 6) {
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
					}
					g.drawImage(img, bb.getX(), bb.getY(), 30, 30, this);
				} else if (bb.getTime() > 3) {
					g.drawImage(img, bb.getX(), bb.getY(), 15, 15, this);
				} else if (bb.getTime() > 0) {
					g.drawImage(img, bb.getX(), bb.getY(), 1, 1, this);
				}
			}
			bb.livedown();
			if (bb.getTime() == 0)
				bs.remove(bb);
		}
	}

	public boolean isHitEnemy(Bullet b, Tank ek) {
		if (ek.getDrect() == 0 || ek.getDrect() == 2) {
			// 坦克竖着时宽20，高30
			if (b.getX() >= ek.getX() && b.getX() <= ek.getX() + 20 && b.getY() >= ek.getY()
					&& b.getY() <= ek.getY() + 30) {
				b.islive = false;
				ek.islive = false;
				Bomb bb = new Bomb(ek.getX(), ek.getY());
				bs.add(bb);
				return true;
			}
			return false;
		} else {// 横着宽30，高20；
			if (b.getX() >= ek.getX() && b.getX() <= ek.getX() + 30 && b.getY() >= ek.getY()
					&& b.getY() <= ek.getY() + 20) {
				ek.islive = false;
				b.islive = false;
				Bomb bb = new Bomb(ek.getX(), ek.getY());
				bs.add(bb);
				return true;
			}
			return false;
		}
	}

	public void drawEnemyTank(int x, int y, int drect, Graphics g) {
		drawTank(x, y, g, drect, 1);
	}

	public void drawTank(int x, int y, Graphics g, int drect, int type) {
		switch (type) {
		case 0:
			g.setColor(Color.cyan);
			break;
		case 1:
			g.setColor(Color.GREEN);
		default:
			break;
		}
		switch (drect) {
		case 0:
			// 坦克向上时宽20，高30
			g.fill3DRect(x, y, 5, 30, false);
			g.fill3DRect(x + 15, y, 5, 30, false);
			g.fill3DRect(x + 5, y + 9, 10, 15, false);
			g.drawLine(x + 10, y + 14, x + 10, y);
			break;
		case 1:
			// 坦克向右时宽30，高20
			g.fill3DRect(x, y, 30, 5, false);
			g.fill3DRect(x, y + 15, 30, 5, false);
			g.fill3DRect(x + 7, y + 5, 15, 10, false);
			g.drawLine(x + 13, y + 10, x + 30, y + 10);
			break;
		case 2:
			g.fill3DRect(x, y, 5, 30, false);
			g.fill3DRect(x + 15, y, 5, 30, false);
			g.fill3DRect(x + 5, y + 7, 10, 15, false);
			g.drawLine(x + 10, y + 12, x + 10, y + 30);
			break;
		case 3:
			g.fill3DRect(x, y, 30, 5, false);
			g.fill3DRect(x, y + 15, 30, 5, false);
			g.fill3DRect(x + 8, y + 5, 15, 10, false);
			g.drawLine(x, y + 10, x + 13, y + 10);
			break;
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(50);// 画板刷新频率
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 判断自己坦克的子弹是否击中敌人坦克
			for (int i = 0; i < mytank.mybs.size(); i++) {
				Bullet mb = new Bullet();
				mb = mytank.mybs.get(i);
				if (mb.islive) {
					for (int j = 0; j < eks.size(); j++) {
						Tank ek = new Tank();
						ek = eks.get(j);
						if (ek.islive) {
							isHitEnemy(mb, ek);
						}
					}
				}
			}
			// 判断敌方坦克 的子弹是否击中我方坦克
			for (int i = 0; i < eks.size(); i++) {
				Tank et = new Tank();
				et = eks.get(i);
				for (int j = 0; j < et.mybs.size(); j++) {// 这里写错ek查到死。。。
					Bullet etb = new Bullet();
					etb = et.mybs.get(j);
					if (etb.islive) {
						isHitEnemy(etb, mytank);
					}
				}
			}
			this.repaint();// 刷新
			if (!mytank.islive) {
				JOptionPane.showMessageDialog(this, "你被GG");
				mytank.islive = true;
			}
		}
	}
}