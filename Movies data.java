import java.sql.*;

public class Movies {
    private Connection connect() 
    {
        Connection c=null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            c=DriverManager.getConnection("jdbc:sqlite:movies.db");
            c.setAutoCommit(false);
        }
        catch(SQLException | ClassNotFoundException e)
        {
            System.err.println(e.getClass().getName()+":"+e.getMessage());
            System.exit(0);
        }
        return c;
    }

    public void createTable() 
    {
        String cr="CREATE TABLE MOVIES (MOVID INT PRIMARY KEY NOT NULL,"+"NAME TEXT NOT NULL,"+"ACTOR TEXT NOT NULL,"+"ACTRESS TEXT NOT NULL,"+"YEAR CHAR(4),"+"DIRNAME TEXT)";
        try
        {
            Connection c = this.connect();
            Statement stat =c.createStatement();
            stat.execute(cr);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void insert(int movid,String name,String actor,String actress,String year,String dir_name) 
    {
        String insr ="INSERT INTO MOVIES(MOVID,NAME,ACTOR,ACTRESS,YEAR,DIRNAME) VALUES(?,?,?,?,?,?)";
        try
        {
            Connection c=this.connect();

            PreparedStatement pstat=c.prepareStatement(insr);
            pstat.setInt(1,movid);
            pstat.setString(2,name);
            pstat.setString(3,actor);
            pstat.setString(4,actress);
            pstat.setString(5,year);
            pstat.setString(6,dir_name);
            pstat.executeUpdate();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void movselect() 
    {

        try
        {
            Connection c =this.connect();
            Statement stat = c.createStatement();
            ResultSet res= stat.executeQuery("SELECT * FROM MOVIES;");
            while(res.next())
            {
                int id=res.getInt("MOVID");
                String movieName=res.getString("NAME");
                String actor=res.getString("ACTOR");
                String actress=res.getString("ACTRESS");
                String year=res.getString("YEAR");
                String dir_name=res.getString("DIRNAME");

                System.out.println("MOVIE ID = "+id);
                System.out.println("MOVIE NAME = "+movieName);
                System.out.println("ACTOR NAME = "+actor);
                System.out.println("ACTRESS NAME = "+actress);
                System.out.println("YEAR = "+year);
                System.out.println("Director Name = "+dir_name);
                System.out.println();
            }
            res.close();
            stat.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void selectR() 
    {

        try
        {
            Connection c =this.connect();
            Statement stat = c.createStatement();
            ResultSet res= stat.executeQuery("SELECT NAME FROM MOVIES WHERE ACTOR='DARSHAN';");
            System.out.println("The movies in which DARSHAN acted are: ");
            while(res.next())
            {
                String movieName=res.getString("NAME");
                System.out.println(movieName);
            }
            res.close();
            stat.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }


    public static void main(String[] args) {
        Movies a =new Movies(); //creating an object of the class Movies
        a.createTable();
        a.insert(001,"GARUDA","SHETTY","-","2020","kundan");
        a.insert(002,"RAJA","Pavan","ANAND","2017","Ram");
        a.insert(003,"Rajahuli","YASH","Meghana","2018","Harsh");
        a.insert(004,"KGF CHAPTER 2","YASH","SHRINIDHI SHETTY","2022","PRASHANTH NEEL");
        a.insert(005,"JAMES","PUNITH RAJKUMAR","PRIYA ANAND","2022","CHETAN KUMAR");
        a.insert(006,"Yajamana","DARSHAN","RASHMIKA","2019","SHASHANK");
        a.insert(007,"OM","UPENDRA","PREMA","1996","Vasu");
        a.movselect();
        a.selectR();
    }
}
