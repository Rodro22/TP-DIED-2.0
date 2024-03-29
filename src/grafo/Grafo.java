package grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;


import modelo.Planta;
import modelo.Recorrido;


public class Grafo<T> {
	
	public List<Arista<T>> aristas;
	public List<Vertice<T>> vertices;

	public Grafo(){
		this.aristas = new ArrayList<Arista<T>>();
		this.vertices = new ArrayList<Vertice<T>>();
	}

	public void addNodo(T nodo){
		this.addNodo(new Vertice<T>(nodo));
	}

	public void addNodo(Vertice<T> nodo){
		this.vertices.add(nodo);
	}
	
	public void conectar(T n1,T n2){
		this.conectar(getNodo(n1), getNodo(n2), 1.0);
	}

	public void conectar(T n1,T n2,Number valor){
		this.conectar(getNodo(n1), getNodo(n2), valor);
	}

	public void conectar(Vertice<T> nodo1,Vertice<T> nodo2,Number valor){
		this.aristas.add(new Arista<T>(nodo1,nodo2,valor));
	}
	public void conectarFull(Vertice<T> nodo1,Vertice<T> nodo2,Number valor, Double km, Double tiempo){
		this.aristas.add(new Arista<T>(nodo1, nodo2, valor, km, tiempo));
	}
	//creo un nuevo constructor que incluya el peso
	public void conectarFull(Vertice<T> nodo1,Vertice<T> nodo2,Number valor, Double km, Double tiempo, Double peso){
		this.aristas.add(new Arista<T>(nodo1, nodo2, valor, km, tiempo, peso));
	}
	public Vertice<T> getNodo(T valor){
		return this.vertices.get(this.vertices.indexOf(new Vertice<T>(valor)));
	}

	public List<T> getAdyacentes(T valor){ 
		Vertice<T> unNodo = this.getNodo(valor);
		List<T> salida = new ArrayList<T>();
		for(Arista<T> enlace : this.aristas){
			if( enlace.getInicio().equals(unNodo)){
				salida.add(enlace.getFin().getValor());
			}
		}
		return salida;
	}
	

	public List<Vertice<T>> getAdyacentes(Vertice<T> unNodo){ 
		List<Vertice<T>> salida = new ArrayList<Vertice<T>>();
		for(Arista<T> enlace : this.aristas){
			if( enlace.getInicio().equals(unNodo)){
				salida.add(enlace.getFin());
			}
		}
		return salida;
	}
	
	public void imprimirAristas(){
		System.out.println(this.aristas.toString());
	}

	public Integer gradoEntrada(Vertice<T> vertice){
		Integer res =0;
		for(Arista<T> arista : this.aristas){
			if(arista.getFin().equals(vertice)) ++res;
		}
		return res;
	}

	public Integer gradoEntrada2(Vertice<Planta> vertice){
		Integer res =0;
		for(Arista<T> arista : this.aristas){
			if(arista.getFin().equals(vertice)) ++res;
		}
		return res;
	}
	public Integer gradoSalida(Vertice<T> vertice){
		Integer res =0;
		for(Arista<T> arista : this.aristas){
			if(arista.getInicio().equals(vertice)) ++res;
		}
		return res;
	}
	public void agregarVertice() {
		
		
		
	}
	public List<Planta> ordenarPlantasSegunPageRank(List<Vertice<Planta>> lista_vertices){
		//5.2
		int max = -1;
		List<Planta> lista_Planta_PageRank = new ArrayList<>();
		Vertice<Planta> vertice_aux = new Vertice<Planta>();
		List<Vertice<Planta>> lista_vertices_aux = new ArrayList<>();
		int j_aux=0;
		
		for(Vertice<Planta> ver : lista_vertices) {
			lista_vertices_aux.add(ver);
		}
		
		
		for(int i=0; i<lista_vertices.size(); i++) {
			
			if(lista_vertices_aux.size() == 0 ) {
				return lista_Planta_PageRank;
			}

			for(int j=0; j < lista_vertices_aux.size(); j++) {
				
				//while(lista_vertices_aux != null) {
			
				if(this.gradoEntrada2(lista_vertices_aux.get(j)) > max) {
					

					max = this.gradoEntrada2(lista_vertices_aux.get(j));
					j_aux = j;
					vertice_aux = new Vertice<Planta>(lista_vertices_aux.get(j).getValor());
					
				}
				
	
			
			}
			
			max=-1;
			
			lista_Planta_PageRank.add(vertice_aux.getValor());

			lista_vertices_aux.remove(j_aux);
			
			
			
			
		}
		//Collection.reverse(lista_Planta_PageRank);
		
		
		return lista_Planta_PageRank;
	}

