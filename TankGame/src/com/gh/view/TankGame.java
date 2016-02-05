package com.gh.view;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.gh.control.KeyListen;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * 1、画出坦克
 * 2、我的坦克可以上下移动
 * 3、可以发射子弹，子弹连发（或者最多5发）
 * 4、当我的坦克击中敌人坦克时，敌人坦克消失（或者爆炸效果）
 * 5、我被击中也显示爆炸效果。
 * 6、游戏开始选项
 * @author ganhang
 *
 */
public class TankGame {
	private JFrame frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TankGame window = new TankGame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TankGame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u5766\u514B\u5927\u6218");
		frame.setBounds(450, 70, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("\u9009\u9879");
		mnNewMenu.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("开始游戏");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mypanel mp = new Mypanel();
				Thread t=new Thread(mp);
				t.start();
				frame.getContentPane().add(mp, BorderLayout.CENTER);
				frame.addKeyListener(new KeyListen(mp));
				frame.setVisible(true);
			}
		});
		
		mntmNewMenuItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_1 = new JMenu("\u6E38\u620F\u8BF4\u660E");
		mnNewMenu_1.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("\u5173\u4E8E\u6E38\u620F");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "上:W, 下:A ,左:S, 右:D ,射击：空格\n Made by Ganhang");;
			}
		});
		mntmNewMenuItem_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		mnNewMenu_1.add(mntmNewMenuItem_1);

	}

}
