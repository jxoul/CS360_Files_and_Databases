import javax.servlet.*;
import java.io.*;
import java.sql.*;
import org.apache.commons.lang3.StringUtils;

public class CallOfDuty extends GenericServlet {
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        //HTML HEAD
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<title>Call Of Duty Page</title>");
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
        ResultSet rs8 = null;
        ResultSet rs9 = null;
        ResultSet rs10 = null;
        ResultSet rs11 = null;
        ResultSet rs12 = null;
        ResultSet rs13 = null;

        ResultSet valid = null;

        String edate = req.getParameter("edate");
        String sid = req.getParameter("sid");
        String pass = req.getParameter("pass");
        String xeirD = req.getParameter("xeirD");
        String xeirN = req.getParameter("xeirN");
        String pathD = req.getParameter("pathD");
        String pathN = req.getParameter("pathN");
        String kardD = req.getParameter("kardD");
        String kardN = req.getParameter("kardN");
        String pneuD = req.getParameter("pneuD");
        String pneuN = req.getParameter("pneuN");
        String dermD = req.getParameter("dermD");
        String dermN = req.getParameter("dermN");
        String yp1 = req.getParameter("yp1");
        String yp2 = req.getParameter("yp2");

        if ( edate.equals("") || sid.equals("") || pass.equals("") || xeirD.equals("") || xeirN.equals("") || pathD.equals("") ||
                pathN.equals("") || kardD.equals("") || kardN.equals("") || pneuD.equals("") || pneuN.equals("") || dermD.equals("") ||
                dermN.equals("") || yp1.equals("") || yp2.equals("") ) {
            out.println("<h1 style='background-color: #eb1616'>All fields with asterisk must be filled!</h1>");
        } //NO DUPLICATE NURSE ID
        else if(xeirN.equals(kardN) || xeirN.equals(pathN) || xeirN.equals(pneuN) || xeirN.equals(dermN) || xeirN.equals(yp1) || xeirN.equals(yp2) ) {
            out.println("<h1 style='background-color: #eb1616'>Error! Duplicate Nurse ID: "+xeirN+"</h1>");
        }else if(kardN.equals(xeirN) || kardN.equals(pathN) || kardN.equals(pneuN) || kardN.equals(dermN) || kardN.equals(yp1) || kardN.equals(yp2)) {
            out.println("<h1 style='background-color: #eb1616'>Error! Duplicate Nurse ID: "+kardN+"</h1>");
        }else if(pathN.equals(xeirN) || pathN.equals(kardN) || pathN.equals(pneuN) || pathN.equals(dermN) || pathN.equals(yp1) || pathN.equals(yp2)) {
            out.println("<h1 style='background-color: #eb1616'>Error! Duplicate Nurse ID: "+pathN+"</h1>");
        }else if(pneuN.equals(xeirN) || pneuN.equals(kardN) || pneuN.equals(pathN) || pneuN.equals(dermN) || pneuN.equals(yp1) || pneuN.equals(yp2)) {
            out.println("<h1 style='background-color: #eb1616'>Error! Duplicate Nurse ID: "+pneuN+"</h1>");
        }else if(dermN.equals(xeirN) || dermN.equals(kardN) || dermN.equals(pathN) || dermN.equals(pneuN) || dermN.equals(yp1) || dermN.equals(yp2)) {
            out.println("<h1 style='background-color: #eb1616'>Error! Duplicate Nurse ID: "+dermN+"</h1>");
        }else if(yp1.equals(xeirN) || yp1.equals(kardN) || yp1.equals(pathN) || yp1.equals(pneuN) || yp1.equals(dermN) || yp1.equals(yp2)) {
            out.println("<h1 style='background-color: #eb1616'>Error! Duplicate Nurse ID: "+yp1+"</h1>");
        }else if(yp2.equals(xeirN) || yp2.equals(kardN) || yp2.equals(pathN) || yp2.equals(pneuN) || yp2.equals(dermN) || yp2.equals(yp1)) {
            out.println("<h1 style='background-color: #eb1616'>Error! Duplicate Nurse ID: "+yp2+"</h1>");
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

                    // an uparxei idi to date, lathos
                    query = "select * from Tep where edate='" + edate + "'";
                    rs = stmt.executeQuery(query);

                    if (rs.next()) {
                        out.println("<h1 style='background-color: #eb1616'>Invalid date</h1>");
                    } else {

                        // an den einai xeirourgos. lathos
                        query = "select * from Doctors where sid='" + xeirD + "'";
                        rs2 = stmt.executeQuery(query);

                        if (!rs2.next()) {
                            out.println("<h1 style='background-color: #eb1616'>Lathos xeirourgos </h1>");
                        } else {
                            if(!rs2.getString("specialty").equals("Xeirourgos")){
                                out.println("<h1 style='background-color: #eb1616'>Lathos xeirourgos </h1>");
                            }else {

                                // an den einai pathologos. lathos
                                query = "select * from Doctors where sid='" + pathD + "'";
                                rs3 = stmt.executeQuery(query);

                                if (!rs3.next()) {
                                    out.println("<h1 style='background-color: #eb1616'>Lathos pathologos </h1>");
                                } else {
                                    if (!rs3.getString("specialty").equals("Pathologos")) {
                                        out.println("<h1 style='background-color: #eb1616'>Lathos Pathologos </h1>");
                                    } else {

                                        // an den einai kardiologos. lathos
                                        query = "select * from Doctors where sid='" + kardD + "'";
                                        rs4 = stmt.executeQuery(query);

                                        if (!rs4.next()) {
                                            out.println("<h1 style='background-color: #eb1616'>Lathos kardiologos </h1>");
                                        } else {
                                            if (!rs4.getString("specialty").equals("Kardiologos")) {
                                                out.println("<h1 style='background-color: #eb1616'>Lathos Kardiologos </h1>");
                                            } else {

                                                // an den einai pneumonologo. lathos
                                                query = "select * from Doctors where sid='" + pneuD + "'";
                                                rs5 = stmt.executeQuery(query);

                                                if (!rs5.next()) {
                                                    out.println("<h1 style='background-color: #eb1616'>Lathos pneumonologos </h1>");
                                                } else {
                                                    if (!rs5.getString("specialty").equals("Pneumonologos")) {
                                                        out.println("<h1 style='background-color: #eb1616'>Lathos Pneumonologos </h1>");
                                                    } else {

                                                        // an den einai dermatologos. lathos
                                                        query = "select * from Doctors where sid='" + dermD + "'";
                                                        rs6 = stmt.executeQuery(query);

                                                        if (!rs6.next()) {
                                                            out.println("<h1 style='background-color: #eb1616'>Lathos dermatologos </h1>");
                                                        } else {
                                                            if (!rs6.getString("specialty").equals("Dermatologos")) {
                                                                out.println("<h1 style='background-color: #eb1616'>Lathos Pneumonologos </h1>");
                                                            } else {

                                                                //an den uparxoun o nosileutis tou xeirourgikou, lathos
                                                                query = "select * from Nurses where sid='" + xeirN + "'";
                                                                rs7 = stmt.executeQuery(query);

                                                                if(!rs7.next()){
                                                                    out.println("<h1 style='background-color: #eb1616'>Lathos nosileutis xeirourgikou </h1>");
                                                                }else {

                                                                    //an den uparxoun o nosileutis tou kardiologikou, lathos
                                                                    query = "select * from Nurses where sid='" + kardN + "'";
                                                                    rs8 = stmt.executeQuery(query);

                                                                    if(!rs8.next()){
                                                                        out.println("<h1 style='background-color: #eb1616'>Lathos nosileutis kardiologikou </h1>");
                                                                    }else {

                                                                        //an den uparxoun o nosileutis tou pathologikou, lathos
                                                                        query = "select * from Nurses where sid='" + pathN + "'";
                                                                        rs9 = stmt.executeQuery(query);

                                                                        if(!rs9.next()){
                                                                            out.println("<h1 style='background-color: #eb1616'>Lathos nosileutis pathologikou </h1>");
                                                                        }else {

                                                                            //an den uparxoun o nosileutis tou pneumonologikou, lathos
                                                                            query = "select * from Nurses where sid='" + pneuN + "'";
                                                                            rs10 = stmt.executeQuery(query);

                                                                            if(!rs10.next()){
                                                                                out.println("<h1 style='background-color: #eb1616'>Lathos nosileutis pneumonologikou </h1>");
                                                                            }else {

                                                                                //an den uparxoun o nosileutis tou dermatologikou, lathos
                                                                                query = "select * from Nurses where sid='" + dermN + "'";
                                                                                rs11 = stmt.executeQuery(query);

                                                                                if(!rs11.next()){
                                                                                    out.println("<h1 style='background-color: #eb1616'>Lathos nosileutis dermatologikou </h1>");
                                                                                }else {

                                                                                    //an den uparxoun o nosileutis tis ypodoxis 1, lathos
                                                                                    query = "select * from Nurses where sid='" + yp1 + "'";
                                                                                    rs12 = stmt.executeQuery(query);

                                                                                    if(!rs12.next()){
                                                                                        out.println("<h1 style='background-color: #eb1616'>Lathos nosileutis Ypodoxis 1 </h1>");
                                                                                    }else {

                                                                                        //an den uparxoun o nosileutis tis ypodoxis 2, lathos
                                                                                        query = "select * from Nurses where sid='" + yp2 + "'";
                                                                                        rs13 = stmt.executeQuery(query);

                                                                                        if(!rs13.next()){
                                                                                            out.println("<h1 style='background-color: #eb1616'>Lathos nosileutis Ypodoxis 2 </h1>");
                                                                                        }else {

                                                                                            //Insert new date
                                                                                            String update = "insert into Tep values('" + edate + "')";
                                                                                            stmt.executeUpdate(update);

                                                                                            //Insert docs on duty

                                                                                            update = "insert into DoctorsOnDuty values(?,'"+edate+"',?)";
                                                                                            PreparedStatement updateDocs = conn.prepareStatement(update);


                                                                                            updateDocs.setString(1,xeirD);
                                                                                            updateDocs.setString(2,"Xeirourgiko");
                                                                                            updateDocs.executeUpdate();

                                                                                            updateDocs.setString(1,pneuD);
                                                                                            updateDocs.setString(2,"Pneumonologiko");
                                                                                            updateDocs.executeUpdate();

                                                                                            updateDocs.setString(1,kardD);
                                                                                            updateDocs.setString(2,"Kardiologiko");
                                                                                            updateDocs.executeUpdate();

                                                                                            updateDocs.setString(1,pathD);
                                                                                            updateDocs.setString(2,"Pathologiko");
                                                                                            updateDocs.executeUpdate();

                                                                                            updateDocs.setString(1,dermD);
                                                                                            updateDocs.setString(2,"Dermatologiko");
                                                                                            updateDocs.executeUpdate();

                                                                                            //Insert nurses on duty
                                                                                            update = "insert into NursesOnDuty values(?,'"+edate+"',?)";
                                                                                            PreparedStatement updateNurs = conn.prepareStatement(update);

                                                                                            updateNurs.setString(1,xeirN);
                                                                                            updateNurs.setString(2,"Xeirourgiko");
                                                                                            updateNurs.executeUpdate();

                                                                                            updateNurs.setString(1,pneuN);
                                                                                            updateNurs.setString(2,"Pneumonologiko");
                                                                                            updateNurs.executeUpdate();

                                                                                            updateNurs.setString(1,kardN);
                                                                                            updateNurs.setString(2,"Kardiologiko");
                                                                                            updateNurs.executeUpdate();

                                                                                            updateNurs.setString(1,pathN);
                                                                                            updateNurs.setString(2,"Pathologiko");
                                                                                            updateNurs.executeUpdate();

                                                                                            updateNurs.setString(1,dermN);
                                                                                            updateNurs.setString(2,"Dermatologiko");
                                                                                            updateNurs.executeUpdate();

                                                                                            updateNurs.setString(1,yp1);
                                                                                            updateNurs.setString(2,"Ypodoxi");
                                                                                            updateNurs.executeUpdate();

                                                                                            updateNurs.setString(1,yp2);
                                                                                            updateNurs.setString(2,"Ypodoxi");
                                                                                            updateNurs.executeUpdate();

                                                                                            out.println("<h1> Duty Updated for " + edate + "</h1>");
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
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
                    out.println("<p><a href = 'callofduty.html'>Back</a>");
                    out.println("<p><a href = 'adfeatures.html'>Return to Administrative Stuff Features</a></p>");
                    out.println("<a href = 'index.html'>Return to Home Page</a></p>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");
                    rs.close();
                    rs2.close();
                    rs3.close();
                    rs4.close();
                    rs5.close();
                    rs6.close();
                    rs7.close();
                    rs8.close();
                    rs9.close();
                    rs10.close();
                    rs11.close();
                    rs12.close();
                    rs13.close();
                    valid.close();
                    stmt.close();
                    conn.close();
                    out.close();
                } catch (SQLException e) {
                    out.println(e);
                }
            }
        }
        out.println("<p><a href = 'callofduty.html'>Back</a>");
        out.println("<p><a href = 'adfeatures.html'>Return to Administrative Stuff Features</a></p>");
        out.println("<a href = 'index.html'>Return to Home Page</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}