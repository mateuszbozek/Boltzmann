package Boltzmann;


public class D2Q9 {

    private static double weights[] = {
            4.0 / 9.0,
            1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0,
            1.0 / 36.0, 1.0 / 36.0, 1.0 / 36.0, 1.0 / 36.0};
    // centralna |  prawo |   lewo  |   gora  |   dol   | prawa gora | lewa gora | lewy dol | prawy dol

//    private static Cell tmpNetEquilibrium[][][];  // tablica zawierająca 9 równań ( wartosci ) dla kazdej komórki i współrzednyhc x,y
    private static double tmpNetEquilibrium[][][];  // tablica zawierająca 9 równań ( wartosci ) dla kazdej komórki i współrzednyhc x,y


    public static Cell[][] metodaBoltzmann(Cell netIn[][]) {


        Cell tmp[][] = new Cell[netIn.length][netIn[0].length]; // tablica tymczasowa
        for (int i = 0; i < netIn.length; i++)
            for (int j = 0; j < netIn[0].length; j++) {
                tmp[i][j] = new Cell(0,0);  // tworzenie komorek
            }

        System.out.println(netIn.length + "  " + netIn[0].length);

            tmpNetEquilibrium = new double[netIn.length][netIn[0].length][9];


//        for (int i = 0; i < netIn.length; i++)
//            for (int j = 0; j < netIn[0].length; j++)
//                for(int k=0;k<9;k++){
//                    tmpNetEquilibrium [i][j][k]= new Cell();
//                    System.out.println(tmpNetEquilibrium[i][j][k].getTemperatura()+" "+tmpNetEquilibrium[i][j][k].getTemperatura());
//                }


        for (int i = 0; i < netIn.length; i++) {
            for (int j = 0; j < netIn[0].length; j++) {
                System.out.println(i+" "+j+" "+netIn[i][j].getTemperatura());

                 equilibriumFunction(netIn[i][j], i, j);
            } }

        for(int i=0;i<9;i++)
            System.out.println("Komorka "+i+" "+tmpNetEquilibrium[14][14][i]);

        for(int i=0;i<9;i++)
            System.out.println("Komorka "+i+" "+tmpNetEquilibrium[30][30][i]);


        tmp = colisionFunction(netIn.length,netIn[0].length,tmp);

//      double e [][]={ {0,0},{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,1},{-1,-1},{1,-1} };

        return tmp;
    }

    private static void equilibriumFunction(Cell mainCell, int rzad, int kolumna) {

        for (int i = 0; i < 9; i++) {
     //       System.out.println(rzad+" "+kolumna+" "+i+" "+mainCell.getTemperatura()+" "+weights[i]);
//            tmpNetEquilibrium[rzad][kolumna][i].setTemperatura(mainCell.getTemperatura() * weights[i]);  // liczenie wartości dla komórki
        tmpNetEquilibrium[rzad][kolumna][i]=mainCell.getTemperatura() * weights[i];  // liczenie wartości dla komórki

        }

    }



