package org.zezutom.yarn.logexplorer.utils;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/application.properties")
public class DbUtils implements InitializingBean {

	public static final String CF_INFO = "cfInfo";
	
	public static final byte[] CF_INFO_BYTES = Bytes.toBytes("cfInfo");
	
	@Value("${hbase.table.name}")
	private String tableName;
	
	@Resource(name = "hbaseConfiguration")
	private Configuration conf;
		
	private Admin admin;
	
	public void initialize() throws IOException {
		TableName table = TableName.valueOf(tableName);
		if (admin.tableExists(table)) {
			if (!admin.isTableDisabled(table)) {
				System.out.printf("Disabling %s\n", tableName);
				admin.disableTable(table);				
			}
			System.out.printf("Deleting %s\n", tableName);
			admin.deleteTable(table);
		}
		
		HTableDescriptor tableDesc = new HTableDescriptor(table);
		HColumnDescriptor colDesc = new HColumnDescriptor(CF_INFO_BYTES);
		tableDesc.addFamily(colDesc);
		admin.createTable(tableDesc);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Connection conn = ConnectionFactory.createConnection(conf);
		admin = conn.getAdmin();		
	}

}