	public List<T> recorridoAnchura(Vertice<T> inicio){
		List<T> resultado = new ArrayList<T>();
		//estructuras auxiliares
		Queue<Vertice<T>> pendientes = new LinkedList<Vertice<T>>();
		HashSet<Vertice<T>> marcados = new HashSet<Vertice<T>>();
		marcados.add(inicio);
		pendientes.add(inicio);
		
		while(!pendientes.isEmpty()){
			Vertice<T> actual = pendientes.poll();
			List<Vertice<T>> adyacentes = this.getAdyacentes(actual);
			resultado.add(actual.getValor());
			for(Vertice<T> v : adyacentes){
				if(!marcados.contains(v)){ 
					pendientes.add(v);
					marcados.add(v);
				}
			}
		}		
		//System.out.println(resultado);
		return resultado;
 	}
	
	public List<T> recorridoProfundidad(Vertice<T> inicio){
		List<T> resultado = new ArrayList<T>();
		Stack<Vertice<T>> pendientes = new Stack<Vertice<T>>();
		HashSet<Vertice<T>> marcados = new HashSet<Vertice<T>>();
		marcados.add(inicio);
		pendientes.push(inicio);
		
		while(!pendientes.isEmpty()){
			Vertice<T> actual = pendientes.pop();
			List<Vertice<T>> adyacentes = this.getAdyacentes(actual);
			resultado.add(actual.getValor());
			for(Vertice<T> v : adyacentes){
				if(!marcados.contains(v)){ 
					pendientes.push(v);
					marcados.add(v);
				}
			}
		}		
		//System.out.println(resultado);
		return resultado;
 	}
 	
	public List<T> recorridoTopologico(){
		List<T> resultado = new ArrayList<T>();
		Map<Vertice<T>,Integer> gradosVertice = new HashMap<Vertice<T>,Integer>();
		for(Vertice<T> vert : this.vertices){
			gradosVertice.put(vert, this.gradoEntrada(vert));
		}
		while(!gradosVertice.isEmpty()){
		
			List<Vertice<T>> nodosSinEntradas = gradosVertice.entrySet()
							.stream()
							.filter( x -> x.getValue()==0)
							.map( p -> p.getKey())
							.collect( Collectors.toList());
			
            if(nodosSinEntradas.isEmpty()) System.out.println("TIENE CICLOS");
            
			for(Vertice<T> v : nodosSinEntradas){
            	resultado.add(v.getValor());
            	gradosVertice.remove(v);
            	for(Vertice<T> ady: this.getAdyacentes(v)){
            		gradosVertice.put(ady,gradosVertice.get(ady)-1);
            	}
            }
		}
		
		System.out.println(resultado);
		return resultado;
 	}
        
    public boolean esAdyacente(Vertice<T> v1,Vertice<T> v2){
    	List<Vertice<T>> ady = this.getAdyacentes(v1);
        for(Vertice<T> unAdy : ady){
        	if(unAdy.equals(v2)) return true;
        }
        return false;
    }
    
