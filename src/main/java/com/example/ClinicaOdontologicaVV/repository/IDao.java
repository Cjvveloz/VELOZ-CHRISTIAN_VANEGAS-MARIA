package com.example.ClinicaOdontologicaVV.repository;

import java.util.List;

public interface IDao<T> {
    T registrar(T t);
    T buscarPorId(Long id);
    List<T> listarTodos();
}