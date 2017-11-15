import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Game implements IPlayable
{
    public int[][] Field;
    public Map<Integer, Position> Coordinates;

    public Game()
    {
        Field = new int[4][4];
        Coordinates = new HashMap<>();
        writeData(getRandomSequence());
    }

    public Game(Integer[] numbers)
    {
        try
        {
            check(numbers);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }

        double size = Math.sqrt(numbers.length);
        Field = new int[(int)size][(int)size];
        Coordinates = new HashMap<>();
        writeData(numbers);
    }

    public static Game ReadFile(String filename)
    {
        ArrayList<Integer> a = new ArrayList<>();
        try (Scanner sc = new Scanner(new FileReader(filename)))
        {
            while (sc.hasNextInt())
            {
                a.add(sc.nextInt());
            }
        }
        catch (IOException ex)
        {
            System.out.print(ex.getMessage());
        }

        Integer[] temp = null;
        temp = a.toArray(new Integer[a.size()]);

        Game MyGame = new Game(temp);
        return MyGame;
    }

    private void check(Integer[] numbers)
    {
        double size = Math.sqrt(numbers.length);
        int flag = 0;

        if ((size % 1) > 0) {
            throw new IllegalArgumentException("Wrong count of number in parameter 'numbers'");
        }

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == 0) {
                flag++;
            }
            for (int j = 0; j < numbers.length; j++) {
                if (i != j && numbers[i] == numbers[j]) {
                    throw new IllegalArgumentException("Repeat number in parameter 'numbers'");
                }
            }
        }

        if (flag == 0) {
            throw new IllegalArgumentException("Need empty block in parameter 'numbers'");
        }

    }

    private void swap(Position a, Position b)
    {
        Coordinates.put(Field[a.X][a.Y], b);
        Coordinates.put(Field[b.X][b.Y], a);
        int x = Field[a.X][a.Y];
        Field[a.X][a.Y] = Field[b.X][b.Y];
        Field[b.X][b.Y] = x;
    }

    private void writeData(Integer[] numbers)
    {
        int index = 0;
        for (int i = 0; i < Field[0].length; i++)
        {
            for (int j = 0; j < Field[0].length; j++)
            {
                Field[i][j] = numbers[index];
                Position temp = new Position();
                temp.X = i;
                temp.Y = j;
                Coordinates.put(numbers[index], temp);
                index++;
            }
        }
    }

    private Integer[] getRandomSequence()
    {
        List<Integer> fill = new ArrayList<>();
        for ( int i = 1; i < Side() * Side(); i++ ) {
            fill.add(i);
        }
        Collections.shuffle(fill);

        Integer[] numbers = null;
        numbers = fill.toArray(new Integer[fill.size()]);

        int count = 0;
        for (int i = 0; i < numbers.length - 1; i++)
        {
            for (int j = i + 1; j < numbers.length; j++)
            {
                if (numbers[i] > numbers[j])
                {
                    count++;
                }
            }
        }

        if (count % 2 != 0)
        {
            for (int i = 0; i < numbers.length; i++)
            {
                if (numbers[i] == numbers.length - 1)
                {
                    numbers[i] = numbers.length - 2;
                }
                else if (numbers[i] == numbers.length - 2)
                {
                    numbers[i] = numbers.length - 1;
                }
            }
        }
        List<Integer> temp = new ArrayList<>();
        temp = Arrays.asList(numbers);
        fill.add(0);
        Integer[] result = null;
        result = fill.toArray(new Integer[fill.size()]);

        return result;
    }

    public int Side() {
        return Field[0].length;
    }

    public int GetValue(int x, int y)
    {
        return Field[x][y];
    }

    public boolean IsFinished()
    {
        if (Field[Side() - 1][Side() - 1] == 0)
        {
            int count = 1;
            for (int i = 0; i < Side(); i++)
            {
                for (int j = 0; j < Side(); j++)
                {
                    if (Field[i][j] != count && Field[i][j] != 0)
                    {
                        return false;
                    }
                    count++;
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    public void Randomize()
    {
        Coordinates.clear();
        writeData(getRandomSequence());
    }

    public void Shift(int value)
    {
        if (value <= 0 || value > Side() * Side() - 1)
        {
            throw new IllegalArgumentException("The value does not exist");
        }

        Position temp = new Position();
        Position temp0 = new Position();
        temp = Coordinates.get(value);
        temp0 = Coordinates.get(0);
        if ((Math.abs(temp.X - temp0.X) + Math.abs(temp.Y - temp0.Y)) == 1)
        {
            swap(temp, temp0);
        }

        else
        {
            throw new IllegalArgumentException("The value must be next to the empty block");
        }
    }
}
