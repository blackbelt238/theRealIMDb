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
            ResultSet q2part2;
            ResultSet q3;
            ResultSet q4;
            ResultSet q5;
            //string used to pull info from a specific genere
            String genreString = "Romance";
            //string builders for writing to csv files
            StringBuilder saveStr1 = new StringBuilder();
            StringBuilder saveStr2 = new StringBuilder();
            StringBuilder saveStr3 = new StringBuilder();
            StringBuilder saveStr4 = new StringBuilder();
            StringBuilder saveStr5 = new StringBuilder();

            //query one
            //to compare average ratings against the adult rating of titles
            q1 = stmt.executeQuery("SELECT T.isAdult, AR.averageRating FROM TITLE T, AVERAGE_RATING AR, IS_RATED IR WHERE T.tconst = IR.tconst AND IR.rconst = AR.rconst");
            while ( q1.next() ) {
                String adult = q1.getString("T.isAdult");
                saveStr1.append(adult);
                saveStr1.append(",");
                String averating = q1.getString("AR.averageRating");
                saveStr1.append(averating);
                saveStr1.append("\n");
            }
            //query two
            //to compare average rating and adult rating against the number of women in titles
            q2 = stmt.executeQuery("SELECT T.tconst, T.isAdult, AR.averageRating, G.genre, T.startYear FROM TITLE T, AVERAGE_RATING AR, IS_RATED IR, GENRE G WHERE T.tconst = IR.tconst AND IR.rconst = AR.rconst AND T.tconst = G.tconst");
            while ( q2.next() ) {
                String q2tconst = q2.getString("T.tconst");
                saveStr2.append(q2tconst);
                saveStr2.append(",");
                String adult = q2.getString("T.isAdult");
                saveStr2.append(adult);
                saveStr2.append(",");
                String averating = q2.getString("AR.averageRating");
                saveStr2.append(averating);
                saveStr2.append(",");
                String genre = q2.getString("G.genre");
                saveStr2.append(genre);
                saveStr2.append(",");
                String year = q2.getString("T.startYear");
                saveStr2.append(year);
                saveStr2.append(",");
                //secondary query to find number of women in the title
                q2part2 = stmt.executeQuery("SELECT COUNT(PC.nconst) FROM ACTS_IN AI, PRINCIPAL_CAST PC, PRIMARY_PROFESSION PP WHERE (PP.profession LIKE “_ctress” OR PP.profession LIKE “ACTRESS”) AND AI.tconst = " + q2tconst + " AND PC.pconst = AI.pconst AND PP.nconst = PC.nconst");
                while ( q2part2.next() ) {
                    String numwomen = q2part2.getString("COUNT(PC.nconst)");
                    saveStr2.append(numwomen);
                }
                saveStr2.append("\n");
            }
            //query three
            //to compare average rating and for Warcraft to make predictions given the average ratings for titles in the same genre as Warcraft
            q3 = stmt.executeQuery("SELECT T.tconst, AR.averageRating, G.genre FROM TITLE T, AVERAGE_RATING AR, IS_RATED IR, GENRE G WHERE T.title LIKE “Warcraft” AND T.startYear = 2016 AND T.tconst = G.tconst AND T.tconst = IR.tconst AND IR.rconst = AR.rconst");
            while ( q3.next() ) {
                String tconst = q3.getString("T.tconst");
                saveStr3.append(tconst);
                saveStr3.append(",");
                String rating = q3.getString("AR.averageRating");
                saveStr3.append(rating);
                saveStr3.append(",");
                String genre = q3.getString("G.genre");
                saveStr3.append(genre);
                saveStr3.append("\n");
            }
            //query four
            //to compare average rating and language for a specific genre
            q4 = stmt.executeQuery("SELECT AR.averageRating, T.language, G.genre FROM TITLE T, AVERAGE_RATING AR, IS_RATED IR, GENRE G WHERE T.tconst = IR.tconst AND IR.rconst = AR.rconst AND T.tconst = G.tconst AND G.genre = " + genreString);
            while ( q4.next() ) {
                String rating = q4.getString("AR.averageRating");
                saveStr4.append(rating);
                saveStr4.append(",");
                String language = q4.getString("T.language");
                saveStr4.append(language);
                saveStr4.append(",");
                String genre = q4.getString("G.genre");
                saveStr4.append(genre);
                saveStr4.append("\n");
            }
            //query five
            //to compare average rating and number of titles for the star wars and star trek franchises
            q5 = stmt.executeQuery("SELECT AR.averageRating, (T.primaryTitle LIKE '%_tar _ars%')+1 FROM TITLE T, AVERAGE_RATING AR, IS_RATED IR WHERE T.primaryTitle LIKE '%_tar _ars%' OR T.primaryTitle LIKE '%_tar _rek%'AND T.tconst = IR.tconst AND IR.rconst = AR.rconst");
            while ( q5.next() ) {
                String aveRating = q5.getString("AR.averageRating");
                saveStr5.append(aveRating);
                saveStr5.append(",");
                String count = q5.getString("COUNT(T.primaryTitle)");
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
            //try writing to files
            try{
              filewrite1 = new FileWriter("q1.csv");
              filewrite1.append(saveStr1.toString());
              filewrite2 = new FileWriter("q2.csv");
              filewrite2.append(saveStr2.toString());
              filewrite3 = new FileWriter("q3.csv");
              filewrite3.append(saveStr3.toString());
              filewrite4 = new FileWriter("q4.csv");
              filewrite4.append(saveStr4.toString());
              filewrite5 = new FileWriter("q5.csv");
              filewrite5.append(saveStr5.toString());
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
