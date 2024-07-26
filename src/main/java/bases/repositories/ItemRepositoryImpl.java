package bases.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import bases.config.ConexionDB;
import bases.config.ExceptionHandler;
import bases.dtos.CreateDto;
import bases.dtos.UpdateDto;
import bases.models.Item;
import bases.dtos.items.*;
import bases.utils.*;

public class ItemRepositoryImpl implements Repository<Item> {

    private Connection getConnection() throws SQLException {
        return ConexionDB.getConnection();
    }

    @Override
    public List<Item> getAll() throws ExceptionHandler {
        final String SQL = "SELECT * FROM item";
        List<Item> items = new ArrayList<Item>();

        try (

                Connection conn = getConnection();
                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery(SQL);

        ) {

            while (result.next()) {
                Item item = new Item(result.getLong("id"), result.getString("name"));
                items.add(item);
            }

            return items;
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public Optional<Item> getById(Long id) throws ExceptionHandler {
        final String SQL = "SELECT * FROM item WHERE id = ?";
        Optional<Item> item = Optional.empty();

        try (

                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL)

        ) {
            stmt.setLong(1, id);

            try (ResultSet result = stmt.executeQuery()) {

                if (result.next()) {
                    item = Optional.of(new Item(result.getLong("id"), result.getString("name")));
                    return item;
                }

                throw new ExceptionHandler("Item not found", HttpStatus.NOT_FOUND);
            }

        } catch (SQLException e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ExceptionHandler e) {
            throw new ExceptionHandler(e.getMessage(), e.getStatus());
        }
    }

    @Override
    public String save(CreateDto<?, ?> createDto) throws ExceptionHandler {
        CreateItemDto createItemDto = (CreateItemDto) createDto;
        final String SQL = "INSERT INTO item (name) VALUES (?)";

        try (

                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL)

        ) {

            stmt.setString(1, createItemDto.getName());
            stmt.executeUpdate();

            return "Item created successfully";
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String update(UpdateDto<?, ?> updateDto) throws ExceptionHandler {
        UpdateItemDto updateItemDto = (UpdateItemDto) updateDto;
        getById(updateItemDto.getId());

        final String SQL = "UPDATE item SET name = ? WHERE id = ?";

        try (

                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL)

        ) {
            String name = (String) updateItemDto.getValues().get().get("name");
            stmt.setString(1, name);
            stmt.setLong(2, updateItemDto.getId());
            stmt.executeUpdate();

            return "Item updated successfully";
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String delete(Long id) throws ExceptionHandler {
        getById(id);
        final String SQL = "DELETE FROM item WHERE id = ?";

        try (

                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL)

        ) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

            return "Item deleted successfully";
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorsShared.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
