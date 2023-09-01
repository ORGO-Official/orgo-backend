package orgo.backend.setting;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.util.List;

public class TruncationTestExecutionListener extends AbstractTestExecutionListener {
    private final static String MYSQL_FOREIGN_KEY_CHECK_OFF = "SET FOREIGN_KEY_CHECKS = 0;";
    private final static String MYSQL_FOREIGN_KEY_CHECK_ON = "SET FOREIGN_KEY_CHECKS = 1;";
    private final static String TRUNCATE_ALL_JPQL = "SELECT Concat('TRUNCATE TABLE ', TABLE_NAME, ';') AS q FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'";

    @Override
    public void afterTestMethod(final TestContext testContext) {
        final JdbcTemplate jdbcTemplate = getJdbcTemplate(testContext);
        final List<String> truncateQueries = getTruncateQueries(jdbcTemplate);
        truncateTables(jdbcTemplate, truncateQueries);
    }

    private List<String> getTruncateQueries(final JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForList(TRUNCATE_ALL_JPQL, String.class);
    }

    private JdbcTemplate getJdbcTemplate(final TestContext testContext) {
        return testContext.getApplicationContext().getBean(JdbcTemplate.class);
    }

    private void truncateTables(final JdbcTemplate jdbcTemplate, final List<String> truncateQueries) {
        execute(jdbcTemplate, MYSQL_FOREIGN_KEY_CHECK_OFF);
        truncateQueries.forEach(v -> execute(jdbcTemplate, v));
        execute(jdbcTemplate, MYSQL_FOREIGN_KEY_CHECK_ON);
    }

    private void execute(final JdbcTemplate jdbcTemplate, final String query) {
        jdbcTemplate.execute(query);
    }
}
