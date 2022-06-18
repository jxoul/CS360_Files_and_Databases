import javax.servlet.*;
import java.io.*;
import java.sql.*;
import org.apache.commons.lang3.StringUtils;

public class CovidReport extends GenericServlet {
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        //HTML HEAD
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<title>Covid Report Page</title>");
        out.println("<style type= \"text/css\">");
        out.println("body{ background-color: #666}");
        out.println("div{text-align: center; width: 500px; box-shadow:  1px 10px #a8a8a8; position: absolute; left: 50%; transform: translate(-50%); background-color: aqua; padding-bottom: 10px");
        out.println("h1{ color: brown; font-size: 50px; text-align: center; margin-top: 0; padding: 20px;}");
        out.println("p{text-align: center; font-size: 20px; alignment: center;}");
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

        ResultSet valid = null;

        String sid = req.getParameter("sid");
        String pass = req.getParameter("pass");

        if (sid.equals("") || pass.equals("")) {
            out.println("<h1 style='background-color: #eb1616'>All fields with asterisk must be filled!</h1>");
        } else {
            try {
                //Connection
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tepdatabase?characterEncoding=UTF-8", "root", "");
                stmt = conn.createStatement();

                // an einai sostos kodikos
                String query = "select * from Ads where ((sid='" + sid + "') and (password='" + pass + "'))";
                valid = stmt.executeQuery(query);

                if (!valid.next()) {
                    out.println("<h1 style='background-color: #eb1616'>Invalid ID or password</h1>");
                } else {
                    query = "select * from covid";
                    rs = stmt.executeQuery(query);

                    out.println("<h1> COVID REPORT </h1>");
                    int i=1;
                    while(rs.next()){
                        out.println("<p>"+i+") " + rs.getString("stoixeia"));

                        XroniaNosimata(conn,out,rs.getString("amka"));
                        i++;
                        out.println("<br> ");
                    }
                }
            }catch (ClassNotFoundException e) {
                out.println(e);
            } catch (SQLException e) {
                out.println(e);
            } finally {
                try {
                    out.println("<p><a href = 'covidreport.html'>Back</a>");
                    out.println("<p><a href = 'adfeatures.html'>Return to Administrative Stuff Features</a></p>");
                    out.println("<a href = 'index.html'>Return to Home Page</a></p>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");
                    rs.close();
                    valid.close();
                    stmt.close();
                    conn.close();
                    out.close();
                } catch (SQLException e) {
                    out.println(e);
                }
            }

        }
        out.println("<p><a href = 'covidreport.html'>Back</a>");
        out.println("<p><a href = 'adfeatures.html'>Return to Administrative Stuff Features</a></p>");
        out.println("<a href = 'index.html'>Return to Home Page</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }


    public void XroniaNosimata(Connection conn, PrintWriter out, String amka) throws ServletException, IOException{
        Statement stmt2 = null;
        ResultSet rs2 = null;
        try {
            String query2 = "select cdisease from PatientsCdisease where amka = '" + amka + "'";
            stmt2 = conn.createStatement();
            rs2 = stmt2.executeQuery(query2);
            if(!rs2.next()){

            }else{
                out.println(" Chronic Disease: ");
                while (!rs2.getString(1).isEmpty()){
                    out.println(rs2.getString(1)+ "    ");
                    rs2.next();
                }
            }

        }catch (Exception e) {
            out.println("<p>-----------------------------------</p>");
        } finally {
            try {
                stmt2.close();
                rs2.close();
            }catch (SQLException e) {
                out.println(e);
            }
        }
    }
}