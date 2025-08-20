package com.diego.desafio_goat_b.mapper;

public interface BaseMapper<E, D> {

    D toDTO(E entity);

    E toEntity(E entity, D dto);
}