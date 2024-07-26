package bases.dtos;

import java.util.Optional;

public abstract class CreateDto<T, R> {
    public abstract R create(Optional<T> t);
}