package ProgramViewModel.SudokuMenu;

import Program.SudokuProgram.Sudoku;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SudokuDrawer {

    private Sudoku sudoku;

    private int selectedx;
    private int selectedy;

    private double lastx;
    private double lasty;
    private boolean numSelection;
    private double numsize;

    public SudokuDrawer(Sudoku sudoku){
        selectedx = -1;
        selectedy  = -1;
        numSelection = false;
        numsize = 40;
        this.sudoku = sudoku;
    }

    public void ClickEvent(double x, double y){
        if (numSelection) {
            if (x > lastx && x < lastx + 3 * numsize && y > lasty && y < lasty + 4 * numsize){
                int number = (int)(x - lastx) / (int)numsize + (int)(y - lasty) / (int)numsize * 3 + 1;
                if (number > 9){
                    number = 0;
                }
                if (selectedx >= 0 && selectedy >= 0){
                    TypedNumber(number);
                }
            }
            ClickedSudoku(-1, -1);
        }
        else{
            lastx = x;
            lasty = y;
            int nx = (int) (9 * lastx / 540);
            int ny = (int) (9 * lasty / 540);
            ClickedSudoku(nx, ny);
            if (lastx > 540.0 - 3 * numsize){
                lastx -= 3 * numsize;
            }
            if (lasty > 540.0 - 4 * numsize){
                lasty -= 4 * numsize;
            }
        }
        numSelection = !numSelection;
    }

    public void ClickedSudoku(int x, int y){
        selectedx = x;
        selectedy = y;
    }

    public void TypedNumber(int number){
        if (selectedx >= 0 && selectedy >= 0){
            sudoku.setNumber(selectedx, selectedy, number, number == 0);
        }
    }

    public void Draw(GraphicsContext graphicsContext){
        DrawBackground(graphicsContext);
        DrawNumbers(graphicsContext);
        if (numSelection)
            DrawNumberSelector(graphicsContext);
    }

    private void DrawNumberSelector(GraphicsContext graphicsContext){
        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.fillRect(lastx, lasty, 3 * numsize, 4 * numsize);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setFont(new Font(numsize * 0.8));
        for (int i = 0; i < 9; i++){
            graphicsContext.fillText(Integer.toString(i + 1),
                    (i % 3) * numsize + numsize * 0.15 + lastx, (i / 3) * numsize + numsize * 0.85 + lasty);
        }
        graphicsContext.fillText("empty", numsize * 0.15 + lastx, 3 * numsize + numsize * 0.85 + lasty);
    }

    private void DrawNumbers(GraphicsContext graphicsContext){
        Color black = Color.BLACK;
        Color blue = Color.BLUE;
        graphicsContext.setFont(new Font(48));
        for (int i = 0; i < 81; i++){
            int value = sudoku.getNumber(i);
            if (value != 0){
                String num = Integer.toString(value);
                int x = (i % 9) * 60 + 18;
                int y = (i / 9) * 60 + 50;
                if (sudoku.isPreFilled(i)){
                    graphicsContext.setFill(black);
                }
                else{
                    graphicsContext.setFill(blue);
                }
                graphicsContext.fillText(num, x, y);
            }
        }
    }

    private void DrawBackground(GraphicsContext graphicsContext){
        graphicsContext.clearRect(0, 0, 540, 540);
        if (selectedx >= 0 && selectedy >= 0){
            graphicsContext.setFill(Color.LIGHTGRAY);
            graphicsContext.fillRect(selectedx * 60.0, selectedy * 60.0, 60.0, 60.0);
        }
        graphicsContext.setFill(Color.BLACK);
        for (int i = 0; i < 10; i++){
            double linewidth = 4;
            if (i % 3 == 0) {
                linewidth += 2;
            }
            graphicsContext.fillRect(0, i * 60 - linewidth / 2, 540, linewidth);
            graphicsContext.fillRect(i * 60 - linewidth / 2, 0, linewidth, 540);
        }
    }
}
