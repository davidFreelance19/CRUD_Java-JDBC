package bases.repositories;

import java.util.List;
import java.util.Optional;

import bases.config.ExceptionHandler;
import bases.dtos.CreateDto;
import bases.dtos.UpdateDto;

public interface Repository<T> {
    List<T> getAll() throws ExceptionHandler;

    Optional<T> getById(Long id) throws ExceptionHandler;

    String save(CreateDto<?, ?> createDto) throws ExceptionHandler;

    String update(UpdateDto<?, ?> updateDto) throws ExceptionHandler;

    String delete(Long id) throws ExceptionHandler;
}
