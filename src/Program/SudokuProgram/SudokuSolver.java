package Program.SudokuProgram;

class SudokuSolver{

    private boolean solvable;
    int emptyCellCount;
    boolean changed;
    private SudokuCell[] grid;
    SudokuPart[] rows;
    SudokuPart[] cols;
    SudokuPart[] squs;

    private Sudoku sudoku;


    SudokuSolver(Sudoku sudoku){
        CopyOf(sudoku);
    }

    private SudokuSolver(SudokuSolver other){
        CopyOf(other);
    }

    private void Init(){
        grid = new SudokuCell[81];
        rows = new SudokuPart[9];
        cols = new SudokuPart[9];
        squs = new SudokuPart[9];
        for (int i = 0; i < 9; i++){
            rows[i] = new SudokuPart();
            cols[i] = new SudokuPart();
            squs[i] = new SudokuPart();
        }
        for (int i = 0; i < 81; i++){
            grid[i] = new SudokuCell(i, this);
        }
        emptyCellCount = 81;
        solvable = true;
    }

    private void CopyOf(Sudoku sudoku){
        this.sudoku = sudoku;
        Init();
        for (int i = 0; i < 81; i++){
            solvable &= grid[i].GiveValue(sudoku.getNumber(i));
        }
    }

    private void CopyOf(SudokuSolver other){
        sudoku = other.sudoku;
        Init();
        for (int i = 0; i < 81; i++){
            grid[i].CopyOf(other.grid[i]);
        }
        for (int i = 0; i < 9; i++){
            rows[i].CopyOf(other.rows[i]);
            cols[i].CopyOf(other.cols[i]);
            squs[i].CopyOf(other.squs[i]);
        }
        emptyCellCount = other.emptyCellCount;
        solvable = other.solvable;
        changed = other.changed;
    }

    boolean Solve() {
        if (SolveSudoku()){
            FillSudoku();
            return true;
        }
        return false;
    }

    private boolean SolveSudoku(){
        if (!solvable){
            return false;
        }
        while (emptyCellCount != 0) {
            changed = false;
            for (SudokuCell cell : grid){
                if (!cell.Filled()){
                    if (!cell.Check()){
                        return false;
                    }
                }
            }
            if (!changed) {
                return TryAgain();
            }
        }
        return true;
    }

    private boolean TryAgain() {
        int index = SearchBestChance();
        if (index < 0){
            return false;
        }
        SudokuSolver save = new SudokuSolver(this);
        for (int value = 1; value < 10; value++){
            if (!save.grid[index].excluded[value - 1]){
                grid[index].GiveValue(value);
                if (SolveSudoku()){
                    return true;
                }
                if (!save.grid[index].ExcludeValue(value)){
                    return false;
                }
                CopyOf(save);
            }
        }
        return false;
    }

    private int SearchBestChance(){
        int best = -1;
        int index = -1;
        for (int i = 0; i < 81; i++){
            if (!grid[i].Filled() && grid[i].excludedNumber > best){
                best = grid[i].excludedNumber;
                index = i;
            }
        }
        return index;
    }

    private void FillSudoku(){
        for (int i = 0; i < 81; i++){
            sudoku.setNumber(i, grid[i].value);
        }
    }
}

class SudokuPart{

    SudokuCell[] cells;
    boolean[] included;


    SudokuPart() {
        cells = new SudokuCell[9];
        included = new boolean[9];
        for (int i = 0; i < 9; i++){
            included[i] = false;
        }
    }

    void CopyOf(SudokuPart specimen){
        System.arraycopy(specimen.included, 0, included, 0, 9);
    }
}

class SudokuCell{
    int value;
    int excludedNumber;
    boolean[] excluded;
    private SudokuPart row;
    private SudokuPart col;
    private SudokuPart squ;
    private SudokuSolver sudoku;



    SudokuCell(int index, SudokuSolver parent){
        value = 0;
        excludedNumber = 0;
        excluded = new boolean[9];
        for (int i = 0; i < 9; i++)
            excluded[i] = false;
        sudoku = parent;
        row = sudoku.rows[index / 9];
        col = sudoku.cols[index % 9];
        squ = sudoku.squs[(index / 27) * 3 + (index % 9) / 3];
        row.cells[index % 9] = this;
        col.cells[index / 9] = this;
        squ.cells[(index % 3) + (index / 9) % 3 * 3] = this;
    }

    void CopyOf(SudokuCell specimen){
        value = specimen.value;
        excludedNumber = specimen.excludedNumber;
        System.arraycopy(specimen.excluded, 0, excluded, 0, 9);
    }

    boolean GiveValue(int value){
        if (value > 0 && value <= 9){
            if (row.included[value - 1] || col.included[value - 1] || squ.included[value - 1]){
                return false;
            }
            this.value = value;
            for (int i = 0; i < 9; i++){
                excluded[i] = (i != value-1);
            }
            excludedNumber = 8;
            row.included[value - 1] = true;
            col.included[value - 1] = true;
            squ.included[value - 1] = true;
            sudoku.emptyCellCount--;
        }
        return true;
    }

    boolean ExcludeValue(int value) {
        if (value == 0)
            return true;
        if (this.value == value)
            return false;
        if (!excluded[value - 1]){
            excluded[value - 1] = true;
            excludedNumber++;
            if (excludedNumber == 8){
                for (int i = 0; i < 9; i++){
                    if (!excluded[i]){
                        if (row.included[i] || col.included[i] || squ.included[i]){
                            return false;
                        }
                        this.value = i + 1;
                        row.included[i] = true;
                        col.included[i] = true;
                        squ.included[i] = true;
                        sudoku.changed = true;
                        sudoku.emptyCellCount--;
                    }
                }
            }
        }
        return true;
    }

    boolean Check() {
        for (int i = 0; i < 9; i++){
            if (!ExcludeValue(row.cells[i].value))
                return false;
            if (Filled())
                return true;
            if (!ExcludeValue(col.cells[i].value))
                return false;
            if (Filled())
                return true;
            if (!ExcludeValue(squ.cells[i].value))
                return false;
            if (Filled())
                return true;
        }
        return true;
    }

    boolean Filled(){
        return value != 0;
    }
}
