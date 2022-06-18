import javax.servlet.*;
import java.io.*;
import java.sql.*;
import org.apache.commons.lang3.StringUtils;

public class ReviewCheck extends GenericServlet {
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        //HTML HEAD
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<title>Review Check Page</title>");
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
        ResultSet rs2 = null;
        ResultSet valid = null;

        String sid = req.getParameter("sid");
        String cid = req.getParameter("cid");
        String box = req.getParameter("box");
        String pass = req.getParameter("pass");

        //ELEGXOI

        //Ypoxrewtika pedia
        if(pass.equals("") || cid.equals("") || sid.equals("") ){
            out.println("<h1 style='background-color: #eb1616'>All fields with asterisk must be filled!</h1>");
        } //To id tou check prepei na einai arithmos
        else if (!StringUtils.isNumeric(cid)) {
            out.println("<h1 style='background-color: #eb1616'>Invalid Check ID!</h1>");
        } //To id tou giatrou prepei na einai arithmos
        else if(!StringUtils.isNumeric(sid)){
            out.println("<h1 style='background-color: #eb1616'>Invalid Doctor ID!</h1>");
        }  //Prepei na deixnei se giatro
        else if((Integer.parseInt(sid) < 1000) || (Integer.parseInt(sid) > 1999)) {
            out.println("<h1 style='background-color: #eb1616'>Invalid Doctor ID!</h1>");
        } //prepei na exei epileksi yes or no apo to checkbox
        else if (box == null){
            out.println("<h1 style='background-color: #eb1616'>You must choose if the patient will stay in.</h1>");
        }else{
            try{
                //Connection
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tepdatabase?characterEncoding=UTF-8", "root", "");
                stmt = conn.createStatement();

                // an einai sostos kodiikos
                String query = "select * from Doctors where ((sid='"+sid+"') and (password='"+pass+"'))";
                valid = stmt.executeQuery(query);

                if(!valid.next()){
                    out.println("<h1 style='background-color: #eb1616'>Invalid ID or password</h1>");
                }else {

                    // An den uparxei i exetasi, LATHOS
                    query = "select * from Checks where cid='" + cid + "'";
                    rs = stmt.executeQuery(query);

                    if (!rs.next()) {
                        out.println("<h1 style='background-color: #eb1616'>This check doesn't exist.</h1>");
                    } else {
                        String giatros = rs.getString("dsid"); //o giatros tis eksetasis

                        // Prepei o giatros pou kanei review na einai autos pou orise tin exetasi
                        if (!sid.equals(giatros)) {
                            out.println("<h1 style='background-color: #eb1616'>You are not the responsible doctor for this exam.</h1>");
                        } else {
                            // update check
                            String update = "update Checks set stayIn=" + box + " where cid= '" + cid + "'";
                            stmt.executeUpdate(update);

                            // display updated checck
                            query = "select * from Checks where cid='" + cid + "'";
                            rs2 = stmt.executeQuery(query);
                            if (rs2.next()) {
                                out.println("<h1>Updated</h1>");
                                out.println("<p>ID - " + rs2.getString("cid") + "</p>");
                                out.println("<p>Name - " + rs2.getString("name") + "</p>");
                                out.println("<p>Report - " + rs2.getString("report") + "</p>");
                                out.println("<p>StayIn - " + rs2.getBoolean("stayIn") + "</p>");
                                out.println("<p>Nurse - " + rs2.getString("nsid") + "</p>");
                                out.println("<p>Doctor - " + rs2.getString("dsid") + "</p>");
                                out.println("<p>Patient - " + rs2.getString("amka") + "</p>");
                                out.println("<p>Date - " + rs2.getDate("edate") + "</p>");
                            }
                        }
                    }
                }
            }catch (Exception e) {
                out.println(e);
            } finally {
                try {
                    out.println("<p><a href = 'reviewcheck.html'>Back</a>");
                    out.println("<p><a href = 'doctorfeatures.html'>Return to Doctor Features</a></p>");
                    out.println("<a href = 'index.html'>Return to Home Page</a></p>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");
                    out.close();
                    rs.close();
                    rs2.close();
                    valid.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    out.println(e);
                }
            }

        }
        out.println("<p><a href = 'reviewcheck.html'>Back</a>");
        out.println("<p><a href = 'doctorfeatures.html'>Return to Doctor Features</a></p>");
        out.println("<a href = 'index.html'>Return to Home Page</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}