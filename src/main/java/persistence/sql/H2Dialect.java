package persistence.sql;

import persistence.sql.definition.ColumnDefinition;

import java.util.Map;

public class H2Dialect implements Dialect {
    private final Map<SqlType, String> typeMap = Map.of(
            SqlType.VARCHAR, "VARCHAR",
            SqlType.BIGINT, "BIGINT",
            SqlType.INTEGER, "INTEGER"
    );

    @Override
    public String translateType(ColumnDefinition columnDefinition) {
        return switch (columnDefinition.sqlType()) {
            case VARCHAR -> typeMap.get(SqlType.VARCHAR) + "(" + columnDefinition.length() + ")";
            case BIGINT -> typeMap.get(SqlType.BIGINT);
            case INTEGER -> typeMap.get(SqlType.INTEGER);
        };
    }
}
