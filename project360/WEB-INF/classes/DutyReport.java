import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;

import org.apache.commons.lang3.StringUtils;

public class DutyReport extends GenericServlet {
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        //HTML HEAD
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<title>Duty Report Page</title>");
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
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet valid = null;

        String id = req.getParameter("id");
        String sdate = req.getParameter("sdate");
        String fdate = req.getParameter("fdate");
        String sid = req.getParameter("sid");
        String pass = req.getParameter("pass");



        if ( id.equals("") || sdate.equals("") || fdate.equals("") || sid.equals("") || pass.equals("")  ) {
            out.println("<h1 style='background-color: #eb1616'>All fields with asterisk must be filled!</h1>");
        }else {
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

                    //an i arxiki imerominia den uparxei, lathos
                    query = "select * from Tep where edate='" + sdate + "'";
                    rs = stmt.executeQuery(query);

                    if(!rs.next()){
                        out.println("<h1 style='background-color: #eb1616'>This starting date doesn't exist</h1>");
                    }else{

                        //an i teliki imerominia den uparxei, lathos
                        query = "select * from Tep where edate='" + fdate + "'";
                        rs2 = stmt.executeQuery(query);

                        if(!rs2.next()){
                            out.println("<h1 style='background-color: #eb1616'>This finishing date doesn't exist</h1>");
                        }else{

                            // an einai AdminStuff lathos
                            if (Integer.parseInt(id) <= 999) {
                                out.println("<h1 style='background-color: #eb1616'>Administrative stuff does have Duty Report</h1>");
                            }else if(Integer.parseInt(id) <= 1999 ){

                                //an einai giatros kai uparxei
                                query = "select * from Doctors where sid='"+id+"'";
                                rs3 = stmt.executeQuery(query);

                                if(!rs3.next()){
                                    out.println("<h1 style='background-color: #eb1616'>Invalid requested ID</h1>");
                                }else{

                                    // an den eixe efimeria se ekeino to range , LATHOS
                                    query = "select * from doctorsDutyRecord where ((sid='"+id+"') and ( edate between '"+sdate+"' and '"+fdate+"'))";
                                    rs4 = stmt.executeQuery(query);

                                    if(!rs4.next()){
                                        out.println("<h1 style='background-color: #eb1616'>This Doctor was not on duty in this date range</h1>");
                                    }else{
                                        out.println("<h1>Duty Report Results for DoctorID: "+id+" From: " +sdate+" To: "+fdate+" </h1>");
                                        try {
                                            while (!rs4.getString(1).isEmpty()) {
                                                out.println("<p>" + rs4.getString("details") + "</p>");
                                                rs4.next();
                                            }
                                        }catch (SQLException e){
                                            out.println("<p>----------------------------------</p>");
                                        }
                                    }
                                }
                            }else if(Integer.parseInt(id) > 1999){

                                //an einai nosileutis kai uparxei
                                query = "select * from Nurses where sid='"+id+"'";
                                rs3 = stmt.executeQuery(query);

                                if(!rs3.next()){
                                    out.println("<h1 style='background-color: #eb1616'>Invalid requested ID</h1>");
                                }else{

                                    // an den eixe efimeria se ekeino to range , LATHOS
                                    query = "select * from nursesDutyRecord where ((sid='"+id+"') and ( edate between '"+sdate+"' and '"+fdate+"'))";
                                    rs4 = stmt.executeQuery(query);

                                    if(!rs4.next()){
                                        out.println("<h1 style='background-color: #eb1616'>This Nurse was not on duty in this date range</h1>");
                                    }else{
                                        out.println("<h1>Duty Report Results for NurseID: "+id+" From: " +sdate+" To: "+fdate+" </h1>");
                                        try {
                                            while (!rs4.getString(1).isEmpty()) {
                                                out.println("<p>" + rs4.getString("details") + "</p>");
                                                rs4.next();
                                            }
                                        }catch (SQLException e){
                                            out.println("<p>----------------------------------</p>");
                                        }
                                    }
                                }
                            }else{
                                out.println("<h1 style='background-color: #eb1616'>Invalid requested ID</h1>");
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                out.println(e);
            } catch (SQLException e) {
                out.println(e);
            } finally {
                try {
                    out.println("<p><a href = 'dutyreport.html'>Back</a>");
                    out.println("<p><a href = 'adfeatures.html'>Return to Administrative Stuff Features</a></p>");
                    out.println("<a href = 'index.html'>Return to Home Page</a></p>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");
                    rs.close();
                    rs2.close();
                    rs3.close();
                    rs4.close();
                    valid.close();
                    stmt.close();
                    conn.close();
                    out.close();
                } catch (SQLException e) {
                    out.println(e);
                }
            }
        }
        out.println("<p><a href = 'dutyreport.html'>Back</a>");
        out.println("<p><a href = 'adfeatures.html'>Return to Administrative Stuff Features</a></p>");
        out.println("<a href = 'index.html'>Return to Home Page</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}