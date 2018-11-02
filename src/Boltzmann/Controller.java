package Boltzmann;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static Boltzmann.Stan.stan;

public class Controller implements Initializable {

    @FXML
    public Canvas canvas;
    public Label labelStan;
    public Label labelDanaX;
    public Label labelTemperatura;
    public Label labelDanaY;

    protected boolean start = false;

    private GraphicsContext graphicsContext;

    private final int liczbaKomorekWysokosc = 100;  // liczba komórek na osi Y
    private final int liczbaKomorekSzerokosc = 100; // liczba komórek na osi X

    double wysokosc;
    double szerokosc;

    private Cell siatka [][];

    public static final Map<String,Color> map = new HashMap<>(){{

        put(stan[0],Color.rgb(91,45,247)); // woda
        put(stan[1],Color.rgb(141,115,247)); //posredni
        put(stan[2],Color.rgb(186,172,247)); // lod

    }};

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println(canvas.getHeight()+" "+canvas.getWidth());

        wysokosc = canvas.getHeight()/liczbaKomorekWysokosc;   // wysokosć siatki ( oś x )
        szerokosc = canvas.getWidth()/liczbaKomorekSzerokosc;  // szerokość siatki ( oś y )

        siatka = new Cell[liczbaKomorekWysokosc][liczbaKomorekSzerokosc] ;  //tworzenie tablicy

        for(int i=0;i<liczbaKomorekSzerokosc;i++)     // inicjalizacja tablicy
            for(int j=0;j<liczbaKomorekWysokosc;j++)  //  i TO KOLUMNA , j to wiersz
            {
                if( ( i>25 && j>25 ) &&  ( i<75 && j<75 )   )
                    siatka[i][j]=new Cell(50,0);  // materiał - woda
                else
                    siatka[i][j]=new Cell(-10,2);  // warunek brzegowy - lód

            }

        graphicsContext = canvas.getGraphicsContext2D();

        for(int i=0;i<liczbaKomorekWysokosc;i++)
            for(int j=0;j<liczbaKomorekSzerokosc;j++)
            for (int k=0;k<9;k++)
            {
                graphicsContext.setFill( map.get(siatka[i][j][k].getStan()) );
                graphicsContext.fillRect(i*szerokosc, j*wysokosc, szerokosc, wysokosc);  // przedstawienie początkowego stanu materiału
            }

    }


    public void onButtonStart(ActionEvent actionEvent) {

        System.out.println("Start");
        start = true;

        new Thread(() -> {
//            for (int i = 0; /*i < 25*/; i++) {
                if (start) {
                    siatka=D2Q9.metodaBoltzmann(siatka);     //wyliczenie nowej tablicy na podstawie wstępnej

//                    engine.nextGeneration();
//                    Platform.runLater(this::printWholeStructure);
//                    try {
//                        Thread.sleep(250);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }

//            }
        }).start();

    }


    public void canvasOnMouseClicked(MouseEvent mouseEvent) {

        double wartoscX = mouseEvent.getSceneX()-100; // os X
        double wartoscY = mouseEvent.getSceneY()-95;  // os Y

        double poleX=wartoscX/wysokosc;
        double poleY=wartoscY/szerokosc;

        System.out.println(wartoscX +" "+ wartoscY+" "+poleX+" "+poleY );


        if(poleX == 0 || poleX==liczbaKomorekWysokosc)
        {
            labelStan.setText("IZOLACJA");
            labelTemperatura.setText("BRAK");
        }
        else
            {
            labelStan.setText(siatka[(int) poleX][(int) poleY].getStan());
            labelTemperatura.setText(String.valueOf(siatka[(int) poleX][(int) poleY].getTemperatura()));
        }

    }

}
