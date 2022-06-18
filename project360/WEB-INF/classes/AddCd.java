import javax.servlet.*;
import java.io.*;
import java.sql.*;
import org.apache.commons.lang3.StringUtils;

public class AddCd extends GenericServlet{
    public void service(ServletRequest req, ServletResponse res) throws ServletException,IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        //HTML HEAD
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<title>AddCd Page</title>");
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
        ResultSet valid = null;

        String amka = req.getParameter("amka");
        String cd = req.getParameter("cd");
        String pass = req.getParameter("pass");

        //upoxreotika peida
        if( cd.equals("") || amka.equals("") || pass.equals("")){
            out.println("<h1 style='background-color: #eb1616'>All fields with asterisk must be filled!</h1>");
        } else{
            try {
                //Connection
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tepdatabase?characterEncoding=UTF-8", "root", "");
                stmt = conn.createStatement();

                // an einai lathos kodikos, lathos
                String query = "select * from Patients where ((amka='" + amka + "') and (password='" + pass + "'))";
                valid = stmt.executeQuery(query);

                if (!valid.next()) {
                    out.println("<h1 style='background-color: #eb1616'>Invalid AMKA or password</h1>");
                } else {
                    // update check
                    String update = "insert into PatientsCdisease values ('" + amka + "','" + cd + "')";
                    stmt.executeUpdate(update);
                    out.println("<h1>Chronic Disease Added</h1>");
                }
            }catch (Exception e) {
                out.println(e);
            } finally {
                try {
                    out.println("<p><a href = 'addcd.html'>Back</a>");
                    out.println("<p><a href = 'patientfeatures.html'>Return to Patient Features</a></p>");
                    out.println("<a href = 'index.html'>Return to Home Page</a></p>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");
                    out.close();
                    valid.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    out.println(e);
                }
            }
        }
        out.println("<p><a href = 'addcd.html'>Back</a>");
        out.println("<p><a href = 'patientfeatures.html'>Return to Patient Features</a></p>");
        out.println("<a href = 'index.html'>Return to Home Page</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}