package org.zezutom.yarn.logexplorer.utils;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;
import org.zezutom.yarn.logexplorer.model.LogEntry;

@Service
public class LogEntryMapper {

	// Qualifiers
	
	private byte[] qIpAddress = Bytes.toBytes("ipAddress");
	
	private byte[] qCountry = Bytes.toBytes("country");
	
	private byte[] qStatus = Bytes.toBytes("status");
	
	private byte[] qTimestamp = Bytes.toBytes("timestamp");
	
	public Put map(LogEntry entry) {
		Put p = new Put(Bytes.toBytes(entry.getIpAddress()));
		p.addColumn(DbUtils.CF_INFO_BYTES, qIpAddress, Bytes.toBytes(entry.getIpAddress()));
		p.addColumn(DbUtils.CF_INFO_BYTES, qCountry, Bytes.toBytes(entry.getCountry()));
		p.addColumn(DbUtils.CF_INFO_BYTES, qStatus, Bytes.toBytes(entry.getStatus()));
		p.addColumn(DbUtils.CF_INFO_BYTES, qTimestamp, Bytes.toBytes(entry.getTimeStamp()));
		
		return p;
	}
	
	public LogEntry map(Result result) {
		LogEntry entry = new LogEntry();
		entry.setIpAddress(Bytes.toString(result.getValue(DbUtils.CF_INFO_BYTES, qIpAddress)));
		entry.setCountry(Bytes.toString(result.getValue(DbUtils.CF_INFO_BYTES, qCountry)));
		entry.setStatus(Bytes.toString(result.getValue(DbUtils.CF_INFO_BYTES, qStatus)));
		entry.setTimeStamp(Bytes.toString(result.getValue(DbUtils.CF_INFO_BYTES, qTimestamp)));
		return entry;
	}
}
