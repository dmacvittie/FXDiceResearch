//import com.sun.javafx.geom.Rectangle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.AmbientLight;
//import javafx.builders.RectangleBuilder;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
 
/**
 * @author Jasper Potts
 */
public class Cube3D extends Application{
 
    @Override public void start(Stage stage) throws Exception {

    	float [] points = { 200, 0, 0,
    					    200, 40, 0,
    					    240, 40, 0,
    					    240, 0, 0
    					  };
    	float [] coords = { 0, 0,
    					    0, 100,
    					    100, 100, 
    					    100, 0
    					  };
    	int [] faces = 
    		{   0, 0, 3, 3, 1, 1,
    			0, 0, 1, 1, 3, 3, 
    			1, 1, 3, 3, 2, 2, 
    			1, 1, 2, 2, 3, 3
    		};
    	
    	TriangleMesh mesh = new TriangleMesh();
    	mesh.getPoints().addAll(points);
    	mesh.getTexCoords().addAll(coords);
    	mesh.getFaces().addAll(faces);
    	MeshView mv = new MeshView(mesh);
    	
    	final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.ORANGE);
        redMaterial.setDiffuseColor(Color.RED);
 //       Image bumpy = new Image(getClass().getResourceAsStream("D6Full.png"));
 //       redMaterial.setBumpMap(bumpy);

        mv.setMaterial(redMaterial);
        // mv.setLayoutX(600);
       // mv.setLayoutY(600);
        
    	stage.setTitle("Cube 3D");
        // stage.initDepthBuffer(true);//
 
        Box c = new Box(100,100,100);
        c.setLayoutX(225);
        c.setLayoutY(-150);
        c.setMaterial(redMaterial);
        
        Box c2 = new Box(200,200,200);
        c2.setLayoutY(-150);
        c2.setMaterial(new PhongMaterial(Color.BLUE));
        c2.setDrawMode(DrawMode.FILL);
        
        Box c3 = new Box(50,50,50);
        c3.setLayoutX(350);
        c3.setLayoutY(-100);
        Image normalMap = new Image(getClass().getResourceAsStream("D6Side1.png"));
        //        Image normalMap = new Image("file:C:\\Users\\Don\\Pictures\\D6Side1.png");
        PhongMaterial side1 = new PhongMaterial(Color.ANTIQUEWHITE);
        side1.setBumpMap(normalMap);
        c3.setMaterial(side1);
        c3.getTransforms().add(new Rotate(10, 80, 80));

        D20Mesh d20 = new D20Mesh();
        d20.setCullFace(CullFace.FRONT);
        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseMap(new Image(getClass().getResourceAsStream("D20.png")));
        d20.setMaterial(mat);
        Rotate rotateY = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        d20.getTransforms().addAll(new Rotate(30,Rotate.X_AXIS),rotateY);

        
        
        Timeline animation = new Timeline( );
        

        animation.getKeyFrames().addAll(
               new KeyFrame(Duration.seconds(0),
                        new KeyValue(c.rotateProperty(), 0d),
                        new KeyValue(c2.rotateProperty(), 0d),
                        new KeyValue(c3.rotateProperty(), 0d),
                        new KeyValue(mv.rotateProperty(), 0d),
               			new KeyValue(d20.rotateProperty(), 0d)
                ),
                new KeyFrame(Duration.seconds(100),
                        new KeyValue(c.rotateProperty(), 360d),
                        new KeyValue(c2.rotateProperty(), 360d),
                        new KeyValue(c3.rotateProperty(), 360d),
                		new KeyValue(mv.rotateProperty(), 360d),
       					new KeyValue(d20.rotateProperty(), 360d)
                ));
        animation.setCycleCount(20);
        // create root group
        Group root = new Group(c,c2,c3, mv, d20);

        // translate and rotate group so that origin is center and +Y is up
        root.setTranslateX(400/2);
        root.setTranslateY(150/2);
        root.getTransforms().add(new Rotate(180,Rotate.X_AXIS));
        // create scene
        Scene scene = new Scene(root, 800,800);
        scene.setFill(Color.BLACK);        
        scene.setCamera(new PerspectiveCamera());
        stage.setScene(scene);
        stage.show(); // .setVisible(true);
        // start spining animation
        animation.play();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
 
 }