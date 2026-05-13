package com.sudoku.ui;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainWindow extends JFrame {

	private JTextField[][] cells;

	public MainWindow() {

		initializeWindow();

		createBoard();

		setVisible(true);
	}

	private void initializeWindow() {

		setTitle("Sudoku");

		setSize(700, 700);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLocationRelativeTo(null);

		setLayout(new BorderLayout());

	}

	private void createBoard() {

		JPanel boardPanel = new JPanel();

		boardPanel.setLayout(new GridLayout(9, 9));

		cells = new JTextField[9][9];

		Font cellFont = new Font("Arial", Font.BOLD, 20);

		for (int row = 0; row < 9; row++) {

			for (int col = 0; col < 9; col++) {

				JTextField cell = new JTextField();

				cell.setHorizontalAlignment(JTextField.CENTER);

				cell.setFont(cellFont);

				int top = (row % 3 == 0) ? 3 : 1;
				int left = (col % 3 == 0) ? 3 : 1;
				int bottom = (row == 8) ? 3 : 1;
				int right = (col == 8) ? 3 : 1;

				Border border = BorderFactory.createMatteBorder(
						top,
						left,
						bottom,
						right,
						Color.BLACK
						);

				cell.setBorder(border);

				cells[row][col] = cell;
				//solo permite numeros bloquea las letras
				cell.addKeyListener(new KeyAdapter() {

					@Override
					public void keyTyped(KeyEvent e) {

						char input = e.getKeyChar();

						if (!Character.isDigit(input)
								|| input == '0') {

							e.consume();

						}
					}
				});
				boardPanel.add(cell);

			}
		}

		add(boardPanel, BorderLayout.CENTER);

	}
}