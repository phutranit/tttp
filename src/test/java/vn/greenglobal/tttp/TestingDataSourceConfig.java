package vn.greenglobal.tttp;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

//@Configuration
public class TestingDataSourceConfig {

	// @Bean
	// @Primary
//	public DataSource dataSource() {
//		return new EmbeddedDatabaseBuilder()
//				.generateUniqueName(true)
//				.setType(EmbeddedDatabaseType.H2)
//				.setScriptEncoding("UTF-8")
//				.ignoreFailedDrops(true).addScript("data_sample.sql")
				// .addScripts("user_data.sql", "country_data.sql")
//				.build();
//	}
}
