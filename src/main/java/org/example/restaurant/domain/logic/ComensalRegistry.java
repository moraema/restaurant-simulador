package org.example.restaurant.domain.logic;

import org.example.restaurant.domain.entities.Comensal;

import java.util.HashMap;
import java.util.Map;

public class ComensalRegistry {
    private static final Map<Integer, Comensal> comensales = new HashMap<>();

    public static synchronized void registrarComensal(Comensal comensal) {
        comensales.put(comensal.getId(), comensal);
    }

    public static synchronized Comensal getComensalById(int id) {
        return comensales.get(id);
    }

    public static synchronized void removerComensal(int id) {
        comensales.remove(id);
    }
}