import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.cfg.Configuration;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@ContextConfiguration(locations={"/META-INF/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback=true)
@ActiveProfiles("export-schema")
public class ExportDatabaseSchema {

	@Resource(name="&entityManagerFactory")
	private LocalContainerEntityManagerFactoryBean entityManagerFactory;

	@Test
	public void exportDatabaseSchema(){


		PersistenceUnitInfo persistenceUnitInfo = entityManagerFactory.getPersistenceUnitInfo();
		Map jpaPropertyMap = entityManagerFactory.getJpaPropertyMap();

		Configuration configuration = new Ejb3Configuration().configure( persistenceUnitInfo, jpaPropertyMap ).getHibernateConfiguration();

		SchemaExport schema = new SchemaExport(configuration);
		schema.setOutputFile("C:/J2EE/git/appa-planning/AppaPlanningAdmin/src/main/resources/db/schema.sql");
		schema.create(false, false);
	}
}