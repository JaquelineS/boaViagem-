package br.edu.usj.boaviagem.dao;

import java.util.List;

import br.edu.usj.boaviagem.entity.Gasto;

/**
 * Created by jaqueline on 26/06/2017.
 */

public interface IGastoDAO extends IDAO<Gasto> {

    List<Gasto> buscarPorViagemId(Integer id);

}
