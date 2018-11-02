package Boltzmann;

public class D2Q9 {


    private static double weights[] = {4.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0, 1.0 / 36.0, 1.0 / 36.0, 1.0 / 36.0, 1.0 / 36.0};
    // centralna |  prawo |   lewo  |   gora  |   dol   | prawa gora | lewa gora | lewy dol | prawy dol
    private static Cell tmpNet[][][];

    public static Cell[][] metodaBoltzmann(Cell netIn[][]) {
        tmpNet = new Cell[netIn.length][netIn[0].length][9];

        for (int i = 0; i < netIn.length; i++) {
            for (int j = 0; j < netIn[0].length; j++)
            for (int k=0;k<9;k++){
                tmpNet[i][j][k].setTemperatura(netIn[i][j].getTemperatura());
            }
        }

        Cell netOut[][] = new Cell[netIn.length][netIn[0].length];       // pusta tablica na nową generacj
//      double e [][]={ {0,0},{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,1},{-1,-1},{1,-1} };



        colisionFunction(netIn.length,netIn[0].length);

        return netOut;
    }

    private static void equilibriumFunction(Cell mainCell, int rzad, int kolumna) {
        for (int i = 0; i < 9; i++) {
            tmpNet[rzad][kolumna][i].setTemperatura(mainCell.getTemperatura() * weights[i]);  // liczenie wartości dla komórki

        }
    }

    public static void colisionFunction(int rzad, int kolumna) { // tablica pomocnicza która zawierałaby sume temperatury z komorek sasiednich z eguilibrium [][][k]

        for( int i=0;i<rzad;i++)
            for(int j=0;j<kolumna;j++)
            {
                tmpNet[i][j].setTemperatura = tmpNet[i][j+1][2].getTemperatura()
                        +tmpNet[i+1][j+1][6].getTemperatura()
                        +tmpNet[i+1][j][3].getTemperatura()
                        +tmpNet[i][j][0].getTemperatura()
                        +tmpNet[i][j][1].getTemperatura()+tmpNet[i][j][4].getTemperatura()+
                        tmpNet[i][j][5].getTemperatura()+tmpNet[i][j][7].getTemperatura()+
                        tmpNet[i][j][8].getTemperatura();
            }


//
//        if(rzad==0 && kolumna==0) // jesli lewy gorny rog
//            System.out.println("a");
//        if(rzad == 0)
//            System.out.println("a");
//
//        if(rzad==99 && kolumna==0)  // lewy dolny
//            System.out.println("a");
//
//        if(rzad==0 && kolumna==99 ) // prawy gorny
//            System.out.println("a");
//
//        if(rzad==99 && kolumna ==99)  // prawy dolny
//            System.out.println("a");


    }

    public static void streamingFunction(Cell k) {

    }

//    public static Cell macroscopicParameters(Cell k0, Cell k1, Cell k2, Cell k3, Cell k4, Cell k5, Cell k6, Cell k7, Cell k8) {
//        Cell new_cell = new Cell(k0.getTemperatura(), k0.getStan());
//
//        double temperatura = k0.getTemperatura()
//                + k1.getTemperatura()
//                + k2.getTemperatura()
//                + k3.getTemperatura()
//                + k4.getTemperatura()
//                + k5.getTemperatura()
//                + k6.getTemperatura()
//                + k7.getTemperatura()
//                + k8.getTemperatura();
//
//        temperatura = temperatura / 9;
//
//        new_cell.setTemperatura(temperatura);
//
//        if (temperatura < 0.5 && temperatura > -0.5)
//            new_cell.setStan("liquidSolid");
//        else if (temperatura < -0.5)
//            new_cell.setStan("solid");
//
//        return new_cell;
//
//    }

}
