import javax.swing.*;

public class Program {

    public static void main(String[] args)
    {
        Integer[] a = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15};
        Game myGame = new Game(a);
        JFrame app = new FifteenGUI(Game.ReadFile("C:\\Users\\Мария\\IdeaProjects\\untitled1\\src\\text.txt"));
        app.setVisible(true);
    }
}