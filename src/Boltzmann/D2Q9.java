package Boltzmann;


public class D2Q9 {
/*
        6    3    5
          \  |   /
            \| /
        2----0----1
           / | \
         /   |  \
        7    4    8

*/
    private static double weights[] = {
            4.0 / 9.0,
            1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0,
            1.0 / 36.0, 1.0 / 36.0, 1.0 / 36.0, 1.0 / 36.0};
    // centralna |  prawo |   lewo  |   gora  |   dol   | prawa gora | lewa gora | lewy dol | prawy dol

    private static double tmpNetEquilibrium[][][];  // tablica zawierająca 9 równań ( wartosci ) dla kazdej komórki i współrzednyhc x,y
    private static Cell out[][];

    private static double relaxTime = 0.9;

    public static Cell[][] metodaBoltzmann(Cell netIn[][],int i) {

            if (i==0)
            {
                setTmpNetEquilibrium(netIn);

                for (int j = 0; j < netIn.length; j++) {
                    for (int k = 0; k < netIn[0].length; k++) {
                        equilibriumFunction(netIn[j][k], j, k); // equilibrium
                    }
                }

                out = colisionFunction(netIn.length, netIn[0].length); // colision   1 krok
            }
            else
                {

                setTmpNetEquilibrium(netIn);

                for (int j = 0; j < netIn.length; j++) {
                    for (int k = 0; k < netIn[0].length; k++) {
                        equilibriumFunction(netIn[j][k], j, k); // equilibrium
                    }
                }

                out = colisionFunction(netIn.length, netIn[0].length,netIn); // colision   1 krok
            }
        return out;
    }

    private static void setTmpNetEquilibrium(Cell netIn[][])
    {
        tmpNetEquilibrium = new double[netIn.length][netIn[0].length][9];
        for (int i = 0; i < netIn.length; i++)
            for (int j = 0; j < netIn[0].length; j++)
                for(int k=0;k<9;k++){

                    tmpNetEquilibrium[i][j][k]=0;
                }
    }

    private static void equilibriumFunction(Cell mainCell, int rzad, int kolumna) {

        for (int i = 0; i < 9; i++) {
        tmpNetEquilibrium[rzad][kolumna][i]=mainCell.getTemperatura() * weights[i];  // liczenie wartości dla komórki
        }
    }


