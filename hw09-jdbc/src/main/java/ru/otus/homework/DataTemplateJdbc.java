package ru.otus.homework;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;
import ru.otus.homework.impls.EntityClassMetaDataImpl;
import ru.otus.homework.impls.EntitySQLMetaDataImpl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
        List<Object> args = new ArrayList<>();
        List<Field> fields = new ArrayList<>();

        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {

                    for(Field f : fields){
                        args.add(rs.getObject(rs.findColumn(f.getName())));
                    }
                    try {
                        return (T) constructor.newInstance(args.stream().toArray());
                    }catch (Exception e){
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
        throw new UnsupportedOperationException();
    }

    @Override
    public long insert(Connection connection, T client) {

        List<Object> params = new ArrayList<>();
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        for (Field f : fieldsWithoutId) {
            try {
                f.setAccessible(true);
                params.add(f.get(client));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }

    }

    @Override
    public void update(Connection connection, T client) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {

        EntityClassMetaData entityClassMetaDataClient = new EntityClassMetaDataImpl(Manager.class);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);

        var constructor = entityClassMetaDataClient.getConstructor();
        Object [] s = {1488,"b","c"};
        try {
            var a = (Manager)constructor.newInstance(s);
            System.out.println(s);
            System.out.println(a.getLabel());
        } catch (Exception e) {

        }


    }
}


//initArgsList.add(rs.getObject(rs.findColumn(field.getName())));