import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class PatientLogin extends GenericServlet{
    public void service(ServletRequest req, ServletResponse res) throws ServletException,IOException{
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        //HTML HEAD
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<title>Patient Login Page</title>");
        out.println("<style type= \"text/css\">");
        out.println("body{ background-color: #666}");
        out.println("div{text-align: center; width: 500px; box-shadow:  1px 10px #a8a8a8; position: absolute; top: 50%; left: 50%; transform: translate(-50%,-50%); background-color: aqua; padding-bottom: 10px}");
        out.println("h1{ color: brown; font-size: 50px; text-align: center; margin-top: 0; padding: 20px;}");
        out.println("h2{ color: black; font-size: 50px; text-align: center; margin-top: 0; padding: 20px;}");
        out.println("p{text-align: center; font-size: 30px; alignment: center;}");
        out.println("a{alignment: center; padding: 5px; border-radius: 3px; background-color: black; text-decoration: none; display: inline-block; color: aliceblue;}");
        out.println("a:hover{color: #fff; background-color: beige; transition: all 0.3s}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div>");

        //Variables
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        String amka = req.getParameter("amka");
        String pass = req.getParameter("pass");

        //Elegxoi
        if (!StringUtils.isNumeric(amka)){      //amka prepei na einai arithmos
            out.println("<h2 style='background-color: #eb1616'>Invalid AMKA or password!</h2>");

        }else {
            try {
                //Connection
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tepdatabase?characterEncoding=UTF-8", "root", "");
                stmt = conn.createStatement();

                String query = "select * from Patients where amka ='" + amka + "' and password ='" + pass + "'";
                rs = stmt.executeQuery(query);
                if (rs.next()) { //An uparxei o xristis
                    out.println("<h1>Welcome</h1>");
                    out.println("<p>AMKA - " + rs.getString("amka") + "</p>");
                    out.println("<p>First Name - " + rs.getString("fname") + "</p>");
                    out.println("<p>Last Name - " + rs.getString("lname") + "</p>");
                    out.println("<p>Birthday - " + rs.getString("bday") + "</p>");
                    out.println("<p>Address - " + rs.getString("address") + "</p>");
                    out.println("<p>Insurer - " + rs.getString("insurer") + "</p>");

                    //Display ta chronic disease
                    out.println("<p>Chronic Diseases: ");
                    query = "select cdisease from PatientsCdisease where amka ='" + amka + "'";
                    rs2 = stmt.executeQuery(query);
                    while (rs2.next()) {
                        out.println(rs2.getString("cdisease") + "  ");
                    }
                    out.println("</p>");

                    //Display to record
                    out.println("<p>Record: </p>");
                    query = "select * from patientRecord where amka ='" + amka + "'";
                    rs3 = stmt.executeQuery(query);
                    int i = 1;

                    while (rs3.next()) {
                            out.println("<p>" + i + ")" + rs3.getString("intro") + "</p>");
                            if(!rs3.getString("therapeia").equals(null)) {
                                out.println("<p>" + rs3.getString("therapeia") + "</p>");
                                out.println("<p>-----------------------------------------</p>");
                            }
                            i++;
                    }
                    out.println("<p><a style='font-size: 30px' href = 'patientfeatures.html'>Features</a></p>");

                } else {
                    out.println("<h2 style='background-color: #eb1616'>Invalid AMKA or password!</h2>");
                }
            } catch (ClassNotFoundException e) {
                out.println(e);
            } catch (SQLException e) {
                out.println(e);
            } finally {
                try {
                    out.println("<p><a href = 'patientlogin.html'>Back</a></p>");
                    out.println("<p><a href = 'index.html'>Return to Home Page</a></p>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");
                    out.close();
                    rs.close();
                    rs2.close();
                    rs3.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    out.println(e);
                }
            }
        }
        out.println("<p><a href = 'patientlogin.html'>Back</a></p>");
        out.println("<p><a href = 'index.html'>Return to Home Page</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}