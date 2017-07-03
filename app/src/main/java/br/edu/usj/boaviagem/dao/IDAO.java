package br.edu.usj.boaviagem.dao;

import java.util.List;

/**
 * Created by jaqueline on 26/06/2017.
 */

interface IDAO<T> {

    boolean salvar(T p);

    boolean excluir(Integer id);

    boolean atualizar (T p);

    List<T> listar();

    T buscarPorId(Integer id);
}
