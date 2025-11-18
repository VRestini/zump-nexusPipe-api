package we.travel.etl;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Properties;

public class ConexaoBanco {
    private final JdbcTemplate jdbcTemplate;
    private final BasicDataSource basicDataSource;

    public ConexaoBanco() {
        BasicDataSource basicDataSource = new BasicDataSource();
        Properties prop = new Properties();
        //basicDataSource.setUrl("jdbc:mysql://localhost:3306/nexus?useSSL=false&serverTimezone=UTC");
        //basicDataSource.setUrl("jdbc:mysql://54.82.118.20:3306/zump?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        basicDataSource.setUrl(System.getenv("S3_BUCKET_NAME"));
        //basicDataSource.setUsername("nexus");
        basicDataSource.setUsername(System.getenv("DB_USERNAME"));
        //basicDataSource.setPassword("senha_nexus");
        basicDataSource.setPassword(prop.getProperty("DB_PASSWORD"));

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
