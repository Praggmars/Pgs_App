package Program.SudokuProgram;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Sudoku {

    private int[] numbers;
    private boolean[] prefillednums;

    public int[] getNumbers(){
        return numbers;
    }

    public void setNumber(int x, int y, int value, boolean solving){
        setNumber(getIndex(x, y), value, solving);
    }
    public void setNumber(int index, int value, boolean preFilled){
        if (value < 1 || value > 9){
            numbers[index] = 0;
            prefillednums[index] = false;
        }
        else{
            numbers[index] = value;
            prefillednums[index] = preFilled;
        }
    }
    public void setNumber(int x, int y, int value){
        setNumber(getIndex(x, y), value);
    }
    public void setNumber(int index, int value){
        if (value < 1 || value > 9){
            numbers[index] = 0;
        }
        else{
            numbers[index] = value;
        }
    }

    public int getNumber(int x, int y){
        return numbers[getIndex(x, y)];
    }
    public int getNumber(int index){
        return numbers[index];
    }

    public boolean isPreFilled(int x, int y){
        return prefillednums[getIndex(x, y)];
    }
    public boolean isPreFilled(int index){
        return prefillednums[index];
    }

    private int getIndex(int x, int y){
        return x+9*y;
    }

    public Sudoku(){
        numbers = new int[81];
        prefillednums = new boolean[81];
        Clear();
    }

    public void Clear(){
        for (int i = 0; i < 81; i++){
            numbers[i] = 0;
            prefillednums[i] = false;
        }
    }

    public boolean Solve(){
        return new SudokuSolver(this).Solve();
    }

    public void Load(int[] grid) {
        Clear();
        int length = Integer.min(grid.length, this.numbers.length);
        for (int i = 0; i < length; i++) {
            if (grid[i] <= 9 && grid[i] > 0) {
                this.numbers[i] = grid[i];
                prefillednums[i] = true;
            }
            else{
                this.numbers[i] = 0;
                prefillednums[i] = false;
            }
        }
    }

    public boolean Load(String filepath) {
        try(Reader input = new FileReader(filepath)) {
            Load(input);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public boolean Load(File file){
        try(Reader input = new FileReader(file)) {
            Load(input);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public void Load(Reader input) throws IOException {
        Clear();
        int i = 0;
        while (i < 81){
            int in = input.read();
            if (in == -1){
                break;
            }
            if (in >= '0' && in <= '9'){
                numbers[i] = in - '0';
                prefillednums[i] = numbers[i] != 0;
                i++;
            }
        }
    }
}
