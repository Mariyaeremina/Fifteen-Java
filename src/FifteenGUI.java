import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FifteenGUI extends JFrame
{
    private JPanel panel = new JPanel(new GridLayout(4, 4, 2, 2));
    private IPlayable interfaceGame;

    public FifteenGUI(Game game)
    {
        super("Пятнашки");
        interfaceGame = game;
        setBounds(300, 300, 400, 400);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton newGame = new JButton("Новая игра");
        newGame.addActionListener(new StartNewGameListener());
        newGame.setFocusable(false);

        Container container = getContentPane();
        container.add(panel);
        container.add(newGame, BorderLayout.PAGE_END);
        repaintField();
    }

    public void repaintField()
    {
        panel.removeAll();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JButton button = new JButton(Integer.toString(interfaceGame.GetValue(i, j)));
                button.setFocusable(false);
                panel.add(button);
                if (interfaceGame.GetValue(i, j) == 0)
                {
                    button.setVisible(false);
                }
                else {
                    button.addActionListener(new ClickListener());
                }
            }
        }
        panel.validate();
        panel.repaint();
    }

    private class ClickListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            button.setVisible(false);
            String name = button.getText();
            change(Integer.parseInt(name));
        }
    }

    private class StartNewGameListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            interfaceGame.Randomize();
            repaintField();
        }
    }

    public void change(int num) {
        try
        {
            interfaceGame.Shift(num);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
        repaintField();

        if (interfaceGame.IsFinished()) {
            JOptionPane.showMessageDialog(null, "YOU WIN!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            interfaceGame.Randomize();
            repaintField();
        }
    }
}