    public void buscarCaminosAux(Vertice<T> v1,Vertice<T> v2, List<Vertice<T>> marcados, List<List<Vertice<T>>> todos) {
    	List<Vertice<T>> adyacentes = this.getAdyacentes(v1);
    	// Vector copiaMarcados;
    	List<Vertice<T>>  copiaMarcados =null;


    	 for(Vertice<T> ady: adyacentes){
    		 //System.out.println(">> " + ady);
    		 copiaMarcados = marcados.stream().collect(Collectors.toList());
    		if(ady.equals(v2)) {
    			copiaMarcados.add(v2);
    			todos.add(new ArrayList<Vertice<T>>(copiaMarcados));
    			//System.out.println("ENCONTRO CAMINO "+ todos.toString());
    		} else {
    			if( !copiaMarcados.contains(ady)) {
    				
    				copiaMarcados.add(ady);
    		     
    		     this.buscarCaminosAux(ady,v2,copiaMarcados,todos);
    		    }
    		}
    	 }

    }
    
    public List<Recorrido> armarRecorridos(List<List<Vertice<Planta>>> todos){
    	
    	int i = 0;
    	
    	ArrayList<Recorrido> lista_recorridos = new ArrayList<Recorrido>();
    	
    	for(List<Vertice<Planta>> aux :  todos) {
    		i= i+1;
    		Recorrido rec = new Recorrido(i, aux);
    		//i++;
    		this.calcularDuracionTotalRecorrido(rec);
    		lista_recorridos.add(rec);
    	}
    	
    	return lista_recorridos;
    }
    
    
    
    public void calcularDuracionTotalRecorrido(Recorrido rec) {
    	Double sumatoriaT = 0.0;
    	Double sumatoriaKM = 0.0;
    	
    	for(int i= 0; i < rec.recorrido.size() - 1; i++) {
    	sumatoriaT += this.buscarAristaCalcularDuracionTiempo(rec.recorrido.get(i), rec.recorrido.get(i+1));
    	sumatoriaKM += this.buscarAristaCalcularDuracionDistancia(rec.recorrido.get(i), rec.recorrido.get(i+1));
    	
    	}
    	
    	rec.duracion_tiempo = sumatoriaT;
    	rec.duracion_km= sumatoriaKM;
    	
    }
 public Double buscarAristaCalcularDuracionTiempo(Vertice<Planta> p1, Vertice<Planta> p2) {

    	Double contador_tiempo= 0.0;
    	int i;
    	//for(Arista<T> unaArista : this.aristas) {
    	for(i = 0; i < this.aristas.size(); i++) {
    		//System.out.println("ENTRO AL FOR");
    		//if(unaArista.getInicio().equals(p1) && unaArista.getFin().equals(p2)) {
    		//if(((Planta) unaArista.getInicio().getValor()).getId() == (p1.getValor().getId()) && ((Planta) unaArista.getFin().getValor()).getId() == (p2.getValor().idplanta)) {
    		if(((Planta) aristas.get(i).getInicio().getValor()).getId() == (p1.getValor().getId()) && (((Planta) aristas.get(i).getFin().getValor()).getId() == (p2.getValor().idplanta))) {
    			//System.out.println("ContadorTiempoAntes: " + contador_tiempo);
    			//contador_tiempo += unaArista.duracion;
    			contador_tiempo = aristas.get(i).duracion;
    			//System.out.println("ContadorTiempo: " + contador_tiempo);
    			//System.out.println("Arista_duracion: " + aristas.get(i).duracion);
    			//System.out.println("----------------------------------------");
    		}
    		
    	}
    	return contador_tiempo;
    }
    
