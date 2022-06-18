import javax.servlet.*;
import java.io.*;
import java.sql.*;
import org.apache.commons.lang3.StringUtils;

public class OrderCheck extends GenericServlet {
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        //HTML HEAD
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<title>Order Check Page</title>");
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
        ResultSet rs5 = null;
        ResultSet rs6 = null;
        ResultSet rs7 = null;
        ResultSet valid = null;

        String did = req.getParameter("did");
        String cid = req.getParameter("cid");
        String cname = req.getParameter("cname");
        String nsid = req.getParameter("nsid");
        String dsid = req.getParameter("dsid");
        String edate = req.getParameter("edate");
        String amka = req.getParameter("amka");
        String pass = req.getParameter("pass");

        //ELEGXOI

        // Ypoxrewtika pedia
        if( pass.equals("") || cid.equals("") || cname.equals("") || nsid.equals("") || dsid.equals("") || edate.equals("") || amka.equals("")){
            out.println("<h1 style='background-color: #eb1616'>All fields with asterisk must be filled!</h1>");
        }//To id tou diagnosis prepei na einai arithmos
        else if (!StringUtils.isNumeric(did)) {
            out.println("<h1 style='background-color: #eb1616'>Invalid Diagnosis ID!</h1>");
        }//To id tou check prepei na einai arithmos
        else if (!StringUtils.isNumeric(cid)) {
            out.println("<h1 style='background-color: #eb1616'>Invalid Check ID!</h1>");
        } //To id tou nosileuti prepei na einai arithmos
        else if(!StringUtils.isNumeric(nsid)){
            out.println("<h1 style='background-color: #eb1616'>Invalid Nurse ID!</h1>");
        } //To id tou giatrou prepei na einai arithmos
        else if(!StringUtils.isNumeric(dsid)){
            out.println("<h1 style='background-color: #eb1616'>Invalid Doctor ID!</h1>");
        } //To amka tou astheni prepei na einai arithmos
        else if(!StringUtils.isNumeric(amka)){
            out.println("<h1 style='background-color: #eb1616'>Invalid AMKA!</h1>");
        } //To nsid prepei na deixnei se nosileuti
        else if((Integer.parseInt(nsid) < 2000)){
            out.println("<h1 style='background-color: #eb1616'>Invalid Nurse ID!</h1>");
        } //to dsid prepei na deixnei se giatro
        else if(( (Integer.parseInt(dsid) < 1000) || (Integer.parseInt(dsid) > 1999) )){
            out.println("<h1 style='background-color: #eb1616'>Invalid Doctor ID!</h1>");
        }//to onoma tis exetasis prepei na einai sugkekrimeno
        else if( !cname.equals("Magnitiki") && !cname.equals("Aktinografia") && !cname.equals("Exetaseis aimatos") && !cname.equals("COVID Test") && !cname.equals("Exetaseis ourwn") ){
            out.println("<h1 style='background-color: #eb1616'>Invalid Disease. Must be Magnitiki or Aktinografia or Exetaseis aimatos or COVID Test or Exetaseis ourwn!</h1>");
        } else {
            try {
                //Connection
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tepdatabase?characterEncoding=UTF-8", "root", "");
                stmt = conn.createStatement();

                // an einai lathos kodikos, lathos
                String query = "select * from Doctors where ((sid='"+dsid+"') and (password='"+pass+"'))";
                valid = stmt.executeQuery(query);

                if(!valid.next()){
                    out.println("<h1 style='background-color: #eb1616'>Invalid ID or password</h1>");
                }else {

                    //an den uparxei i diagnosi, LATHOS
                    query = "select * from Diagnosis where did='" + did + "'";
                    rs7 = stmt.executeQuery(query);

                    if (!rs7.next()) {
                        out.println("<h1 style='background-color: #eb1616'>This diagnosis doesn't exist.!</h1>");
                    } else {

                        //an uparxei hdh check me to idio id, LATHOS
                        query = "select * from Checks where cid='" + cid + "'";
                        rs = stmt.executeQuery(query);

                        if (rs.next()) {
                            out.println("<h1 style='background-color: #eb1616'>A check with this ID already exist.!</h1>");
                        } else {
                            // an o nosileutis den exei efimeria, LATHOS
                            query = "select * from NursesOnDuty where ((sid='" + nsid + "') and (edate='" + edate + "'))";
                            rs2 = stmt.executeQuery(query);

                            if (!rs2.next()) {
                                out.println("<h1 style='background-color: #eb1616'>This Nurse is NOT on duty in this date!</h1>");
                            } else {
                                String tmimaNos = rs2.getString("department");

                                // an o giatros den exei efimeria, LATHOS
                                query = "select * from DoctorsOnDuty where ((sid='" + dsid + "') and (edate='" + edate + "'))";
                                rs3 = stmt.executeQuery(query);

                                if (!rs3.next()) {
                                    out.println("<h1 style='background-color: #eb1616'>This Doctor is NOT on duty in this date!</h1>");
                                } else {
                                    String tmimaDoc = rs3.getString("department");

                                    // an o nosileutis den einai sto tmima tou giatrou, LATHOS
                                    if (!tmimaDoc.equals(tmimaNos)) {
                                        out.println("<h1 style='background-color: #eb1616'>This Nurse is NOT on your department!</h1>");
                                    } else {

                                        // an o asthenis den episkefthike to tep ekeini tin mera, LATHOS
                                        query = "select * from Visits where ((edate='" + edate + "') and (amka='" + amka + "'))";
                                        rs4 = stmt.executeQuery(query);

                                        if (!rs4.next()) {
                                            out.println("<h1 style='background-color: #eb1616'>This patient didn't visit TEP in this date!</h1>");
                                        } else {

                                            //an den ton exetase autos
                                            query = "select * from Examines where ((amka='" + amka + "') and (sid='" + dsid + "') and (edate='" + edate + "'))";
                                            rs5 = stmt.executeQuery(query);

                                            if (!rs5.next()) {
                                                out.println("<h1 style='background-color: #eb1616'>You didn't examine this patient in this date!</h1>");
                                            } else {

                                                //Insert new check
                                                String update = "insert into Checks values('" + cid + "','" + cname + "'," + null + "," + null + ",'" + nsid + "','" + dsid + "','" + edate + "','" + amka + "','" + did + "')";
                                                stmt.executeUpdate(update);

                                                //Display new check
                                                query = "select * from Checks where cid ='" + cid + "'";
                                                rs6 = stmt.executeQuery(query);
                                                if (rs6.next()) {
                                                    out.println("<h1>New Check Ordered</h1>");
                                                    out.println("<p>ID - " + rs6.getString("cid") + "</p>");
                                                    out.println("<p>Name - " + rs6.getString("name") + "</p>");
                                                    out.println("<p>Report - " + rs6.getString("report") + "</p>");
                                                    out.println("<p>StayIn - " + rs6.getString("stayIn") + "</p>");
                                                    out.println("<p>Responsible Nurse - " + rs6.getString("nsid") + "</p>");
                                                    out.println("<p>Responsible Doctor - " + rs6.getString("dsid") + "</p>");
                                                    out.println("<p>Date - " + rs6.getDate("edate") + "</p>");
                                                    out.println("<p>Patient's AMKA - " + rs6.getString("amka") + "</p>");
                                                    out.println("<p>Diagnosis ID - " + rs6.getString("did") + "</p>");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                out.println(e);
            } finally {
                try {
                    out.println("<p><a href = 'ordercheck.html'>Back</a>");
                    out.println("<p><a href = 'doctorfeatures.html'>Return to Doctor Features</a></p>");
                    out.println("<a href = 'index.html'>Return to Home Page</a></p>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");
                    out.close();
                    rs.close();
                    rs2.close();
                    rs3.close();
                    rs4.close();
                    rs5.close();
                    rs6.close();
                    rs7.close();
                    valid.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    out.println(e);
                }
            }
        }
        out.println("<p><a href = 'ordercheck.html'>Back</a>");
        out.println("<p><a href = 'doctorfeatures.html'>Return to Doctor Features</a></p>");
        out.println("<a href = 'index.html'>Return to Home Page</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}