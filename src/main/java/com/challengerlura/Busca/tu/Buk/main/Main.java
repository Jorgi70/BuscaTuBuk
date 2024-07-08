package com.challengerlura.Busca.tu.Buk.main;

import com.challengerlura.Busca.tu.Buk.model.Autor;
import com.challengerlura.Busca.tu.Buk.model.DatosLibro;
import com.challengerlura.Busca.tu.Buk.model.LibroAPI;
import com.challengerlura.Busca.tu.Buk.model.Libros;
import com.challengerlura.Busca.tu.Buk.reposity.IAutorReposity;
import com.challengerlura.Busca.tu.Buk.reposity.ILibroReposity;
import com.challengerlura.Busca.tu.Buk.service.ConsumoAPI;
import com.challengerlura.Busca.tu.Buk.service.ConvierteDatos;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.*;


public class Main {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private List<Libros> datosLibros = new ArrayList<>();
    private ILibroReposity libroReposity;
    private IAutorReposity autorReposity;

    public Main(ILibroReposity libroReposity, IAutorReposity autorReposity){
        this.libroReposity = libroReposity;
        this.autorReposity = autorReposity;
    }


    public void Menu(){

        var opcion = -1;
        while(opcion != 9){
            var menu = """
                       ******** TU BUK ********
                    tu lugar favoritos para tus libros
                    **********************************
                    1 - Agregar Libro Por Nombre
                    2 - Libros Buscados
                    3 - Buscar por Nombre
                    4 - Mostrar todos los Autores
                    5 - Buscar por Autor vivo en desde el año
                    6 - Buscar Libro por Idioma
                    7 - Top 10 Libro mas Descargados
                    
                    9 - Salir
                    **********************************
                    """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Ingrese un Numero del 1 al 7");
                opcion = 10;
                teclado.nextLine();
            }
            switch (opcion) {
                case 1:
                    ingresarLibro();
                    break;
                case 2:
                    librosBuscados();
                    break;
                case 3:
                    libroPorNombre();
                    break;
                case 4:
                    libroPorAutor();
                    break;
                case 5:
                    autorVivoAno();
                    break;
                case 6:
                    libroPorIdioma();
                    break;
                case 7:
                    libroTop10();
                    break;

                case 9:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida, ingrese nuevamente");
            }

        }
    }

    private Libros obtenerLibro(){
        System.out.println("Ingrese el nombre del Libro: ");
        var nombreLibro = teclado.nextLine().toLowerCase();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        LibroAPI datos = conversor.obtenerDatos(json, LibroAPI.class);

        if (datos != null && datos.resultadoLibros() != null && !datos.resultadoLibros().isEmpty()){
            DatosLibro primerLibro = datos.resultadoLibros().get(0);
            return new Libros(primerLibro);
        } else {
            System.out.println("No se Encontro nada");
            return null;
        }

    }

    private void ingresarLibro(){
        Libros libros = obtenerLibro();

        if(libros == null){
            System.out.println("Libro no Encontrado");
            return;
        }

        try{
            boolean libroExists = libroReposity.existsByTitulo(libros.getTitulo());
            if (libroExists){
                System.out.println("El libro ya existe en la base de datos!");
            }else {
                libroReposity.save(libros);
                System.out.println(libros.toString());
            }
        }catch (InvalidDataAccessApiUsageException e){
            System.out.println("No se puede persisitir el libro buscado!");
        }

    }

    private void librosBuscados(){
        datosLibros = libroReposity.findAll();
        if (datosLibros.isEmpty()){
            System.out.println("No se Encontramos Libros en la BD");
        } else {
            System.out.println("Libros Encontrados;");
            for(Libros libro : datosLibros){
                System.out.println(libro.toString());
            }
        }
    }

    private void libroPorNombre(){
        System.out.println("Ingrese Nombre del Libro para Buscar: ");
        var libroBuscado = teclado.nextLine();
        Libros libroNombre = libroReposity.findByTituloContainsIgnoreCase(libroBuscado);
        if(libroNombre != null){
            System.out.println("El Libro buscado fue: " + libroNombre);
        } else {
            System.out.println("No se encontro el libro: " + libroBuscado);
        }
    }

    private void libroPorAutor() {
        var autoresTotal = autorReposity.findAll();
        if(autoresTotal.isEmpty()){
            System.out.println("No hay Autores en la Base de Datos");
        } else {
            System.out.println("Autores: ");
            autoresTotal.stream().forEach(a -> System.out.println(a.getNombre()));
//            System.out.println("Ingrese nombre de autor que desea buscar: ");
//            var autor = teclado.next();
//            Optional<Libros> libroBuscado = libroReposity.findLibrosByAutorNombre(autor);
//            //libroBuscado.stream().forEach(System.out::println);
//            if (libroBuscado != null) {
//                libroBuscado.stream()
//                                .forEach(System.out::println);
//
//                //System.out.println("Los libros del autor buscado son: " + libroBuscado.get().getTitulo());
//            } else {
//                System.out.println("El autor:" + autor + "no fue encontrado");
//            }
        }
    }

    private void autorVivoAno(){
        System.out.println("Ingrese desde que año busca el autor: ");
        var anoBuscado = teclado.nextInt();
        teclado.nextLine();
//        System.out.println("Ingrese nuevamente la otra fecha: ");
//        var anoBuscado1 = teclado.nextInt();
//        teclado.nextLine();
        List<Autor> autorBuscado = autorReposity.buscarPorFechas(anoBuscado);

        if (autorBuscado.isEmpty()){
            System.out.println("No se encontraron autores vivos entre esa fechas");
        } else {
            autorBuscado.stream()
                    .forEach(System.out::println);
        }
    }

    private void libroPorIdioma(){
        System.out.println("Ingrese el idioma para buscar libro (en = Ingles || es: Español || fr : Frances): ");
        var idiomaLibro = teclado.nextLine();

        List<Libros> idiomasLibro = libroReposity.BuscarPorIdioma(idiomaLibro);

        if(idiomasLibro.isEmpty()){
            System.out.println("No se Encontraron libros con ese Idioma");
        } else {
            idiomasLibro.stream()
                    .forEach(System.out::println);
        }
    }

    private void libroTop10(){
    List<Libros> top10Libros = libroReposity.top10MasDescargados();
    if (!top10Libros.isEmpty()){
        int index = 1;
        for (Libros libro : top10Libros){
            System.out.printf("%d Libro: %s, Autor: %s, Descargas: %s\n",
                    index, libro.getTitulo(), libro.getAutor(), libro.getDescargas());
            index++;
        }
      }
    }
}


