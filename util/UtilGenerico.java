package util;

import java.util.ArrayList;

public class UtilGenerico {

    
    @SafeVarargs
    public static <T> ArrayList<T> crearLista(T... elementos) {
        ArrayList<T> lista = new ArrayList<>();
        for (T elemento : elementos) {
            lista.add(elemento);
        }
        return lista;
    }
}