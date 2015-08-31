package org.zezutom.yarn.logexplorer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zezutom.yarn.logexplorer.model.LogEntry;
import org.zezutom.yarn.logexplorer.service.LogEntryRepository;

import static org.junit.Assert.*;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(locations="classpath:/application-context.xml")
public class LogEntryRepositoryTest {

	@Autowired
	private LogEntryRepository dao;
	
	@Test
	public void contextLoads() {
		assertNotNull(dao);
	}
	
	@Test
	public void logsAreUploaded() {
		assertNotNull(dao.uploadLogs());
	}
	
	@Test
	public void logsAreRetrieved() {
		List<LogEntry> entries = dao.findAll();
		assertNotNull(entries);
		assertTrue(entries.size() == 20);		
	}
}
