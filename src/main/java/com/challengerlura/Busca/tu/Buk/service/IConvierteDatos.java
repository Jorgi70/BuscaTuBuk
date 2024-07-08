package com.challengerlura.Busca.tu.Buk.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