    private static Cell[][] colisionFunction(int height, int width) { // tablica pomocnicza która zawierałaby sume temperatury z komorek sasiednich z eguilibrium [][][k]

        Cell tmp[][] = new Cell[height][width]; // tablica tymczasowa
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tmp[i][j] = new Cell(0, 0);  // tworzenie komorek
            }
        }

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
                } else if ((i > 0 && i < 99) && (j == 99))// prawa krawedz)
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
                }
                else if (i == 99 && j == 99) // prawy dol
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
                }
                else if (i == 0 && (j > 0 && j < 99)) //gora
                {
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
                }
                else if (i == 99 && (j > 0 && j < 99)) { // dol
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


    private static Cell[][] colisionFunction(int height, int width,Cell netIn[][]) { // tablica pomocnicza która zawierałaby sume temperatury z komorek sasiednich z eguilibrium [][][k]

        Cell tmp[][] = new Cell[height][width]; // tablica tymczasowa
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tmp[i][j] = new Cell(0, 0);  // tworzenie komorek
            }
        }

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {

                if (i == 0 && j == 0) // lewa gora
                    tmp[i][j].setTemperatura(
                            netIn[i][j].getTemperatura()
                                    +( 1/relaxTime )    // deltaT = 1
                            *( ( tmpNetEquilibrium[i][j + 1][2]
                                    + tmpNetEquilibrium[i + 1][j + 1][6]
                                    + tmpNetEquilibrium[i + 1][j][3]
                                    + tmpNetEquilibrium[i][j][0]
                                    + tmpNetEquilibrium[i][j][7]
                                    + tmpNetEquilibrium[i][j][2]
                                    + tmpNetEquilibrium[i][j][6]
                                    + tmpNetEquilibrium[i][j][3]
                                    + tmpNetEquilibrium[i][j][5]) - netIn[i][j].getTemperatura() )
                    );
                else if ((i > 0 && i < 99) && (j == 0)) // lewa krawedź
                {

                    tmp[i][j].setTemperatura(
                            netIn[i][j].getTemperatura()
                                    +( 1/relaxTime )    // deltaT = 1
                                    *( ( tmpNetEquilibrium[i - 1][j][4]
                                    + tmpNetEquilibrium[i - 1][j + 1][7]
                                    + tmpNetEquilibrium[i][j + 1][2]
                                    + tmpNetEquilibrium[i + 1][j + 1][6]
                                    + tmpNetEquilibrium[i + 1][j][3]
                                    + tmpNetEquilibrium[i][j][5]
                                    + tmpNetEquilibrium[i][j][1]
                                    + tmpNetEquilibrium[i][j][8]
                                    + tmpNetEquilibrium[i][j][0] - netIn[i][j].getTemperatura() ))
                    );
                }
                else if (i == 99 && j == 0) // lewy dol
                {

                    tmp[i][j].setTemperatura(
                            netIn[i][j].getTemperatura()
                                    +( 1/relaxTime )    // deltaT = 1
                                    *( ( tmpNetEquilibrium[i - 1][j][4]
                                    + tmpNetEquilibrium[i - 1][j + 1][7]
                                    + tmpNetEquilibrium[i][j + 1][2]
                                    + tmpNetEquilibrium[i][j][0]
                                    + tmpNetEquilibrium[i][j][6]
                                    + tmpNetEquilibrium[i][j][2]
                                    + tmpNetEquilibrium[i][j][7]
                                    + tmpNetEquilibrium[i][j][4]
                                    + tmpNetEquilibrium[i][j][8] - netIn[i][j].getTemperatura() ))
                    );
                }
                else if (i == 0 && j == 99)  // prawa gora
                {

                    tmp[i][j].setTemperatura(
                            netIn[i][j].getTemperatura()
                                    +( 1/relaxTime )    // deltaT = 1
                                    *( ( tmpNetEquilibrium[i][j - 1][1]
                                    + tmpNetEquilibrium[i + 1][j - 1][5]
                                    + tmpNetEquilibrium[i + 1][j][3]
                                    + tmpNetEquilibrium[i][j][0]
                                    + tmpNetEquilibrium[i][j][6]
                                    + tmpNetEquilibrium[i][j][3]
                                    + tmpNetEquilibrium[i][j][5]
                                    + tmpNetEquilibrium[i][j][1]
                                    + tmpNetEquilibrium[i][j][8] - netIn[i][j].getTemperatura() ))
                    );
                }
                else if ( (i > 0 && i < 99) && (j == 99) )// prawa krawedz)
                {

                    tmp[i][j].setTemperatura(
                            netIn[i][j].getTemperatura()
                                    +( 1/relaxTime )    // deltaT = 1
                                    *( ( tmpNetEquilibrium[i - 1][j][4]
                                    + tmpNetEquilibrium[i - 1][i - 1][8]
                                    + tmpNetEquilibrium[i][j - 1][1]
                                    + tmpNetEquilibrium[i + 1][j - 1][5]
                                    + tmpNetEquilibrium[i + 1][j][3]
                                    + tmpNetEquilibrium[i][j][0]
                                    + tmpNetEquilibrium[i][j][5]
                                    + tmpNetEquilibrium[i][j][1]
                                    + tmpNetEquilibrium[i][j][8] - netIn[i][j].getTemperatura() ))
                    );
                }
                else if (i == 99 && j == 99) // prawy dol
                {
                    tmp[i][j].setTemperatura(
                            netIn[i][j].getTemperatura()
                                    +( 1/relaxTime )    // deltaT = 1
                                    *( ( tmpNetEquilibrium[i - 1][j][4]
                                    + tmpNetEquilibrium[i - 1][j - 1][8]
                                    + tmpNetEquilibrium[i][j - 1][1]
                                    + tmpNetEquilibrium[i][j][0]
                                    + tmpNetEquilibrium[i][j][5]
                                    + tmpNetEquilibrium[i][j][1]
                                    + tmpNetEquilibrium[i][j][8]
                                    + tmpNetEquilibrium[i][j][4]
                                    + tmpNetEquilibrium[i][j][7] - netIn[i][j].getTemperatura() ))
                    );
                }
                else if ( i == 0 && ( ( j > 0 && j < 99 ))) //gora
                {

                    tmp[i][j].setTemperatura(
                            netIn[i][j].getTemperatura()
                                    +( 1/relaxTime )    // deltaT = 1
                                    *( ( tmpNetEquilibrium[i][j - 1][1]
                                    + tmpNetEquilibrium[i + 1][j - 1][5]
                                    + tmpNetEquilibrium[i + 1][j][3]
                                    + tmpNetEquilibrium[i + 1][j + 1][6]
                                    + tmpNetEquilibrium[i][j + 1][2]
                                    + tmpNetEquilibrium[i][j][0]
                                    + tmpNetEquilibrium[i][j][6]
                                    + tmpNetEquilibrium[i][j][3]
                                    + tmpNetEquilibrium[i][j][5] - netIn[i][j].getTemperatura() ))
                    );

                }
                else if (i == 99 && (j > 0 && j < 99))
                { // dol
                    tmp[i][j].setTemperatura(
                            netIn[i][j].getTemperatura()
                                    +( 1/relaxTime )    // deltaT = 1
                                    *( ( tmpNetEquilibrium[i][j - 1][1]
                                    + tmpNetEquilibrium[i - 1][j - 1][8]
                                    + tmpNetEquilibrium[i - 1][j][4]
                                    + tmpNetEquilibrium[i - 1][j + 1][7]
                                    + tmpNetEquilibrium[i][j + 1][2]
                                    + tmpNetEquilibrium[i][j][0]
                                    + tmpNetEquilibrium[i][j][7]
                                    + tmpNetEquilibrium[i][j][4]
                                    + tmpNetEquilibrium[i][j][8] - netIn[i][j].getTemperatura() ))
                    );
                }
                else
                    {
                        tmp[i][j].setTemperatura(
                                netIn[i][j].getTemperatura()
                                        +( 1/relaxTime )    // deltaT = 1
                                        *( ( tmpNetEquilibrium[i][j][0]
                                        + tmpNetEquilibrium[i - 1][j - 1][8]
                                        + tmpNetEquilibrium[i][j - 1][1]
                                        + tmpNetEquilibrium[i + 1][j - 1][5]
                                        + tmpNetEquilibrium[i - 1][j][4]
                                        + tmpNetEquilibrium[i + 1][j][3]
                                        + tmpNetEquilibrium[i - 1][j + 1][7]
                                        + tmpNetEquilibrium[i][j + 1][2]
                                        + tmpNetEquilibrium[i + 1][j + 1][6] - netIn[i][j].getTemperatura() ))
                        );
                }
            }
        return tmp;
    }
    }
