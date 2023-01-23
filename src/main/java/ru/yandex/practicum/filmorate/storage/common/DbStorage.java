package ru.yandex.practicum.filmorate.storage.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.StorageObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DbStorage<T extends StorageObject> {
    protected final JdbcTemplate jdbcTemplate;
    public T add(T obj) {
        //adding data to main StorageObject table
        Map<String, Object> toMap = obj.toMainTableMap();
        String dbName = obj.getMainDBName();
        String idColumnName = new ArrayList<>(toMap.keySet()).get(0);
        toMap.remove(idColumnName);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(dbName)
                .usingGeneratedKeyColumns(idColumnName);
        Integer id = simpleJdbcInsert.executeAndReturnKey(toMap).intValue();
        obj.setId(id);

        //adding data to related StorageObject tables if necessary
        addToRelatedTable(id, idColumnName, obj);
        return obj;
    }

    public T update(T obj) {
        try {
            Map<String, Object> toMap = obj.toMainTableMap();
            String dbName = obj.getMainDBName();
            String idColumnName = new ArrayList<>(toMap.keySet()).get(0);
            toMap.remove(idColumnName);
            String selectQuery = "SELECT " + idColumnName + " FROM " + dbName + " WHERE " + idColumnName + "= ?";
            Map<String, Object> selectMap = jdbcTemplate.queryForMap(selectQuery, obj.getId());
            Integer id = (Integer) selectMap.get(idColumnName);
            StringBuilder updateQueryBuilder = new StringBuilder();
            updateQueryBuilder.append("UPDATE ").append(dbName).append(" SET ");
            for (String key : toMap.keySet()) {
                updateQueryBuilder.append(key + "= ?,");
            }
            String updateQuery = updateQueryBuilder.substring(0, updateQueryBuilder.toString().length() - 1) + " WHERE " + idColumnName + "=?";
            toMap.put("StorageObjectId", obj.getId());
            jdbcTemplate.update(updateQuery, toMap.values().toArray());

            //adding data to related StorageObject tables if necessary
            addToRelatedTable(obj.getId(), idColumnName, obj);
            return obj;
        } catch (EmptyResultDataAccessException exp) {
            throw new ResourceNotFoundException(String.format("Object with id %d for update is not found", obj.getId()));
        }
    }

    private void addToRelatedTable(Integer id, String idColumnName, T obj) {
        Map<String, List<StorageObject>> toRelatedTablesMap = obj.toRelatedTablesMap();
        if (toRelatedTablesMap != null)
            for (String relatedDbName : toRelatedTablesMap.keySet()) {
                String queryStr = "DELETE FROM " + relatedDbName + " WHERE " + idColumnName +"= ?";
                jdbcTemplate.update(queryStr, id);
                List<StorageObject> listToAdd = toRelatedTablesMap.get(relatedDbName);
                if (listToAdd != null && listToAdd.size() != 0) {
                    insertListToRelatedTable(obj.getId(), idColumnName, relatedDbName, listToAdd);
                }
            }
    }

    protected T deleteObj(T obj) {
        Map<String, Object> toMap = obj.toMainTableMap();
        String dbName = obj.getMainDBName();
        String idColumnName = new ArrayList<>(toMap.keySet()).get(0);
        String queryStr = "DELETE FROM " + dbName + " WHERE " + idColumnName + " = ?";
        jdbcTemplate.update(queryStr, obj.getId());
        return obj;
    }

    private void insertListToRelatedTable(Integer id, String idColumnName, String relatedDbName, List<StorageObject> listToAdd) {
        String relatedDbIdColumnName = new ArrayList<>(listToAdd.get(0).toMainTableMap().keySet()).get(0);
        String queryStr2 = "INSERT INTO " + relatedDbName + " (" + idColumnName + ", " + relatedDbIdColumnName + ") VALUES (?, ?)";
        jdbcTemplate.batchUpdate(queryStr2, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, listToAdd.get(i).getId());
            }
            @Override
            public int getBatchSize() {
                return listToAdd.size();
            }
        });
    }
}
