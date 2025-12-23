package com.react.common;

import java.util.List;
import java.util.Optional;

public interface IService<E, D, ID> {

    List<D> getAll();

    D getById(ID id);

    D create(D dto);

    D update(ID id, D dto);

    void delete(ID id);
}