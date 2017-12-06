import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;
/*
 * Emily Felde, Ethan Peterson, Taylor Koth
 * Deliverable 5
 */
public class sql {

    public static void main (String[] args) {
        try {
            //create connection to our database
            String url = "jdbc:msql://localhost:3306/Demo";
            Connection conn = DriverManager.getConnection(url,"","");
            //execute statement in database
            Statement stmt = conn.createStatement();
            //create result sets for each query
            ResultSet q1;
            ResultSet q2;
            ResultSet q3;
            ResultSet q4;
            ResultSet q5;
            //string builders for writing to csv files
            StringBuilder saveStr1 = new StringBuilder();
            StringBuilder saveStr2 = new StringBuilder();
            StringBuilder saveStr3 = new StringBuilder();
            StringBuilder saveStr4 = new StringBuilder();
            StringBuilder saveStr5 = new StringBuilder();

            //query one
            //to compare average ratings against the adult rating of titles
            q1 = stmt.executeQuery("SELECT T.isAdult, R.averageRating FROM TITLE T, RATING R, IS_RATED IR WHERE T.tconst = IR.tconst AND IR.rconst = R.rconst");
            while ( q1.next() ) {
                //add boolean isAdult
                String adult = q1.getString("T.isAdult");
                saveStr1.append(adult);
                saveStr1.append(",");
                //add average rating
                String averating = q1.getString("R.averageRating");
                saveStr1.append(averating);
                saveStr1.append("\n");
            }
            //query two
            //to compare start year, runtime, primary genre, and average rating
            q2 = stmt.executeQuery("SELECT G.genre, T.startYear, T.runtimeMinutes, R.averageRating FROM TITLE T, RATING R, IS_RATED IR, PRIMARY_GENRE G WHERE T.tconst = IR.tconst AND IR.rconst = R.rconst AND T.tconst = G.tconst AND T.runtimeMinutes IS NOT NULL");
            while ( q2.next() ) {
                //add genre
                String genre = q2.getString("G.genre");
                saveStr2.append(genre);
                saveStr2.append(",");
                //add start year
                String year = q2.getString("T.startYear");
                saveStr2.append(year);
                saveStr2.append(",");
                //add runtime in minutes
                String runtime = q2.getString("T.runtimeMinutes");
                saveStr2.append(runtime);
                saveStr2.append(",");
                //add average rating
                String averating = q2.getString("R.averageRating");
                saveStr2.append(averating);
                saveStr2.append("\n");
            }
            //query three
            //to compare average rating and for Warcraft to make predictions given the average ratings for titles in the same genre as Warcraft
            q3 = stmt.executeQuery("SELECT R.averageRating, T.startYear, T.runtimeMinutes, G.genre FROM TITLE T, RATING R, IS_RATED IR, PRIMARY_GENRE G WHERE T.tconst = IR.tconst AND IR.rconst = R.rconst AND T.tconst = G.tconst AND T.runtimeMinutes IS NOT NULL AND T.startYear is NOT NULL");
            while ( q3.next() ) {
                //add average rating
                String rating = q3.getString("R.averageRating");
                saveStr3.append(rating);
                saveStr3.append(",");
                //add start year
                String year = q3.getString("T.startYear");
                saveStr3.append(year);
                saveStr3.append(",");
                //add runtime
                String time = q3.getString("T.runtimeMinutes");
                saveStr3.append(time);
                saveStr3.append(",");
                //add genre
                String genre = q3.getString("G.genre");
                saveStr3.append(genre);
                saveStr2.append("\n");
            }
            //query four
            //to compare runtime across different genres
            q4 = stmt.executeQuery("SELECT T.runtimeMinutes, G.genre FROM TITLE T, PRIMARY_GENRE G WHERE T.tconst = G.tconst");
            while ( q4.next() ) {
                //add runtime in minutes
                String runtime = q4.getString("T.runtimeMinutes");
                saveStr4.append(runtime);
                saveStr4.append(",");
                //add genre
                String genre = q4.getString("G.genre");
                saveStr4.append(genre);
                saveStr4.append("\n");
            }
            //query five
            //to compare average rating and number of titles for the star wars and star trek franchises
            q5 = stmt.executeQuery("SELECT R.averageRating, (T.primaryTitle LIKE '%_tar _ars%')+1 FROM TITLE T, RATING R, IS_RATED IR WHERE T.primaryTitle LIKE '%_tar _ars%' OR T.primaryTitle LIKE '%_tar _rek%' AND T.tconst = IR.tconst AND IR.rconst = R.rconst");
            while ( q5.next() ) {
                //add average rating
                String aveRating = q5.getString("R.averageRating");
                saveStr5.append(aveRating);
                saveStr5.append(",");
                //add primary title
                String count = q5.getString("T.primaryTitle");
                saveStr5.append(count);
                saveStr5.append("\n");
            }
            //close database connection
            conn.close();
            //create file writers
            FileWriter filewrite1 = null;
            FileWriter filewrite2 = null;
            FileWriter filewrite3 = null;
            FileWriter filewrite4 = null;
            FileWriter filewrite5 = null;
            //try writing to .csv files
            try{
              filewrite1 = new FileWriter("q1.csv");
              filewrite1.append(saveStr1.toString());
              System.out.println("Generated q1.csv");
              filewrite2 = new FileWriter("q2.csv");
              filewrite2.append(saveStr2.toString());
              System.out.println("Generated q2.csv");
              filewrite3 = new FileWriter("q3.csv");
              filewrite3.append(saveStr3.toString());
              System.out.println("Generated q3.csv");
              filewrite4 = new FileWriter("q4.csv");
              filewrite4.append(saveStr4.toString());
              System.out.println("Generated q4.csv");
              filewrite5 = new FileWriter("q5.csv");
              filewrite5.append(saveStr5.toString());
              System.out.println("Generated q5.csv");
            }
            catch(Exception e){
              System.out.println(e);
            }
            finally{
              //try closing files
              try{
                filewrite1.flush();
                filewrite1.close();
                filewrite2.flush();
                filewrite2.close();
                filewrite3.flush();
                filewrite3.close();
                filewrite4.flush();
                filewrite4.close();
                filewrite5.flush();
                filewrite5.close();
              }
              catch(IOException e){
                System.out.println(e);
              }
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
