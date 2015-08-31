package org.zezutom.yarn.logexplorer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zezutom.yarn.logexplorer.model.LogEntry;
import org.zezutom.yarn.logexplorer.utils.LogFileParser;

import static org.junit.Assert.*;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(locations="classpath:/application-context.xml")
public class LogFileParserTest {

	@Autowired
	private LogFileParser parser;
	
	@Test
	public void inputFileIsParsed() {
		List<LogEntry> entries = parser.parse();
		assertNotNull(entries);
		assertTrue(entries.size() == 20);		
	}
}
