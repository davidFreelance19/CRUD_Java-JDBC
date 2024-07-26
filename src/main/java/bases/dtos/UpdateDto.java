package bases.dtos;

import java.util.Optional;

public abstract class UpdateDto<T, R> {

    public abstract R update(Long id, Optional<T> t);
}