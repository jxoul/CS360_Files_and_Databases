import javax.servlet.*;
import java.io.*;
import java.sql.*;
import org.apache.commons.lang3.*;

public class DoctorRegister extends GenericServlet{
    public void service(ServletRequest req, ServletResponse res) throws ServletException,IOException{
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        //HTML HEADER
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<title>Doctors Register Page</title>");
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
        ResultSet rs = null;
        Statement stmt = null;
        String sid = req.getParameter("sid");
        String pass = req.getParameter("pass");
        String fn = req.getParameter("fn");
        String ln = req.getParameter("ln");
        String sp = req.getParameter("sp");
        String ph = req.getParameter("ph");

        //Elegxoi
        if((sid == "") || (pass == "") || (fn == "") || (ln == "") || (sp == "")){ //TA pedia auta prepei na exoun timi
            out.println("<h1 style='background-color: #eb1616'>All fields with asterisk must be filled!</h1>");
        }else if(!StringUtils.isNumeric(sid)){      //Prepei to sid na einai noumero
            out.println("<h1 style='background-color: #eb1616'>Invalid ID. Must be a number!</h1>");
        }else if( !(sp.equals("Pathologos")) && !(sp.equals("Dermatologos")) && !(sp.equals("Xeirourgos")) &&
                !(sp.equals("Kardiologos")) && !(sp.equals("Pneumonologos"))){  // Prepei na exei sugkekrimeni eidikotita
            out.println("<h1 style='background-color: #eb1616'>Invalid specialty. Must be Pathologos or Dermatologos or Xeirourgos or Kardiologos or Pneumonologos!</h1>");

        }else if(!ph.equals("") && !StringUtils.isNumeric(ph)){      //Prepei to phone na einai noumero an uparxei
            out.println("<h1 style='background-color: #eb1616'>Invalid phone. Must be a number!</h1>");
        }else {
            if ((Integer.parseInt(sid) < 1000) || (Integer.parseInt(sid) > 1999)) { // Prepei na einai Giatros
                out.println("<h1 style='background-color: #eb1616'>Invalid ID. Must be between 1000-1999!</h1>");
            } else {
                try {
                    //Connect
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tepdatabase?characterEncoding=UTF-8", "root", "");
                    stmt = conn.createStatement();

                    String query = "select * from Doctors where sid ='" + sid + "'";
                    rs = stmt.executeQuery(query);
                    if (rs.next()) {  // An sid uparxei idi, LATHOS
                        out.println("<h1 style='background-color: #eb1616'>A doctor with this sid already exists</h1>");
                    } else {
                        //Create new Doctor
                        String update = "insert into Doctors(sid,fname,lname,specialty,password,phone)" +
                                "values('" + sid + "','" + fn + "','" + ln + "','" + sp + "','" + pass + "','" + ph + "')";
                        stmt.executeUpdate(update);

                        //Display new Doctor
                        out.println("<h1>Just Registered</h1>");
                        out.println("<p>ID - " + sid + "</p>");
                        out.println("<p>First Name - " + fn + "</p>");
                        out.println("<p>Last Name - " + ln + "</p>");
                        out.println("<p>Specialty - " + sp + "</p>");
                        out.println("<p>Phone Number - " + ph + "</p>");
                        out.println("<p>Password - " + pass + "</p>");
                        out.println("<p><a href = 'stufflogin.html'>Login</a></p>");
                    }
                } catch (ClassNotFoundException e) {
                    out.println(e);
                } catch (SQLException e) {
                    out.println(e);
                } finally {
                    try {
                        out.println("<p><a href = 'doctorregister.html'>Back to Register</a></p>");
                        out.println("<p><a href = 'index.html'>Return to Home Page</a></p>");
                        out.println("</div>");
                        out.println("</body>");
                        out.println("</html>");
                        rs.close();
                        stmt.close();
                        conn.close();
                    } catch (SQLException e) {
                        out.println(e);
                    }
                }
            }
        }
        out.println("<p><a href = 'doctorregister.html'>Back to Register</a></p>");
        out.println("<p><a href = 'index.html'>Return to Home Page</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}