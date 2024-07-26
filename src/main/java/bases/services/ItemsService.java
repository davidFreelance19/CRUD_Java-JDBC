package bases.services;

import java.util.List;
import java.util.Optional;

import bases.config.ExceptionHandler;
import bases.dtos.CreateDto;
import bases.dtos.UpdateDto;
import bases.models.Item;
import bases.repositories.*;

public class ItemsService implements Service<Item> {

    private Repository<Item> repository;

    public ItemsService(Repository<Item> repository) {
        this.repository = new ItemRepositoryImpl();
    }

    @Override
    public List<Item> getAll() throws ExceptionHandler {
        return this.repository.getAll();
    }

    @Override
    public Optional<Item> getById(Long id) throws ExceptionHandler {
        return this.repository.getById(id);
    }

    @Override
    public String save(CreateDto<?, ?> createDto) throws ExceptionHandler {
        return this.repository.save(createDto);
    }

    @Override
    public String update(UpdateDto<?, ?> updateDto) throws ExceptionHandler {
        return this.repository.update(updateDto);
    }

    @Override
    public String delete(Long id) throws ExceptionHandler {
        return this.repository.delete(id);
    }

}
