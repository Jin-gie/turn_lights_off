package Views;

import Controllers.GridController;
import Models.Grid;
import Models.State;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * View for the game grid
 * @author erinb
 */

@SuppressWarnings("deprecation")
public class GridView extends JPanel implements Observer {
    private final GridController gc;
    private final Color green = new Color(0, 121, 41);
    private final Color ligtGreen = new Color(138, 246, 138);
    private WinView wv;

    public GridView(GridController gc) {
        this.gc = gc;
        this.gc.getGrid().addObserver(this);
        this.addMouseListener(gc);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.wv = new WinView(this.getWidth(), this.getHeight(), String.valueOf(this.gc.getGrid().getCounter()));
        if (this.gc.getGrid().getState() == State.FINISH)
            this.add(wv);
        else {
            this.removeAll();
            this.revalidate();
        }

        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(green);
        for (int x = 0; x < Grid.CELL_SIZE * Grid.GRID_SIZE; x += Grid.CELL_SIZE) {
            for (int y = 0; y < Grid.CELL_SIZE * Grid.GRID_SIZE; y += Grid.CELL_SIZE) {
                if (this.gc.getGrid() != null) {
                    // change color regarding the state of the light
                    if (this.gc.getGrid().isOn(this.gc.getGrid().reduce(x), this.gc.getGrid().reduce(y)))
                        g.setColor(ligtGreen);
                    else
                        g.setColor(green);
                }
                g.fillRect(x + 1, y + 1, Grid.CELL_SIZE - 1, Grid.CELL_SIZE - 1);
            }
        }

    }
}
