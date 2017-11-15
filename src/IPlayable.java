public interface IPlayable {
    int Side();
    boolean IsFinished();
    void Randomize();
    void Shift(int value);
    int GetValue(int x, int y);
}
