import java.sql.*;

public class Database {


    public static void main(String[] args) throws Exception{
        Connection connection = getConnection();
        createTables(connection);
        initTables(connection);
        createViews(connection);
    }

    public static void createViews(Connection conn) throws Exception{
        try {

            Statement stmt = conn.createStatement();

            String createView = new String( "create view patientinfo as select "+
                    " amka , Concat('Onomatepwnumo: ', fname, ' ' ,lname, ' , Hm.Gennisis: ', bday, ' , Odos: ', address, ' , Asfalistis: ',insurer) as stoixeia "+
                    " from Patients");
            stmt.executeUpdate(createView);

            createView = new String( "create view doctorsinfo as select "+
                    " sid , Concat('Onomatepwnumo: ', fname, ' ' ,lname, ' , Eidikotita: ', specialty, ' , Tilefono: ',phone) as stoixeia "+
                    " from Doctors ");
            stmt.executeUpdate(createView);

            createView = new String("create view nurseinfo  as select "+
                    " sid , Concat('Onomatepwnumo: ', fname, ' ' ,lname) as stoixeia "+
                    " from Nurses ");
            stmt.executeUpdate(createView);

            createView = new String("create view adsinfo  as select "+
                    " sid , Concat('Onomatepwnumo: ', fname, ' ' ,lname, ' , Thesi: ', position) as stoixeia "+
                    " from Ads ");
            stmt.executeUpdate(createView);

            createView = new String("create view medinfo  as select "+
                    " mid , Concat('Onoma: ', name, ' , Tupos: ' ,type, ' , Periektikotita se drastiki ousia: ', content, 'mg , Katallilo gia : ', disease) as stoixeia "+
                    " from Medicines ");
            stmt.executeUpdate(createView);

             createView = new String("create view patientRecord as " +
                    "select c.amka, Concat('Stis ', c.edate,' episkefthikate to TEP me ', " +
                    "(select symptoms from Visits where ((amka = c.amka) and (edate=c.edate))) , "+
                    "' .Kanate ', c.name , ' me apotelesmata: ', c.report ,' .') as intro, "+
                    "Concat('Diagnostikate me ', (select disease from Diagnosis where did = c.did) , ' kai gia tin therapeia, suntagografithike ', "+
                    "(select type from Medicines where mid IN (select mid from Defines where did = c.did)), ' ' , "+
                    "(select name from Medicines where mid IN (select mid from Defines where did = c.did)) , '.') as therapeia "+
                    "from Checks as c");
            stmt.executeUpdate(createView);


            createView = new String("create view doctorsDutyRecord as "+
                "select sid, edate, Concat('Stis ', edate, ' eixe efimeria sto tmima: ' , department , '.') as details from DoctorsOnDuty");
            stmt.executeUpdate(createView);

            createView = new String( "create view nursesDutyRecord as "+
                    "select sid, edate, Concat('Stis ', edate, ' eixe efimeria sto tmima: ' , department , '.') as details from NursesOnDuty");
            stmt.executeUpdate(createView);


            createView = new String("create view covid as select "+
                    "distinct info.amka , info.stoixeia from patientinfo as info where info.amka  in (select amka from Checks where did in (select did from Diagnosis where disease ='COVID'))");
            stmt.executeUpdate(createView);


            createView =  new String("create view episkepseis as select "+
                    "x.edate, Concat('Onomatepwnumo: ', (select fname from Patients where amka = x.amka), ' ', (select lname from Patients where amka = x.amka), ' -> Symptwmata: ', x.symptoms) as info from Visits as x");

            stmt.executeUpdate(createView);



        }catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Create Views COMPLETE");
        }
    }

    public static void initVisits(Connection conn) throws Exception{
        try{
            String prep = new String("insert into Visits values(?,?,?)");
            String prep2 = new String("insert into Examines values(?,?,?)");
            String prep3 = new String("insert into Diagnosis values(?,?)");
            String prep4 = new String("insert into Defines values(?,?,?)");
            String prep5 = new String("insert into Checks values(?,?,?,?,?,?,?,?,?)");
            String prep6 = new String("insert into Record values(?,?,?,?)");
            PreparedStatement insertVisits = conn.prepareStatement(prep);
            PreparedStatement insertExamines = conn.prepareStatement(prep2);
            PreparedStatement insertDiagnosis = conn.prepareStatement(prep3);
            PreparedStatement insertDefines = conn.prepareStatement(prep4);
            PreparedStatement insertCheck = conn.prepareStatement(prep5);
            PreparedStatement insertRecord = conn.prepareStatement(prep6);


            //Patient1
            insertVisits.setString(1,"1000000001");
            insertVisits.setDate(2, Date.valueOf("2021-1-1"));
            insertVisits.setString(3,"Puretos");
            insertVisits.executeUpdate();
            insertExamines.setString(1,"1000000001");
            insertExamines.setString(2, "1003");
            insertExamines.setDate(3,Date.valueOf("2021-1-1"));
            insertExamines.executeUpdate();
            insertDiagnosis.setString(1,"1");
            insertDiagnosis.setString(2,"COVID");
            insertDiagnosis.executeUpdate();
            insertDefines.setString(1,"1");
            insertDefines.setString(2,"15");
            insertDefines.setString(3,"1003");
            insertDefines.executeUpdate();
            insertCheck.setString(1,"1");
            insertCheck.setString(2,"COVID Test");
            insertCheck.setString(3,"COVID positive");
            insertCheck.setBoolean(4,true);
            insertCheck.setString(5,"2001");
            insertCheck.setString(6,"1003");
            insertCheck.setDate(7,Date.valueOf("2021-1-1"));
            insertCheck.setString(8,"1000000001");
            insertCheck.setString(9,"1");
            insertCheck.executeUpdate();
            insertRecord.setDate(1,Date.valueOf("2021-1-1"));
            insertRecord.setString(2,"1");
            insertRecord.setString(3,"1");
            insertRecord.setString(4,"1000000001");
            insertRecord.executeUpdate();

            //Patient2
            insertVisits.setString(1,"1000000002");
            insertVisits.setDate(2, Date.valueOf("2021-1-1"));
            insertVisits.setString(3,"Dispnoia");
            insertVisits.executeUpdate();
            insertExamines.setString(1,"1000000002");
            insertExamines.setString(2, "1009");
            insertExamines.setDate(3,Date.valueOf("2021-1-1"));
            insertExamines.executeUpdate();
            insertDiagnosis.setString(1,"2");
            insertDiagnosis.setString(2,"Stithaxgi");
            insertDiagnosis.executeUpdate();
            insertDefines.setString(1,"2");
            insertDefines.setString(2,"5");
            insertDefines.setString(3,"1009");
            insertDefines.executeUpdate();
            insertCheck.setString(1,"2");
            insertCheck.setString(2,"Aktinografia");
            insertCheck.setString(3,"Xreiazetai oksigono");
            insertCheck.setBoolean(4,true);
            insertCheck.setString(5,"2005");
            insertCheck.setString(6,"1009");
            insertCheck.setDate(7,Date.valueOf("2021-1-1"));
            insertCheck.setString(8,"1000000002");
            insertCheck.setString(9,"2");
            insertCheck.executeUpdate();
            insertRecord.setDate(1,Date.valueOf("2021-1-1"));
            insertRecord.setString(2,"2");
            insertRecord.setString(3,"2");
            insertRecord.setString(4,"1000000002");
            insertRecord.executeUpdate();


            //Patient3
            insertVisits.setString(1,"1000000003");
            insertVisits.setDate(2, Date.valueOf("2021-1-1"));
            insertVisits.setString(3,"Spasmeno Xeri");
            insertVisits.executeUpdate();
            insertExamines.setString(1,"1000000003");
            insertExamines.setString(2, "1000");
            insertExamines.setDate(3,Date.valueOf("2021-1-1"));
            insertExamines.executeUpdate();
            insertDiagnosis.setString(1,"3");
            insertDiagnosis.setString(2,"Katagma");
            insertDiagnosis.executeUpdate();
            insertDefines.setString(1,"3");
            insertDefines.setString(2,"7");
            insertDefines.setString(3,"1000");
            insertDefines.executeUpdate();
            insertCheck.setString(1,"3");
            insertCheck.setString(2,"Aktinografia");
            insertCheck.setString(3,"Xreiazetai gipso");
            insertCheck.setBoolean(4,true);
            insertCheck.setString(5,"2003");
            insertCheck.setString(6,"1000");
            insertCheck.setDate(7,Date.valueOf("2021-1-1"));
            insertCheck.setString(8,"1000000003");
            insertCheck.setString(9,"3");
            insertCheck.executeUpdate();
            insertRecord.setDate(1,Date.valueOf("2021-1-1"));
            insertRecord.setString(2,"3");
            insertRecord.setString(3,"3");
            insertRecord.setString(4,"1000000003");
            insertRecord.executeUpdate();


            //Patient4
            insertVisits.setString(1,"1000000004");
            insertVisits.setDate(2, Date.valueOf("2021-1-1"));
            insertVisits.setString(3,"Ponokoilos");
            insertVisits.executeUpdate();
            insertExamines.setString(1,"1000000004");
            insertExamines.setString(2, "1003");
            insertExamines.setDate(3,Date.valueOf("2021-1-1"));
            insertExamines.executeUpdate();
            insertDiagnosis.setString(1,"4");
            insertDiagnosis.setString(2,null);
            insertDiagnosis.executeUpdate();
            insertDefines.setString(1,"4");
            insertDefines.setString(2,null);
            insertDefines.setString(3,"1003");
            insertDefines.executeUpdate();
            insertCheck.setString(1,"4");
            insertCheck.setString(2,"Exetaseis aimatos");
            insertCheck.setString(3,"Xreiazetai xalarwsi");
            insertCheck.setBoolean(4,false);
            insertCheck.setString(5,"2001");
            insertCheck.setString(6,"1003");
            insertCheck.setDate(7,Date.valueOf("2021-1-1"));
            insertCheck.setString(8,"1000000004");
            insertCheck.setString(9,"4");
            insertCheck.executeUpdate();
            insertRecord.setDate(1,Date.valueOf("2021-1-1"));
            insertRecord.setString(2,"4");
            insertRecord.setString(3,"4");
            insertRecord.setString(4,"1000000004");
            insertRecord.executeUpdate();


            //Patient5
            insertVisits.setString(1,"1000000005");
            insertVisits.setDate(2, Date.valueOf("2021-1-1"));
            insertVisits.setString(3,"Spasmeno Podi");
            insertVisits.executeUpdate();
            insertExamines.setString(1,"1000000005");
            insertExamines.setString(2, "1000");
            insertExamines.setDate(3,Date.valueOf("2021-1-1"));
            insertExamines.executeUpdate();
            insertDiagnosis.setString(1,"5");
            insertDiagnosis.setString(2,"Katagma");
            insertDiagnosis.executeUpdate();
            insertDefines.setString(1,"5");
            insertDefines.setString(2,"9");
            insertDefines.setString(3,"1000");
            insertDefines.executeUpdate();
            insertCheck.setString(1,"5");
            insertCheck.setString(2,"Magnitiki");
            insertCheck.setString(3,"Xreiazetai gipso");
            insertCheck.setBoolean(4,true);
            insertCheck.setString(5,"2003");
            insertCheck.setString(6,"1000");
            insertCheck.setDate(7,Date.valueOf("2021-1-1"));
            insertCheck.setString(8,"1000000005");
            insertCheck.setString(9,"5");
            insertCheck.executeUpdate();
            insertRecord.setDate(1,Date.valueOf("2021-1-1"));
            insertRecord.setString(2,"5");
            insertRecord.setString(3,"5");
            insertRecord.setString(4,"1000000005");
            insertRecord.executeUpdate();


            //Patient6
            insertVisits.setString(1,"1000000006");
            insertVisits.setDate(2, Date.valueOf("2021-1-2"));
            insertVisits.setString(3,"Spasmeno Laimo");
            insertVisits.executeUpdate();
            insertExamines.setString(1,"1000000006");
            insertExamines.setString(2, "1001");
            insertExamines.setDate(3,Date.valueOf("2021-1-2"));
            insertExamines.executeUpdate();
            insertDiagnosis.setString(1,"6");
            insertDiagnosis.setString(2,"Katagma");
            insertDiagnosis.executeUpdate();
            insertDefines.setString(1,"6");
            insertDefines.setString(2,"7");
            insertDefines.setString(3,"1001");
            insertDefines.executeUpdate();
            insertCheck.setString(1,"6");
            insertCheck.setString(2,"Aktinografia");
            insertCheck.setString(3,"Xreiazetai kolaro");
            insertCheck.setBoolean(4,true);
            insertCheck.setString(5,"2010");
            insertCheck.setString(6,"1001");
            insertCheck.setDate(7,Date.valueOf("2021-1-2"));
            insertCheck.setString(8,"1000000006");
            insertCheck.setString(9,"6");
            insertCheck.executeUpdate();
            insertRecord.setDate(1,Date.valueOf("2021-1-2"));
            insertRecord.setString(2,"6");
            insertRecord.setString(3,"6");
            insertRecord.setString(4,"1000000006");
            insertRecord.executeUpdate();

            //Patient7
            insertVisits.setString(1,"1000000007");
            insertVisits.setDate(2, Date.valueOf("2021-1-2"));
            insertVisits.setString(3,"Puretos, Vixas");
            insertVisits.executeUpdate();
            insertExamines.setString(1,"1000000007");
            insertExamines.setString(2, "1010");
            insertExamines.setDate(3,Date.valueOf("2021-1-2"));
            insertExamines.executeUpdate();
            insertDiagnosis.setString(1,"7");
            insertDiagnosis.setString(2,"COVID");
            insertDiagnosis.executeUpdate();
            insertDefines.setString(1,"7");
            insertDefines.setString(2,"13");
            insertDefines.setString(3,"1010");
            insertDefines.executeUpdate();
            insertCheck.setString(1,"7");
            insertCheck.setString(2,"COVID Test");
            insertCheck.setString(3,"COVID positive");
            insertCheck.setBoolean(4,true);
            insertCheck.setString(5,"2012");
            insertCheck.setString(6,"1010");
            insertCheck.setDate(7,Date.valueOf("2021-1-2"));
            insertCheck.setString(8,"1000000007");
            insertCheck.setString(9,"7");
            insertCheck.executeUpdate();
            insertRecord.setDate(1,Date.valueOf("2021-1-2"));
            insertRecord.setString(2,"7");
            insertRecord.setString(3,"7");
            insertRecord.setString(4,"1000000007");
            insertRecord.executeUpdate();

            //Patient8
            insertVisits.setString(1,"1000000008");
            insertVisits.setDate(2, Date.valueOf("2021-1-2"));
            insertVisits.setString(3,"Puretos, Diaroia");
            insertVisits.executeUpdate();
            insertExamines.setString(1,"1000000008");
            insertExamines.setString(2, "1010");
            insertExamines.setDate(3,Date.valueOf("2021-1-2"));
            insertExamines.executeUpdate();
            insertDiagnosis.setString(1,"8");
            insertDiagnosis.setString(2,"COVID");
            insertDiagnosis.executeUpdate();
            insertDefines.setString(1,"8");
            insertDefines.setString(2,"13");
            insertDefines.setString(3,"1010");
            insertDefines.executeUpdate();
            insertCheck.setString(1,"8");
            insertCheck.setString(2,"COVID Test");
            insertCheck.setString(3,"COVID positive");
            insertCheck.setBoolean(4,true);
            insertCheck.setString(5,"2012");
            insertCheck.setString(6,"1010");
            insertCheck.setDate(7,Date.valueOf("2021-1-2"));
            insertCheck.setString(8,"1000000008");
            insertCheck.setString(9,"8");
            insertCheck.executeUpdate();
            insertRecord.setDate(1,Date.valueOf("2021-1-2"));
            insertRecord.setString(2,"8");
            insertRecord.setString(3,"8");
            insertRecord.setString(4,"1000000008");
            insertRecord.executeUpdate();

            //Patient9
            insertVisits.setString(1,"1000000009");
            insertVisits.setDate(2, Date.valueOf("2021-1-2"));
            insertVisits.setString(3,"Taxikardia");
            insertVisits.executeUpdate();
            insertExamines.setString(1,"1000000009");
            insertExamines.setString(2, "1007");
            insertExamines.setDate(3,Date.valueOf("2021-1-2"));
            insertExamines.executeUpdate();
            insertDiagnosis.setString(1,"9");
            insertDiagnosis.setString(2,null);
            insertDiagnosis.executeUpdate();
            insertDefines.setString(1,"9");
            insertDefines.setString(2,null);
            insertDefines.setString(3,"1007");
            insertDefines.executeUpdate();
            insertCheck.setString(1,"9");
            insertCheck.setString(2,"Exetaseis aimatos");
            insertCheck.setString(3,"Efragma");
            insertCheck.setBoolean(4,false);
            insertCheck.setString(5,"2011");
            insertCheck.setString(6,"1007");
            insertCheck.setDate(7,Date.valueOf("2021-1-2"));
            insertCheck.setString(8,"1000000009");
            insertCheck.setString(9,"9");
            insertCheck.executeUpdate();
            insertRecord.setDate(1,Date.valueOf("2021-1-2"));
            insertRecord.setString(2,"9");
            insertRecord.setString(3,"9");
            insertRecord.setString(4,"1000000009");
            insertRecord.executeUpdate();

            //Patient10
            insertVisits.setString(1,"1000000010");
            insertVisits.setDate(2, Date.valueOf("2021-1-2"));
            insertVisits.setString(3,"Kammeni sarka");
            insertVisits.executeUpdate();
            insertExamines.setString(1,"1000000010");
            insertExamines.setString(2, "1013");
            insertExamines.setDate(3,Date.valueOf("2021-1-2"));
            insertExamines.executeUpdate();
            insertDiagnosis.setString(1,"10");
            insertDiagnosis.setString(2,"Stithaxgi");
            insertDiagnosis.executeUpdate();
            insertDefines.setString(1,"10");
            insertDefines.setString(2,"4");
            insertDefines.setString(3,"1013");
            insertDefines.executeUpdate();
            insertCheck.setString(1,"10");
            insertCheck.setString(2,"Aktinografia");
            insertCheck.setString(3,"Egavma");
            insertCheck.setBoolean(4,true);
            insertCheck.setString(5,"2009");
            insertCheck.setString(6,"1013");
            insertCheck.setDate(7,Date.valueOf("2021-1-2"));
            insertCheck.setString(8,"1000000010");
            insertCheck.setString(9,"10");
            insertCheck.executeUpdate();
            insertRecord.setDate(1,Date.valueOf("2021-1-2"));
            insertRecord.setString(2,"10");
            insertRecord.setString(3,"10");
            insertRecord.setString(4,"1000000010");
            insertRecord.executeUpdate();

            //Patient11
            insertVisits.setString(1,"1000000011");
            insertVisits.setDate(2, Date.valueOf("2021-1-3"));
            insertVisits.setString(3,"Zalades");
            insertVisits.executeUpdate();
            insertExamines.setString(1,"1000000011");
            insertExamines.setString(2, "1005");
            insertExamines.setDate(3,Date.valueOf("2021-1-3"));
            insertExamines.executeUpdate();
            insertDiagnosis.setString(1,"11");
            insertDiagnosis.setString(2,null);
            insertDiagnosis.executeUpdate();
            insertDefines.setString(1,"11");
            insertDefines.setString(2,null);
            insertDefines.setString(3,"1005");
            insertDefines.executeUpdate();
            insertCheck.setString(1,"11");
            insertCheck.setString(2,"Exetaseis ourwn");
            insertCheck.setString(3,"Tipota to sovaro");
            insertCheck.setBoolean(4,true);
            insertCheck.setString(5,"2015");
            insertCheck.setString(6,"1005");
            insertCheck.setDate(7,Date.valueOf("2021-1-3"));
            insertCheck.setString(8,"1000000011");
            insertCheck.setString(9,"11");
            insertCheck.executeUpdate();
            insertRecord.setDate(1,Date.valueOf("2021-1-3"));
            insertRecord.setString(2,"11");
            insertRecord.setString(3,"11");
            insertRecord.setString(4,"1000000011");
            insertRecord.executeUpdate();

            //Patient12
            insertVisits.setString(1,"1000000012");
            insertVisits.setDate(2, Date.valueOf("2021-1-3"));
            insertVisits.setString(3,"Puretos, Dispnoia");
            insertVisits.executeUpdate();
            insertExamines.setString(1,"1000000012");
            insertExamines.setString(2, "1011");
            insertExamines.setDate(3,Date.valueOf("2021-1-3"));
            insertExamines.executeUpdate();
            insertDiagnosis.setString(1,"12");
            insertDiagnosis.setString(2,"COVID");
            insertDiagnosis.executeUpdate();
            insertDefines.setString(1,"12");
            insertDefines.setString(2,"14");
            insertDefines.setString(3,"1011");
            insertDefines.executeUpdate();
            insertCheck.setString(1,"12");
            insertCheck.setString(2,"COVID Test");
            insertCheck.setString(3,"COVID positive");
            insertCheck.setBoolean(4,true);
            insertCheck.setString(5,"2019");
            insertCheck.setString(6,"1011");
            insertCheck.setDate(7,Date.valueOf("2021-1-3"));
            insertCheck.setString(8,"1000000012");
            insertCheck.setString(9,"12");
            insertCheck.executeUpdate();
            insertRecord.setDate(1,Date.valueOf("2021-1-3"));
            insertRecord.setString(2,"12");
            insertRecord.setString(3,"12");
            insertRecord.setString(4,"1000000012");
            insertRecord.executeUpdate();

            //Patient13
            insertVisits.setString(1,"1000000013");
            insertVisits.setDate(2, Date.valueOf("2021-1-3"));
            insertVisits.setString(3,"Puretos, Ponolaimos");
            insertVisits.executeUpdate();
            insertExamines.setString(1,"1000000013");
            insertExamines.setString(2, "1011");
            insertExamines.setDate(3,Date.valueOf("2021-1-3"));
            insertExamines.executeUpdate();
            insertDiagnosis.setString(1,"13");
            insertDiagnosis.setString(2,"Gripi");
            insertDiagnosis.executeUpdate();
            insertDefines.setString(1,"13");
            insertDefines.setString(2,"2");
            insertDefines.setString(3,"1011");
            insertDefines.executeUpdate();
            insertCheck.setString(1,"13");
            insertCheck.setString(2,"Exetaseis aimatos");
            insertCheck.setString(3,"Gripi");
            insertCheck.setBoolean(4,false);
            insertCheck.setString(5,"2019");
            insertCheck.setString(6,"1011");
            insertCheck.setDate(7,Date.valueOf("2021-1-3"));
            insertCheck.setString(8,"1000000013");
            insertCheck.setString(9,"13");
            insertCheck.executeUpdate();
            insertRecord.setDate(1,Date.valueOf("2021-1-3"));
            insertRecord.setString(2,"13");
            insertRecord.setString(3,"13");
            insertRecord.setString(4,"1000000013");
            insertRecord.executeUpdate();

            //Patient15
            insertVisits.setString(1,"1000000014");
            insertVisits.setDate(2, Date.valueOf("2021-1-3"));
            insertVisits.setString(3,"Ponokoilos");
            insertVisits.executeUpdate();
            insertExamines.setString(1,"1000000014");
            insertExamines.setString(2, "1005");
            insertExamines.setDate(3,Date.valueOf("2021-1-3"));
            insertExamines.executeUpdate();
            insertDiagnosis.setString(1,"14");
            insertDiagnosis.setString(2,"Gastrenteritida");
            insertDiagnosis.executeUpdate();
            insertDefines.setString(1,"14");
            insertDefines.setString(2,"12");
            insertDefines.setString(3,"1005");
            insertDefines.executeUpdate();
            insertCheck.setString(1,"14");
            insertCheck.setString(2,"Exetaseis aimatos");
            insertCheck.setString(3,"Gastrenteritida");
            insertCheck.setBoolean(4,false);
            insertCheck.setString(5,"2015");
            insertCheck.setString(6,"1005");
            insertCheck.setDate(7,Date.valueOf("2021-1-3"));
            insertCheck.setString(8,"1000000014");
            insertCheck.setString(9,"14");
            insertCheck.executeUpdate();
            insertRecord.setDate(1,Date.valueOf("2021-1-3"));
            insertRecord.setString(2,"14");
            insertRecord.setString(3,"14");
            insertRecord.setString(4,"1000000014");
            insertRecord.executeUpdate();

            //Patien15
            insertVisits.setString(1,"1000000015");
            insertVisits.setDate(2, Date.valueOf("2021-1-3"));
            insertVisits.setString(3,"Spasmeno Xeri");
            insertVisits.executeUpdate();
            insertExamines.setString(1,"1000000015");
            insertExamines.setString(2, "1002");
            insertExamines.setDate(3,Date.valueOf("2021-1-3"));
            insertExamines.executeUpdate();
            insertDiagnosis.setString(1,"15");
            insertDiagnosis.setString(2,"Katagma");
            insertDiagnosis.executeUpdate();
            insertDefines.setString(1,"15");
            insertDefines.setString(2,"7");
            insertDefines.setString(3,"1002");
            insertDefines.executeUpdate();
            insertCheck.setString(1,"15");
            insertCheck.setString(2,"Aktinografia");
            insertCheck.setString(3,"Xreiazetai gipso");
            insertCheck.setBoolean(4,true);
            insertCheck.setString(5,"2017");
            insertCheck.setString(6,"1002");
            insertCheck.setDate(7,Date.valueOf("2021-1-3"));
            insertCheck.setString(8,"1000000015");
            insertCheck.setString(9,"15");
            insertCheck.executeUpdate();
            insertRecord.setDate(1,Date.valueOf("2021-1-3"));
            insertRecord.setString(2,"15");
            insertRecord.setString(3,"15");
            insertRecord.setString(4,"1000000015");
            insertRecord.executeUpdate();

        }catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Init Visits COMPLETE");
        }
    }

    public static void initTepDuty(Connection conn) throws Exception{
        try{
            String pstmt = new String("insert into Tep values(?)");
            PreparedStatement insertTep = conn.prepareStatement(pstmt);
            

            insertTep.setDate(1,Date.valueOf("2021-1-1"));
            insertTep.executeUpdate();

            insertTep.setDate(1,Date.valueOf("2021-1-2"));
            insertTep.executeUpdate();

            insertTep.setDate(1,Date.valueOf("2021-1-3"));
            insertTep.executeUpdate();

            insertTep.setDate(1,Date.valueOf("2021-1-4"));
            insertTep.executeUpdate();

            insertTep.setDate(1,Date.valueOf("2021-1-5"));
            insertTep.executeUpdate();



            String pstmt2 = new String("insert into DoctorsOnDuty values(?,?,?)");
            PreparedStatement insertDocD = conn.prepareStatement(pstmt2);

            String pstmt3 = new String("insert into NursesOnDuty values(?,?,?)");
            PreparedStatement insertNurD = conn.prepareStatement(pstmt3);


            //2021-1-1
            insertNurD.setString(1,"2001");
            insertNurD.setDate(2,Date.valueOf("2021-1-1"));
            insertNurD.setString(3, "Pathologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1003");
            insertDocD.setDate(2,Date.valueOf("2021-1-1"));
            insertDocD.setString(3, "Pathologiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1, "2002");
            insertNurD.setDate(2,Date.valueOf("2021-1-1"));
            insertNurD.setString(3, "Dermatologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1012");
            insertDocD.setDate(2,Date.valueOf("2021-1-1"));
            insertDocD.setString(3, "Dermatologiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1, "2003");
            insertNurD.setDate(2,Date.valueOf("2021-1-1"));
            insertNurD.setString(3, "Xeirourgiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1000");
            insertDocD.setDate(2,Date.valueOf("2021-1-1"));
            insertDocD.setString(3, "Xeirourgiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1, "2004");
            insertNurD.setDate(2,Date.valueOf("2021-1-1"));
            insertNurD.setString(3, "Kardiologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1006");
            insertDocD.setDate(2,Date.valueOf("2021-1-1"));
            insertDocD.setString(3, "Kardiologiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1,"2005");
            insertNurD.setDate(2,Date.valueOf("2021-1-1"));
            insertNurD.setString(3, "Pneumonologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1009");
            insertDocD.setDate(2,Date.valueOf("2021-1-1"));
            insertDocD.setString(3, "Pneumonologiko");
            insertDocD.executeUpdate();

            insertNurD.setString(1,"2006");
            insertNurD.setDate(2,Date.valueOf("2021-1-1"));
            insertNurD.setString(3, "Ypodoxi");
            insertNurD.executeUpdate();

            insertNurD.setString(1,"2007");
            insertNurD.setDate(2,Date.valueOf("2021-1-1"));
            insertNurD.setString(3, "Ypodoxi");
            insertNurD.executeUpdate();


            insertNurD.setString(1,"2008");
            insertNurD.setDate(2,Date.valueOf("2021-1-2"));
            insertNurD.setString(3, "Pathologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1004");
            insertDocD.setDate(2,Date.valueOf("2021-1-2"));
            insertDocD.setString(3, "Pathologiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1, "2009");
            insertNurD.setDate(2,Date.valueOf("2021-1-2"));
            insertNurD.setString(3, "Dermatologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1013");
            insertDocD.setDate(2,Date.valueOf("2021-1-2"));
            insertDocD.setString(3, "Dermatologiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1, "2010");
            insertNurD.setDate(2,Date.valueOf("2021-1-2"));
            insertNurD.setString(3, "Xeirourgiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1001");
            insertDocD.setDate(2,Date.valueOf("2021-1-2"));
            insertDocD.setString(3, "Xeirourgiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1, "2011");
            insertNurD.setDate(2,Date.valueOf("2021-1-2"));
            insertNurD.setString(3, "Kardiologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1007");
            insertDocD.setDate(2,Date.valueOf("2021-1-2"));
            insertDocD.setString(3, "Kardiologiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1,"2012");
            insertNurD.setDate(2,Date.valueOf("2021-1-2"));
            insertNurD.setString(3, "Pneumonologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1010");
            insertDocD.setDate(2,Date.valueOf("2021-1-2"));
            insertDocD.setString(3, "Pneumonologiko");
            insertDocD.executeUpdate();

            insertNurD.setString(1,"2013");
            insertNurD.setDate(2,Date.valueOf("2021-1-2"));
            insertNurD.setString(3, "Ypodoxi");
            insertNurD.executeUpdate();

            insertNurD.setString(1,"2014");
            insertNurD.setDate(2,Date.valueOf("2021-1-2"));
            insertNurD.setString(3, "Ypodoxi");
            insertNurD.executeUpdate();


            insertNurD.setString(1,"2015");
            insertNurD.setDate(2,Date.valueOf("2021-1-3"));
            insertNurD.setString(3, "Pathologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1005");
            insertDocD.setDate(2,Date.valueOf("2021-1-3"));
            insertDocD.setString(3, "Pathologiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1, "2016");
            insertNurD.setDate(2,Date.valueOf("2021-1-3"));
            insertNurD.setString(3, "Dermatologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1014");
            insertDocD.setDate(2,Date.valueOf("2021-1-3"));
            insertDocD.setString(3, "Dermatologiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1, "2017");
            insertNurD.setDate(2,Date.valueOf("2021-1-3"));
            insertNurD.setString(3, "Xeirourgiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1002");
            insertDocD.setDate(2,Date.valueOf("2021-1-3"));
            insertDocD.setString(3, "Xeirourgiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1, "2018");
            insertNurD.setDate(2,Date.valueOf("2021-1-3"));
            insertNurD.setString(3, "Kardiologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1008");
            insertDocD.setDate(2,Date.valueOf("2021-1-3"));
            insertDocD.setString(3, "Kardiologiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1,"2019");
            insertNurD.setDate(2,Date.valueOf("2021-1-3"));
            insertNurD.setString(3, "Pneumonologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1011");
            insertDocD.setDate(2,Date.valueOf("2021-1-3"));
            insertDocD.setString(3, "Pneumonologiko");
            insertDocD.executeUpdate();

            insertNurD.setString(1,"2020");
            insertNurD.setDate(2,Date.valueOf("2021-1-3"));
            insertNurD.setString(3, "Ypodoxi");
            insertNurD.executeUpdate();

            insertNurD.setString(1,"2000");
            insertNurD.setDate(2,Date.valueOf("2021-1-3"));
            insertNurD.setString(3, "Ypodoxi");
            insertNurD.executeUpdate();

            insertNurD.setString(1,"2001");
            insertNurD.setDate(2,Date.valueOf("2021-1-4"));
            insertNurD.setString(3, "Pathologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1003");
            insertDocD.setDate(2,Date.valueOf("2021-1-4"));
            insertDocD.setString(3, "Pathologiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1, "2002");
            insertNurD.setDate(2,Date.valueOf("2021-1-4"));
            insertNurD.setString(3, "Dermatologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1012");
            insertDocD.setDate(2,Date.valueOf("2021-1-4"));
            insertDocD.setString(3, "Dermatologiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1, "2003");
            insertNurD.setDate(2,Date.valueOf("2021-1-4"));
            insertNurD.setString(3, "Xeirourgiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1000");
            insertDocD.setDate(2,Date.valueOf("2021-1-4"));
            insertDocD.setString(3, "Xeirourgiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1, "2004");
            insertNurD.setDate(2,Date.valueOf("2021-1-4"));
            insertNurD.setString(3, "Kardiologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1006");
            insertDocD.setDate(2,Date.valueOf("2021-1-4"));
            insertDocD.setString(3, "Kardiologiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1,"2005");
            insertNurD.setDate(2,Date.valueOf("2021-1-4"));
            insertNurD.setString(3, "Pneumonologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1009");
            insertDocD.setDate(2,Date.valueOf("2021-1-4"));
            insertDocD.setString(3, "Pneumonologiko");
            insertDocD.executeUpdate();

            insertNurD.setString(1,"2006");
            insertNurD.setDate(2,Date.valueOf("2021-1-4"));
            insertNurD.setString(3, "Ypodoxi");
            insertNurD.executeUpdate();

            insertNurD.setString(1,"2007");
            insertNurD.setDate(2,Date.valueOf("2021-1-4"));
            insertNurD.setString(3, "Ypodoxi");
            insertNurD.executeUpdate();


            insertNurD.setString(1,"2008");
            insertNurD.setDate(2,Date.valueOf("2021-1-5"));
            insertNurD.setString(3, "Pathologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1004");
            insertDocD.setDate(2,Date.valueOf("2021-1-5"));
            insertDocD.setString(3, "Pathologiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1, "2009");
            insertNurD.setDate(2,Date.valueOf("2021-1-5"));
            insertNurD.setString(3, "Dermatologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1013");
            insertDocD.setDate(2,Date.valueOf("2021-1-5"));
            insertDocD.setString(3, "Dermatologiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1, "2010");
            insertNurD.setDate(2,Date.valueOf("2021-1-5"));
            insertNurD.setString(3, "Xeirourgiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1001");
            insertDocD.setDate(2,Date.valueOf("2021-1-5"));
            insertDocD.setString(3, "Xeirourgiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1, "2011");
            insertNurD.setDate(2,Date.valueOf("2021-1-5"));
            insertNurD.setString(3, "Kardiologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1007");
            insertDocD.setDate(2,Date.valueOf("2021-1-5"));
            insertDocD.setString(3, "Kardiologiko");
            insertDocD.executeUpdate();


            insertNurD.setString(1,"2012");
            insertNurD.setDate(2,Date.valueOf("2021-1-5"));
            insertNurD.setString(3, "Pneumonologiko");
            insertNurD.executeUpdate();

            insertDocD.setString(1,"1010");
            insertDocD.setDate(2,Date.valueOf("2021-1-5"));
            insertDocD.setString(3, "Pneumonologiko");
            insertDocD.executeUpdate();

            insertNurD.setString(1,"2013");
            insertNurD.setDate(2,Date.valueOf("2021-1-5"));
            insertNurD.setString(3, "Ypodoxi");
            insertNurD.executeUpdate();

            insertNurD.setString(1,"2014");
            insertNurD.setDate(2,Date.valueOf("2021-1-5"));
            insertNurD.setString(3, "Ypodoxi");
            insertNurD.executeUpdate();

        }catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Init Tep COMPLETE");
        }
    }

    public static void initPatients(Connection conn) throws Exception{
        try{
            String pstmt = new String("insert into Patients values(?,?,?,?,?,?,?)");
            String pstmt2 = new String("insert into PatientsCdisease values(?,?)");
            PreparedStatement insertPat = conn.prepareStatement(pstmt);
            PreparedStatement insertCd = conn.prepareStatement(pstmt2);

            insertPat.setString(1,"1000000001");
            insertPat.setString(2,"Giannis");
            insertPat.setString(3, "Papadakis");
            insertPat.setDate(4,Date.valueOf("1996-10-22"));
            insertPat.setString(5,"qwerty");
            insertPat.setString(6,"Athanasiou Diakou 40");
            insertPat.setString(7,"IKA");
            insertPat.executeUpdate();
            insertCd.setString(1,"1000000001");
            insertCd.setString(2,"Asthma");
            insertCd.executeUpdate();
            insertCd.setString(1,"1000000001");
            insertCd.setString(2,"Mikites");
            insertCd.executeUpdate();

            insertPat.setString(1,"1000000002");
            insertPat.setString(2,"Dimitrs");
            insertPat.setString(3, "Veggelas");
            insertPat.setDate(4,Date.valueOf("1982-1-2"));
            insertPat.setString(5,"qwerty");
            insertPat.setString(6,"Paulou Mela 21");
            insertPat.setString(7,"EFKA");
            insertPat.executeUpdate();

            insertPat.setString(1,"1000000003");
            insertPat.setString(2,"Euaggelia");
            insertPat.setString(3, "Sparaki");
            insertPat.setDate(4,Date.valueOf("1962-4-20"));
            insertPat.setString(5,"qwerty");
            insertPat.setString(6,"Agias Foteinis 12");
            insertPat.setString(7,"OGA");
            insertPat.executeUpdate();

            insertPat.setString(1,"1000000004");
            insertPat.setString(2,"Kostas");
            insertPat.setString(3, "Kostakis");
            insertPat.setDate(4,Date.valueOf("1992-1-10"));
            insertPat.setString(5,"qwerty");
            insertPat.setString(6,"Panepistimiou 14");
            insertPat.setString(7,"OAEE");
            insertPat.executeUpdate();
            insertCd.setString(1,"1000000004");
            insertCd.setString(2,"Mikites");
            insertCd.executeUpdate();

            insertPat.setString(1,"1000000005");
            insertPat.setString(2,"Anastasia");
            insertPat.setString(3, "Mpatati");
            insertPat.setDate(4,Date.valueOf("1999-9-12"));
            insertPat.setString(5,"qwerty");
            insertPat.setString(6,"Plateia Eleutherias 2");
            insertPat.setString(7,"IKA");
            insertPat.executeUpdate();

            insertPat.setString(1,"1000000006");
            insertPat.setString(2,"Stelios");
            insertPat.setString(3, "Kaparakis");
            insertPat.setDate(4,Date.valueOf("1974-2-2"));
            insertPat.setString(5,"qwerty");
            insertPat.setString(6,"");
            insertPat.setString(7,"");
            insertPat.executeUpdate();

            insertPat.setString(1,"1000000007");
            insertPat.setString(2,"Manos");
            insertPat.setString(3, "Kamnakis");
            insertPat.setDate(4,Date.valueOf("1989-12-31"));
            insertPat.setString(5,"qwerty");
            insertPat.setString(6,"Mpizaniou 6");
            insertPat.setString(7,"TSEMEDE");
            insertPat.executeUpdate();
            insertCd.setString(1,"1000000007");
            insertCd.setString(2,"Magoulades");
            insertCd.executeUpdate();

            insertPat.setString(1,"1000000008");
            insertPat.setString(2,"Giannis");
            insertPat.setString(3, "Louloudis");
            insertPat.setDate(4,Date.valueOf("1990-4-8"));
            insertPat.setString(5,"qwerty");
            insertPat.setString(6,"Amerikis 98");
            insertPat.setString(7,"OGA");
            insertPat.executeUpdate();

            insertPat.setString(1,"1000000009");
            insertPat.setString(2,"Dimitra");
            insertPat.setString(3, "Kopalaki");
            insertPat.setDate(4,Date.valueOf("2000-11-1"));
            insertPat.setString(5,"qwerty");
            insertPat.setString(6,"Agias Sofias 53");
            insertPat.setString(7,"IKA");
            insertPat.executeUpdate();
            insertCd.setString(1,"1000000009");
            insertCd.setString(2,"Disleitourgeia enterou");
            insertCd.executeUpdate();

            insertPat.setString(1,"1000000010");
            insertPat.setString(2,"Eirini");
            insertPat.setString(3, "Sarra");
            insertPat.setDate(4,Date.valueOf("1979-8-10"));
            insertPat.setString(5,"qwerty");
            insertPat.setString(6,"");
            insertPat.setString(7,"TSEMEDE");
            insertPat.executeUpdate();

            insertPat.setString(1,"1000000011");
            insertPat.setString(2,"Sofia");
            insertPat.setString(3, "Spara");
            insertPat.setDate(4,Date.valueOf("1992-1-30"));
            insertPat.setString(5,"qwerty");
            insertPat.setString(6,"Paramenis 64");
            insertPat.setString(7,"");
            insertPat.executeUpdate();

            insertPat.setString(1,"1000000012");
            insertPat.setString(2,"Tilemaxos");
            insertPat.setString(3, "Galanis");
            insertPat.setDate(4,Date.valueOf("1953-2-2"));
            insertPat.setString(5,"qwerty");
            insertPat.setString(6,"Amourgianis 41");
            insertPat.setString(7,"EFKA");
            insertPat.executeUpdate();
            insertCd.setString(1,"1000000012");
            insertCd.setString(2,"Aimoroides");
            insertCd.executeUpdate();

            insertPat.setString(1,"1000000013");
            insertPat.setString(2,"Nikitas");
            insertPat.setString(3, "Papoutsakis");
            insertPat.setDate(4,Date.valueOf("1962-4-20"));
            insertPat.setString(5,"qwerty");
            insertPat.setString(6,"Armeniou 82");
            insertPat.setString(7,"");
            insertPat.executeUpdate();

            insertPat.setString(1,"1000000014");
            insertPat.setString(2,"Stavros");
            insertPat.setString(3, "Mpertis");
            insertPat.setDate(4,Date.valueOf("1996-12-1"));
            insertPat.setString(5,"qwerty");
            insertPat.setString(6,"Menemenis 91");
            insertPat.setString(7,"TSEMEDE");
            insertPat.executeUpdate();

            insertPat.setString(1,"1000000015");
            insertPat.setString(2,"Giorgos");
            insertPat.setString(3, "Okamenos");
            insertPat.setDate(4,Date.valueOf("1993-10-20"));
            insertPat.setString(5,"qwerty");
            insertPat.setString(6,"");
            insertPat.setString(7,"");
            insertPat.executeUpdate();
            insertCd.setString(1,"1000000015");
            insertCd.setString(2,"Mikites");
            insertCd.executeUpdate();
            insertCd.setString(1,"1000000015");
            insertCd.setString(2,"Asthma");
            insertCd.executeUpdate();
            insertCd.setString(1,"1000000015");
            insertCd.setString(2,"Disleitourgia enterou");
            insertCd.executeUpdate();
            insertCd.setString(1,"1000000015");
            insertCd.setString(2,"Aimoroides");
            insertCd.executeUpdate();



        }catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Init Patients COMPLETE");
        }
    }

    public static void initMedicines(Connection conn) throws Exception{
        try{
            String pstmt = new String("insert into Medicines values(?,?,?,?,?)");
            PreparedStatement insertMed = conn.prepareStatement(pstmt);

            insertMed.setString(1,"1");
            insertMed.setString(2,"Cold&Flu");
            insertMed.setString(3,"Xapi");
            insertMed.setInt(4,500);
            insertMed.setString(5,"Gripi");
            insertMed.executeUpdate();

            insertMed.setString(1,"2");
            insertMed.setString(2,"Ponstan");
            insertMed.setString(3,"Xapi");
            insertMed.setInt(4,400);
            insertMed.setString(5,"Gripi");
            insertMed.executeUpdate();

            insertMed.setString(1,"3");
            insertMed.setString(2,"Ponstan");
            insertMed.setString(3,"Siropi");
            insertMed.setInt(4,450);
            insertMed.setString(5,"Gripi");
            insertMed.executeUpdate();

            insertMed.setString(1,"4");
            insertMed.setString(2,"Mpoom");
            insertMed.setString(3,"Aloifi");
            insertMed.setInt(4, 400);
            insertMed.setString(5,"Stithaxgi");
            insertMed.executeUpdate();

            insertMed.setString(1,"5");
            insertMed.setString(2,"Zorix");
            insertMed.setString(3,"Xapi");
            insertMed.setInt(4, 150);
            insertMed.setString(5,"Stithaxgi");
            insertMed.executeUpdate();

            insertMed.setString(1,"6");
            insertMed.setString(2,"Zorix");
            insertMed.setString(3,"Siropi");
            insertMed.setInt(4, 450);
            insertMed.setString(5,"Stithaxgi");
            insertMed.executeUpdate();

            insertMed.setString(1,"7");
            insertMed.setString(2,"Zenil");
            insertMed.setString(3,"Enesimo");
            insertMed.setInt(4,100 );
            insertMed.setString(5,"Katagma");
            insertMed.executeUpdate();

            insertMed.setString(1,"8");
            insertMed.setString(2,"Zelin");
            insertMed.setString(3,"Aloifi");
            insertMed.setInt(4, 250);
            insertMed.setString(5,"Katagma");
            insertMed.executeUpdate();

            insertMed.setString(1,"9");
            insertMed.setString(2,"Metil");
            insertMed.setString(3,"Xapi");
            insertMed.setInt(4, 400);
            insertMed.setString(5,"Katagma");
            insertMed.executeUpdate();

            insertMed.setString(1,"10");
            insertMed.setString(2,"Zovirax");
            insertMed.setString(3,"Aloifi");
            insertMed.setInt(4, 300 );
            insertMed.setString(5,"Gastrenteritida");
            insertMed.executeUpdate();

            insertMed.setString(1,"11");
            insertMed.setString(2,"Zovirax");
            insertMed.setString(3,"Siropi");
            insertMed.setInt(4, 400);
            insertMed.setString(5,"Gastrenteritida");
            insertMed.executeUpdate();

            insertMed.setString(1,"12");
            insertMed.setString(2,"Fusidin");
            insertMed.setString(3,"Eispneomeno");
            insertMed.setInt(4, 360);
            insertMed.setString(5,"Gastrenteritida");
            insertMed.executeUpdate();

            insertMed.setString(1,"13");
            insertMed.setString(2,"Borax");
            insertMed.setString(3,"Xapi");
            insertMed.setInt(4, 450);
            insertMed.setString(5,"COVID");
            insertMed.executeUpdate();

            insertMed.setString(1,"14");
            insertMed.setString(2,"Borax");
            insertMed.setString(3,"Enesimo");
            insertMed.setInt(4, 80);
            insertMed.setString(5,"COVID");
            insertMed.executeUpdate();

            insertMed.setString(1,"15");
            insertMed.setString(2,"Borax");
            insertMed.setString(3,"Eispneomeno");
            insertMed.setInt(4, 420);
            insertMed.setString(5,"COVID");
            insertMed.executeUpdate();

        }catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Init Medicine COMPLETE");
        }
    }

    public static void initAds(Connection conn) throws Exception{
        try {

            String pstmt = new String("insert into Ads values(?,?,?,?,?)");
            PreparedStatement insertAd = conn.prepareStatement(pstmt);

            insertAd.setString(1,"0");
            insertAd.setString(2,"Periadros");
            insertAd.setString(3,"Popotas");
            insertAd.setString(4,"qwerty");
            insertAd.setString(5,"Dioikitis");
            insertAd.executeUpdate();

            insertAd.setString(1,"1");
            insertAd.setString(2,"Konstantinos");
            insertAd.setString(3,"Katakouzinos");
            insertAd.setString(4,"qwerty");
            insertAd.setString(5,"Anaplirotis Dioikitis");
            insertAd.executeUpdate();

            insertAd.setString(1,"2");
            insertAd.setString(2,"Eleni");
            insertAd.setString(3,"Vlaxaki");
            insertAd.setString(4,"qwerty");
            insertAd.setString(5,"Proedros");
            insertAd.executeUpdate();

            insertAd.setString(1,"3");
            insertAd.setString(2,"Pegki");
            insertAd.setString(3,"Karra");
            insertAd.setString(4,"qwerty");
            insertAd.setString(5,"Antiproedros");
            insertAd.executeUpdate();

            insertAd.setString(1,"4");
            insertAd.setString(2,"Manthos");
            insertAd.setString(3,"Foustanos");
            insertAd.setString(4,"qwerty");
            insertAd.setString(5,"Melos");
            insertAd.executeUpdate();

            insertAd.setString(1,"5");
            insertAd.setString(2,"Matina");
            insertAd.setString(3,"Mantarinaki");
            insertAd.setString(4,"qwerty");
            insertAd.setString(5,"Grammateas");
            insertAd.executeUpdate();

        }catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Init Administrator Stuff COMPLETE");
        }
    }

    public static void initNurses(Connection conn) throws Exception{
        try {
            String pstmt = new String("insert into Nurses values(?,?,?,?)");
            PreparedStatement insertNurse = conn.prepareStatement(pstmt);

            insertNurse.setString(1,"2000");
            insertNurse.setString(2,"Kostas");
            insertNurse.setString(3,"Atzaris");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2001");
            insertNurse.setString(2,"Anna");
            insertNurse.setString(3,"Alani");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2002");
            insertNurse.setString(2,"Eua");
            insertNurse.setString(3,"Papapetrou");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2003");
            insertNurse.setString(2,"Despoina");
            insertNurse.setString(3,"Karastergiou");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2004");
            insertNurse.setString(2,"Eirini");
            insertNurse.setString(3,"Xoulaki");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2005");
            insertNurse.setString(2,"Sokratis");
            insertNurse.setString(3,"Mpampes");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2006");
            insertNurse.setString(2,"Thanasis");
            insertNurse.setString(3,"Karipidis");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2007");
            insertNurse.setString(2,"Giorgos");
            insertNurse.setString(3,"Makris");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2008");
            insertNurse.setString(2,"Maria");
            insertNurse.setString(3,"Stirna");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2009");
            insertNurse.setString(2,"Eleni");
            insertNurse.setString(3,"Asimoglou");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2010");
            insertNurse.setString(2,"Xristina");
            insertNurse.setString(3,"Mpizeli");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2011");
            insertNurse.setString(2,"Dimiris");
            insertNurse.setString(3,"Georgiadis");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2012");
            insertNurse.setString(2,"Petros");
            insertNurse.setString(3,"Ganitis");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2013");
            insertNurse.setString(2,"Giannis");
            insertNurse.setString(3,"Gialamas");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();


            insertNurse.setString(1,"2014");
            insertNurse.setString(2,"Pantelis");
            insertNurse.setString(3,"Petsos");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2015");
            insertNurse.setString(2,"Xaralampos");
            insertNurse.setString(3,"Valios");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2016");
            insertNurse.setString(2,"Dimitris");
            insertNurse.setString(3,"Gizas");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2017");
            insertNurse.setString(2,"Athina");
            insertNurse.setString(3,"Kontou");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2018");
            insertNurse.setString(2,"Lamprini");
            insertNurse.setString(3,"Ntouma");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2019");
            insertNurse.setString(2,"Eva");
            insertNurse.setString(3,"Loukoumi");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

            insertNurse.setString(1,"2020");
            insertNurse.setString(2,"Giorgos");
            insertNurse.setString(3,"Papageorgiou");
            insertNurse.setString(4,"qwerty");
            insertNurse.executeUpdate();

        }catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Insert Nurses COMPLETE");
        }
    }

    public static void initDoctors(Connection conn) throws Exception{
        try{

            String pstmt = new String("insert into Doctors values(?,?,?,?,?,?)");
            PreparedStatement insertDoctor = conn.prepareStatement(pstmt);

            insertDoctor.setString(1,"1000");
            insertDoctor.setString(2,"Mpampis");
            insertDoctor.setString(3,"Sougias");
            insertDoctor.setString(4,"qwerty");
            insertDoctor.setString(5,"Xeirourgos");
            insertDoctor.setString(6,"6908741000");
            insertDoctor.executeUpdate();


            insertDoctor.setString(1,"1001");
            insertDoctor.setString(2,"Despoina");
            insertDoctor.setString(3,"Ramataki");
            insertDoctor.setString(4,"qwerty");
            insertDoctor.setString(5,"Xeirourgos");
            insertDoctor.setString(6,"6908741001");
            insertDoctor.executeUpdate();

            insertDoctor.setString(1,"1002");
            insertDoctor.setString(2,"Alexandros");
            insertDoctor.setString(3,"Paterakis");
            insertDoctor.setString(4,"qwerty");
            insertDoctor.setString(5,"Xeirourgos");
            insertDoctor.setString(6,"6908741002");
            insertDoctor.executeUpdate();

            insertDoctor.setString(1,"1003");
            insertDoctor.setString(2,"Despoina");
            insertDoctor.setString(3,"Lampraki");
            insertDoctor.setString(4,"qwerty");
            insertDoctor.setString(5,"Pathologos");
            insertDoctor.setString(6,"6908741004");
            insertDoctor.executeUpdate();

            insertDoctor.setString(1,"1004");
            insertDoctor.setString(2,"Kostas");
            insertDoctor.setString(3,"Kolivakis");
            insertDoctor.setString(4,"qwerty");
            insertDoctor.setString(5,"Pathologos");
            insertDoctor.setString(6,"6908741004");
            insertDoctor.executeUpdate();

            insertDoctor.setString(1,"1005");
            insertDoctor.setString(2,"Maria");
            insertDoctor.setString(3,"Dimitrelou");
            insertDoctor.setString(4,"qwerty");
            insertDoctor.setString(5,"Pathologos");
            insertDoctor.setString(6,"6908741005");
            insertDoctor.executeUpdate();

            insertDoctor.setString(1,"1006");
            insertDoctor.setString(2,"Giannis");
            insertDoctor.setString(3,"Pappas");
            insertDoctor.setString(4,"qwerty");
            insertDoctor.setString(5,"Kardiologos");
            insertDoctor.setString(6,"6908741006");
            insertDoctor.executeUpdate();

            insertDoctor.setString(1,"1007");
            insertDoctor.setString(2,"Ioanna");
            insertDoctor.setString(3,"Kalimera");
            insertDoctor.setString(4,"qwerty");
            insertDoctor.setString(5,"Kardiologos");
            insertDoctor.setString(6,"6908741007");
            insertDoctor.executeUpdate();

            insertDoctor.setString(1,"1008");
            insertDoctor.setString(2,"Marios");
            insertDoctor.setString(3,"Dakeras");
            insertDoctor.setString(4,"qwerty");
            insertDoctor.setString(5,"Kardiologos");
            insertDoctor.setString(6,"6908741008");
            insertDoctor.executeUpdate();

            insertDoctor.setString(1,"1009");
            insertDoctor.setString(2,"Thanos");
            insertDoctor.setString(3,"Mpermperis");
            insertDoctor.setString(4,"qwerty");
            insertDoctor.setString(5,"Pneumonologos");
            insertDoctor.setString(6,"6908741009");
            insertDoctor.executeUpdate();

            insertDoctor.setString(1,"1010");
            insertDoctor.setString(2,"Eirini");
            insertDoctor.setString(3,"Mpompa");
            insertDoctor.setString(4,"qwerty");
            insertDoctor.setString(5,"Pneumonologos");
            insertDoctor.setString(6,"6908741010");
            insertDoctor.executeUpdate();

            insertDoctor.setString(1,"1011");
            insertDoctor.setString(2,"Thanasis");
            insertDoctor.setString(3,"Xrysos");
            insertDoctor.setString(4,"qwerty");
            insertDoctor.setString(5,"Pneumonologos");
            insertDoctor.setString(6,"6908741011");
            insertDoctor.executeUpdate();

            insertDoctor.setString(1,"1012");
            insertDoctor.setString(2,"Athanasia");
            insertDoctor.setString(3,"Mperaki");
            insertDoctor.setString(4,"qwerty");
            insertDoctor.setString(5,"Dermatologos");
            insertDoctor.setString(6,"6908741012");
            insertDoctor.executeUpdate();

            insertDoctor.setString(1,"1013");
            insertDoctor.setString(2,"Stavros");
            insertDoctor.setString(3,"Kozos");
            insertDoctor.setString(4,"qwerty");
            insertDoctor.setString(5,"Dermatologos");
            insertDoctor.setString(6,"6908741013");
            insertDoctor.executeUpdate();

            insertDoctor.setString(1,"1014");
            insertDoctor.setString(2,"Ilias");
            insertDoctor.setString(3,"Moutseras");
            insertDoctor.setString(4,"qwerty");
            insertDoctor.setString(5,"Dermatologos");
            insertDoctor.setString(6,"6908741014");
            insertDoctor.executeUpdate();

        }catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Insert Doctors COMPLETE");
        }
    }

    public static void initTables(Connection conn) throws Exception {
        try {
            initDoctors(conn);
            initNurses(conn);
            initAds(conn);
            initMedicines(conn);
            initPatients(conn);
            initTepDuty(conn);
            initVisits(conn);
        }catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Init Tables COMPLETE");
        }

    }

    public static void createTables(Connection conn) throws Exception {

        try {
            Statement stmt = conn.createStatement();
            //Create Patiens table
            String createPatients = new String(
                    "CREATE TABLE Patients(" +
                            "amka char(10) NOT NULL, " +
                            "fname varchar(20) NOT NULL, " +
                            "lname varchar(30) NOT NULL, " +
                            "bday date NOT NULL, " +
                            "password varchar(255) NOT NULL, " +
                            "address varchar(50), " +
                            "insurer varchar(15), " +
                            "PRIMARY KEY(amka))");
            stmt.executeUpdate(createPatients);

            //Create Patiens Chronic Disease table
            String createPatientsCdisease = new String(
                    "CREATE TABLE PatientsCdisease(" +
                            "amka char(10), " +
                            "cdisease varchar(50), " +
                            "FOREIGN KEY (amka) REFERENCES Patients(amka))" );
            stmt.executeUpdate(createPatientsCdisease);

            //Create Doctors table
            String createDoctors = new String(
                    "CREATE TABLE Doctors(" +
                            "sid varchar(10) NOT NULL, " +
                            "fname varchar(20) NOT NULL, " +
                            "lname varchar(30) NOT NULL, " +
                            "password varchar(255) NOT NULL, " +
                            "specialty varchar(13) NOT NULL, " +
                            "phone char(10), " +
                            "PRIMARY KEY(sid), " +
                            "CHECK " +
                            "((specialty IN " +
                            "('Pathologos', 'Dermatologos', 'Xeirourgos', 'Kardiologos', 'Pneumonologos')) " +
                            "AND(sid >= 1000) AND (sid <= 1999)))");
            stmt.executeUpdate(createDoctors);

            //Create Administrators  table
            String createAds = new String(
                    "CREATE TABLE Ads(" +
                            "sid varchar(10) NOT NULL, " +
                            "fname varchar(20) NOT NULL, " +
                            "lname varchar(30) NOT NULL, " +
                            "password varchar(255) NOT NULL, " +
                            "position varchar(21) NOT NULL, " +
                            "PRIMARY KEY(sid)," +
                            "CHECK " +
                            "((position IN " +
                            "('Dioikitis', 'Anaplirotis Dioikitis', 'Proedros', 'Antiproedros', 'Melos', 'Grammateas')) " +
                            "AND(sid >= 0) AND (sid <= 999)))");
            stmt.executeUpdate(createAds);

            //Create Nurses table
            String createNurses = new String(
                    "CREATE TABLE Nurses(" +
                            "sid varchar(10) NOT NULL, " +
                            "fname varchar(20) NOT NULL, " +
                            "lname varchar(30) NOT NULL, " +
                            "password varchar(255) NOT NULL, " +
                            "PRIMARY KEY(sid)," +
                            "CHECK (sid >= 2000))" );
            stmt.executeUpdate(createNurses);

            //Create Diagnosis table
            String createDiagnosis = new String(
                    "CREATE TABLE Diagnosis(" +
                            "did varchar(10) NOT NULL, " +
                            "disease varchar(15), " +
                            "PRIMARY KEY(did)," +
                            "CHECK " +
                            "(disease IN " +
                            "(null , 'Katagma' , 'Stithaxgi' , 'Gripi', 'Gastrenteritida', 'COVID')))");
            stmt.executeUpdate(createDiagnosis);

            //Create Medicines table
            String createMedicines = new String(
                    "CREATE TABLE Medicines(" +
                            "mid varchar(10) NOT NULL, " +
                            "name varchar(30) NOT NULL, " +
                            "type varchar(11) NOT NULL, " +
                            "content int NOT NULL, " +
                            "disease varchar(15) NOT NULL, " +
                            "PRIMARY KEY(mid), " +
                            "CHECK " +
                            "((disease IN " +
                            "('Katagma' , 'Stithaxgi' , 'Gripi', 'Gastrenteritida', 'COVID'))" +
                            "AND (type IN " +
                            "('Xapi','Enesimo','Aloifi','Eispneomeno', 'Siropi'))" +
                            "AND (content > 0)))");
            stmt.executeUpdate(createMedicines);

            //Create TEP table
            String createTEP = new String(
                    "CREATE TABLE Tep(" +
                            "edate date NOT NULL, " +
                            "PRIMARY KEY(edate))" );
            stmt.executeUpdate(createTEP);

            //Create Checks table
            String createChecks = new String(
                    "CREATE TABLE Checks(" +
                            "cid varchar(10) NOT NULL, " +
                            "name varchar(17) NOT NULL, " +
                            "report varchar(255), " +
                            "stayIn boolean , " +
                            "nsid varchar(10) NOT NULL, " +
                            "dsid varchar(10) NOT NULL, " +
                            "edate date NOT NULL, " +
                            "amka char(10) NOT NULL, " +
                            "did varchar(10) NOT NULL, " +
                            "PRIMARY KEY(cid), " +
                            "FOREIGN KEY (nsid) REFERENCES Nurses(sid), " +
                            "FOREIGN KEY (dsid) REFERENCES Doctors(sid), " +
                            "FOREIGN KEY (did) REFERENCES Diagnosis(did), " +
                            "FOREIGN KEY (edate) REFERENCES Tep(edate), " +
                            "FOREIGN KEY (amka) REFERENCES Patients(amka), " +
                            "CHECK " +
                            "(name IN " +
                            "('Aktinografia' , 'Exetaseis aimatos' , 'COVID Test', 'Exetaseis ourwn' , 'Magnitiki' )))");
            stmt.executeUpdate(createChecks);

            //Create Record table
            String createRecord = new String(
                    "CREATE TABLE Record(" +
                            "edate date NOT NULL, " +
                            "cid varchar(10) NOT NULL, " +
                            "did varchar(10) NOT NULL, " +
                            "amka char(10) NOT NULL, " +
                            "FOREIGN KEY (amka) REFERENCES Patients(amka), " +
                            "FOREIGN KEY (edate) REFERENCES Tep(edate), " +
                            "FOREIGN KEY (cid) REFERENCES Checks(cid), " +
                            "FOREIGN KEY (did) REFERENCES Diagnosis(did)) ");
            stmt.executeUpdate(createRecord);


            //Create Visits table
            String createVisits = new String(
                    "CREATE TABLE Visits(" +
                            "amka char(10) NOT NULL, " +
                            "edate date NOT NULL, " +
                            "symptoms varchar(255) NOT NULL, " +
                            "PRIMARY KEY(amka,edate), " +
                            "FOREIGN KEY (amka) REFERENCES Patients(amka), " +
                            "FOREIGN KEY (edate) REFERENCES Tep(edate))" );
            stmt.executeUpdate(createVisits);

            //Create Doctors On Duty table
            String createDoctorsOnDuty = new String(
                    "CREATE TABLE DoctorsOnDuty(" +
                            "sid varchar(10) NOT NULL, " +
                            "edate date NOT NULL, " +
                            "department varchar(14) NOT NULL, " +
                            "PRIMARY KEY(sid,edate), " +
                            "FOREIGN KEY (sid) REFERENCES Doctors(sid), " +
                            "FOREIGN KEY (edate) REFERENCES Tep(edate), " +
                            "CHECK " +
                            "(department IN " +
                            "('Pathologiko' , 'Dermatologiko' , 'Xeirourgiko' , 'Kardiologiko' , 'Pneumonologiko')))");
            stmt.executeUpdate(createDoctorsOnDuty);

            //Create Nurses On Duty table
            String createNursesOnDuty = new String(
                    "CREATE TABLE NursesOnDuty(" +
                            "sid varchar(10) NOT NULL, " +
                            "edate date NOT NULL, " +
                            "department varchar(14) NOT NULL, " +
                            "PRIMARY KEY(sid,edate), " +
                            "FOREIGN KEY (sid) REFERENCES Nurses(sid), " +
                            "FOREIGN KEY (edate) REFERENCES Tep(edate)," +
                            "CHECK " +
                            "(department IN " +
                            "('Ypodoxi' , 'Pathologiko' , 'Dermatologiko' , 'Xeirourgiko' , 'Kardiologiko' , 'Pneumonologiko')))");
            stmt.executeUpdate(createNursesOnDuty);

            //Create Defines table
            String createDefines = new String(
                    "CREATE TABLE Defines(" +
                            "did varchar(10) NOT NULL, " +
                            "mid varchar(10), " +
                            "sid varchar(10) NOT NULL, " +
                            "PRIMARY KEY(did), " +
                            "FOREIGN KEY (did) REFERENCES Diagnosis(did), " +
                            "FOREIGN KEY (sid) REFERENCES Doctors(sid), " +
                            "FOREIGN KEY (mid) REFERENCES Medicines(mid))");
            stmt.executeUpdate(createDefines);

            //Create Examines table
            String createExamines = new String(
                    "CREATE TABLE Examines(" +
                            "amka char(10) NOT NULL, " +
                            "sid varchar(10) NOT NULL, " +
                            "edate date NOT NULL, " +
                            "PRIMARY KEY(amka, sid, edate), " +
                            "FOREIGN KEY (edate) REFERENCES Tep(edate)," +
                            "FOREIGN KEY (amka) REFERENCES Patients(amka), " +
                            "FOREIGN KEY (sid) REFERENCES Doctors(sid)) "  );
            stmt.executeUpdate(createExamines);


        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Create Tables COMPLETE");
        }
    }

    public static Connection getConnection() throws Exception{
        try{
            //Useful variables
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306";
            String databasename = "tepdatabase";
            String username = "root";
            String password = "";

            //Create connection with localhost
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url,username,password);
            System.out.println("Connected");

            //Create database in localhost
            String createDatabase = new String("CREATE DATABASE "+databasename);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createDatabase);
            System.out.println("Created");

            //Update connection's url with the databasename
            conn = DriverManager.getConnection(url+"/"+databasename +"?characterEncoding=UTF-8",username,password);
            System.out.println("URL Updated");

            return conn;
        }catch (Exception e){
            System.out.println(e);
        }

        return null;
    }
}