 public Double buscarAristaCalcularDuracionDistancia(Vertice<Planta> p1, Vertice<Planta> p2) {
    	
	 Double contador_distancia= 0.0;
 	int i;
 	//for(Arista<T> unaArista : this.aristas) {
 	for(i = 0; i < this.aristas.size(); i++) {
 		//System.out.println("ENTRO AL FOR");
 		//if(unaArista.getInicio().equals(p1) && unaArista.getFin().equals(p2)) {
 		//if(((Planta) unaArista.getInicio().getValor()).getId() == (p1.getValor().getId()) && ((Planta) unaArista.getFin().getValor()).getId() == (p2.getValor().idplanta)) {
 		if(((Planta) aristas.get(i).getInicio().getValor()).getId() == (p1.getValor().getId()) && (((Planta) aristas.get(i).getFin().getValor()).getId() == (p2.getValor().idplanta))) {
 			//System.out.println("ContadorTiempoAntes: " + contador_tiempo);
 			//contador_tiempo += unaArista.duracion;
 			contador_distancia = aristas.get(i).distancia;
 			//System.out.println("ContadorTiempo: " + contador_tiempo);
 			//System.out.println("Arista_duracion: " + aristas.get(i).duracion);
 			//System.out.println("----------------------------------------");
 		}
 		
 	}
 	return contador_distancia;
 }
    
    
    
    
    public List<List<Vertice<T>>> caminos(T v1,T v2){
    	return this.caminos(new Vertice(v1), new Vertice(v2));
    }

    
    public List<List<Vertice<T>>> caminos(Vertice<T> v1,Vertice<T> v2){
    	//todos los caminos posibles
    	List<List<Vertice<T>>>salida = new ArrayList<List<Vertice<T>>>();
    	List<Vertice<T>> marcados = new ArrayList<Vertice<T>>();
      marcados.add(v1);
      buscarCaminosAux(v1,v2,marcados,salida);
      return salida;
    }

    public Map<T,Integer> caminosMinimoDikstra(T valorOrigen){
    	Vertice<T> vOrigen = new Vertice<T>(valorOrigen);
    	Map<Vertice<T>, Integer> caminosResultado = this.caminosMinimoDikstra(vOrigen);
    	Map<T,Integer> resultado = new LinkedHashMap<T, Integer>();
    	for(Entry<Vertice<T>, Integer> unNodo : caminosResultado.entrySet()) {
    		resultado.put(unNodo.getKey().getValor(), unNodo.getValue());
    	}
    	return resultado;
    }
    
    public Map<Vertice<T>, Integer> caminosMinimoDikstra(Vertice<T> origen) {

    	// inicializo todas las distancias a INFINITO
    	Map<Vertice<T>, Integer> distancias = new HashMap<Vertice<T>, Integer>();
    	for(Vertice<T> unVertice : this.vertices) {
    		distancias.put(unVertice, Integer.MAX_VALUE);
    	}
    	distancias.put(origen, 0);
    	
    	// guardo visitados y pendientes de visitar
    	Set<Vertice<T>> visitados = new HashSet<Vertice<T>>();
    	TreeMap<Integer,Vertice<T>> aVisitar= new TreeMap<Integer,Vertice<T>>();

    	aVisitar.put(0,origen);
    	 
    	while (!aVisitar.isEmpty()) {
    		Entry<Integer, Vertice<T>> nodo = aVisitar.pollFirstEntry();
    		visitados.add(nodo.getValue());
    		
        	int nuevaDistancia = Integer.MIN_VALUE;
        	List<Vertice<T>> adyacentes = this.getAdyacentes(nodo.getValue());
        	
        	for(Vertice<T> unAdy : adyacentes) {
        		if(!visitados.contains(unAdy)) {
        			Arista<T> enlace = this.buscarArista(nodo.getValue(), unAdy);
        			if(enlace !=null) {
        				nuevaDistancia = enlace.getValor().intValue();
        			}
        			int distanciaHastaAdy = distancias.get(nodo.getValue()).intValue();
        			int distanciaAnterior = distancias.get(unAdy).intValue();
        			if(distanciaHastaAdy  + nuevaDistancia < distanciaAnterior ) {
        				distancias.put(unAdy, distanciaHastaAdy  + nuevaDistancia);
        				aVisitar.put(distanciaHastaAdy  + nuevaDistancia,unAdy);
        			}        			
        		}
        	}    		
    	}
 
    	return distancias;
    }
    
   
    public Arista<T> buscarArista(Vertice<T> v1, Vertice<T> v2){
    	for(Arista<T> unaArista : this.aristas) {
    		
    		if(unaArista.getInicio().equals(v1) && unaArista.getFin().equals(v2)) return unaArista;
    	}
    	return null;
    }
    
