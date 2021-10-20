package dad.rubenpablo.compleja;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleExpression;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraCompleja extends Application {

	private ComboBox<String> operadores;
	private TextField real1, imaginario1, real2, imaginario2;
	private TextField realRes, imagRes;
	private HBox root;
	
	private Complejo complejo1, complejo2, resultado;
	
	private DoubleExpression sumReal, sumImg, resReal, resImg, mulReal, mulImg, divReal, divImg;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Números Complejos
		complejo1 = new Complejo();
		complejo2 = new Complejo();
		resultado = new Complejo();
		
		// Inputs
		real1 = new TextField();
		real2 = new TextField();
		imaginario1 = new TextField();
		imaginario2 = new TextField();
		realRes = new TextField();
		imagRes = new TextField();

		// Propiedades de los Inputs
			// real1
		real1.setAlignment(Pos.CENTER);
		real1.setMaxWidth(65);
			// real2
		real2.setAlignment(Pos.CENTER);
		real2.setMaxWidth(65);
			// imaginario1
		imaginario1.setAlignment(Pos.CENTER);
		imaginario1.setMaxWidth(65);
			// imaginario2
		imaginario2.setAlignment(Pos.CENTER);
		imaginario2.setMaxWidth(65);
			// realRes
		realRes.setAlignment(Pos.CENTER);
		realRes.setMaxWidth(65);
		realRes.setDisable(true);
			// imagRes
		imagRes.setAlignment(Pos.CENTER);
		imagRes.setMaxWidth(65);
		imagRes.setDisable(true);
		
		// Símbolos del ComboBox
		String[] simbolos = {"+","-","*","/"};
		ArrayList<String> ops = new ArrayList<String>();
		
		for (String s:simbolos) {
			ops.add(s);
		}
		
		// Adición de los símbolos al ComboBox
		operadores = new ComboBox<String>();
		operadores.getItems().addAll(ops);
		
		// Elementos de la izquierda
		VBox izq = new VBox(operadores);
		izq.setAlignment(Pos.CENTER_LEFT);
		
		// Elementos del medio
		HBox elementosComp1 = new HBox(5, real1, new Label("+"), imaginario1, new Label("i"));
		HBox elementosComp2 = new HBox(5, real2, new Label("+"), imaginario2, new Label("i"));
		HBox resComp = new HBox(5, realRes, new Label("+"), imagRes, new Label("i"));
		VBox mid = new VBox(5, elementosComp1, elementosComp2, new Separator(), resComp);
		mid.setAlignment(Pos.CENTER);
		
		this.root = new HBox(5, izq, mid);
		this.root.setAlignment(Pos.CENTER);
		this.root.setSpacing(5);
		
		
		Scene escena = new Scene(this.root, 640, 300);
		
		primaryStage.setTitle("Calculadora Compleja");
		primaryStage.setScene(escena);
		primaryStage.show();
		
		// Bindeos para los inputs del primer número complejo
		
		Bindings.bindBidirectional(real1.textProperty(), complejo1.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(imaginario1.textProperty(), complejo1.imaginarioProperty(), new NumberStringConverter());
		
		// Bindeos para los inputs del segundo número complejo
		Bindings.bindBidirectional(real2.textProperty(), complejo2.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(imaginario2.textProperty(), complejo2.imaginarioProperty(), new NumberStringConverter());
		
		sumReal = complejo1.realProperty().add(complejo2.realProperty());
		
		sumImg = complejo1.imaginarioProperty().add(complejo2.imaginarioProperty());
		
		resReal = complejo1.realProperty().subtract(complejo2.realProperty());
		resImg = complejo1.imaginarioProperty().subtract(complejo2.imaginarioProperty());
		
		mulReal = (complejo1.realProperty().multiply(complejo2.realProperty())).subtract(complejo1.imaginarioProperty().multiply(complejo2.imaginarioProperty()));
		mulImg = (complejo1.realProperty().multiply(complejo2.imaginarioProperty())).add(complejo1.imaginarioProperty().multiply(complejo2.realProperty()));
		
		divReal = (complejo1.realProperty().multiply(complejo2.realProperty())).add(complejo1.imaginarioProperty().multiply(complejo2.imaginarioProperty()))
				.divide(complejo2.realProperty().multiply(complejo2.realProperty()).add(complejo2.imaginarioProperty().multiply(complejo2.imaginarioProperty())) );
		divImg = (complejo1.imaginarioProperty().multiply(complejo2.realProperty())).subtract(complejo1.realProperty().multiply(complejo2.imaginarioProperty()))
				.divide(complejo2.realProperty().multiply(complejo2.realProperty()).add(complejo2.imaginarioProperty().multiply(complejo2.imaginarioProperty())) );
		
		// Listener para cuando el usuario seleccione un operador, realizar los cálculos correspondientes
		operadores.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
			switch (nv) {
				case "+": {
					resultado.realProperty().bind(sumReal);
					resultado.imaginarioProperty().bind(sumImg);
					break;
				}
				case "-": {
					resultado.realProperty().bind(resReal);
					resultado.imaginarioProperty().bind(resImg);
					break;
				}
				case "*": {
					resultado.realProperty().bind(mulReal);
					resultado.imaginarioProperty().bind(mulImg);
					break;
				}
				case "/": {
					resultado.realProperty().bind(divReal);
					resultado.imaginarioProperty().bind(divImg);
					break;
				}
			}
			});
		operadores.getSelectionModel().selectFirst();
		
		
		// Bindeos para el resultado
		realRes.textProperty().bind(resultado.realProperty().asString("%.0f"));
		imagRes.textProperty().bind(resultado.imaginarioProperty().asString("%.0f"));
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
