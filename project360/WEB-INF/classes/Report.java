import javax.servlet.*;
import java.io.*;
import java.sql.*;
import org.apache.commons.lang3.StringUtils;

public class Report extends GenericServlet {
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        //HTML HEAD
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<title>Enter Report Page</title>");
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

        //Variables
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet valid = null;

        String nsid = req.getParameter("nsid");
        String edate = req.getParameter("edate");
        String amka = req.getParameter("amka");
        String cid = req.getParameter("cid");
        String report = req.getParameter("report");
        String pass = req.getParameter("pass");

        //Elegxoi

        //upoxrewtika pedia
        if ( pass.equals("") || nsid.equals("") || amka.equals("") || edate.equals("") || cid.equals("") || report.equals("") ) {
            out.println("<h1 style='background-color: #eb1616'>All fields with asterisk must be filled!</h1>");
        } //To id tou nosileyti prepei na einai arithmos
        else if (!StringUtils.isNumeric(nsid)) {
            out.println("<h1 style='background-color: #eb1616'>Invalid Nurse ID!</h1>");
        } //To id tou check prepei na einai arithmos
        else if (!StringUtils.isNumeric(cid)) {
            out.println("<h1 style='background-color: #eb1616'>Invalid check ID!</h1>");
        } //To amka tou astheni prepei na einai arithmos
        else if (!StringUtils.isNumeric(amka)) {
            out.println("<h1 style='background-color: #eb1616'>Invalid patient's AMKA!</h1>");
        }  //To nsid prepei na deixnei se nosileuti
        else if (Integer.parseInt(nsid) < 2000) {
            out.println("<h1 style='background-color: #eb1616'>Invalid Nurse ID!</h1>");
        } else {
            try{
                //Connection
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tepdatabase?characterEncoding=UTF-8", "root", "");
                stmt = conn.createStatement();

                // an einai sostos kodikos
                String query = "select * from Nurses where ((sid='"+nsid+"') and (password='"+pass+"'))";
                valid = stmt.executeQuery(query);

                if(!valid.next()){
                    out.println("<h1 style='background-color: #eb1616'>Invalid ID or password</h1>");
                }else {

                    // an den uparxei to check, lathos
                    query = "select * from checks where cid ='" + cid + "'";
                    rs = stmt.executeQuery(query);

                    if (!rs.next()) {
                        out.println("<h1 style='background-color: #eb1616'>This check doesn't exist!</h1>");
                    } else {
                        //an den einai upeuthini gia auto to check
                        if (!rs.getString("nsid").equals(nsid)) {
                            out.println("<h1 style='background-color: #eb1616'>You are not responsible for this check!</h1>");
                        }  // an den einai gia auton ton astheni i exetasi LATHOS
                        else if (!rs.getString("amka").equals(amka)) {
                            out.println("<h1 style='background-color: #eb1616'>Wrong AMKA!</h1>");
                        } else {
                            String did = rs.getString("did");

                            // update check
                            String update = "update Checks set report='" + report + "' where cid= '" + cid + "'";
                            stmt.executeUpdate(update);

                            //updare record
                            update = "insert into Record values ('" + edate + "','" + cid + "','" + did + "','" + amka + "')";
                            stmt.executeUpdate(update);
                            out.println("<h1>Report Updated</h1>");
                        }
                    }
                }

            }catch (ClassNotFoundException e) {
                out.println(e);
            } catch (SQLException e) {
                out.println(e);
            } finally {
                try {
                    out.println("<p><a href = 'visit.html'>Back</a>");
                    out.println("<p><a href = 'nursefeatures.html'>Return to Nurse Features</a></p>");
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
        out.println("<p><a href = 'visit.html'>Back</a>");
        out.println("<p><a href = 'nursefeatures.html'>Return to Nurse Features</a></p>");
        out.println("<a href = 'index.html'>Return to Home Page</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}