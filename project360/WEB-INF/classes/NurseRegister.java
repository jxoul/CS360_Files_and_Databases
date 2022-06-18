import javax.servlet.*;
import java.io.*;
import java.sql.*;
import org.apache.commons.lang3.*;

public class NurseRegister extends GenericServlet{
    public void service(ServletRequest req, ServletResponse res) throws ServletException,IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        //HTML HEAD
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<title>Nurses Register Page</title>");
        out.println("<style type= \"text/css\">");
        out.println("body{ background-color: #666}");
        out.println("div{text-align: center; width: 500px; box-shadow:  1px 10px #a8a8a8; position: absolute; top: 50%; left: 50%; transform: translate(-50%,-50%); background-color: aqua; padding-bottom: 10px");
        out.println("h1{ color: brown; font-size: 50px; text-align: center; margin-top: 0; padding: 20px;}");
        out.println("p{text-align: center; font-size: 20px; alignment: center;}");
        out.println("a{alignment: center; padding: 5px; border-radius: 3px; background-color: black; text-decoration: none; display: inline-block; color: aliceblue;}");
        out.println("a:hover{color: #fff; background-color: beige; transition: all 0.3s}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div>");

        //VAriables
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;
        String sid = req.getParameter("sid");
        String pass = req.getParameter("pass");
        String fn = req.getParameter("fn");
        String ln = req.getParameter("ln");

        //Elegxoi
        if ((sid == "") || (pass == "") || (fn == "") || (ln == "")) {    //TA pedia auta prepei na exoun timi
            out.println("<h1 style='background-color: #eb1616'>All fields must be filled!</h1>");
        } else if (!StringUtils.isNumeric(sid)) { // To sid prepei na einai noumero
            out.println("<h1 style='background-color: #eb1616'>Invalid ID. Must be a number!</h1>");
        } else {
            if (Integer.parseInt(sid) < 1999) { // Prepei na einai Nosileutis
                out.println("<h1 style='background-color: #eb1616'>Invalid ID. Must be equal or bigger than 2000!</h1>");
            } else {
                try {
                    //Connect
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tepdatabase?characterEncoding=UTF-8", "root", "");
                    stmt = conn.createStatement();

                    String query = "select * from Nurses where sid ='" + sid + "'";
                    rs = stmt.executeQuery(query);
                    if(rs.next()){  // An sid uparxei idi, LATHOS
                        out.println("<h1 style='background-color: #eb1616'>A nurse with this sid already exists</h1>");
                    }else {

                        //Create new Nurse
                        String update = "insert into Nurses(sid,fname,lname,password)" +
                                "values('" + sid + "','" + fn + "','" + ln + "','" + pass + "')";
                        stmt.executeUpdate(update);

                        //Display new Nurse
                        out.println("<h1>Just Registered</h1>");
                        out.println("<p>ID - " + sid+ "</p>");
                        out.println("<p>First Name - " + fn + "</p>");
                        out.println("<p>Last Name - " + ln + "</p>");
                        out.println("<p>Password - " + pass + "</p>");
                        out.println("<p><a href = 'stufflogin.html'>Login</a></p>");
                    }
                } catch (ClassNotFoundException e) {
                    out.println(e);
                } catch (SQLException e) {
                    out.println(e);
                } finally {
                    try {
                        out.println("<p><a href = 'nurseregister.html'>Back to Register</a></p>");
                        out.println("<p><a href = 'index.html'>Return to Home Page</a></p>");
                        out.println("</div>");
                        out.println("</body>");
                        out.println("</html>");
                        out.close();
                        rs.close();
                        stmt.close();
                        conn.close();
                    } catch (SQLException e) {
                        out.println(e);
                    }
                }
            }
        }
        out.println("<p><a href = 'nurseregister.html'>Back to Register</a></p>");
        out.println("<p><a href = 'index.html'>Return to Home Page</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}