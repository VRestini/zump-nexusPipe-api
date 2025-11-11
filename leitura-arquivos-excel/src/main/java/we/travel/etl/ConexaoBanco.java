package we.travel.etl;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
public class ConexaoBanco {
    private final JdbcTemplate jdbcTemplate;
    private final BasicDataSource basicDataSource;

    public ConexaoBanco() {
        BasicDataSource basicDataSource = new BasicDataSource();
        //basicDataSource.setUrl("jdbc:mysql://localhost:3306/zump?useSSL=false&serverTimezone=UTC");
        //basicDataSource.setUrl("jdbc:mysql://54.82.118.20:3306/zump?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        basicDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/zump?useSSL=false&serverTimezone=UTC");
        basicDataSource.setUsername("wetravel");
        basicDataSource.setPassword("Urubu100@");

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
