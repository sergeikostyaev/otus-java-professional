package ru.otus.homework.impls;


import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;
import ru.otus.homework.EntityClassMetaData;
import ru.otus.homework.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        String tableName = entityClassMetaData.getName().toLowerCase();

        return String.format("select * from %s", tableName);
    }

    @Override
    public String getSelectByIdSql() {
        List<Field> a = entityClassMetaData.getAllFields();
        String tableName = entityClassMetaData.getName().toLowerCase();
        String part = a.stream().map(c -> c.getName().toString()).collect(Collectors.joining(", "));
        String id = entityClassMetaData.getIdField().getName();

        return String.format("select %s from %s where %s = ?", part, tableName, id);
    }

    @Override
    public String getInsertSql() {
        List<Field> a = entityClassMetaData.getFieldsWithoutId();
        String tableName = entityClassMetaData.getName().toLowerCase();
        String fields = a.stream().map(c -> c.getName().toString()).collect(Collectors.joining(", ", "", ""));
        String args = a.stream().map(c -> "?").collect(Collectors.joining(", ", "", ""));

        return String.format("insert into %s(%s) values (%s)", tableName, fields, args);
    }

    @Override
    public String getUpdateSql() {
        List<Field> a = entityClassMetaData.getFieldsWithoutId();
        String tableName = entityClassMetaData.getName().toLowerCase();
        String fields = a.stream().map(c -> c.getName().toString()+ " = ?").collect(Collectors.joining(", ", "", ""));
        String id = entityClassMetaData.getIdField().getName();


        return String.format("update %s set %s where %s = ?", tableName, fields, id);
    }
}


class Test {
    public static void main(String[] args) {

        EntityClassMetaData entityClassMetaDataClient = new EntityClassMetaDataImpl(Manager.class);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        System.out.println(entitySQLMetaDataClient.getSelectAllSql());
        System.out.println(entitySQLMetaDataClient.getSelectByIdSql());
        System.out.println(entitySQLMetaDataClient.getInsertSql());
        System.out.println(entitySQLMetaDataClient.getUpdateSql());

    }

}
