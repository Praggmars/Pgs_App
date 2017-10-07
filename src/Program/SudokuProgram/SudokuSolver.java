package Program.SudokuProgram;

class SudokuSolver{

    private boolean valid;  //megoldható-e
    int emptyCell;  //üres cellák száma
    boolean changed;    //változott-e
    private SudokuCell[] grid;  //cellák
    SudokuPart[] rows;  //sorok
    SudokuPart[] cols;  //oszlopok
    SudokuPart[] squs;  //négyzetek

    private Sudoku sudoku;


    /**
     * megoldó kitöltése sudoku alapján
     * @param sudoku sudoku
     */
    SudokuSolver(Sudoku sudoku){
        CopyOf(sudoku);
    }

    private SudokuSolver(SudokuSolver specimen){
        CopyOf(specimen);
    }

    private void SetUp(){
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
        emptyCell = 81;
        valid = true;
    }

    private void CopyOf(Sudoku sudoku){
        this.sudoku = sudoku;
        SetUp();
        for (int i = 0; i < 81; i++){
            valid &= grid[i].GiveValue(sudoku.getNumber(i));
        }
    }

    private void CopyOf(SudokuSolver specimen){
        sudoku = specimen.sudoku;
        SetUp();
        for (int i = 0; i < 81; i++){
            grid[i].CopyOf(specimen.grid[i]);
        }
        for (int i = 0; i < 9; i++){
            rows[i].CopyOf(specimen.rows[i]);
            cols[i].CopyOf(specimen.cols[i]);
            squs[i].CopyOf(specimen.squs[i]);
        }
        emptyCell = specimen.emptyCell;
        valid = specimen.valid;
        changed = specimen.changed;
    }

    boolean Solve() {
        if (SolveSudoku()){
            FillSudoku();
            return true;
        }
        return false;
    }

    /**
     * megoldja a sudokut
     */
    private boolean SolveSudoku(){
        if (!valid){
            return false;
        }
        //amíg van kitöltetlen cella, addig dolgozik
        while (emptyCell != 0) {
            changed = false;
            for (SudokuCell cell : grid){
                if (!cell.Filled()){
                    if (!cell.Check()){
                        return false;
                    }
                }
            }
            if (!changed) {
                //ha nem történt változás, mással próbálkozik
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


    //mezők létrehozása, inicializálás
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
    int value;  //cella értéke, 0 ha kitöltetlen
    int excludedNumber; //mennyi szám nem lehet benne
    boolean[] excluded; //melyik számok nem lehetnek benne
    private SudokuPart row; //sora
    private SudokuPart col; //oszlopa
    private SudokuPart squ; //négyzete
    private SudokuSolver sudoku;    //sudokuja



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

    /**
     * Értéket ad a cellának
     * @param value Érték
     */
    boolean GiveValue(int value){
        //érvényes érték
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
            sudoku.emptyCell--;
        }
        return true;
    }

    /**
     * Kizár egy értéket a lehetséges értékek közül
     * @param value Kizárandó érték
     * @return  Hamis, ha megoldhatatlan
     */
    boolean ExcludeValue(int value) {
        if (value == 0)
            return true;
        if (this.value == value)
            return false;
        if (!excluded[value - 1]){
            excluded[value - 1] = true;
            excludedNumber++;
            //ha csak egy szám lehetséges, azt beírja
            if (excludedNumber == 8){
                for (int i = 0; i < 9; i++){
                    if (!excluded[i]){
                        //ha nem lehet beírni, hiba
                        if (row.included[i] || col.included[i] || squ.included[i]){
                            return false;
                        }
                        //beírás
                        this.value = i + 1;
                        row.included[i] = true;
                        col.included[i] = true;
                        squ.included[i] = true;
                        sudoku.changed = true;
                        sudoku.emptyCell--;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Megnézi, hogy milyen értékek nem lehetnek a cellában, kizárja ami nem lehet
     * Ha egy érték lehetséges, beírja
     * @return  Hamis, ha megoldhatatlan
     */
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

    /**
     * Ki van-e töltve
     * @return igaz, ha ki van töltve
     */
    boolean Filled(){
        return value != 0;
    }
}
