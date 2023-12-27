
package database.operations;

import java.io.*;
import java.io.InputStreamReader;
import java.util.*;
import java.sql.*;

/**
 *
 * @author Sandesh
 */
public class Bank {

    Scanner sc = new Scanner(System.in);
    int id, tempid, ph, bal, depo, withd, choice, pass;
    String name;

    void menu()throws IOException {
        do {
            System.out.println("\n\t\t=================\tB A N K\t=================");
            System.out.println("\n\t\t1. Sign up to create new Account for new user ");
            System.out.println("\t\t2. Login if you are registered a Customer");
            System.out.println("\t\t3. Enter 3 to Exit ");
            try {

                try {

                    System.out.print("\n\t\tEnter your C H O I C E : ");
                    InputStreamReader in = new InputStreamReader(System.in);
                    BufferedReader br = new BufferedReader(in);

                    choice = Integer.parseInt(br.readLine());
                } catch (NumberFormatException e) {
                    System.out.println("please put valid number"+e);
                    menu();
                }

                switch (choice) {
                    case 1:
                        Signin();
                        break;

                    case 2:
                        LoginMenu();
                        break;

                    case 3:
                        break;

                    default:
                        try {
                            if (choice > 3) {
                                throw new BankException("Wrong Input!!");
                            }
                        } catch (BankException e) {
                            System.out.println("\n\t\tPlease Enter Correct Input" + e);
                        }
                }
            } catch (Exception e) {
                System.out.println(e);

            }

        } while (choice != 3);
    }

