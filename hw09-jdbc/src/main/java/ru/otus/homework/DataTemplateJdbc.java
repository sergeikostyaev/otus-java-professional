package ru.otus.homework;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;
import ru.otus.homework.impls.EntityClassMetaDataImpl;
import ru.otus.homework.impls.EntitySQLMetaDataImpl;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;
import ru.otus.homework.impls.EntityClassMetaDataImpl;
import ru.otus.homework.impls.EntitySQLMetaDataImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        var constructor = entityClassMetaData.getConstructor();
        var args = new ArrayList<>();
        List<Field> fields = entityClassMetaData.getAllFields();

        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {

                    for (Field f : fields) {
                        args.add(rs.getObject(rs.findColumn(f.getName())));
                    }
                    try {
                        return constructor.newInstance(args.stream().toArray());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        var constructor = entityClassMetaData.getConstructor();
        List<T> list = new ArrayList<>();
        List<Field> fields = entityClassMetaData.getAllFields();
        var args = new ArrayList<>();

        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {

            try {
                while (rs.next()) {
                    for (Field f : fields) {
                        args.add(rs.getObject(rs.findColumn(f.getName())));
                    }
                    try {
                        list.add(constructor.newInstance(args.stream().toArray()));
                        args.clear();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                return list;

            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        List<Object> params = new ArrayList<>();
        var fields = entityClassMetaData.getFieldsWithoutId();

        try {
            for (Field f : fields) {
                f.setAccessible(true);
                params.add(f.get(client));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        List<Object> params = new ArrayList<>();
        var fields = entityClassMetaData.getFieldsWithoutId();
        try {
            for (Field f : fields) {
                f.setAccessible(true);
                params.add(f.get(client));
            }
            var idField = entityClassMetaData.getIdField();
            idField.setAccessible(true);
            params.add(idField.get(client));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {

            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
        throw new UnsupportedOperationException();
    }
}