    public void floydWarshall() {
    	int cantVertices= this.vertices.size();
    	int[][] matrizDistancias = new int[cantVertices][cantVertices];
    	
    	for(int i=0; i<cantVertices;i++) {
        	for(int j=0; j<cantVertices;j++) {
        		if(i== j) {
            		matrizDistancias[i][j] = 0;        			
        		}else {
	        		Arista<T> arista = this.buscarArista(this.vertices.get(i), this.vertices.get(j));
	        		if(arista!=null) {
	            		matrizDistancias[i][j] = arista.getValor().intValue();        			
	        		} else {
	            		matrizDistancias[i][j] = 9999;        			
	        		}
        		}
        	}    		
    	}
    	imprimirMatriz(matrizDistancias);
    	
    	for (int k = 0; k < cantVertices; k++) 
        { 
            // Pick all vertices as source one by one 
            for (int i = 0; i < cantVertices; i++) 
            { 
                // Pick all vertices as destination for the 
                // above picked source 
                for (int j = 0; j < cantVertices; j++) 
                { 
                    // If vertex k is on the shortest path from 
                    // i to j, then update the value of dist[i][j] 
                    if (matrizDistancias[i][k] + matrizDistancias[k][j] < matrizDistancias[i][j]) 
                    	matrizDistancias[i][j] = matrizDistancias[i][k] + matrizDistancias[k][j]; 
                } 
            } 
      
            imprimirMatriz(matrizDistancias);
        } 
    	
    }
    
    public void imprimirMatriz(int[][] m) {
    	for(int i=0; i<m.length;i++) {
    		System.out.print("[ ");
        	for(int j=0; j<m[i].length;j++) {
        		System.out.print(i+":"+j+" = "+m[i][j]+ ",   ");
        	}
        	System.out.println(" ]");
    	}
    	
    	

    }
    
    public Boolean existeCamino(Vertice<T> v1, Vertice<T> v2, Integer n) {
    	
    	Stack<Vertice<T>> visitar = new Stack<Vertice<T>>();
    	HashSet<Vertice<T>> visitados = new HashSet<Vertice<T>>();
    	
    	visitar.push(v1);
    	int saltos = 0;
    	
    	while(!visitar.empty()) {
    		saltos++;
    		Vertice<T> vInicio = visitar.pop();
    		for(Vertice<T> unAdya : this.getAdyacentes(vInicio)) {
    			if(saltos<=n && unAdya.equals(v2)) return true;
    			if(!visitados.contains(unAdya)) {
    				visitar.push(unAdya);
    				visitados.add(unAdya);
    			}
    		}
    	}
    	return false;
    }
    
    public Boolean existeCaminoRec(Vertice<T> v1, Vertice<T> v2, Integer n) {
    	HashSet<Vertice<T>> visitados = new HashSet<Vertice<T>>();
    	visitados.add(v1);
    	return existeCaminoRec(v1, v2, n,visitados );
    }
    
    public Boolean existeCaminoRec(Vertice<T> v1, Vertice<T> v2, Integer n, HashSet<Vertice<T>> visitados) {
		if (n < 0)
			return false;
		for (Vertice<T> unAdya : this.getAdyacentes(v1)) {
			if (n >= 0 && unAdya.equals(v2))
				return true;
			if (!visitados.contains(unAdya)) {
				visitados.add(unAdya);
				Boolean existe = existeCaminoRec(unAdya, v2, n - 1, visitados);
				if (existe)
					return true;
			}

		}
		return false;
	}
    
    
}
