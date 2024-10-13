package persistence.sql.ddl.query;

import jakarta.persistence.GenerationType;
import persistence.sql.ddl.PrimaryKeyGenerationStrategy;
import persistence.sql.ddl.definition.PrimaryKey;

public class IdentityKeyGenerationStrategy implements PrimaryKeyGenerationStrategy {

    @Override
    public String generatePrimaryKeySQL(PrimaryKey pk) {
        return "GENERATED BY DEFAULT AS IDENTITY";
    }

    @Override
    public boolean supports(PrimaryKey pk) {
        return pk.generationType().equals(GenerationType.IDENTITY);
    }
}
