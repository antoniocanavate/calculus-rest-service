package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class PolinomioController {

	//Otras variables
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	//variables de polinomios
	private int tamCaja = 3; //Numero de polinomios que caben en la caja
	private PolinomioDTO [] cajaPolinomios = new PolinomioDTO [tamCaja]; //OJO El tamaño es 3 (se puede ampliar)

	//---------------------------------------------------------------------------------------------------------------------------------
	//Ejemplos para entender el uso de las peticiones

	//EJEMPLO: SALUDO (GET)
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	//EJEMPLO: OBTENER UN POLINOMIO ALEATORIO (GET)
	//Los datos se generan aleatoriamente en el servidor
	@RequestMapping("/polinomio")
	public PolinomioDTO getPolinomio() {
		int [] coeficientes = new int [] {(int) (Math.random()*10), (int) (Math.random()*10), (int) (Math.random()*10)};
		return new PolinomioDTO(coeficientes);
	}

	//---------------------------------------------------------------------------------------------------------------------------------
	//A continuación tenemos los servicios Rest que realizan operaciones sobre los polinomios enviados en las peticiones

	//SERVICIO PARA MULTIPLICAR POLINOMIOS (POST)
	//Los datos se envían en el campo body de la petición POST
	@PostMapping("/polinomio/multiplicar")
	public PolinomioDTO multiplicar(@RequestParam(value="coef1", defaultValue= "0,1") int [] coef1, @RequestParam(value="coef2", defaultValue= "0,1") int [] coef2) {
		PolinomioDTO pol1 = new PolinomioDTO(coef1);
		PolinomioDTO pol2 = new PolinomioDTO(coef2);
		PolinomioDTO pol3 = pol1.multiplicar(pol2);
		return pol3;
	}

	//---------------------------------------------------------------------------------------------------------------------------------
	//OPCIONAL:
	//Las siguientes peticiones sirven para gestionar los polinomios que tenemos almacenados en el servidor para posteriormente
	//utilizarlos para realizar operaciones. Ahora los vamos a almacenar en una variable de tipo array llamada cajaPolinomios
	//El siguiente paso seria usar una BBDD. Juan Angel estará orgulloso de nosotros :)

	//GUARDAR UN POLINOMIO EN LA CAJA (POST)
	//Como no indicamos el id se modifica uno al azar y nos devuelve el id del que se ha modificado
	@PostMapping("/polinomio")
	public int setPolinomionuevo(@RequestParam(value="coef", defaultValue= "0,1") int [] coeficientes) {
		PolinomioDTO p = new PolinomioDTO(coeficientes);
		int id = (int) (Math.random()*tamCaja-1);
		cajaPolinomios[id] = p;
		return id;
	}

	//GUARDAR UN POLINOMIO CON ID EN LA CAJA (PUT)
	//Además de enviar el campo body con los datos, se indica el id del polinomio a guardar
	//*Si el polinomio ya estaba almacenado lo sobreescribe 
	//**No se pueden guardar polinomios con id mayor que el tamaño de la caja
	@PutMapping("/polinomio/{id}")
	public int setPolinomio(@PathVariable(value = "id") int id, @RequestParam(value="coef", defaultValue= "0,1") int [] coeficientes) {
		PolinomioDTO p = new PolinomioDTO(coeficientes);
		cajaPolinomios[id] = p;
		return id;
	}

	//OBTENER UN POLINOMIO DE LA CAJA (GET)
	//*No se pueden obtener polinomios con id mayor que el tamaño de la caja
	@RequestMapping("/polinomio/{id}")
	public PolinomioDTO getPolinomioCaja(@PathVariable(value = "id") int id) {
		return cajaPolinomios[id];
	}

	//OBTENER LOS POLINOMIOS DE LA CAJA (GET)
	@RequestMapping("/polinomios")
	public PolinomioDTO [] getPolinomiosCaja() {
		return cajaPolinomios;
	}

	//---------------------------------------------------------------------------------------------------------------------------------
	//Una vez almacenados los polinomios podemos empezar a realizar operaciones con ellos

	//SERVICIO PARA MULTIPLICAR POLINOMIOS DE LA CAJA (POST)
	//En este caso indicamos los ids de los polinomios que van a usarse y el tipo de operacion
	//Aqui ya no haria falta enviar los datos en el campo body
	@PostMapping("/polinomio1/{id1}/polinomio2/{id2}/multiplicar")
	public PolinomioDTO multiplicar_id(@PathVariable(value = "id1") int id1, @PathVariable(value = "id2") int id2) {
		PolinomioDTO pol3 = cajaPolinomios[id1].multiplicar(cajaPolinomios[id2]);
		return pol3;
	}
}
