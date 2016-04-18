package fabflix;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class dbFunctions
{
	private Connection connection;

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

	public DatabaseMetaData get_metadata() throws Exception
	{
		return connection.getMetaData();
	}

	public ResultSet select(String stmt) throws Exception
	{
		Statement select = connection.createStatement();
		ResultSet results = select.executeQuery(stmt);
		return results;
	}

	public ResultSet raw_select(String stmt) throws Exception
	{
		Statement select = connection.createStatement();
		ResultSet results = select.executeQuery(stmt);
		//select.close();
		return results;
	}

	public int raw_update(String stmt) throws Exception
	{
		Statement update = connection.createStatement();
		int results = update.executeUpdate(stmt);
		update.close();
		return results;
	}

	public int update(String stmt, String[] args) throws Exception
	{
		PreparedStatement update = connection.prepareStatement(stmt);
		for(int i = 1; i <= args.length; ++i)
		{
			update.setObject(i, args[i-1]);
		}
		int results = 0;

		try{
			results  = update.executeUpdate();
		}
		catch(Exception ex){ 
		
			//Skip handling exception.  Result will return that no rows were inserted
			//System.out.println("Exception: ");
			//System.out.println(ex.getMessage());
		}
		
		update.close();
		return results;

	}
	
	public ArrayList< Map< String, Object > > selectUsing(String table, String columns, String values) throws SQLException
	{

		String statementString = "SELECT * FROM " + table ;
		List<String> listOfColums =  Arrays.asList(columns.split(","));
		List<String> listOfvalues = Arrays.asList(values.split(","));
		if(listOfColums.size() > 0)
		{
			statementString += " WHERE " + listOfColums.get(0) + "=" + listOfvalues.get(0);	
			for(int i = 0; i < listOfColums.size(); i++)
			{

				statementString +=  " AND " + listOfColums.get(i) + "=" + listOfvalues.get(i);
			}
		}
		statementString += ";";
		PreparedStatement statement = connection.prepareStatement(statementString);
		ResultSet results = statement.executeQuery();
		ArrayList< Map< String, Object > > output = new ArrayList< Map< String, Object > >();
		ResultSetMetaData rsmd = results.getMetaData();
		int numCol = rsmd.getColumnCount();
		ArrayList<String> coulumNames = new  ArrayList<String>();
		for(int i =1; i <= numCol; i++)
		{
			coulumNames.add(rsmd.getColumnLabel(i));
		}
		while(results.next())
		{
			 
			Map< String, Object >temp =new TreeMap< String, Object >();
			for(String currCol : coulumNames)
			{
				temp.put(currCol, results.getObject(currCol));
			}
			output.add(temp);
			
		}
		results.close();
		statement.close();
		return output;
	}
	public void close()
	{
		try {
			if(connection != null)
			
				connection.close();
		} catch (SQLException e) 
		{
			System.out.println("Warning:The database connection was not closed properly.");
		}
	}
	public int countMovieByTilte(String letterOfTitle) throws SQLException
	{
		String statementString = "SELECT COUNT(*) FROM movies";
		if(!"".equals(letterOfTitle))
		{
			statementString += " WHERE  title LIKE \'" + letterOfTitle + "%\' ";
		}
		statementString += ";";
		PreparedStatement statement = connection.prepareStatement(statementString);
		ResultSet results = statement.executeQuery();
		int output = 0;
		if(results.next())
		{
			output = results.getInt(1);
		}
		results.close();
		statement.close();
		return output;
	}
	
	public ArrayList<Movie> getmovieByTilte(int start, int limit, String letterOfTitle) throws SQLException
	{
		String statementString = "SELECT * FROM movies";
		if(!"".equals(letterOfTitle))
		{
			statementString += " WHERE  title LIKE \'" + letterOfTitle + "%\' ";
		}
		statementString += " ORDER BY title " ;
		if(limit > 0)
		{
			statementString += " LIMIT " + limit + " OFFSET " + (start> 0 ? start : 0);
		}
		
		statementString += ";";
		PreparedStatement statement = connection.prepareStatement(statementString);
		ResultSet results = statement.executeQuery();
		ArrayList< Movie > output = new ArrayList< Movie >();
		while(results.next())
		{
			Movie newMovie = new Movie();
			newMovie.setTitle(results.getString("title"));
			newMovie.setId(results.getInt("id"));
			newMovie.setDirector(results.getString("director"));
			newMovie.setYear(results.getInt("year"));
			newMovie.setBanner_url(results.getString("banner_url"));
			newMovie.setTrailer_url(results.getString("trailer"));
			output.add(newMovie);
			
		}
		results.close();
		statement.close();
		return output;
	}
	
	public User loginToFabFlix(String userName, String password) throws SQLException
	{
		User logged_in_user = null;
		if(userName == null || password == null)
			return null;
		String statementString = "SELECT * FROM customers WHERE email=\'" + userName + "\' AND password=\'" + password + "\';";
		PreparedStatement statement = connection.prepareStatement(statementString);
		ResultSet results = statement.executeQuery();
		if(results.next())
		{
			logged_in_user = new User(
					results.getInt("id"),
					results.getString("first_name"),
					results.getString("last_name"),
					results.getString("address"),
					results.getString("email"));
					
		}
		results.close();
		statement.close();
		return logged_in_user;
	}
}
