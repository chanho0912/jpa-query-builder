package persistence.sql.dml.query;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.definition.TableDefinition;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateQueryBuilderTest {
    @Entity
    private static class HasNullableColumnEntity {
        @Id
        private Long id;

        private String name;

        private Integer age;

        public HasNullableColumnEntity() {
        }

        public HasNullableColumnEntity(Long id, Integer age) {
            this.id = id;
            this.age = age;
        }

        public HasNullableColumnEntity(Long id, String name, Integer age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }

    @Test
    @DisplayName("모든 필드에 대한 update 쿼리를 정상적으로 생성한다.")
    void shouldBuildUpdateQuery() {
        HasNullableColumnEntity hasNullableColumnEntity = new HasNullableColumnEntity(1L, "john_doe", 30);
        TableDefinition tableDefinition = new TableDefinition(HasNullableColumnEntity.class);

        UpdateQueryBuilder queryBuilder = new UpdateQueryBuilder();
        queryBuilder.addColumn(tableDefinition.queryableColumns(), hasNullableColumnEntity);
        String query = queryBuilder.build(hasNullableColumnEntity);

        assertThat(query).isEqualTo("UPDATE HasNullableColumnEntity SET id = 1, name = 'john_doe', age = 30;");
    }

    @Test
    @DisplayName("nullable 필드가 있어도 update 쿼리를 정상적으로 생성한다.")
    void shouldBuildUpdateQueryWhenHasNullableColumns() {
        HasNullableColumnEntity hasNullableColumnEntity = new HasNullableColumnEntity(1L, 30);
        TableDefinition tableDefinition = new TableDefinition(HasNullableColumnEntity.class);

        UpdateQueryBuilder queryBuilder = new UpdateQueryBuilder();
        queryBuilder.addColumn(tableDefinition.queryableColumns(), hasNullableColumnEntity);
        String query = queryBuilder.build(hasNullableColumnEntity);

        assertThat(query).isEqualTo("UPDATE HasNullableColumnEntity SET id = 1, name = null, age = 30;");
    }

    @Test
    @DisplayName("조건이 있는 update 쿼리를 정상적으로 생성한다.")
    void shouldBuildUpdateQueryWhenHasConditionColumns() {
        HasNullableColumnEntity hasNullableColumnEntity = new HasNullableColumnEntity(1L, "john_doe", 30);
        TableDefinition tableDefinition = new TableDefinition(HasNullableColumnEntity.class);

        UpdateQueryBuilder queryBuilder = new UpdateQueryBuilder();
        queryBuilder.addColumn(tableDefinition.queryableColumns(), hasNullableColumnEntity);
        queryBuilder.addConditions(List.of(tableDefinition.tableId()), hasNullableColumnEntity);

        String query = queryBuilder.build(hasNullableColumnEntity);

        assertThat(query).isEqualTo("UPDATE HasNullableColumnEntity SET id = 1, name = 'john_doe', age = 30 WHERE id = 1;");
    }
}
