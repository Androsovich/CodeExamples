package org.androsovich.accounts.dto.assemblers;

public interface EntityAssembler<T, D> {
    D toEntity(T entity);
}