    void Signin() {
        System.out.print("Enter your name : ");
        name = sc.next();

        System.out.print("Enter your phone number : ");
        ph = sc.nextInt();

        System.out.print("Enter password : ");
        pass = sc.nextInt();

        for (int i = 0; i < 1;) {
            System.out.print("Enter the amount you wish to deposit to start your account<min = 200, max = 2000>: ");
            bal = sc.nextInt();
            if (bal < 200 || bal > 2000) {
                System.out.println("Invalid Input");
            } else {
                break;
            }
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");

            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "1234")) {
                Statement stmt = con.createStatement();
                
                ResultSet rs = stmt.executeQuery("select * from Bank");
                
                while (rs.next()) {
                    id = rs.getInt(1);
                    
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");

            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "1234")) {
                PreparedStatement prst = con.prepareStatement("insert into bank(cust_id, cust_name, cust_ph, cust_password, cust_bal) values(?,?,?,?,?)");
                System.out.println("ID : " + id);
                id++;
                prst.setInt(1, id);
                prst.setString(2, name);
                prst.setInt(3, ph);
                prst.setInt(4, pass);
                prst.setInt(5, bal);
                
                prst.executeUpdate();
                System.out.println("Your Account Has Been Created!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    void LoginMenu() throws IOException{
        do {
            System.out.println("");
            System.out.println("\n\t\t=================\tPersonal Account\t=================");
            System.out.println("\n\t\t1. Deposit");
            System.out.println("\t\t2. Withdrawal");
            System.out.println("\t\t3. Check Balance");
            System.out.println("\t\t4. Update Personal Information");
            System.out.println("\t\t5. Delete Account");
            System.out.println("\t\t6. EXIT");

            System.out.print("\n\t\tEnter your C H O I C E : ");
            try {
                InputStreamReader in = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(in);
                choice = Integer.parseInt(br.readLine());
            } 
            catch (IOException | NumberFormatException e) {
                System.out.println("Wrong Input PLease Try again.."+e);
                break;
            }

            switch (choice) {
                case 1:
                    Deposit();
                    break;

                case 2:
                    Withdraw();
                    break;

                case 3:
                    Balance();
                    break;

                case 4:
                    Update();
                    break;
                    
                case 5 :
                    Delete();
                    break;
                    
                case 6:
                    break;

                default:
                    try {
                        if (choice > 6) {
                            throw new BankException("Wrong Input!!");
                        }
                    } catch (BankException e) {
                        System.out.println("\n\t\tPlease Enter Correct Input" + e);
                    }
            }

        } while (choice != 6);
    }

    void Deposit() {
        int flag =0;
        System.out.print("Enter your username: ");
        name = sc.next();

        System.out.print("Enter your password: ");
        pass = sc.nextInt();

        System.out.print("Enter the amount you wish to deposit: ");
        depo = sc.nextInt();

        try{ 
            if(depo < 200 || depo > 2000) {
                throw new DepositException("Amount to deposit must be between 200-2000");
            } 
            else {
                try {
                    Class.forName("com.mysql.jdbc.Driver");

                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "1234")) {
                        Statement stmt = con.createStatement();

                        ResultSet rs = stmt.executeQuery("select * from Bank ");

                        while (rs.next()) {
                            if (name.equals(rs.getString(2)) && pass == rs.getInt(4)) {
                                id = rs.getInt(1);
                                bal = rs.getInt(5);
                                bal = bal + depo;
                                PreparedStatement prst = con.prepareStatement("update Bank set cust_bal = " + bal + " where cust_id = " + id);
                                prst.executeUpdate();
                                System.out.println("System Updated");
                                flag=0;
                                break;
                            }
                            else
                            {
                                flag = 1;
                            }
                        }
                        if(flag ==1)
                        {
                            throw new DepositException("Please Enter Correct Name /Password..");
                        }
                    }
                } 
                catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e);
                    ForgotPassword();
                }   
            }
        }
        catch(DepositException e)
        {
            System.out.println(e);
        }
    }

    void Withdraw() {
        int flag =0;
        System.out.print("Enter your username: ");
        name = sc.next();

        System.out.print("Enter your password: ");
        pass = sc.nextInt();

        System.out.print("Enter the amount you wish to withdraw: ");
        withd = sc.nextInt();

        try {
            Class.forName("com.mysql.jdbc.Driver");

            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "1234")) {
                Statement stmt = con.createStatement();

                ResultSet rs = stmt.executeQuery("select * from Bank ");

                while (rs.next()) {
                    if (name.equals(rs.getString(2)) && pass == rs.getInt(4)) {
                        id = rs.getInt(1);
                        bal = rs.getInt(5);
                        if (withd > bal || withd>2000) {
                            throw new WithdrawException("Amount to withdraw must be between 0-2000 and should not be less than Balance amount");
                        }
                        bal = bal - withd;
                        PreparedStatement prst = con.prepareStatement("update Bank set cust_bal = " + bal + " where cust_id = " + id);
                        prst.executeUpdate();
                        System.out.println("System Updated");
                        flag=0;
                        break;
                    }
                    else
                    {
                        flag=1;
                    }
                }
                if(flag ==1)
                {
                    throw new WithdrawException("Please Enter Correct Name /Password..");
                }
                
            }
        } catch (ClassNotFoundException | SQLException  | WithdrawException e) {
            System.out.println(e);
            ForgotPassword();
        }
    }

    void Balance() {
        System.out.print("Enter your username: ");
        name = sc.next();

        System.out.print("Enter your password: ");
        pass = sc.nextInt();

        try {
            Class.forName("com.mysql.jdbc.Driver");

            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "1234")) {
                Statement stmt = con.createStatement();

                ResultSet rs = stmt.executeQuery("select * from Bank ");

                while (rs.next()) {
                    if (name.equals(rs.getString(2)) && pass == rs.getInt(4)) {
                        bal = rs.getInt(5);
                        System.out.println("Balance = " + bal);
                    }
                }
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            ForgotPassword();
        }
    }
    
    void Update()throws IOException
   {
        int choose = 0;
        
        do{
            System.out.println("==================\tEdit User Profile\t==================");
            System.out.println("1. Update your Name ");
            System.out.println("2. Update your Phone Number ");
            System.out.println("3. Update your Password ");
            System.out.println("4. Exit ");
        try {

                try {

                    System.out.print("\n\t\tEnter your C H O I C E : ");
                    InputStreamReader in = new InputStreamReader(System.in);
                    BufferedReader br = new BufferedReader(in);

                    choose = Integer.parseInt(br.readLine());
                } 
                catch (NumberFormatException e) {
                    System.out.println("please put valid number"+e);
                    menu();
                }

                switch (choose) {
                    case 1:
                        Updatename();
                        break;

                    case 2:
                        Updateph();
                        break;

                    case 3:
                        Updatepass();
                        break;

                    case 4:
                        break;
                        
                    default:
                        try {
                            if (choice > 4) {
                                throw new UpdateMenuException ("Wrong Input!!");
                            }
                        } catch (UpdateMenuException  e) {
                            System.out.println("\n\t\tPlease Enter Correct Input" + e);
                        }
                }
            } catch (Exception e) {
                System.out.println(e);

            }

        } while (choose != 4);
    }

    void Updatename(){
        int flag =0;
        String name1;
        
        System.out.print("Enter your username: ");
        name = sc.next();

        System.out.print("Enter your password: ");
        pass = sc.nextInt();
        
        
         try {
                    Class.forName("com.mysql.jdbc.Driver");

                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "1234")) {
                        Statement stmt = con.createStatement();
 
                        ResultSet rs = stmt.executeQuery("select * from Bank ");
                        
                        while (rs.next()) {
                            if (name.equals(rs.getString(2)) && pass == rs.getInt(4)) {
                                id = rs.getInt(1);
                                System.out.print("Enter update name: ");
                                name1 = sc.next();
                                PreparedStatement prst = con.prepareStatement("update Bank set cust_name = '" + name1 + "' where cust_id = " + id);
                                prst.executeUpdate();
                                System.out.println("System Updated");
                                flag=0;
                                break;
                            }
                            else
                            {
                                flag = 1;
                            }
                        }
                        if(flag ==1)
                        {
                            throw new UpdateNameException("Please Enter Correct Name /Password..");
                        }
                    }
                } 
                catch (ClassNotFoundException | SQLException |UpdateNameException e ) {
                    System.out.println(e);
                    ForgotPassword();
                }   
    }
    
    void Updateph(){
        int flag =0, ph1;
        
        System.out.print("Enter your username: ");
        name = sc.next();

        System.out.print("Enter your password: ");
        pass = sc.nextInt();
        
         try {
                    Class.forName("com.mysql.jdbc.Driver");

                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "1234")) {
                        Statement stmt = con.createStatement();
 
                        ResultSet rs = stmt.executeQuery("select * from Bank ");

                        while (rs.next()) {
                            if (name.equals(rs.getString(2)) && pass == rs.getInt(4)) {
                                id = rs.getInt(1);
                                System.out.print("Enter update Phone Number : ");
                                ph1 = sc.nextInt();
                                PreparedStatement prst = con.prepareStatement("update Bank set cust_ph = " + ph1 + " where cust_id = " + id);
                                prst.executeUpdate();
                                System.out.println("System Updated");
                                flag=0;
                                break;
                            }
                            else
                            {
                                flag = 1;
                            }
                        }
                        if(flag ==1)
                        {
                            throw new UpdatePhException("Please Enter Correct Name /Password..");
                        }
                    }
                } 
                catch (ClassNotFoundException | SQLException |UpdatePhException e ) {
                    System.out.println(e);
                    ForgotPassword();
                }
    }
    
    void Updatepass(){
        int flag =0, pass1;
        
        System.out.print("Enter your username: ");
        name = sc.next();

        System.out.print("Enter your password: ");
        pass = sc.nextInt();
        
         try {
                    Class.forName("com.mysql.jdbc.Driver");

                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "1234")) {
                        Statement stmt = con.createStatement();
 
                        ResultSet rs = stmt.executeQuery("select * from Bank ");

                        while (rs.next()) {
                            if (name.equals(rs.getString(2)) && pass == rs.getInt(4)) {
                                id = rs.getInt(1);
                                System.out.print("Enter update Password : ");
                                pass1 = sc.nextInt();
                                PreparedStatement prst = con.prepareStatement("update Bank set cust_password = " + pass1 + " where cust_id = " + id);
                                prst.executeUpdate();
                                System.out.println("System Updated");
                                flag=0;
                                break;
                            }
                            else
                            {
                                flag = 1;
                            }
                        }
                        if(flag ==1)
                        {
                            throw new UpdatePassException("Please Enter Correct Name /Password..");
                        }
                    }
                } 
                catch (ClassNotFoundException | SQLException |UpdatePassException e ) {
                    System.out.println(e);
                    ForgotPassword();
                }
    }
    
    void Delete(){
         int flag =0;
        
        System.out.print("Enter your username: ");
        name = sc.next();

        System.out.print("Enter your password: ");
        pass = sc.nextInt();
        
         try {
                    Class.forName("com.mysql.jdbc.Driver");

                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "1234")) {
                        Statement stmt = con.createStatement();
 
                        ResultSet rs = stmt.executeQuery("select * from Bank ");

                        while (rs.next()) {
                            if (name.equals(rs.getString(2)) && pass == rs.getInt(4)) {
                                if(rs.getInt(5)==0){
                                    id = rs.getInt(1);    
                                    PreparedStatement prst = con.prepareStatement("delete from Bank where cust_id = " + id);
                                    prst.executeUpdate();
                                    System.out.println("System Updated");
                                    flag=0;
                                    break;
                                }
                                else{
                                    System.out.println("Please withdraw all your money First..");
                                    break;
                                }
                            }
                            else
                            {
                                flag = 1;
                            }
                        }
                        if(flag ==1)
                        {
                            throw new DeleteException("Please Enter Correct Name /Password..");
                        }
                    }
                } 
                catch (ClassNotFoundException | SQLException |DeleteException e ) {
                    System.out.println(e);
                    ForgotPassword();
                }
    }
    
    void ForgotPassword(){
        String forg;
        
        System.out.print("Enter any of the first letters that ypu remember of your name : ");
        forg = sc.next();
        
         try {
                    Class.forName("com.mysql.jdbc.Driver");

                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "1234")) {
                        Statement stmt = con.createStatement();
 
                        ResultSet rs = stmt.executeQuery("select *from Bank where cust_name like'"+forg+"%'");
                        int i=0;
                                
                        while (rs.next()) {
                            name = rs.getString(2);
                            System.out.println("Name : "+1+ " " +name);
                            i++;
                        }
                            
                    }  
                } 
                catch (ClassNotFoundException | SQLException e ) {
                    System.out.println(e);
                }   
    
    }
        
    public static void main(String[] args) throws IOException {
        Bank b = new Bank();

        b.menu();
    }
}
