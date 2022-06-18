import javax.servlet.*;
import java.io.*;
import java.sql.*;
import org.apache.commons.lang3.StringUtils;

public class Examine extends GenericServlet {
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // HTML HEAD
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<title>Examine Page</title>");
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

        //Variebles
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet valid = null;

        String did = req.getParameter("did");
        String disease = req.getParameter("disease");
        String sid = req.getParameter("sid");
        String mtype = req.getParameter("mtype");
        String mname = req.getParameter("mname");
        String pass = req.getParameter("pass");

        //ELEGXOI

        // upoxrewtika pedia
        if( pass.equals("") || did.equals("") || sid.equals("") || mtype.equals("") || mname.equals("")) {
            out.println("<h1 style='background-color: #eb1616'>Alla fields with asterisk must be filled</h1>");
        }// Prepei to did na einai noumero
        else if (!StringUtils.isNumeric(did)) {
            out.println("<h1 style='background-color: #eb1616'>Invalid Diagnosis ID!</h1>");
        } // Prepei to sid na einai noumero
        else if (!StringUtils.isNumeric(sid)) {
            out.println("<h1 style='background-color: #eb1616'>Invalid Diagnosis ID!</h1>");
        }// Prepei na einai giatros
        else if ((Integer.parseInt(sid) < 1000) || (Integer.parseInt(sid) > 1999)){
            out.println("<h1 style='background-color: #eb1616'>Invalid Doctor ID!</h1>");
        }  //H astheneia prepei na einai sugkekrimeni
        else if( !disease.equals("") && !disease.equals("Katagma") && !disease.equals("Stithaxgi") && !disease.equals("Gripi") && !disease.equals("Gastrenteritida") && !disease.equals("COVID")){
            out.println("<h1 style='background-color: #eb1616'>Invalid Disease. Must be Katagma or Stithaxgi or Gripi or Gastrenteritida or COVID!</h1>");
        } else {
            try {
                //Connection
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tepdatabase?characterEncoding=UTF-8", "root", "");
                stmt = conn.createStatement();

                // an einai lathos kodikos, lathos
                String query = "select * from Doctors where ((sid='"+sid+"') and (password='"+pass+"'))";
                valid = stmt.executeQuery(query);

                if(!valid.next()){
                    out.println("<h1 style='background-color: #eb1616'>Invalid ID or password</h1>");
                }else {

                    //An uparxei Diagnosi me to idio id, LATHOS
                    query = "select * from Diagnosis where did ='" + did + "'";
                    rs = stmt.executeQuery(query);

                    if (rs.next()) {
                        out.println("<h1 style='background-color: #eb1616'>A Diagnosis with this ID already exist.</h1>");
                    } else {
                        //An den uparxei to farmako me auta ta name,type LATHOS
                        query = "select mid from Medicines where ((name ='" + mname + "') and (type ='" + mtype + "'))";
                        rs2 = stmt.executeQuery(query);

                        if (!rs2.next()) {
                            out.println("<h1 style='background-color: #eb1616'>This medicine doesn't exists.</h1>");
                        } else {
                            String med = rs2.getString("mid");

                            //An auto to farmako den einai gia auti tin asthenia LATHOS
                            query = "select * from Medicines where ((mid ='" + med + "') and (disease ='" + disease + "'))";
                            rs3 = stmt.executeQuery(query);

                            if (!rs3.next()) {
                                out.println("<h1 style='background-color: #eb1616'>This medicine doesn't work on this disease.</h1>");

                            } else {
                                // New Diagnosis
                                String update = "insert into Diagnosis values('" + did + "','" + disease + "')";
                                stmt.executeUpdate(update);

                                // New Define
                                update = "insert into Defines values('" + did + "','" + med + "', '" + sid + "')";
                                stmt.executeUpdate(update);

                                //Display new Diagnosis
                                out.println("<h1>New Diagnosis</h1>");
                                out.println("<p>Disease: " + disease + "</p>");
                                out.println("<p>Medicine: " + mname + "</p><p>  Type: " + mtype + " </p>");
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
                    out.println("<p><a href = 'define.html'>Back</a>");
                    out.println("<p><a href = 'doctorfeatures.html'>Return to Doctor Features</a></p>");
                    out.println("<a href = 'index.html'>Return to Home Page</a></p>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");
                    out.close();
                    rs.close();
                    rs2.close();
                    rs3.close();
                    valid.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    out.println(e);
                }
            }
        }
        out.println("<p><a href = 'define.html'>Back</a>");
        out.println("<p><a href = 'doctorfeatures.html'>Return to Doctor Features</a></p>");
        out.println("<a href = 'index.html'>Return to Home Page</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}