package we.travel;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
public class ConexaoBanco {
    private final JdbcTemplate jdbcTemplate;
    private final BasicDataSource basicDataSource;

    public ConexaoBanco() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/nexus?useSSL=false&serverTimezone=UTC");
        basicDataSource.setUsername("nexus");
        basicDataSource.setPassword("senha_nexus");

        // Define o driver MySQL
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");


        this.basicDataSource = basicDataSource;
        this.jdbcTemplate = new JdbcTemplate(basicDataSource);
    }

    public BasicDataSource getBasicDataSource() {
        return basicDataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
