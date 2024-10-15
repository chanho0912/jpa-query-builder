package persistence.sql.ddl.query;

import persistence.sql.Dialect;
import persistence.sql.definition.TableDefinition;
import persistence.sql.definition.TableId;

public class CreateQueryBuilder {
    private final Dialect dialect;

    public CreateQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String build(Class<?> entityClass) {
        TableDefinition tableDefinition = new TableDefinition(entityClass);
        StringBuilder query = new StringBuilder();

        query.append("CREATE TABLE ").append(tableDefinition.tableName());
        query.append(" (");

        tableDefinition.queryableColumns().forEach(column -> column.applyToCreateQuery(query, dialect));

        definePrimaryKey(tableDefinition.tableId(), query);

        query.append(");");
        return query.toString();
    }

    private void definePrimaryKey(TableId pk, StringBuilder query) {
        query.append("PRIMARY KEY (").append(pk.name()).append(")");
    }
}
