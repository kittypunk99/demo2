package org.trottlinc.demo2;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class Dreieck extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX Demo: Sierpinski Triangle");

        Canvas canvas = new Canvas(600, 600);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(new BorderPane(canvas)));
        primaryStage.show();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Point2D p1 = new Point2D(10, 580);
        Point2D p2 = new Point2D(580, 580);
        Point2D p3 = new Point2D(295, 10);
        drawSierpinskiTriangle(gc, p1, p2, p3, 5);
    }

    private void drawSierpinskiTriangle(GraphicsContext gc, Point2D p1, Point2D p2, Point2D p3, int size) {
        if (p1.distance(p2) <= size || p2.distance(p3) <= size || p3.distance(p1) <= size) {
            drawTriangle(gc, p1, p2, p3);
        } else {
            Point2D mid1 = p1.midpoint(p2);
            Point2D mid2 = p2.midpoint(p3);
            Point2D mid3 = p3.midpoint(p1);

            drawSierpinskiTriangle(gc, p1, mid1, mid3, size);
            drawSierpinskiTriangle(gc, mid1, p2, mid2, size);
            drawSierpinskiTriangle(gc, mid3, mid2, p3, size);
        }
    }

    private void drawTriangle(GraphicsContext gc, Point2D p1, Point2D p2, Point2D p3) {
        gc.setFill(Color.BLACK);
        gc.fillPolygon(new double[]{p1.getX(), p2.getX(), p3.getX()},
                new double[]{p1.getY(), p2.getY(), p3.getY()}, 3);
    }
}
