import javax.servlet.*;
import java.io.*;
import java.sql.*;
import org.apache.commons.lang3.StringUtils;

public class Visit extends GenericServlet {
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        //HTML HEAD
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<title>Visit Page</title>");
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
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet valid = null;

        String pass = req.getParameter("pass");
        String nsid = req.getParameter("nsid");
        String edate = req.getParameter("edate");
        String symp = req.getParameter("symp");
        String dsid = req.getParameter("dsid");
        String amka = req.getParameter("amka");
        String fn = req.getParameter("fn");
        String ln = req.getParameter("ln");
        String bd = req.getParameter("bd");
        String addr = req.getParameter("addr");
        String ins = req.getParameter("ins");
        String cd1 = req.getParameter("cd1");
        String cd2 = req.getParameter("cd2");
        String cd3 = req.getParameter("cd3");
        String cd4 = req.getParameter("cd4");

        //Elegxoi

        //upoxrewtika pedia
        if ( pass.equals("") || nsid.equals("") || amka.equals("") || edate.equals("") || symp.equals("") || dsid.equals("") || fn.equals("") || ln.equals("") || bd.equals("")  ) {
            out.println("<h1 style='background-color: #eb1616'>All fields with asterisk must be filled!</h1>");
        } //To id tou nosileyti prepei na einai arithmos
        else if (!StringUtils.isNumeric(nsid)) {
            out.println("<h1 style='background-color: #eb1616'>Invalid Nurse ID!</h1>");
        } //To id tou giatrou prepei na einai arithmos
        else if (!StringUtils.isNumeric(dsid)) {
            out.println("<h1 style='background-color: #eb1616'>Invalid Doctor ID!</h1>");
        } //To amka tou astheni prepei na einai arithmos
        else if (!StringUtils.isNumeric(amka)) {
            out.println("<h1 style='background-color: #eb1616'>Invalid patient's AMKA!</h1>");
        } //To dsid prepei na deixnei se giatro
        else if ((Integer.parseInt(dsid) < 1000) || (Integer.parseInt(dsid) > 1999)) {
            out.println("<h1 style='background-color: #eb1616'>Invalid Doctor ID!</h1>");
        } //To nsid prepei na deixnei se nosileuti
        else if (Integer.parseInt(nsid) < 2000) {
            out.println("<h1 style='background-color: #eb1616'>Invalid Nurse ID!</h1>");
        } else {
            try{
                //Connection
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tepdatabase?characterEncoding=UTF-8", "root", "");
                stmt = conn.createStatement();

                // an einai sostos kodiikos
                String query = "select * from Nurses where ((sid='"+nsid+"') and (password='"+pass+"'))";
                valid = stmt.executeQuery(query);

                if(!valid.next()){
                    out.println("<h1 style='background-color: #eb1616'>Invalid ID or password</h1>");
                }else {
                    // an o nosileutis den exei efimeria, Lathos
                    query = "select * from NursesOnDuty where ((sid='" + nsid + "') and (edate='" + edate + "'))";
                    rs = stmt.executeQuery(query);

                    if (!rs.next()) {
                        out.println("<h1 style='background-color: #eb1616'>You are not on duty</h1>");
                    } else {
                        // an o nosoleutis den exei efimeria stin upodoxi, lathos
                        if (!rs.getString("department").equals("Ypodoxi")) {
                            out.println("<h1 style='background-color: #eb1616'>You must be on duty at Ypodoxi to do this action</h1>");
                        } else {
                            // an o giatros den exei efimeria, Lathos
                            query = "select * from DoctorsOnDuty where ((sid='" + dsid + "') and (edate='" + edate + "'))";
                            rs2 = stmt.executeQuery(query);

                            if (!rs2.next()) {
                                out.println("<h1 style='background-color: #eb1616'>This doctor is not on duty</h1>");
                            } else {
                                String update;

                                // an o asthenis uparxei sto arxeio, parousiasi stoixeiwn. An oxi, create new patient
                                query = "select * from Patients where amka ='" + amka + "'";
                                rs3 = stmt.executeQuery(query);

                                if (rs3.next()) {
                                    out.println("<p>AMKA - " + rs3.getString("amka") + "</p>");
                                    out.println("<p>First Name - " + rs3.getString("fname") + "</p>");
                                    out.println("<p>Last Name - " + rs3.getString("lname") + "</p>");
                                    out.println("<p>Birthday - " + rs3.getString("bday") + "</p>");
                                    out.println("<p>Address - " + rs3.getString("address") + "</p>");
                                    out.println("<p>Insurer - " + rs3.getString("insurer") + "</p>");

                                    //Display ta chronic disease
                                    out.println("<p>Chronic Diseases: ");
                                    query = "select cdisease from PatientsCdisease where amka ='" + amka + "'";
                                    rs4 = stmt.executeQuery(query);
                                    while (rs4.next()) {
                                        out.println("| " + rs4.getString("cdisease") + " ");
                                    }
                                    out.println("</p>");
                                } else {
                                    update = "insert into Patients " +
                                            "values('" + amka + "','" + fn + "','" + ln + "','" + bd + "','qwerty','" + addr + "','" + ins + "')";
                                    stmt.executeUpdate(update);

                                    //Add chronic diseases
                                    if (!cd1.equals("")) {
                                        update = ("insert into PatientsCdisease values('" + amka + "','" + cd1 + "')");
                                        stmt.executeUpdate(update);
                                    }
                                    if (!cd2.equals("")) {
                                        update = ("insert into PatientsCdisease values('" + amka + "','" + cd2 + "')");
                                        stmt.executeUpdate(update);
                                    }
                                    if (!cd3.equals("")) {
                                        update = ("insert into PatientsCdisease values('" + amka + "','" + cd3 + "')");
                                        stmt.executeUpdate(update);
                                    }
                                    if (!cd4.equals("")) {
                                        update = ("insert into PatientsCdisease values('" + amka + "','" + cd4 + "')");
                                        stmt.executeUpdate(update);
                                    }
                                }

                                // update visits
                                update = "insert into Visits values('" + amka + "','" + edate + "','" + symp + "')";
                                stmt.executeUpdate(update);

                                // parapompi ston katallilo giatro
                                update = "insert into Examines values('" + amka + "','" + dsid + "','" + edate + "')";
                                stmt.executeUpdate(update);

                                out.println("<h1>New Visit</h1>");
                                out.println("<h1>Record Updated</h1>");
                            }
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
                    out.close();
                    valid.close();
                    rs.close();
                    rs2.close();
                    rs3.close();
                    rs4.close();
                    stmt.close();
                    conn.close();
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