import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;


public class BusicoMan{

	private int life;
	private int[][] map = new int [15][15];
	private boolean flag;
	public int posx =0, posy=0, a,b;
	private static int counter =61;


	//components

	private JFrame game;

	private JPanel center;
	private JPanel top;

	private JLabel lbltime;
	public JLabel lbllife;

	public void setLife(int life){
		lbltime.setText("<html><h2>" +life+ "</h2></html>");
	}

	private JButton[][] blocks;
	private JButton btnclose;

	//constructor

	public BusicoMan(){

		game = new JFrame();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
		game.setExtendedState(JFrame.MAXIMIZED_BOTH);
		game.setUndecorated(true);

		life = 3;
		flag=false;

		top = new JPanel();
		top.setLayout(new GridLayout(1,3));
		top.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		center = new JPanel();
		center.setLayout(new GridLayout(15,15));
		center.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		//labels
		lbllife = new JLabel("<html><h2>LIFE LEFT: " +life+ "</h2></html>");
		lbltime = new JLabel("<html><h2>TIME:</h2></html>", SwingConstants.CENTER);

		//buttons

		btnclose = new JButton("EXIT");
		btnclose.setPreferredSize(new Dimension(40,40));
		btnclose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				game.setVisible(false);
				System.out.println("\n\n\n");
				System.exit(0);
			}
		});
		blocks = new JButton[15][15];

		//apply to frame
		top.add(lbllife);
		top.add(lbltime);
		top.add(btnclose);

		//make the blocks

		for(int i = 0; i<15; i++){
			for(int j = 0; j<15; j++){
				map[i][j] = 0;
				center.add(blocks[i][j] = new JButton());
				blocks[i][j].setBackground(Color.GRAY);
				blocks[i][j].setEnabled(false);
				blocks[i][j].addMouseListener(new ListenForMouse(i,j,lbllife, lbltime));

			}
		}

		setStart();
		setPowers();
		setBombs();
		setFinish();

		//display the map
		System.out.print("\n");
		for(int i = 0; i<15; i++){
			for(int j = 0; j<15; j++){
				System.out.print(map[i][j] + " ");
			}
			System.out.print("\n");
		}


		game.add(top, BorderLayout.NORTH);
		game.add(center, BorderLayout.CENTER);

		game.pack();
		game.setSize(650,650);
		game.setLocationRelativeTo(null);
		game.setVisible(true);
	}

	public void setPowers(){			//there are 2 powers per row

		int rand;
		int count;

		for(int i = 0; i<15; i++){
			count=0;
			flag=false;
			for(int j = 0; j<15; j++){
				rand = (int)(Math.random()*15);
				if(map[i][j] == 0){
					map[i][rand] = 3;
					count++;
				}
				else{							//checks if random is taken, replaces another random
					rand = (int)(Math.random()*15);
					map[i][rand] = 3;
					count++;
				}
				if(count==2){
					break;
				}
			}
		}
	}

	public void setBombs(){			//there are 3 bombs per row

		int rand;
		int count;

		for(int i = 0; i<15; i++){
			count=0;
			flag=false;
			for(int j = 0; j<15; j++){
				rand = (int)(Math.random()*15);
				if(map[i][j] == 0){
					map[i][rand] = 4;
					count++;
				}
				else{							//checks if random is taken, replaces another random
					rand = (int)(Math.random()*15);
					map[i][rand] = 4;
					count++;
				}
				if(count==3){
					break;
				}
			}
		}
	}

	public void setStart(){

		map[0][0] = 1;
		blocks[0][0].setBackground(Color.BLUE);
		blocks[0][0].setEnabled(false);
		blocks[0][1].setBackground(Color.WHITE);
		blocks[0][1].setEnabled(true);
		blocks[1][0].setBackground(Color.WHITE);
		blocks[1][0].setEnabled(true);
	}

	public void setFinish(){

		int rand = (int)(Math.random()*15);
		blocks[14][rand].setBackground(Color.ORANGE);
		map[14][rand] = 6;
	}

	public class ListenForMouse implements MouseListener{

		private int x;
		private int y;
		private JLabel lifer;
		private JLabel timer;
		private boolean flag =false;

		public ListenForMouse(int a, int b, JLabel lbl1, JLabel lbl2){
			this.x = a;
			this.y = b;
			this.lifer = lbl1;
			this.timer = lbl2;
		}

		public void mouseClicked(MouseEvent e){
			if(blocks[x][y].isEnabled() == true){
				clear();
				if(map[x][y] == 6){
					JOptionPane.showMessageDialog (null, "You have reached the Yellow Tile!", "CONGRATULATIONS!", JOptionPane.INFORMATION_MESSAGE);
					flag = true;
				}
				blocks[x][y].setBackground(Color.BLUE);
				blocks[x][y].setEnabled(false);
				setGreen();
				alert();
				map[x][y] = 1;
				if(flag == true){
					System.exit(0);
				}
			}

		}
		public void mouseEntered(MouseEvent e){

		}
		public void mouseExited(MouseEvent e){

		}
		public void mousePressed(MouseEvent e){

		}
		public void mouseReleased(MouseEvent e){

		}
		public void setGreen(){
			if(x>0){
				blocks[x-1][y].setBackground(Color.WHITE);
				blocks[x-1][y].setEnabled(true);
			}
			if(x<14){
				blocks[x+1][y].setBackground(Color.WHITE);
				blocks[x+1][y].setEnabled(true);
			}
			if(y>0){
				blocks[x][y-1].setBackground(Color.WHITE);
				blocks[x][y-1].setEnabled(true);
			}
			if(y<14){
				blocks[x][y+1].setBackground(Color.WHITE);
				blocks[x][y+1].setEnabled(true);
			}
		}

		public void clear(){
			for(int i=0; i<15; i++){
				for(int j=0; j<15; j++){
					if(map[i][j] != 6){
						if(map[i][j] !=1){
							blocks[i][j].setBackground(Color.GRAY);
							blocks[i][j].setEnabled(false);
						}
					}
				}
			}
		}

		public void alert(){

			if(map[x][y] == 3){
				JOptionPane.showMessageDialog (null, "You can now see adjacent tiles.", "YOU'VE ACQUIRED POWER", JOptionPane.INFORMATION_MESSAGE);
				blocks[x][y].setBackground(Color.YELLOW);
				blocks[x][y].setEnabled(false);

				//check for bombs
				if(x<14){
					if(map[x+1][y] == 4){
						blocks[x+1][y].setBackground(Color.RED);
						blocks[x+1][y].setEnabled(true);
						map[x][y] =1;
					}
				}
				if(x>0){
					if(map[x-1][y] == 4){
						blocks[x-1][y].setBackground(Color.RED);
						blocks[x-1][y].setEnabled(true);
						map[x][y] =1;
					}
				}
				if(y<14){
					if(map[x][y+1] == 4){
						blocks[x][y+1].setBackground(Color.RED);
						blocks[x][y+1].setEnabled(true);
						map[x][y] =1;
					}
				}
				if(y>0 ){
					if(map[x][y-1] == 4){
						blocks[x][y-1].setBackground(Color.RED);
						blocks[x][y-1].setEnabled(true);
						map[x][y] =1;
					}
				}

				//checks for power
				if(y>0){
					if(map[x][y-1] == 3){
						blocks[x][y-1].setBackground(Color.GREEN);
						blocks[x][y-1].setEnabled(true);
						map[x][y] =1;
					}
				}
				if(y<14){
					if(map[x][y+1] == 3){
						blocks[x][y+1].setBackground(Color.YELLOW);
						blocks[x][y+1].setEnabled(true);
						map[x][y] =1;
					}
				}
				if(x>0){
					if(map[x-1][y] == 3){
						blocks[x-1][y].setBackground(Color.YELLOW);
						blocks[x-1][y].setEnabled(true);
						map[x][y] =1;
					}
				}
				if(x<14){
					if(map[x+1][y] == 3){
						blocks[x+1][y].setBackground(Color.YELLOW);
						blocks[x+1][y].setEnabled(true);
						map[x][y] =1;
					}
				}
			}
			else if(map[x][y] == 4){
				JOptionPane.showMessageDialog (null, "You have stepped on a bomb. You lost 1 life.", "BOMB DETECTED", JOptionPane.WARNING_MESSAGE);
				blocks[x][y].setBackground(Color.RED);
				blocks[x][y].setEnabled(false);
				life--;
				lifer.setText("<html><h2>LIFE LEFT: " +life+ "</h2></html>");
				if(life == 0){
					JOptionPane.showMessageDialog (null, "You have no life left.", "GAME OVER", JOptionPane.WARNING_MESSAGE);
					System.exit(0);

				}
			}
		}
	}

	public static void main(String[] args){

		BusicoMan b = new BusicoMan();
		JLabel timerr = new JLabel();

		ActionListener down = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				counter-=1;
				b.setLife(counter);
				if(counter == 0){
					JOptionPane.showMessageDialog (null, "You have no time left.", "GAME OVER", JOptionPane.WARNING_MESSAGE);
					System.exit(0);
				}
		 	}
		 };

		Timer timer = new Timer(1000, down);
 		timer.start();

		/*
				0 = empty      GRAY
				1 = location    BLUE
				2 = past          WHITE
				3 = powers      YELLOW
				4 = bombs     RED
				5 = next         GREEN
		        6 = final          ORANGE
		*/
	}
}

