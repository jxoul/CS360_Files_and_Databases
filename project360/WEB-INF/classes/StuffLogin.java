import javax.servlet.*;
import java.io.*;
import java.sql.*;
import org.apache.commons.lang3.StringUtils;

public class StuffLogin extends GenericServlet{
    public void service(ServletRequest req, ServletResponse res) throws ServletException,IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        //HTML HEAD
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<title>Stuff Register Page</title>");
        out.println("<style type= \"text/css\">");
        out.println("body{ background-color: #666}");
        out.println("div{text-align: center; width: 500px; box-shadow:  1px 10px #a8a8a8; position: absolute; top: 50%; left: 50%; transform: translate(-50%,-50%); background-color: aqua; padding-bottom: 10px");
        out.println("h1{ color: brown; font-size: 50px; text-align: center; margin-top: 0; padding: 20px;}");
        out.println("h2{ color: black; font-size: 50px; text-align: center; margin-top: 0; padding: 20px;}");
        out.println("p{text-align: center; font-size: 20px; alignment: center;}");
        out.println("a{alignment: center; padding: 5px; border-radius: 3px; background-color: black; text-decoration: none; display: inline-block; color: aliceblue;}");
        out.println("a:hover{color: #fff; background-color: beige; transition: all 0.3s}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div>");

        //Variebles
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sid = req.getParameter("id");
        String pass = req.getParameter("pass");

        //An sid den einai noumero INVALID
        if (!StringUtils.isNumeric(sid)) {
            out.println("<h2 style='background-color: #eb1616'>Invalid ID or password!</h2>");
        } else {

            try {
                //Get Connection
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tepdatabase?characterEncoding=UTF-8", "root", "");
                stmt = conn.createStatement();

                // An einai Admin Stuff
                if (Integer.parseInt(sid) <= 999) {
                    // An uparxei o xristis
                    String query = "select * from Ads where sid ='" + sid + "' and password ='" + pass + "'";
                    rs = stmt.executeQuery(query);

                    if (rs.next()) {
                        out.println("<h1>Welcome</h1>");
                        out.println("<p>ID - " + rs.getString("sid") + "</p>");
                        out.println("<p>First Name - " + rs.getString("fname") + "</p>");
                        out.println("<p>Last Name - " + rs.getString("lname") + "</p>");
                        out.println("<p>Position - " + rs.getString("position") + "</p>");
                        out.println("<p><a style='font-size: 30px' href = 'adfeatures.html'>Features</a></p>");
                    } else {
                        out.println("<h2 style='background-color: #eb1616'>Invalid ID or password!</h2>");
                    }
                    // An einai Giatros
                } else if (Integer.parseInt(sid) <= 1999) {
                    //An uparxei o xristis
                    String query = "select * from Doctors where sid ='" + sid + "' and password ='" + pass + "'";
                    rs = stmt.executeQuery(query);

                    if (rs.next()) {
                        out.println("<h1>Welcome</h1>");
                        out.println("<p>ID - " + rs.getString("sid") + "</p>");
                        out.println("<p>First Name - " + rs.getString("fname") + "</p>");
                        out.println("<p>Last Name - " + rs.getString("lname") + "</p>");
                        out.println("<p>Specialty - " + rs.getString("specialty") + "</p>");
                        out.println("<p>Phone Number - " + rs.getString("phone") + "</p><br>");
                        out.println("<p><a style='font-size: 30px' href = 'doctorfeatures.html'>Features</a></p>");
                    } else {
                        out.println("<h2 style='background-color: #eb1616'>Invalid ID or password!</h2>");
                    }
                    //An einai Nosileutis
                } else if (Integer.parseInt(sid) >= 1999) {

                    //An yparxei o xristis
                    String query = "select * from Nurses where sid ='" + sid + "' and password ='" + pass + "'";
                    rs = stmt.executeQuery(query);

                    if (rs.next()) {
                        out.println("<h1>Welcome</h1>");
                        out.println("<p>ID - " + rs.getString("sid") + "</p>");
                        out.println("<p>First Name - " + rs.getString("fname") + "</p>");
                        out.println("<p>Last Name - " + rs.getString("lname") + "</p>");
                        out.println("<p><a style='font-size: 30px' href = 'nursefeatures.html'>Features</a></p>");
                    } else {
                        out.println("<h2 style='background-color: #eb1616'>Invalid ID or password!</h2>");
                    }
                } else { // an id < 0 INVALID
                    out.println("<h2 style='background-color: #eb1616'>Invalid ID or password!</h2>");
                }
            } catch (ClassNotFoundException e) {
                out.println(e);
            } catch (SQLException e) {
                out.println(e);
            } finally {
                try {
                    out.println("<p><a  href = 'stufflogin.html'>Back</a>");
                    out.println("<a  href = 'index.html'>Return to Home Page</a></p>");
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
        out.println("<p><a  href = 'stufflogin.html'>Back</a>");
        out.println("<a  href = 'index.html'>Return to Home Page</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}