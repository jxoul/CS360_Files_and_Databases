import javax.servlet.*;
import java.io.*;
import java.sql.*;
import org.apache.commons.lang3.*;

public class PatientRegister extends GenericServlet{
    public void service(ServletRequest req, ServletResponse res) throws ServletException,IOException{
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        //HTML HEAD
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<title>Patients Register Page</title>");
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
        String amka = req.getParameter("amka");
        String pass = req.getParameter("pass");
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
        if((amka == "") || (pass == "") || (fn == "") || (ln == "") || (bd == "")){ //Ypoxreotika pedia
            out.println("<h1 style='background-color: #eb1616'>All fields with asterisk must be filled!</h1>");
        }else if(!StringUtils.isNumeric(amka)){     //Amka prepei na einai arithmos
            out.println("<h1 style='background-color: #eb1616'>Invalid AMKA. Must be a 10-digit number!</h1>");
        }else {
            try {
                //Connection
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tepdatabase?characterEncoding=UTF-8", "root", "");
                stmt = conn.createStatement();

                String query = "select * from Patients where amka ='" + amka + "'";
                rs = stmt.executeQuery(query);
                if(rs.next()){  // An AMKA uparxei idi, LATHOS
                    out.println("<h1 style='background-color: #eb1616'>A patient with this AMKA already exists</h1>");
                }else {
                    // Create new Patient
                    String update = "insert into Patients(amka,fname,lname,bday,password,address,insurer)" +
                            "values('" + amka + "','" + fn + "','" + ln + "','" + bd + "','" + pass + "','" + addr + "','" + ins + "')";
                    stmt.executeUpdate(update);

                    //Display new Patient
                    out.println("<h1>Just Registered</h1>");
                    out.println("<p>AMKA - " + amka + "</p");
                    out.println("<p>First Name - " + fn + "</p");
                    out.println("<p>Last Name - " + ln + "</p");
                    out.println("<p>Birthday - " + bd + "</p");
                    out.println("<p>Address - " + addr + "</p");
                    out.println("<p>Insurer - " + ins + "</p");
                    out.println("<p>Password - " + pass + "</p");

                    //Add chronic diseases
                    if (!cd1.equals("")) {
                        update = ("insert into PatientsCdisease(amka, cdisease) values('" + amka + "','" + cd1 + "')");
                        stmt.executeUpdate(update);
                    }
                    if (!cd2.equals("")) {
                        update = ("insert into PatientsCdisease(amka, cdisease) values('" + amka + "','" + cd2 + "')");
                        stmt.executeUpdate(update);
                    }
                    if (!cd3.equals("")) {
                        update = ("insert into PatientsCdisease(amka, cdisease) values('" + amka + "','" + cd3 + "')");
                        stmt.executeUpdate(update);
                    }
                    if (!cd4.equals("")) {
                        update = ("insert into PatientsCdisease(amka, cdisease) values('" + amka + "','" + cd4 + "')");
                        stmt.executeUpdate(update);
                    }
                    out.println("<p><a href = 'patientlogin.html'>Login</a></p>");
                }

            } catch (ClassNotFoundException e) {
            } catch (SQLException e) {
            } finally {
                try {
                    out.println("<p><a href = 'patientregister.html'>Back to Register</a></p>");
                    out.println("<p><a href = 'index.html'>Return to Home Page</a></p>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");
                    out.close();
                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        out.println("<p><a href = 'patientregister.html'>Back to Register</a></p>");
        out.println("<p><a href = 'index.html'>Return to Home Page</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}