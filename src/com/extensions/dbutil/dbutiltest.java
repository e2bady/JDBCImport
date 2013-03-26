package com.extensions.dbutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.extensions.dbutil.dbcon.DB;
import com.extensions.dbutil.dbcon.DBConnectionData;

public class dbutiltest {
	private static final Logger log = (Logger) LoggerFactory
			.getLogger(dbutiltest.class);
	private final DBConnectionData condata = new DBConnectionData(
				"mytestuser",
				"start123",
				"mysql",
				"localhost",
				3306,
				"ipadressmanagerdb");
	private static final String FILENAME = "filename.txt";
	
	@Before
	public final void setUp() {
		DB.setConnection(condata);
	}
	
	@Test
	public final void sqlSplit() throws Exception {
		String[] strs = SQLImport.split(
				"INSERT INTO ipadressmanagerdb.changelog (username,Beschreibung,Action,ChangeID,Date,ActionObject) VALUES (\"xtfiki\",\"user.Add [UserImpl [getUserid()=0, getUsername()=xtfidm, getPassword()=48, isActive()=true, getActivationKey()=null, isPasswordReseted()=false]]\",\"user.Add\",4855,\"2012-10-18 16:16:17\",\"<ActionObject>"+
						"<redo>INSERT INTO ipadressmanagerdb.userverwaltung (username, password, Aktiviert, Aktivierungskey, PasswortReseted) VALUES (''xtfidm'',48,''true'',null,''false'');</redo>"+
						"<rollback>DELETE FROM ipadressmanagerdb.userverwaltung WHERE userid = 7;</rollback>"+
						"</ActionObject>\");"+
						"INSERT INTO ipadressmanagerdb.changelog (username,Beschreibung,Action,ChangeID,Date,ActionObject) VALUES (\"xtfiki\",\"user.Add [UserImpl [getUserid()=0, getUsername()=xtfidm, getPassword()=48, isActive()=true, getActivationKey()=null, isPasswordReseted()=false]]\",\"user.Add\",4855,\"2012-10-18 16:16:17\",\"<ActionObject>"+
						"<redo>INSERT INTO ipadressmanagerdb.userverwaltung (username, password, Aktiviert, Aktivierungskey, PasswortReseted) VALUES (''xtfidm'',48,''true'',null,''false'');</redo>"+
						"<rollback>DELETE FROM ipadressmanagerdb.userverwaltung WHERE userid = 7;</rollback>"+
						"</ActionObject>\");"+
						"INSERT INTO ipadressmanagerdb.changelog (username,Beschreibung,Action,ChangeID,Date,ActionObject) VALUES (\"xtfiki\",\"user.Add [UserImpl [getUserid()=0, getUsername()=xtfidm, getPassword()=48, isActive()=true, getActivationKey()=null, isPasswordReseted()=false]]\",\"user.Add\",4855,\"2012-10-18 16:16:17\",\"<ActionObject>"+
						"<redo>INSERT INTO ipadressmanagerdb.userverwaltung (username, password, Aktiviert, Aktivierungskey, PasswortReseted) VALUES (''xtfidm'',48,''true'',null,''false'');</redo>"+
						"<rollback>DELETE FROM ipadressmanagerdb.userverwaltung WHERE userid = 7;</rollback>"+
				"</ActionObject>\");");
		
		if(strs.length != 3) throw new Exception("sqlSpit not working.");
		for(String sa : strs) {
			log.error("SA: " + sa);
			for(String sb : strs)
			{
				log.error("SB: " + sb);
				if(!sb.equals(sa)) throw new Exception("sqlSpit not working.");
			}
		}
	}
	@Test
	public final void equals() throws Exception {
		DBExport dbe = new DBExport("ipadressmanagerdb");
		Map<String, List<Map<String, Object>>> before = dbe.readDB();
		if(!DBExport.dbequals(before, before)) throw new Exception("dbEquals not working");
	}

	@Test
	public final void export() throws IOException {
		PrintWriter out = null;
		try {
			out = new PrintWriter(FILENAME);
			out.println(new MySQLExport().mySQLExport("ipadressmanagerdb"));
		}
		finally {
			if(out != null)
				out.close();
		}
	}
	@Test
	public final void importSQL() throws Exception {
		DBExport dbe = new DBExport("ipadressmanagerdb");
		Map<String, List<Map<String, Object>>> before = dbe.readDB();
		if(!SQLImport.importSQL(readFile(FILENAME))) throw new Exception("DB Import failed.");
		export();
		dbe = new DBExport("ipadressmanagerdb");
		Map<String, List<Map<String, Object>>> after = dbe.readDB();
		if(!DBExport.dbequals(before, after)) throw new Exception("IMPORT FAILED.");
	}
	private static String readFile(String path) throws IOException {
		FileReader fr = null;
		BufferedReader reader = null;
		try {
			fr = new FileReader(new File(path));
			reader = new BufferedReader( fr );
			String         line = null;
			StringBuilder  stringBuilder = new StringBuilder();

			while( ( line = reader.readLine() ) != null ) {
				stringBuilder.append( line );
				stringBuilder.append( '\n' );
			}

			return stringBuilder.toString();
		} finally {
			if(reader != null)
				reader.close();
			if(fr != null)
				fr.close();
		}
	}
}
