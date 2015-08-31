package org.zezutom.yarn.logexplorer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zezutom.yarn.logexplorer.utils.DbUtils;

import static org.junit.Assert.*;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(locations="classpath:/application-context.xml")
public class DbUtilsTest {

	@Autowired
	private DbUtils utils;
	
	@Test
	public void contextLoads() {
		assertNotNull(utils);
	}
	
	@Test
	public void initSucceeds() throws IOException {
		utils.initialize();
	}
}
