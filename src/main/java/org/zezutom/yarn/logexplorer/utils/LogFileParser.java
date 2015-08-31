package org.zezutom.yarn.logexplorer.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.zezutom.yarn.logexplorer.model.LogEntry;

@Component
@PropertySource("classpath:/application.properties")
public class LogFileParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogFileParser.class);

	// The expected number of columns
	private static final int COLS = 4;

	@Value("${general.input.file}")
	private String inputFile;

	@Value("${general.input.file.separator:,}")
	private String separator;

	public List<LogEntry> parse() {
		List<LogEntry> entries = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] record = line.split(separator);
				if (record.length != COLS) {
					throw new IllegalStateException(
							"Unexpected number of columns, was " + record.length + ", should be " + COLS);
				}
				LogEntry entry = new LogEntry();
				entry.setTimeStamp(record[0]);
				entry.setIpAddress(record[1]);
				entry.setCountry(record[2]);
				entry.setStatus(record[3]);
				
				entries.add(entry);
			}
		} catch (IOException | IllegalStateException e) {
			LOGGER.error("Failed to parse the log file.", e);
		}
		return entries;
	}
}
