package Biblioteca;

import java.util.ArrayList;
import java.util.Collection;

public class Libro {
    String nombreLibro;
    Boolean libroPrestado;


    public Libro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
        libroPrestado = false;

    }

    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
    }

    public Boolean getLibroPrestado() {
        return libroPrestado;
    }

    public void setLibroPrestado(Boolean libroPrestado) {
        this.libroPrestado = libroPrestado;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "nombreLibro='" + nombreLibro + '\'' +
                '}';
    }
}
