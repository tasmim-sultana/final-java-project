package com.jetbrains;
import java.util.Scanner;
import java.sql.*;
import java.io.Console;

public class Main {
    void showData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3308/facebook";
            String userName = "root";
            String password = "";
            Connection connection = DriverManager.getConnection(url, userName, password);
            if (connection.isClosed()) {
                System.out.printf("Not connected");
            } else {
                System.out.println("connected");
            }

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT firstname,lastname,login.email,dateofbirth FROM usertable,login WHERE usertable.userid=login.luserid AND usertable.password=login.pass AND usertable.email=login.email");
            if(resultSet.next()) {
                System.out.println("-----------WELCOME TO FACEBOOK---------------");
                System.out.println("----------YOUR PROFILE INFORMATION-----------");
                System.out.println("Firstname-----lastname----email-----date of birth-----");
                System.out.println(resultSet.getString("firstname") + "    " + resultSet.getString("lastname") + "    " + resultSet.getString("login.email") + "    " + resultSet.getString("dateofbirth"));
            }
            else
            {
                System.out.println("Incorrect email or password! ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void userinsertData() throws SQLException,ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3308/facebook";
        String user = "root";
        String password = "";

        Connection myConn = null;
        PreparedStatement myStmt = null;

        Scanner scanner = null;

        try {
            scanner = new Scanner(System.in);
            System.out.println("-------------SIGN UP TO FACEBOOK-------------------");

            System.out.print("Enter user id: ");

            int userid=scanner.nextInt();
            String s=scanner.nextLine();

            System.out.print("Enter your first name: ");
            String firstname = scanner.nextLine();

            System.out.print("Enter your last name: ");
            String lastname = scanner.nextLine();

            System.out.println("Enter your email: ");
            String email = scanner.nextLine();
            System.out.println("Enter Date of birth:");
            String dateofbirth = scanner.nextLine();

           System.out.println("Enter password: ");
            String pass=scanner.nextLine();
            myConn = DriverManager.getConnection(url, user, password);

            String sql = "insert into usertable "
                    + " (userid,firstname,lastname,email,dateofbirth,password)" + " values ( ?, ?, ?, ?,?,?)";

            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1,userid);
            myStmt.setString(2, firstname);
            myStmt.setString(3, lastname);
            myStmt.setString(4, email);
            myStmt.setString(5, dateofbirth);
            myStmt.setString(6,pass);

            // 3. Execute SQL query
            myStmt.executeUpdate();

            System.out.println("-------SIGN UP COMPLETED------------");
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }

            if (scanner != null) {
                scanner.close();
            }
        }
    }


    void logininsertData() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3308/facebook";
        String user = "root";
        String password = "";

        Connection myConn = null;
        PreparedStatement myStmt = null;

        Scanner scanner = null;

        try {
            scanner = new Scanner(System.in);
            System.out.println("---------LOG IN TO FACEBOOK------------");


            System.out.print("Enter your email: ");
            String email= scanner.nextLine();

            System.out.print("Enter your password: ");
            String pass = scanner.nextLine();
            System.out.print("Enter  your id: ");
            int id = scanner.nextInt();
            myConn = DriverManager.getConnection(url, user, password);

            String sql = "insert into login"
                    + " (email,pass,luserid)" + " values (?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1,email);
            myStmt.setString(2, pass);
            myStmt.setInt(3, id);

            myStmt.executeUpdate();
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }

            if (scanner != null) {
                scanner.close();
            }
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner sn = new Scanner(System.in);
            System.out.println("1.Sign up");
            System.out.println("2.Log in");
            System.out.println("3.Exit");
            Main obj=new Main();

            System.out.println("Enter any option: ");
            int n = sn.nextInt();
            if (n == 1) {
                obj.userinsertData();
            }
             if (n == 2) {
                obj.logininsertData();
                obj.showData();
            }
    }
}


