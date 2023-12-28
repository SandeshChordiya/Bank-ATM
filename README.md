Bank's Basic Functions Program

This Java program simulates a basic banking system with various functionalities like account creation, deposit, withdrawal, balance check, account update, and deletion using MySQL as the database. It allows users to interact with their accounts by providing options through a console-based menu system.
Features

    Account Management:
        Sign up to create a new account for a new user.
        Login for registered customers.
        Deposit and Withdrawal options.
        Checking account balance.
        Updating personal information (name, phone number, password).
        Deleting an account.

Requirements

    Java Development Kit (JDK): Ensure Java is installed on your system.
    MySQL Database: You need MySQL installed to run the program and have a database named "study" with a table named "Bank" for storing user information.

Usage

    Setup Database:
        Create a MySQL database named "study".
        Create a table named "Bank" with appropriate columns (cust_id, cust_name, cust_ph, cust_password, cust_bal).
        Update the JDBC connection URL in the code (getConnection method) to match your MySQL server configuration.

    Run the Program:
        Compile and run the Bank.java file using a Java IDE or command line.
        Follow the console-based menu prompts to perform banking operations.

    Functionality Details:
        Sign up to create an account by providing required details.
        Login with registered credentials to access account functionalities.
        Perform deposit, withdrawal, and check balance operations.
        Update personal information or delete the account if needed.

File Structure

    Bank.java: Main program file containing the core functionalities.
    BankException.java: Custom exception class for handling bank-related exceptions.
    DepositException.java: Custom exception class for handling deposit-related exceptions.
    WithdrawException.java: Custom exception class for handling withdrawal-related exceptions.
    UpdateNameException.java: Custom exception class for handling name update-related exceptions.
    UpdatePhException.java: Custom exception class for handling phone number update-related exceptions.
    UpdatePassException.java: Custom exception class for handling password update-related exceptions.
    DeleteException.java: Custom exception class for handling account deletion-related exceptions.

Contributing

Contributions to improve or expand functionalities are welcome! Feel free to fork the repository, make changes, and create pull requests.
License

This project is licensed under the MIT License.
