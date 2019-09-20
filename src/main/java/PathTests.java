import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.Character.isDigit;

public class PathTests extends Application
{
    private static final int BOX_SIZE = 5;
    private static int[][] map=null;

    public static void main(String[] args) {
        try {
            map = load( args[0] );
        } catch ( Exception e ) { }

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(144 * BOX_SIZE + 2, 144 * BOX_SIZE + 2 );
        GraphicsContext gc = canvas.getGraphicsContext2D();

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        if (t.getClickCount() > 0) {
                            int i = Math.floorDiv((int)t.getX(), BOX_SIZE );
                            int j = Math.floorDiv((int)t.getY(), BOX_SIZE );
                            map[j][i] = 1;

                            gc.fillRect(i * BOX_SIZE + 1, j * BOX_SIZE + 1, BOX_SIZE, BOX_SIZE);
                        }
                    }
                });

        showMap(canvas, map);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void showMap(Canvas canvas, int[][] map )
    {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height );

        gc.setFill(Color.BLUE);
        for( int j=0; j<144; ++j ) {
            for( int i=0; i<144; ++i ) {
                if( map[j][i] > 0 ) {
                    gc.fillRect(i * BOX_SIZE + 1, j * BOX_SIZE + 1, BOX_SIZE, BOX_SIZE);
                }
            }
        }
    }

    public static int[][] load( String filename ) throws IOException {
        int[][] map = new int[144][144];
        String data = new String( Files.readAllBytes( Paths.get( filename ) ) );

        int y=0, x=0;
        for(int i=0; i<data.length(); ++i) {
            char c = data.charAt(i);
            if( isDigit( c ) ) {
                map[y][x] = c - '0';
                x++;
            }
            else if( c == '\n' ) {
                y++;
                x=0;
            }
            // ignore anything else
        }

        return map;
    }
}

//    private void drawShapes(GraphicsContext gc) {
//        gc.setFill(Color.GREEN);
//        gc.setStroke(Color.BLUE);
//        gc.setLineWidth(5);
//        gc.strokeLine(40, 10, 10, 40);
//        gc.fillOval(10, 60, 30, 30);
//        gc.strokeOval(60, 60, 30, 30);
//        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
//        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
//        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
//        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
//        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
//        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
//        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
//        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
//        gc.fillPolygon(new double[]{10, 40, 10, 40},
//                new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolygon(new double[]{60, 90, 60, 90},
//                new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolyline(new double[]{110, 140, 110, 140},
//                new double[]{210, 210, 240, 240}, 4);
//    }
