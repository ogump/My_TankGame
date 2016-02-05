package com.gh.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.gh.view.Mypanel;

/**
 * 事件监听
 * 这里有个控制最大发射子弹数
 * @author ganhang
 */
public class KeyListen implements KeyListener{
	private Mypanel mp=null;
	
	public KeyListen(Mypanel mp) {
		super();
		this.mp = mp;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		//方向键监听
		if(e.getKeyCode()==KeyEvent.VK_W){
			mp.mytank.setDrect(0);
			mp.mytank.moveUp();
		}else if(e.getKeyCode()==KeyEvent.VK_S){
			mp.mytank.setDrect(2);
			mp.mytank.moveDown();
		}else if(e.getKeyCode()==KeyEvent.VK_D){
			mp.mytank.setDrect(1);
			mp.mytank.moveRight();
		}else if(e.getKeyCode()==KeyEvent.VK_A){
			mp.mytank.setDrect(3);
			mp.mytank.moveLeft();
		}
		//发射子弹监听
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			if(mp.mytank.mybs.size()<5)
			mp.mytank.shot();
		}
		mp.repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