    public static Cell[][] colisionFunction(int height, int width,Cell [][] tmp) { // tablica pomocnicza która zawierałaby sume temperatury z komorek sasiednich z eguilibrium [][][k]

        for( int i=0;i<height;i++)
            for(int j=0;j<width;j++) {
                if (i == 0 && j == 0) // lewa gora
                    tmp[i][j].setTemperatura(
                            tmpNetEquilibrium[i][j + 1][2]
                                    + tmpNetEquilibrium[i + 1][j + 1][6]
                                    + tmpNetEquilibrium[i + 1][j][3]
                                    + tmpNetEquilibrium[i][j][0]
                                    + tmpNetEquilibrium[i][j][7]
                                    + tmpNetEquilibrium[i][j][2]
                                    + tmpNetEquilibrium[i][j][6]
                                    + tmpNetEquilibrium[i][j][3]
                                    + tmpNetEquilibrium[i][j][5]
                    );
                else if ((i > 0 && i < 99) && (j == 0)) // lewa krawedź
                {
                    tmp[i][j].setTemperatura(
                            tmpNetEquilibrium[i - 1][j][4]
                                    + tmpNetEquilibrium[i - 1][j + 1][7]
                                    + tmpNetEquilibrium[i][j + 1][2]
                                    + tmpNetEquilibrium[i + 1][j + 1][6]
                                    + tmpNetEquilibrium[i + 1][j][3]
                                    + tmpNetEquilibrium[i][j][5]
                                    + tmpNetEquilibrium[i][j][1]
                                    + tmpNetEquilibrium[i][j][8]
                                    + tmpNetEquilibrium[i][j][0]
                    );
                } else if (i == 99 && j == 0) // lewy dol
                {
                    tmp[i][j].setTemperatura(
                            tmpNetEquilibrium[i - 1][j][4]
                                    + tmpNetEquilibrium[i - 1][j + 1][7]
                                    + tmpNetEquilibrium[i][j + 1][2]
                                    + tmpNetEquilibrium[i][j][0]
                                    + tmpNetEquilibrium[i][j][6]
                                    + tmpNetEquilibrium[i][j][2]
                                    + tmpNetEquilibrium[i][j][7]
                                    + tmpNetEquilibrium[i][j][4]
                                    + tmpNetEquilibrium[i][j][8]

                    );
                } else if (i == 0 && j == 99)  // prawa gora
                {
                    tmp[i][j].setTemperatura(
                            tmpNetEquilibrium[i][j - 1][1]
                                    + tmpNetEquilibrium[i + 1][j - 1][5]
                                    + tmpNetEquilibrium[i + 1][j][3]
                                    + tmpNetEquilibrium[i][j][0]
                                    + tmpNetEquilibrium[i][j][6]
                                    + tmpNetEquilibrium[i][j][3]
                                    + tmpNetEquilibrium[i][j][5]
                                    + tmpNetEquilibrium[i][j][1]
                                    + tmpNetEquilibrium[i][j][8]
                    );
                } else if ((i > 0 && i < 99) && j == 99)// prawa krawedz)
                {
                    tmp[i][j].setTemperatura(
                            tmpNetEquilibrium[i - 1][j][4]
                                    + tmpNetEquilibrium[i - 1][i - 1][8]
                                    + tmpNetEquilibrium[i][j - 1][1]
                                    + tmpNetEquilibrium[i + 1][j - 1][5]
                                    + tmpNetEquilibrium[i + 1][j][3]
                                    + tmpNetEquilibrium[i][j][0]
                                    + tmpNetEquilibrium[i][j][5]
                                    + tmpNetEquilibrium[i][j][1]
                                    + tmpNetEquilibrium[i][j][8]
                    );
                } else if (i == 99 && j == 99) // prawy dol
                {
                    tmp[i][j].setTemperatura(
                            tmpNetEquilibrium[i - 1][j][4]
                                    + tmpNetEquilibrium[i - 1][j - 1][8]
                                    + tmpNetEquilibrium[i][j - 1][1]
                                    + tmpNetEquilibrium[i][j][0]
                                    + tmpNetEquilibrium[i][j][5]
                                    + tmpNetEquilibrium[i][j][1]
                                    + tmpNetEquilibrium[i][j][8]
                                    + tmpNetEquilibrium[i][j][4]
                                    + tmpNetEquilibrium[i][j][7]
                    );
                } else if (i == 0 && (j > 0 && j < 99)) { // gora
                    tmp[i][j].setTemperatura(
                            tmpNetEquilibrium[i][j - 1][1]
                                    + tmpNetEquilibrium[i + 1][j - 1][5]
                                    + tmpNetEquilibrium[i + 1][j][3]
                                    + tmpNetEquilibrium[i + 1][j + 1][6]
                                    + tmpNetEquilibrium[i][j + 1][2]
                                    + tmpNetEquilibrium[i][j][0]
                                    + tmpNetEquilibrium[i][j][6]
                                    + tmpNetEquilibrium[i][j][3]
                                    + tmpNetEquilibrium[i][j][5]
                    );
                } else if (i == 99 && (j > 0 && j < 99)) { // dol
                    tmp[i][j].setTemperatura(
                            tmpNetEquilibrium[i][j-1][1]
                            +tmpNetEquilibrium[i-1][j-1][8]
                            +tmpNetEquilibrium[i-1][j][4]
                            +tmpNetEquilibrium[i-1][j+1][7]
                            +tmpNetEquilibrium[i][j+1][2]
                            +tmpNetEquilibrium[i][j][0]
                            +tmpNetEquilibrium[i][j][7]
                            +tmpNetEquilibrium[i][j][4]
                            +tmpNetEquilibrium[i][j][8]
                    );
                }
                else {
                    tmp[i][j].setTemperatura(
                            tmpNetEquilibrium[i][j][0]
                                    +tmpNetEquilibrium[i-1][j-1][8]
                                    +tmpNetEquilibrium[i][j-1][1]
                                    +tmpNetEquilibrium[i+1][j-1][5]
                                    +tmpNetEquilibrium[i-1][j][4]
                                    +tmpNetEquilibrium[i+1][j][3]
                                    +tmpNetEquilibrium[i-1][j+1][7]
                                    +tmpNetEquilibrium[i][j+1][2]
                                    +tmpNetEquilibrium[i+1][j+1][6]
                    );
                }
            }
            return tmp;
    }

    public static void streamingFunction(Cell k) {

    }

}
