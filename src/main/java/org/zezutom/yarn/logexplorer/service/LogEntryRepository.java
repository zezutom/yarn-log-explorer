package org.zezutom.yarn.logexplorer.service;

import java.util.List;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;
import org.zezutom.yarn.logexplorer.model.LogEntry;
import org.zezutom.yarn.logexplorer.utils.DbUtils;
import org.zezutom.yarn.logexplorer.utils.LogEntryMapper;
import org.zezutom.yarn.logexplorer.utils.LogFileParser;

@Repository
@PropertySource("classpath:/application.properties")
public class LogEntryRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogEntryRepository.class);
	
	@Autowired
	private HbaseTemplate template;
	
	@Autowired
	private LogEntryMapper mapper;
	
	@Autowired
	private LogFileParser parser;
	
	@Value("${hbase.table.name}")
	private String tableName;
			
	public List<LogEntry> uploadLogs() {				
		return template.execute(tableName, new TableCallback<List<LogEntry>>() {
			
			@Override
			public List<LogEntry> doInTable(HTableInterface table) throws Throwable {
				List<LogEntry> entries = parser.parse();
				entries.stream().forEach(entry -> {
					try {
						table.put(mapper.map(entry));
					} catch (Exception e) {
						LOGGER.error("Failed to put an entry into the table.", e);
					}
				});
				return entries;
			}		
		});
	}
	
	public List<LogEntry> findAll() {
		return template.find(tableName, DbUtils.CF_INFO, new RowMapper<LogEntry>() {

			@Override
			public LogEntry mapRow(Result result, int rowNum) throws Exception {
				return mapper.map(result);
			}
		});
	}
}
