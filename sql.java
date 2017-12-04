import java.sql.*;

/*
 * Emily Felde, Ethan Peterson, Taylor Koth
* Deliverable 5
 */
public class sql {

    public static void main (String[] args) {
        try {
            String url = "jdbc:msql://localhost:3306/Demo";
            Connection conn = DriverManager.getConnection(url,"","");
            Statement stmt = conn.createStatement();
            ResultSet q1;
            ResultSet q2;
            ResultSet q3;
            ResultSet q4;
            ResultSet q5;

            q1 = stmt.executeQuery("SELECT gross, budget FROM TITLE");
            while ( q1.next() ) {
                String gross = q1.getString("gross");
                String budget = q1.getString("budget");
            }
            q2 = stmt.executeQuery("SELECT T.tconst, T.gross, T.budget, G.genre FROM TITLE T, GENRE G WHERE T.tconst = G.tconst");
            while ( q2.next() ) {
                String tconst = q2.getString("T.tconst");
                String gross = q2.getString("T.gross");
                String budget = q2.getString("T.budget");
                String genre = q2.getString("G.genre");
            }
            q3 = stmt.executeQuery("SELECT T.tconst, T.gross, T.budget, G.genre FROM TITLE T, GENRE G WHERE T.title LIKE “Warcraft” AND T.startYear = 2016 AND T.tconst = G.tconst");
            while ( q3.next() ) {
                String tconst = q3.getString("T.tconst");
                String gross = q3.getString("T.gross");
                String budget = q3.getString("T.budget");
                String genre = q3.getString("G.genre");
            }
            q4 = stmt.executeQuery("SELECT T.gross, T.language, G.genre FROM TITLE T, GENRE G WHERE T.tconst = G.tconst AND G.genre = genre");
            while ( q4.next() ) {
                String gross = q4.getString("T.gross");
                String language = q4.getString("T.language");
                String genre = q4.getString("G.genre");
            }
            q5 = stmt.executeQuery("SELECT AR.averageRating, T.gross, COUNT(T.primaryTitle) FROM TITLE T, AVERAGE_RATING AR, IS_RATED IR WHERE T.primaryTitle LIKE title AND T.tconst = IR.tconst AND IR.rconst = AR.rconst");
            while ( q5.next() ) {
                String aveRating = q5.getString("AR.averageRating");
                String gross = q5.getString("T.gross");
                String genre = q5.getString("COUNT(T.primaryTitle)");
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
