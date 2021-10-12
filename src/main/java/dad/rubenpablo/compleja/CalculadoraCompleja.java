package dad.rubenpablo.compleja;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
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
		operadores.getSelectionModel().select(0);
//		operadores.getSelectionModel().select(0);
		
		// Listener para cuando el usuario seleccione un operador, realizar los cálculos correspondientes
		operadores.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
			switch (nv) {
			case "+": {
				resultado.setReal(complejo1.getReal()+complejo2.getReal());
				resultado.setImaginario(complejo1.getImaginario()+complejo2.getImaginario());
				break;
			}
			case "-": {
				resultado.setReal(complejo1.getReal()-complejo2.getReal());
				resultado.setImaginario(complejo1.getImaginario()-complejo2.getImaginario());
				break;
			}
			case "*": {
				double a = complejo1.getReal();
				double c = complejo2.getReal();
				double b = complejo1.getImaginario();
				double d = complejo2.getImaginario();
				resultado.setReal((a*c)-(b*d));
				resultado.setImaginario((a*d)+(b*c));
				break;
			}
			case "/": {
				double a = complejo1.getReal();
				double c = complejo2.getReal();
				double b = complejo1.getImaginario();
				double d = complejo2.getImaginario();
				resultado.setReal(
						((a*c) + (b*d))/((c*c)+(d*d))
						);
				resultado.setImaginario(
						((b*c)-(a*d))/((c*c)+(d*d))
						);
				break;
			}
			}
		});
		
		// Elementos de la izquierda
		VBox izq = new VBox(operadores);
		izq.setAlignment(Pos.CENTER_LEFT);
		
		// Elementos del medio
		HBox elementosComp1 = new HBox(5, real1, new Label("+"), imaginario1, new Label("i"));
		HBox elementosComp2 = new HBox(5, real2, new Label("+"), imaginario2, new Label("i"));
		HBox resComp = new HBox(5, realRes, new Label("+"), imagRes, new Label("i"));
		VBox mid = new VBox(5, elementosComp1, elementosComp2, new Separator(), resComp);
		mid.setAlignment(Pos.CENTER);
		// Elementos de la derecha
		VBox der = new VBox();
		der.setAlignment(Pos.CENTER_RIGHT);
		
		this.root = new HBox(5, izq, mid, der);
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
		
		// Bindeos para el resultado
		realRes.textProperty().bind(resultado.realProperty().asString("%.0f"));
		imagRes.textProperty().bind(resultado.imaginarioProperty().asString("%.0f"));
		
		// Listeners para actualizar en tiempo real
		real1.textProperty().addListener((o, ov, nv) -> {
			Double valor = Double.parseDouble(nv);
			this.complejo1.setReal(valor);
			double a = this.complejo1.getReal();
			double b = this.complejo1.getImaginario();
			double c = this.complejo2.getReal();
			double d = this.complejo2.getImaginario();
			switch (operadores.getValue()) {
				case "+": {
					this.resultado.setReal(this.complejo1.getReal()+this.complejo2.getReal());
					break;
				}
				case "-": {
					this.resultado.setReal(this.complejo1.getReal()-this.complejo2.getReal());
					break;
				}
				case "*": {
					this.resultado.setReal((a*c)-(b*d));
				}
				case "/": {
					this.resultado.setReal(((a*c)+(b*d))/((c*c)+(d*d)));
					break;
				}
			}
		});
		
		real2.textProperty().addListener((o, ov, nv) -> {
			Double valor = Double.parseDouble(nv);
			this.complejo2.setReal(valor);
			double a = this.complejo1.getReal();
			double b = this.complejo1.getImaginario();
			double c = this.complejo2.getReal();
			double d = this.complejo2.getImaginario();
			switch (operadores.getValue()) {
				case "+": {
					this.resultado.setReal(this.complejo1.getReal()+this.complejo2.getReal());
					break;
				}
				
				case "-": {
					this.resultado.setReal(this.complejo1.getReal()-this.complejo2.getReal());
					break;
				}
				
				case "*": {
					this.resultado.setReal((a*c)-(b*d));
					break;
				}
				
				case "/": {
					this.resultado.setReal(((a*c)+(b*d))/((c*c)+(d*d)));
					break;
				}
			}
		});
		
		imaginario1.textProperty().addListener((o, ov, nv) -> {
			Double valor = Double.parseDouble(nv);
			this.complejo1.setImaginario(valor);
			double a = this.complejo1.getReal();
			double b = this.complejo1.getImaginario();
			double c = this.complejo2.getReal();
			double d = this.complejo2.getImaginario();
			
			switch (operadores.getValue()) {
				case "+": {
					this.resultado.setImaginario(this.complejo1.getImaginario()+this.complejo2.getImaginario());
					break;
				}
				
				case "-": {
					this.resultado.setImaginario(this.complejo1.getImaginario()-this.complejo2.getImaginario());
					break;
				}
				
				case "*": {
					this.resultado.setImaginario((a*d)+(b*c));
					break;
				}
				
				case "/": {
					this.resultado.setImaginario(((b*c)-(a*d))/((c*c)+(d*d)));
					break;
				}
			}
		});
		
		imaginario2.textProperty().addListener((o, ov, nv) -> {
			Double valor = Double.parseDouble(nv);
			this.complejo2.setImaginario(valor);
			double a = this.complejo1.getReal();
			double b = this.complejo1.getImaginario();
			double c = this.complejo2.getReal();
			double d = this.complejo2.getImaginario();
			
			switch (operadores.getValue()) {
				case "+": {
					this.resultado.setImaginario(this.complejo1.getImaginario()+this.complejo2.getImaginario());
					break;
				}
				
				case "-": {
					this.resultado.setImaginario(this.complejo1.getImaginario()-this.complejo2.getImaginario());
					break;
				}
				
				case "*": {
					this.resultado.setImaginario((a*d)+(b*c));
					break;
				}
				
				case "/": {
					this.resultado.setImaginario(((b*c)-(a*d))/((c*c)+(d*d)));
					break;
				}
			}
		});
		
		
	}

	public static void main(String[] args) {
		launch(args);

	}

}
