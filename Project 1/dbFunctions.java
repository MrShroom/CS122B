 
import java.sql.*;

public class dbFunctions
{
	private Connection connection;
	public void main(String[] args) throws Exception
	{

	}

	/**
		Establishes connection to database
		@param path - path to db
		@param user_name - mysql username
		@param pass - mysql password
	*/
	public void make_connection(String path, String user_name, String pass) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(path, user_name, pass);
	}

	public String append_string_array(String str, String[] to_append)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < to_append.length; ++i)
		{
   			sb.append(to_append[i]);
    		if (i != to_append.length - 1)
       			sb.append(",");
       	}
       	return str.concat(sb.toString());
    
	}
	public ResultSet select(String stmt) throws Exception
	{
		Statement select = connection.createStatement();
		ResultSet results = select.executeQuery(stmt);
		return results;
	}

	public ResultSet update(String stmt) throws Exception
	{
		Statement update = connection.createStatement();
		ResultSet results = update.executeQuery(stmt);
		return results;

	}

}