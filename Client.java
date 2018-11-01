package com.capgemini.obs.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import sun.swing.BakedArrayList;

import com.capgemini.obs.bean.AccountMasterBean;
import com.capgemini.obs.bean.CustomerBean;
import com.capgemini.obs.bean.FundTransferBean;
import com.capgemini.obs.bean.PayeeBean;
import com.capgemini.obs.bean.ServiceTrackerBean;
import com.capgemini.obs.bean.StatementBean;
import com.capgemini.obs.bean.TransactionsBean;
import com.capgemini.obs.bean.UserBean;
import com.capgemini.obs.exceptions.BankException;
import com.capgemini.obs.service.AccountMasterService;
import com.capgemini.obs.service.CustomerService;
import com.capgemini.obs.service.FundTransferService;
import com.capgemini.obs.service.IAccountMasterService;
import com.capgemini.obs.service.ICustomerService;
import com.capgemini.obs.service.IPayeeService;
import com.capgemini.obs.service.IUserService;
import com.capgemini.obs.service.PayeeService;
import com.capgemini.obs.service.ServiceTracker;
import com.capgemini.obs.service.StatementServiceImpl;
import com.capgemini.obs.service.TransactionService;
import com.capgemini.obs.service.UserService;

public final class Client {

static Scanner sc = new Scanner(System.in);

static Logger logger = Logger.getRootLogger();
static UserBean loginBean1 = null;
static TransactionsBean transactionsBean = null;
static TransactionsBean data=null;
static CustomerBean customerBean = null;
static ServiceTrackerBean serviceTrackerBean = null;
static FundTransferBean fundBean = null;
static FundTransferBean fundTransferBean = null;
static UserBean userBean = null;
static UserBean userBean1 = null;
static UserBean userBean3 = null;
static UserBean selectAcc = null;
static UserBean firstAccBean = null;
static List<UserBean> multipleAccList = null;
static UserBean existingUserBean = null;
static UserBean userForgotPass = null;
static UserBean loginBean = null;
static UserBean account=null;
static AccountMasterBean accountMasterBean = null;
static IUserService userService = null;
static UserService userServiceImpl = null;
static FundTransferService fundTransferService = null;
static ICustomerService CustomerService = null;
static ServiceTracker trackRequestService = null;
static ServiceTracker reqChequeService = null;
static ICustomerService updateAddress = null;
static CustomerService CustomerServiceImpl = null;
static IAccountMasterService accountMasterService = null;
static PayeeBean payee = new PayeeBean();
static IPayeeService payeeService= null;
static PayeeBean addPayee = new PayeeBean();
static FundTransferBean fundBean2 = null;
static AccountMasterBean accountDetails = new AccountMasterBean();
static TransactionsBean transactionbean = null;
static TransactionService transactionService = null;

public static void main(String[] args) {
	PropertyConfigurator.configure("resource//log4j.properties");

	long accId = 0;
	int customerId = 0;
	int choice = 0;
	int choose = 0;
	char opt = 0;
	char ch = 0;
	char option;
	int flag = 0;
	int fund_option = 0;
	UserBean auth = null;
	String bullet= "\u2022";
	
loop1: do {
	// show menu

	System.out.println();
	System.out.println();
	System.out
			.println("*********** Welcome To Bank Application ************\n");
	System.out
			.println("                   LOGIN                          \n");

	loop17:	while(true){
	System.out.println("1. Customer ");
	System.out.println("2. Bank Admin");

		try{
		choice = sc.nextInt();
		break;
		}catch(InputMismatchException im){			
			System.err.println("Invalid option! Please choose from the given options.\n");
			sc.nextLine();
			continue loop17;
		}
	  }
	switch (choice) {
		case 1:
			// method for new Page
			loginBean = null;

loop2:      while (flag < 3) {
		    flag++;
			while (loginBean == null) {
				loginBean = enterUser();
			}
			try {
				userService = new UserService();

				// fetch arraylist of multiple accounts here
				userService = new UserService();
				try {
					List<UserBean> multipleAccList = new ArrayList<UserBean>();
					multipleAccList = UserService.getMultipleAccounts(loginBean);

					if (multipleAccList != null) {
					
						firstAccBean = new UserBean();
						firstAccBean = multipleAccList.get(0);
					} 
				} catch (BankException e) {
					System.err.println("Error  :" + e.getMessage());
				}
										
				//For first item in the list get bean 
	
			if (firstAccBean == null) {	
				System.out.println("---------------------------------");
				System.out.println("Please Enter Valid Credentials!!");
				System.out.println("---------------------------------");

				System.out.println("Forgot Password?y/n");
				boolean validOption = true;
				char ans = ' ';
				do{
			    	 ans = sc.next().charAt(0);
			    	if(ans == 'Y' || ans == 'N'  || ans == 'y'  || ans == 'n' )
			    	{
			    		validOption =true;
			    	}
			    	else
			    	{
			    		System.out.println("Please provide Y or N or y or n");
			    		validOption = false;
			    	}
				}while(!validOption);
                 
				if (ans == 'y' || ans == 'Y') {
					loginBean1 = forgotPassword();
					System.out.println(loginBean1.getSecretAnswer());
					userForgotPass = userService.forgotPassword(loginBean1);
					logger.info("Forgot Password option chosen.");
					try {
						System.out.println(userForgotPass.getSecretAnswer());
						System.out.println(userForgotPass.toString());
					if (loginBean1.getSecretQuestion().equals(userForgotPass.getSecretQuestion())
						&& loginBean1.getSecretAnswer().equalsIgnoreCase(userForgotPass.getSecretAnswer())) {

					System.out.println("\nSet your Login Password with specifications given below.\n");
loop15:             while(true){
					System.out.println("** A digit must occur at least once");
					System.out.println("** An alphabet must occur at least once");
					System.out.println("** A special character must occur at least once");
					System.out.println("** At least 6 characters\n");
					System.out.println("Enter your new Password: ");
					userForgotPass.setLoginPassword(sc.next());

					try {
						//if (userService.validateUserCredentials(userForgotPass)) {	
							if (userService.validateLoginCredentials(userForgotPass)) {	
							
							break;
						}
					} catch (BankException bankexception) {
						logger.error("exception occured while setting login password", bankexception);
						System.err.println("Please set your password with below specifications!");	
						continue loop15;
					}
					}
					// sending to user service layer
					userService.setNewLoginPassword(userForgotPass, userForgotPass.getLoginPassword());
					System.out.println("\nYour Login Password is set successfully. Please login again.");
					logger.info("New Login Password is set successfully.");
					System.out.println("-----------------------------------------------------\n");
					userForgotPass = null;
					flag = 0;
					loginBean1 = null;
					loginBean = null;
					continue loop2;

					} else {
						System.out.println("Wrong Verification Details!");
					}
				} catch (BankException bankException) {
					bankException.getMessage();
				}
				} else {

				if (flag >= 3) {
					System.out.println("\nYou have exceeded your 3 attempts. Please try logging in after 24 hours.");
					loginBean.setLockStatus("L");
					try {
						userService.setLock(loginBean, loginBean.getLockStatus());
						logger.info("User Id-"+ loginBean.getUserId()+ ": Account is Locked.");

					} catch (BankException e) {
						e.printStackTrace();
					}
					System.exit(0);
				} else {
					loginBean = null;
					continue loop2;
				}
			}

			loginBean = null;
			continue loop2;
						
			} else if (firstAccBean.getUserId() == loginBean.getUserId()
					&& firstAccBean.getLoginPassword().equals(loginBean.getLoginPassword())) {
				logger.info("Logged in successfully");
				break loop2;
			}
		} catch (BankException e) {
			e.printStackTrace();
		}
		}
		logger.info("User Id-" + firstAccBean.getUserId()
				+ ": User entered into the system Account.");

		ICustomerService welcome = new CustomerService();
		String customerName = "";
		try {
			 customerName = welcome.getUserName(firstAccBean.getAccountId());
		} catch (BankException e2) {
			e2.printStackTrace();
		}
		
		System.out.println("\n**************Welcome "+ customerName + "!****************\n");
		
		try {
			//fetch arraylist of multiple accounts here
			userService = new UserService();
			
			List<UserBean> multipleAccList = new ArrayList<UserBean>(); 
			multipleAccList = UserService.getMultipleAccounts(loginBean);
						
			if (multipleAccList != null) {
				Iterator<UserBean> itr = multipleAccList.iterator();
				
				if (multipleAccList.size() > 1) {
					System.out.println("Following Accounts are registered on your ID.\n");
					while (itr.hasNext()) {
						account = itr.next();
						System.out.println("\t# "
								+ account.getAccountId() + "\n");
					}
				}

loop7: 	while (true) {
			if (multipleAccList.size() == 1) {
				for (UserBean account : multipleAccList) {
					auth = account;									
				}
				break;
			} else {
				System.out.println("Please choose an account Id from above to avail the services.");
				selectAcc = new UserBean();
				boolean validAccount = true;
				do{
				try{
				selectAcc.setAccountId(sc.nextLong());
				validAccount = true;
				}
				catch(InputMismatchException e)
				{
					System.out.println("Please enter the numeric value for Account id");
					sc.nextLine();
					validAccount = false;
				}
				}while(!validAccount);

				// Check if the entered account id is present in the retrieved list or not
			for (UserBean account : multipleAccList) {

				if (selectAcc.getAccountId() == account.getAccountId()) {
					auth = account;
					break loop7;
				}
			}// end of while for iterator
			System.out.println("Please enter valid account id only!");						
					}// end of while for selecting account id
				}
				} else {
					System.out.println("With given login Credentilas no such Account is available");
				}
				} catch (BankException e) {
					System.err.println("Error  :" + e.getMessage());
				}
			//Should ask to enter account id only if multiple accounts are present
			
loop3:      while (true) {
			System.out.println("\n----------------------- Menu -------------------------------------\n");
			System.out.println("a. View  Mini statement ");
			System.out.println("b. View Detailed statement ");
			System.out.println("c. Request for change in communication address for bank account");
			System.out.println("d. Request for change in email address for bank account");
			System.out.println("e. Request for cheque book ");
			System.out.println("f. Track  service request ");
			System.out.println("g. Fund Transfer ");
			System.out.println("h. Change password ");
			System.out.println("i. Logout ");
			System.out.println("\n------------------------------------------------------------------");
		    System.out.println("\nPlease enter your choice from the menu: ");

			option = sc.next().charAt(0);

			switch (option) {
			case 'a':
			case 'A':
	
				// Method for Mini statement
	
	    		StatementServiceImpl statementService = new StatementServiceImpl();
				StatementBean stmt = new StatementBean();
	
				stmt.setAccountId(auth.getAccountId());
				try {
					List<TransactionsBean> miniStatementList = new ArrayList<TransactionsBean>();
					miniStatementList = statementService.viewMiniStatement(stmt);
					TransactionsBean transaction = null;

				if (miniStatementList != null) {
					Iterator<TransactionsBean> itr = miniStatementList.iterator();

					int i = 1;
					System.out.println("\nThe transaction details are shown below.\n");

					String format = "| %-4s | %-14s | %-16s | %-19s | %-16s | %-18s |%n";
					System.out.format("+------+----------------+------------------+---------------------+------------------+--------------------+%n");
					System.out.format("| S.NO | TRANSACTION_ID | TRAN_DESCRIPTION | DATE OF TRANSACTION | TRANSACTION TYPE | TRANSACTION AMOUNT |%n");
					System.out.format("+------+----------------+------------------+---------------------+------------------+--------------------+%n");
					
					while (itr.hasNext()) {

						transaction = itr.next();
						transaction.print(format, i);
						i++;
					System.out.format("+--------------------------------------------------------------------------------------------------------+%n");
					}
					} else {
						System.out.println("No Transactions are available");
					}
						} catch (BankException e) {
							System.err.println("Error  :" + e.getMessage());
						}

						continue loop3;

			case 'b':
			case 'B':
				// Method for Detailed statement
				boolean onceMore = false;
	            do
	            {
	            onceMore = false;
				boolean valid = true;
				LocalDate startDate = null;
				LocalDate endDate = null;
				do {
				System.out.print("Enter start date [dd/MM/yyyy] : ");
				String sDate = sc.next();
				System.out.println("Enter end date [dd/MM/yyyy] : ");
				String eDate = sc.next();
	
				DateTimeFormatter formatter = DateTimeFormatter
						.ofPattern("dd/MM/yyyy");
				/*LocalDate startDate = LocalDate.parse(sDate, formatter);
				LocalDate endDate = LocalDate.parse(eDate, formatter);*/
				try {
					startDate = LocalDate.parse(sDate, formatter);
					endDate = LocalDate.parse(eDate, formatter);
					valid = true;
				} catch (DateTimeParseException e) {
					System.out
							.println("Please enter the dates in dd/MM/yyyy");
					valid = false;
				}

			} while (!valid);
	
				StatementServiceImpl statementService1 = new StatementServiceImpl();
				StatementBean stmt1 = new StatementBean();
				stmt1.setAccountId(auth.getAccountId());
				stmt1.setStartDate(startDate);
				stmt1.setEndDate(endDate);
				try {
					List<TransactionsBean> detailStatementList = new ArrayList<TransactionsBean>();
					detailStatementList = statementService1.viewDetailedStatement(stmt1);
					TransactionsBean transaction = null;
					TransactionsBean reference = new TransactionsBean();
					reference.setAccountId(120);
					
					if (detailStatementList != null) {
						Iterator<TransactionsBean> itr = detailStatementList.iterator();
							
						int i = 1;
						System.out.println("\nThe transaction details are shown below\n");
						String format = "| %-4s | %-14s | %-16s | %-19s | %-16s | %-18s |%n";
						while (itr.hasNext()) {

							transaction = itr.next();
							if (reference.getAccountId() != transaction.getAccountId()) {
								
								reference.setAccountId(transaction.getAccountId());
								System.out.println("Transactions for Account " + reference.getAccountId()+ "\n");
								
								System.out.format("+------+----------------+------------------+---------------------+------------------+--------------------+%n");
								System.out.format("| S.NO | TRANSACTION_ID | TRAN_DESCRIPTION | DATE OF TRANSACTION | TRANSACTION TYPE | TRANSACTION AMOUNT |%n");
								System.out.format("+------+----------------+------------------+---------------------+------------------+--------------------+%n");
							}
								transaction.print(format, i);
								i++;
								System.out.format("+--------------------------------------------------------------------------------------------------------+%n");
								}
							} else {
								System.out.println("No Transactions are available for given period");
							}
						}

						catch (BankException e) {
							System.err.println("Error  :" + e.getMessage());
							try {
								Thread.sleep(100);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
							
							onceMore = true;
						}
	            }while(onceMore);
						continue loop3;

			case 'c':
			case 'C':
				// Method for Request for change in communication
				// address
				int count = 0;
	            do
	            {
	            	onceMore = false;
				customerBean = new CustomerBean();
	
				System.out.println("Your current address is:");
				customerBean = getAddressDetails(auth.getAccountId());
				CustomerBean addressBean = new CustomerBean();
	
				if (customerBean != null) {
					System.out.println(customerBean.getAddress());
					System.out.println("\nPlease provide your new address details:");
					if(count ==0)
					{
					sc.nextLine();
					}
					String newAddress = sc.nextLine();
					
					addressBean.setAddress(newAddress);
					
					
				} else {
					System.err.println("There are no Address details associated with Account id "
									+ auth.getAccountId());
				}

				try {
					CustomerService customerService = new CustomerService();
					
					//customerBean = UpdateAddress();
					//CustomerService = new CustomerService();

					//updateAddress = new CustomerService();
					addressBean.setAccountId(auth.getAccountId());
					customerId = customerService.updateCustomerAddress(addressBean);
					if (customerId == 1) {
						System.out.println("Your address has been successfully updated");
					}
				} catch (BankException bankexception) {
					logger.error("exception occured", bankexception);
					System.err.println("ERROR : "+ bankexception.getMessage());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count++;
					onceMore = true;
				} finally {
					customerId = 0;
					customerBean = null;
				}
	            }while(onceMore);

				continue loop3;

			case 'd':
				// Method for Request for change in email
	
				customerBean = new CustomerBean();
	
				customerBean.setAccountId(auth.getAccountId());
				System.out.println("Your current email is:");
				customerBean = getEmailDetails(auth.getAccountId());
	
				if (customerBean != null) {
					System.out.println(customerBean.getEmail());
					System.out.println("\nPlease provide your new email details:");
	
				} else {
					System.err.println("There are no email details associated with Account id "+ auth.getAccountId());
				}

				try {
					customerBean = UpdateEmail();
					CustomerService = new CustomerService();

					updateAddress = new CustomerService();
					customerBean.setAccountId(auth.getAccountId());
					customerId = updateAddress.updateEmail(customerBean);
					if (customerId == 1) {
						System.out.println("Your email has been successfully updated.");
					}
				} catch (BankException bankexception) {
					logger.error("exception occured", bankexception);
					System.out.println("ERROR : "+ bankexception.getMessage());
				} finally {
					customerId = 0;
					customerBean = null;
				}

				continue loop3;

			case 'e':
				// Method for Request for cheque book
			    count = 0;
				do
				{
				onceMore = false;
				if(count == 0)
				{
				sc.nextLine();
				}
				ServiceTracker reqChequeService = new ServiceTracker();
				int requestId = 0;
				ServiceTrackerBean chequeRequest = new ServiceTrackerBean();

				System.out
						.println("Enter Description( Service description should contain atleast 3 letters):");
				String serviceDescription = sc.nextLine();
				chequeRequest.setServiceDescription(serviceDescription);
				chequeRequest.setAccountId(auth.getAccountId());
				chequeRequest.setServiceStatus("Open");
				try {
					requestId = reqChequeService
							.reqChequeBook(chequeRequest);
					System.out
							.println("Your request has been successful. Your Service Request id is "
									+ requestId);

				} catch (BankException e) {

					System.err.println("Error :" + e.getMessage());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					onceMore = true;
					count++;
				}
				}while(onceMore);
				continue loop3;

			case 'f':
				// Method for Track service request
loop18:			while(true){
				System.out.println("Select one choice");
				System.out.println("1. Track Request on basis of Service Request Number");
				System.out.println("2. Track Request on basis of Account Number");
				
				int choice3 = 0;
				boolean validChoice3 = true;
				do {
					try {
						sc = new Scanner(System.in);
						choice3 = sc.nextInt();
						validChoice3 = true;
					} catch (InputMismatchException e) {
						System.out.println("Enter only number");
						validChoice3 = false;
					}
				} while (!validChoice3);
				
				ServiceTracker trackRequestService = new ServiceTracker();
				ServiceTrackerBean serviceTracker = new ServiceTrackerBean();
				serviceTracker.setAccountId(auth.getAccountId());
				
				switch (choice3) {
				
				case 1:
					boolean validId = true;
					do{
						validId = true;
					System.out.println("Enter Service Request Number");
					int serviceRequestNumber = 0;
					boolean validrequest = true;
					do {
						try {
							sc = new Scanner(System.in);
							serviceRequestNumber = sc.nextInt();
							validrequest = true;
						} catch (InputMismatchException e) {
							System.out.println("Enter only number");
							validrequest = false;
						}
					} while (!validrequest);
					serviceTracker.setServiceId(serviceRequestNumber);
					ServiceTrackerBean request = null;
					try {
						request = trackRequestService
								.trackServiceRequest(serviceTracker);
						if (request != null) {
							int i = 1;
							String format = "| %-4s | %-10s | %-30s | %-10s | %-19s | %-14s |%n";
							System.out.format("+------+------------+--------------------------------+------------+---------------------+----------------+%n");
							System.out.format("| S.NO | SERVICE_ID |     SERVICE_DESCRIPTION        | ACCOUNT ID | SERVICE RAISED DATE | SERVICE STATUS |%n");
							System.out.format("+------+------------+--------------------------------+------------+---------------------+----------------+%n");
							request.print(format, i);
								i++;
							System.out.format("+--------------------------------------------------------------------------------------------------------+%n");
							} else {
							System.out.println("No record found with provided id for your account.\n");
							continue loop18;
						}
					} catch (BankException e) {
	                    
						System.err.println("Error  :" + e.getMessage());
						validId = false;
						try {
							Thread.sleep(100);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					}while(!validId);
	
					continue loop3;

				case 2:

					List<ServiceTrackerBean> list = new ArrayList<ServiceTrackerBean>();
					try {

						list = trackRequestService.trackAllRequest(serviceTracker);
						Iterator<ServiceTrackerBean> itr = list.iterator();
						if (!list.isEmpty()) {
							System.out.println("");

							int i = 1;
							String format = "| %-4s | %-10s | %-30s | %-10s | %-19s | %-14s |%n";
							System.out.format("+------+------------+--------------------------------+------------+---------------=-----+----------------+%n");
							System.out.format("| S.NO | SERVICE_ID |    SERVICE_DESCRIPTION         | ACCOUNT ID | SERVICE RAISED DATE | SERVICE STATUS |%n");
							System.out.format("+------+------------+--------------------------------+------------+---------------------+----------------+%n");
							
							while (itr.hasNext()) {
								itr.next().print(format, i);
								i++;
							System.out.format("+--------------------------------------------------------------------------------------------------------+%n");
							}
							} else {
							System.out.println("No service requests are found for the given account number");
						}
					} catch (BankException e) {

						System.err.println("Error  :" + e.getMessage());
					}
					continue loop3;
					
					default:
						System.out.println("Invalid Option! Please choose again.");
						continue loop18;					
				}

			//	continue loop3;
				}

		  case 'g':
		  case 'G':
			// Method for Fund Transfer
			  loop19:while(true){
					
				  loop20:while(true){
				  			try{			
				  			System.out.println("*********************ENJOY ONE CLICK TRANSFERS*********************");
				  			System.out.println("\n\t1. Transfer to your own bank account across India");
				  			System.out.println("\n\t2. Transfer to account of same bank account across India");			
				  			fund_option = sc.nextInt();
				  				}catch(InputMismatchException im){
				  					sc.nextLine();
				  					System.err.println("Enter the numeric value for choice.\n");
				  					continue loop20;
				  				}
				  			
				  loop16:switch (fund_option) {

				  				case 1:
				  					int transaction_done;
				  					int op = 0;
				  					while (fundBean == null) {
				  						fundBean = populateDetails(fundBean);
				  					}
				  					
				  					try {
				  						
				  						System.out.println("");
				  						accountMasterService = new AccountMasterService();
				  						
				  				loop22: do{
				  						boolean checkAccount = accountMasterService.checkAccountExists(auth.getAccountId());
				  						boolean checkPayee = accountMasterService.checkAccountExists(fundBean.getPayeeAccountId());
				  						
				  						userService = new UserService();

				  						try {
				  							List<UserBean> multipleAccList = new ArrayList<UserBean>();
				  							multipleAccList = UserService.getMultipleAccounts(auth);
				  							
				  							
				  							Iterator<UserBean> itr = multipleAccList.iterator();
				  							
				  							if (multipleAccList.size() > 1) {
				  								while (itr.hasNext()) {
				  									account = itr.next();
				  									if(fundBean.getPayeeAccountId() == account.getAccountId()){
				  										op = 1;
				  										break;
				  									}
				  								}
				  								if(op==0){
				  										System.out.println("\nGiven payee account id doesn't belong to you");
				  										System.out.println("\nPlease enter valid payee account");
				  										System.out.println("\nDo you want to continue?Please press 'Y' to continue..else 'N'");
				  										
				  								loop35:	while(true){
				  											choose = sc.next().charAt(0);
				  									
				  									if(choose == 'N' || choose == 'n'){
				  										fundBean = null;
				  										continue loop3;
				  									}
				  									else if(choose == 'Y' || choose == 'y'){
				  										
				  							loop42:		while(true){
				  										try{
				  											System.out.println("\nPlease"
				  													+ " enter payee account Id again");
				  											fundBean.setPayeeAccountId(sc.nextLong());
				  											break;
				  										}catch(InputMismatchException im){
				  											sc.nextLine();
				  											System.err
																	.println("\n\tPlease enter numeric value for Payee Account Id.");
				  											Thread.sleep(100);
				  											continue loop42;
				  										}
				  										
				  									}
				  										continue loop22;
				  									}
				  									else {
				  										System.out.println("Please choose either Y or N");
				  										continue loop35;
				  									}
				  								}
				  										
				  								}
				  							}
				  						} catch (BankException e) {
				  							System.err.println("Error  :" + e.getMessage());
				  						}
				  						
				  							if(auth.getAccountId() != fundBean.getPayeeAccountId()){
				  								if(checkAccount && checkPayee){
				  									
				  									System.out.println("\nEnter Nickname of To account");
				  									String nickname = sc.next();
				  									
				  									
				  									FundTransferBean  fundTransferBean = new FundTransferBean(auth.getAccountId(),fundBean.getPayeeAccountId(),fundBean.getTransferAmount());
				  									PayeeBean ownAccount = new PayeeBean(auth.getAccountId(),fundBean.getPayeeAccountId(),nickname);
				  									
				  									try {
				  										payeeService = new PayeeService();
				  										
				  										//CheckPayee() returns 1 if user's other account is not registered
				  										if(payeeService.checkPayee(ownAccount)==1){
				  											
				  											logger.info("User's other account is not registered");
				  											System.out.println("\n" + bullet + "PLEASE WAIT WHILE WE ARE REGISTERING YOUR ACCOUNT...");
				  											TimeUnit.SECONDS.sleep(1);
				  											
				  											addPayee = payeeService.addPayee(ownAccount);
				  											logger.info("Added as payee");
				  											
				  											System.out.println("\nHold On!!!Press 1 to Pay");
				  											int pay_option;
				  											
				  									loop23: while(true){
				  												try{
				  													pay_option = sc.nextInt();
				  													break;
				  												}catch(InputMismatchException im){
				  													sc.nextLine();
				  													System.err
				  															.println("\nPlease enter 1 to pay");
				  													continue loop23;
				  												}
				  											}
				  				
				  											
				  									loop24: do{
				  												try{
				  													if(pay_option == 1){
				  														System.out.println("\nEnter your Transaction Password");
				  														String tranPassword = sc.next();
				  														
				  														transaction_done = doTransactionOwnAccount(auth.getAccountId(),tranPassword,fundBean.getPayeeAccountId(),fundBean.getTransferAmount());
				  														if(transaction_done == 0){
				  															continue loop3;
				  														}	
				  														else{
				  															logger.info("Transaction done");
				  															fundBean = null;
				  															continue loop3;
				  														}
				  													}	
				  													else{
				  														System.err.println("Sorry!!You need to press 1 to pay");
				  														try{
				  															pay_option = sc.nextInt();
				  												
				  														}catch(InputMismatchException im){
				  															sc.nextLine();
				  															System.err
				  																	.println("\nPlease enter 1 to pay");
				  															continue loop24;
				  														}
				  														
				  													}
				  												}catch(BankException e){
				  													e.printStackTrace();
				  												}
				  												finally{
				  													fundBean = null;
				  												}
				  												
				  											}while(true);
				  											
				  											
				  										}else{
				  											
				  										loop27: do{
				  												/*CheckPayee() returns 0 if user's other account is registered */
				  												
				  												if(payeeService.checkPayee(ownAccount)==0){
				  													
				  													logger.info("Payee is already registered");
				  													System.out.println("\nHold On!!!Press 1 to Pay");
				  													int pay_option;
				  													
				  											loop25: while(true){
				  														try{
				  															pay_option = sc.nextInt();
				  															break;
				  														}catch(InputMismatchException im){
				  															sc.nextLine();
				  															System.err
				  																	.println("\nPlease enter 1 to pay");
				  															continue loop25;
				  														}
				  													}
				  													
				  													
				  											loop26:	do{
				  														if(pay_option == 1){
				  															System.out.println("\nEnter your Transaction Password");
				  															String tranPassword = sc.next();
				  															transaction_done = doTransactionOwnAccount(auth.getAccountId(),tranPassword,fundBean.getPayeeAccountId(),fundBean.getTransferAmount());
				  															if(transaction_done == 0)
				  																continue loop3;
				  															else{
				  																fundBean = null;
				  																continue loop3;
				  															}
				  															
				  														}	
				  														else{
				  															System.err.println("\nWarning: *Sorry!!You need to press 1 to pay");
				  															try{
				  																pay_option = sc.nextInt();
				  															}catch(InputMismatchException im){
				  																sc.nextLine();
				  																System.err
				  																		.println("\nPlease enter 1 to pay");
				  																continue loop26;
				  															}
				  														}
				  													}while(true);
				  													
				  												}
				  												
				  												else{
				  													logger.error("The payee is registered, but the nickname doesn't match with the payee");
				  													System.out.println("\nThe Nickname doesn't match with the given payee.");
				  													System.out.println("\nEnter nickname again");
				  													nickname = sc.next();
				  													ownAccount.setNickName(nickname);
				  													continue loop27;
				  												}
				  											}while(true);
				  											
				  											
				  										}
				  										
				  										
				  									} catch (BankException e) {
				  										
				  										e.printStackTrace();
				  									} catch (InterruptedException e) {
				  										logger.error("The thread is kept waited");
				  										e.printStackTrace();
				  									}
				  									
				  									break;
				  								}
				  								else{
				  									logger.error("No record in datbase with given account details");
				  									System.out.println("\nNo record found in database with these details..Please enter your details again");
				  									System.out.println("\tDo you want to continue?If yes..Press yes else No");
				  									while(true){
				  										choose = sc.next().charAt(0);
				  										
				  										if(choose == 'N' || choose == 'n'){
				  											fundBean = null;
				  											continue loop3;
				  										}	
				  										else{
				  											fundBean = null;
				  											fundBean = populateDetails(fundBean);
				  											continue loop22;
				  										}
				  									}
				  										
				  								}
				  				
				  							}
				  							else{
				  								logger.error("AccountId and PayeeAccountId cannot be equal");
				  								System.out.println("\n\tAccountId and payeeAccountId cannot be equal");
				  								System.out.println("\n\tTRY AGAIN.....\n");
				  								Thread.sleep(1000);
				  								fundBean = null;
				  								continue loop20;
				  							}
				  						}while(true);
				  						
				  						
				  					}catch(Exception e) {
				  						logger.error("Server Problem!!Please try again later");
				  						System.err.println("Server Problem!!Please try again later");
				  					}
				  				
				  					continue loop3;

				  				case 2:
				  					
				  					char continue_URN = 0;
				  					int op1 = 0;
				  					
				  					while (fundBean2 == null) {
				  						fundBean2 = populateDetails(fundBean2);

				  					}
				  					
				  					try{
				  						accountMasterService = new AccountMasterService();
				  					loop30: do{
				  							boolean checkAccount = accountMasterService.checkAccountExists(auth.getAccountId());
				  							boolean checkPayee = accountMasterService.checkAccountExists(fundBean2.getPayeeAccountId());
				  							
				  							userService = new UserService();

				  							try {
				  								List<UserBean> multipleAccList = new ArrayList<UserBean>();
				  								multipleAccList = UserService.getMultipleAccounts(auth);
				  								
				  								
				  								Iterator<UserBean> itr = multipleAccList.iterator();
				  								
				  								if (multipleAccList.size() > 1) {
				  									while (itr.hasNext()) {
				  										account = itr.next();
				  										if(fundBean2.getPayeeAccountId() == account.getAccountId()){
				  											op1 = 1;
				  											break;
				  										}
				  									}
				  									if(op1==1){
				  											System.out.println("\nGiven payee account id belongs to you");
				  											System.out.println("\nPlease enter valid payee account which doesn't belongs to you");
				  											System.out.println("\nDo you want to continue?Please press 'Y' to continue..else 'N'");
				  											op1 = 0;
				  									loop36:	while(true){
				  												choose = sc.next().charAt(0);
				  										
				  										if(choose == 'N' || choose == 'n'){
				  											fundBean2 = null;
				  											continue loop3;
				  										}
				  										else if(choose == 'Y' || choose == 'y'){
				  											System.out.println("\nPlease enter payee account Id again");
				  											fundBean2.setPayeeAccountId(sc.nextLong());
				  											continue loop30;
				  										}
				  										else {
				  											System.out.println("Please choose either Y or N");
				  											continue loop36;
				  										}
				  									}
				  											
				  									}
				  								}
				  							} catch (BankException e) {
				  								System.err.println("Error  :" + e.getMessage());
				  							}
				  								if(auth.getAccountId() != fundBean2.getPayeeAccountId()){
				  									if(checkAccount && checkPayee){
				  										
				  										System.out.println("\nEnter Payee Nickname");
				  										String nickname = sc.next();
				  										System.out.println("\nEnter transaction Password");
				  										String tranPwd = sc.next();
				  										
				  										
				  										PayeeBean payeeBean = new PayeeBean(auth.getAccountId(),fundBean2.getPayeeAccountId(),nickname);
				  										
				  										try {
				  											
				  										payeeService = new PayeeService();
				  										
				  										
				  										/*CheckPayee() returns 1 if payee is not registered*/
				  										if(payeeService.checkPayee(payeeBean)==1){
				  											
				  												System.out.println("\n\t PAYEE IS NOT REGISTERED!!");
				  												System.out.println("\t Press 1 to ADD PAYEE");
				  												int addPayee;
				  										loop31: while(true){
				  													try{
				  														addPayee = sc.nextInt();
				  														break;
				  													}catch(InputMismatchException im){
				  														sc.nextLine();
				  														System.err
				  																.println("Please press 1 to pay");
				  														continue loop31;
				  													}
				  												}
				  												
				  												String Dummy_URN = "abc345";
				  												String user_URN = null;
				  												
				  												do{
				  													if(addPayee == 1) {
				  														
				  														System.out.println("\n Your URN is "+Dummy_URN);
				  														System.out.println("To confirm payee registrartion enter your dummy URN.");
				  														user_URN = sc.next();
				  														break;
				  													}else {
				  														System.err.println("\n\t" + bullet + " Sorry!!You need to press 1 to Add Payee..Press 1 again.");
				  														while(true){
				  															try{
				  																addPayee = sc.nextInt();
				  																break;
				  															}catch(InputMismatchException im){
				  																sc.nextLine();
				  																System.err
				  																		.println("Sorry!!You need to press 1 to add payee");
				  															}
				  														}
				  														
				  													}
				  												}while(true);
				  												
				  												
				  										
				  										do{
				  											if(Dummy_URN.equals(user_URN)){
				  											
				  												System.out.println("\nPayee Registration is in pending status..");
				  												TimeUnit.SECONDS.sleep(1);
				  												
				  												//Adding Payee
				  												payee = payeeService.addPayee(payeeBean);
				  											
				  												System.out.println("\n\t\t" + bullet + "Payee Registration Confirmed");
				  												
				  											
				  												transaction_done = doTransaction(auth.getAccountId(),tranPwd,fundBean2.getPayeeAccountId(),fundBean2.getTransferAmount());
				  												if(transaction_done == 0 || transaction_done == 1){
				  													fundBean2 = null;
				  													continue loop3;
				  												}
				  													
				  												else if(transaction_done == 2){
				  													fundBean2 = null;
				  													continue loop1;
				  												}
				  													
				  													
				  											}else{
				  												logger.error("Invalid URN");
				  												System.out.println("\n Please enter valid URN");
				  												System.out.println("Do you want to continue?Press Yes or No");
				  												
				  										loop32: while(true){
				  													try{
				  														continue_URN = sc.next().charAt(0);
				  														break;
				  													}catch(InputMismatchException im){
				  														sc.nextLine();
				  														System.err
				  																.println("Please enter Y or N to continue");
				  														continue loop32;
				  													}
				  												}
				  												
				  												if(continue_URN == 'n' || continue_URN =='N'){
				  													fundBean2 = null;
				  													continue loop3;
				  												}
				  													
				  												System.out.println("\nEnter your URN again");
				  												user_URN = sc.next();
				  											}
				  										}while(true);
				  										
				  										}else{
				  												
				  												do{
				  													//checkPayee() returns 0 if payee is registered
				  													if(payeeService.checkPayee(payeeBean)==0){
				  														
				  														System.out.println("\nHold On!!Press 1 to Pay");
				  														int pay_option = 0;
				  												loop33: do{
				  															try{
				  																pay_option = sc.nextInt();
				  																break;
				  															}catch(InputMismatchException im){
				  																sc.nextLine();
				  																System.err
				  																		.println("Please enter 1 to pay");
				  																continue loop33;
				  															}
				  														}while(true);
				  														
				  														
				  												loop34 :do{
				  															if(pay_option == 1){
				  																
				  																transaction_done =doTransaction(auth.getAccountId(),tranPwd,fundBean2.getPayeeAccountId(),fundBean2.getTransferAmount());
				  																if(transaction_done == 0 || transaction_done == 1){
				  																	fundBean2 = null;
				  																	continue loop3;
				  																}
				  																	
				  																else if(transaction_done == 2){
				  																	fundBean2 = null;
				  																	continue loop1;
				  																}
				  															}else{
				  																System.err.println("Sorry!!You need to press 1 to pay");
				  																pay_option = sc.nextInt();
				  																continue loop34;
				  															}
				  														}while(true);
				  														
				  													}
				  													
				  													else{
				  														System.out.println("\nThe Nickname doesn't match with the Payee");
				  														System.out.println("\nEnter nickname again");
				  														nickname = sc.next();
				  														payeeBean.setNickName(nickname);
				  													}
				  												}while(true);
				  												
				  												
				  											}
				  											
				  											
				  										} catch (Exception e) {
				  											
				  											//logger.error("Server Problem!!Please try again later");
				  											System.err.println("\n\t\t" + bullet + "Server Problem!!Please try again later");
				  										}
				  										
				  										break;
				  									}
				  									else if(checkAccount && !checkPayee){
				  										
				  										System.out.println("\n\t" + bullet + "The Payee AcountId is doesn't belongs to Our bank..Please enter valid payee accountId");
				  										while(true){
				  											System.out.println("\n\t Do you want to continue?If yes..Press yes else No");
				  											try{
				  												choose = sc.next().charAt(0);
				  												break;
				  											}catch(InputMismatchException im){
				  												sc.nextLine();
				  												System.err.println("\nPlease enter either y or n.");
				  											}											
				  										}
				  										
				  										
				  										if(choose == 'N' || choose == 'n'){
				  											fundBean2 = null;
				  											continue loop3;
				  										}
				  										else{
				  											System.out.println("Please enter payee account Id again");
				  											fundBean2.setPayeeAccountId(sc.nextLong());
				  											continue loop30;
				  										}
				  										
				  									}
				  								
				  									else{
				  										System.out.println("\n\t " + bullet + "No record found in database with these details..");
				  										System.out.println("\n\t Do you want to continue?If yes..Press yes else No");
				  										choose = sc.next().charAt(0);
				  										
				  										if(choose == 'N' || choose == 'n'){
				  											fundBean2 = null;
				  											continue loop3;
				  										}
				  										else{
				  											System.out.println("Please enter your details again");
				  											fundBean2 = populateDetails(fundBean2);
				  											continue loop30;
				  										}
				  											
				  									}
				  				
				  								}
				  								
				  								else{
				  									System.out.println("\n\tAccountId and payeeAccountId cannot be equal");
				  									System.out.println("\n\tTRY AGAIN.....\n");
				  									Thread.sleep(1000);
				  									fundBean2 = null;
				  									continue loop20;
				  								}
				  							}while(true);
				  						
				  					}catch(Exception e){
				  						logger.error("Server problem!!Please try again later");
				  						System.err.println(e.getMessage() + "Please refer log");
				  						try {
											Thread.sleep(100);
										} catch (InterruptedException e1) {
											e1.printStackTrace();
										}
				  					}
				  			
				  					
				  					continue loop3;
				  						
				  				default:
				  					System.out.println("Please Choose a Valid Option!\n");
				  					//break;
				  					continue loop19;
				  					}// end of fund_option
				  				}}
				  				//	continue loop3;
			case 'h':
				// Method for Change password
				loginBean = new UserBean();

				userService = new UserService();
				
loop45: 		while(true){
				loginBean = changePassword();
				
				try {
		
				if (loginBean.getSecretQuestion().equals(auth.getSecretQuestion()) && loginBean.getSecretAnswer().equalsIgnoreCase(auth.getSecretAnswer())) {
		
					System.out.println("Enter your old Password: ");
					loginBean.setLoginPassword(sc.next());
		
					if (loginBean.getLoginPassword().equals(auth.getLoginPassword())) {
						System.out.println("\nSet your Login Password with specifications given below.\n");
		
		loop16:             while(true){				
						System.out.println("** A digit must occur at least once");
						System.out.println("** An alphabet must occur at least once");
						System.out.println("** A special character must occur at least once");
						System.out.println("** At least 6 characters\n");
						System.out.println("Enter your new Password: ");
						auth.setLoginPassword(sc.next());
		
						try {
							if (userService.validateLoginCredentials(auth)) {
							break;
							}
						} catch (BankException bankexception) {
							logger.error("exception occured while setting login password", bankexception);
							System.err.println("Please set your password with specifications given below");	
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							continue loop16;
						}
					  }//End of While loop
		
						// sending to user service layer
						userService.setNewLoginPassword(auth, auth.getLoginPassword());
						System.out.println("Your Password is set successfully.");
						logger.info("User Id-" + auth.getUserId() + ": New Login Password is set successfully.");
						} else {
								System.out.println("Wrong Password!");
								logger.info("User Id-" + auth.getUserId() + ": Wrong Password!");
								continue loop45;
							}
						} else {
							System.out.println("Wrong Verification Details!");
							logger.info("User Id-" + auth.getUserId()+ ": Wrong Verification Details!");
							continue loop45;
						}
					} catch (BankException bankException) {
						bankException.getMessage();
						continue loop45;
					}
				continue loop3;	
				}
		
		
										
			case 'i':

				System.out.println("Logging Out..");
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				continue loop1;

				default:
					System.out.println("Invalid Option!");
					logger.info("Invalid Option chosen!");

					break;
				}
				continue loop3;
				}
				
// -----------------------------------------------------------------------

case 2:
// Bank Admin
				
			System.out.println("------------------------------------------------------");
			
			do {
				System.out.println("Enter User Name: ");
				String username = sc.next();
				
				System.out.println("Enter Password: ");
				String password = sc.next();
		
			if ("admin".equals(username) && "admin".equals(password)) {
				System.out.println("------------------------------------------------------");					
				System.out.println("\n Welcome Admin!");
				
loop4:		while(true){
				System.out.println("\n************* Menu ***************");
				
loop21:			while(true){
				System.out.println("\n 1. Create a new Account");
				System.out.println("\n 2. View Transactions of all users");
				System.out.println("\n 3. Log Out");
				System.out.println("\n Choose an option from the menu: ");
				try{
				choose = sc.nextInt();
				break;
				}catch(InputMismatchException im){					
					System.err.println("Please enter a numeric value for choice.");
					sc.nextLine();
					continue loop21;
					}
				}
				switch (choose) {
				case 1:
						
					// Write two functionalities here
					System.out.println("\n Choose an option to create a new account: ");
					System.out.println("\n\ta.New Customer");
					System.out.println("\n\tb.Create a new account for existing customer");

					ch = sc.next().charAt(0);

					switch (ch) {

					case 'a':
						while (customerBean == null && accountMasterBean == null && userBean == null) {
				
							customerBean = populateCustomerdetails();					
							accountMasterBean = populateAccountdetails();		
							userBean = populateUserdetails();
					// Now Write methods in UI
						}
						try {
							accountMasterService = new AccountMasterService();
							CustomerService = new CustomerService();

							accId = accountMasterService.addAccountDetails(accountMasterBean);
							customerBean = CustomerService.addCustomerDetails(customerBean, accId);
							userBean = userService.UserDetails(userBean, accId);

							System.out.println("\n--------------------------------------------------------------------------\n");
							System.out.println("In Customer Email Id: \n");
							System.out.println("Congratulations! Your personal details have been successfully registered. ");
							System.out.println("Please use the following Login details for login and change your password.\n");

							System.out.println("Account ID : "+ userBean.getAccountId());
							System.out.println("User ID : "+ userBean.getUserId());
							System.out.println("Password : "+ userBean.getLoginPassword());
							logger.info("User Id-"+ userBean.getUserId() + ": New Account created by bank admin.");

						} catch (BankException e) {
							e.printStackTrace();
						} finally {
							accId = 0;
							customerBean = null;
							accountMasterBean =null;
							userBean = null;
						}
							
					continue loop4;
							
					case 'b':
					//Create a new account for an existing Customer(Multiple accounts)
					customerBean = null; 
					accountMasterBean =null;
					userBean =null;
					userBean1=null;
loop5:				while (customerBean == null && accountMasterBean == null && userBean == null && userBean1==null) {
						
						userService = new UserService();
						accountMasterService = new AccountMasterService();
						userBean1= new UserBean();
						existingUserBean= new UserBean();
						accountMasterBean = new AccountMasterBean();
						CustomerService = new CustomerService();
					
						System.out.println("Enter the UserId of existing customer: ");	
						try{
					    	userBean1.setUserId(sc.nextLong());
							} catch (InputMismatchException e) {
								sc.nextLine();
								System.out.println("Please enter a numeric value for user Id!");
								continue loop5;
							}
						try {
							//Check if this userId is present in the database
							existingUserBean= userService.authenticUserId(userBean1);
						
							if(existingUserBean==null){
								System.out.println("Invalid UserId");
								continue loop5;
							}else{
								accountMasterBean = populateAccountdetails();			
								userBean = populateUserdetails_add(existingUserBean.getUserId());		
								accId = accountMasterService.addAccountDetails(accountMasterBean);
								
								//Check CustomerDAO for this method
								customerBean = CustomerService.fetchCustomerDetails_add(existingUserBean.getAccountId(), accId);
																
								userBean3 = userService.UserDetails_add(userBean, existingUserBean.getAccountId(), accId);
								
								System.out.println("\n--------------------------------------------------------------------------\n");
								System.out.println("In Customer Email Id: \n");
								System.out.println("Congratulations! Your personal details have been successfully registered. ");
								System.out.println("Please use the following Login details for login and change your password.\n");

								System.out.println("Account ID : "+ userBean3.getAccountId());
								System.out.println("User ID : "+ userBean3.getUserId());
								System.out.println("Password : "+ userBean3.getLoginPassword());
								logger.info("User Id-"+ userBean3.getUserId() + ": New Account created by bank admin.");
							} 
							
							}catch (BankException e) {
								e.printStackTrace();
							} finally {
								accId = 0;
								customerBean = null;
								accountMasterBean =null;
								userBean = null;
							}
						}//End While statement
					continue loop4;
						
					default:
						System.out.println("Invalid option, please enter the option again.\n");
						continue loop4;
					}
				
//-----Case 1 of Bank Admin ends here-------------------------------------------------------
			case 2:
				// Write two functionalities here
				System.out.println("\n Choose an option to view transactions: ");
				System.out.println("\n\ta.View daily Transactions");
				System.out
						.println("\n\tb.View monthly or yearly Transactions");

				opt = sc.next().charAt(0);

				switch (opt) {

				case 'a':
					boolean onceMore =false;
					do{
						onceMore =false;
					System.out.print("\nEnter the date for which you want to view the transactions in [dd/MM/yyyy] format :");

					boolean validDate = true;
					LocalDate daily = null;
					DateTimeFormatter formatter = DateTimeFormatter
							.ofPattern("dd/MM/yyyy");
					do {
						String sDate = sc.next();
						try {
							daily = LocalDate.parse(sDate,
									formatter);
							validDate = true;
						} catch (DateTimeParseException e) {
							System.out
									.println("Please enter date in [dd/MM/yyyy] format");
							validDate = false;
						}
					} while (!validDate);
					
					
					List<TransactionsBean> transactionlist = new ArrayList<>();
					transactionbean = new TransactionsBean();
					transactionbean.setDateOfTransaction(daily);
					LocalDate dateOfTransaction = transactionbean.getDateOfTransaction();
					transactionService = new TransactionService();
					try {
						transactionlist = transactionService.viewDailyTransactionDetails(dateOfTransaction);
					
					TransactionsBean temp = new TransactionsBean();
					Iterator<TransactionsBean> iterator = transactionlist.iterator();

					if (!transactionlist.isEmpty()) {

						System.out.println("\n\n");

						System.out.println("The transaction details for the given date "+ dateOfTransaction + " are shown below");
						System.out.println("\n\n");

						int i = 1;
						String format = "| %-4s | %-14s | %-16s | %-19s | %-16s | %-18s | %-10s |%n";
						System.out.format("+------+----------------+------------------+---------------------+------------------+--------------------+------------+%n");
						System.out.format("| S.NO | TRANSACTION_ID | TRAN_DESCRIPTION | DATE OF TRANSACTION | TRANSACTION TYPE | TRANSACTION AMOUNT | ACCOUNT ID |%n");
						System.out.format("+------+----------------+------------------+---------------------+------------------+--------------------+------------+%n");
						while (iterator.hasNext()) {

							temp = iterator.next();
							temp.print(format, i);
							i++;
						System.out.format("+---------------------------------------------------------------------------------------------------------------------+%n");
						}
					}else{
						System.out.println("No transactions found for this date.");
					}} catch (BankException e2) {
						System.err.println(e2.getMessage());
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						onceMore = true;
					}
					}while(onceMore);
					continue loop4;
								
				case 'b':
					 onceMore =false;
					do{
						onceMore = false;
					boolean validDates = true;
					LocalDate stdate = null;
					LocalDate endate = null;
					
					System.out.print("Enter the start date and end date for which you want to view the transactions in [dd/MM/yyyy] format :");
					System.out.println("");
					do {
					System.out.println("Enter starting date");
					String stDate = sc.next();
					System.out.println("");
					System.out.println("Enter ending date");
					String enDate = sc.next();
					DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					 
					try {
						stdate = LocalDate.parse(stDate,
								formatter1);
						endate = LocalDate.parse(enDate,
								formatter1);
						validDates = true;
					} catch (DateTimeParseException e) {
						System.out
								.println("Please enter dates in valid format [dd/MM/yyyy]");
						validDates = false;
					}
					} while (!validDates);					
					
					transactionbean = new TransactionsBean();
					transactionService = new TransactionService();
					List<TransactionsBean> transactionlist = new ArrayList<TransactionsBean>();
					try{
					transactionlist = transactionService.viewMyTransactionDetails(stdate, endate);
					data = new TransactionsBean();
					Iterator<TransactionsBean> dataiterator = transactionlist.iterator();

					if (!transactionlist.isEmpty()) {

						System.out.println("\n\n");
						System.out.println("The transaction details between the dates "
										+ stdate
										+ " and "
										+ endate
										+ "are shown below");
						System.out.println("\n\n");
						int i = 1;
						String format = "| %-4s | %-14s | %-16s | %-19s | %-16s | %-18s | %-10s |%n";
						System.out.format("+------+----------------+------------------+---------------------+------------------+--------------------+------------+%n");
						System.out.format("| S.NO | TRANSACTION_ID | TRAN_DESCRIPTION | DATE OF TRANSACTION | TRANSACTION TYPE | TRANSACTION AMOUNT | ACCOUNT ID |%n");
						System.out.format("+------+----------------+------------------+---------------------+------------------+--------------------+------------+%n");
						while (dataiterator.hasNext()) {

							data = dataiterator.next();
							data.print(format, i);
							i++;
						System.out.format("+---------------------------------------------------------------------------------------------------------------------+%n");
						}
					}
					else
					{
						System.out.println("No transactions available for specified period");
					}
					}catch(BankException e)
					{
						System.err.println("Error: "+e.getMessage());
						try {
							Thread.sleep(100);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						onceMore = true;
					}
					}while(onceMore);
					continue loop4;	
				
				/*case default:
					System.out.println("option invalid");
					continue loop4;*/
				}

				continue loop4;	
				
//-----Case 2 of Bank Admin ends here-------------------------------------------------------		
							
		//Logout from Admin account
		case 3:
							
			System.out.println("Logging Out..");
			
			try {
				TimeUnit.SECONDS.sleep(2);
				logger.info("Logged out of admin account.");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}finally{
				customerBean=null;
				userBean=null;
				userBean1=null;
				accountMasterBean=null;
				transactionbean=null;
				accId=0;	
				data=null;
			}
			continue loop1;
//-----Case 3 of Bank Admin ends here-------------------------------------------------------					
		
		default:
			System.out.println("Please choose valid option!");
			logger.info("Invalid Option chosen!");

			continue loop4;							
		 
			}   //End of (choose from 3) switch statement	
					//break;
		} //End of While loop				
		} else {
			System.out.println("Invalid Username/Password! \nPlease enter the valid credentials.\n");
			logger.info("Invalid Username/Password.");
		}	
					
		} while (true);
		// break;
	}

		} while (true);

	}

//---------------------Methods Below---------------------------------------------------------------- 

	private static UserBean populateUserdetails_add(long existingUserId) {
		UserBean userBean = new UserBean();

		userBean.setUserId(existingUserId);
		System.out.println("\n---Enter Details for Security Credentials---");
		
loop10: while(true){
		System.out.println("\nSet your Transaction Password \n");
		System.out.println("** A digit must occur at least once");
		System.out.println("** An alphabet must occur at least once");
		System.out.println("** A special character must occur at least once");
		System.out.println("** At least 6 characters");
		System.out.println("** At Most 10 characters");
		userBean.setTransactionPassword(sc.next());

		userService = new UserService();

		try {
			if (userService.validateUserCredentials(userBean)) {
				return userBean;
			} else {
				continue loop10;
			}
		} catch (BankException bankexception) {
			logger.error("exception occured while setting transaction password", bankexception);
			System.err.println("Invalid Password, Please Enter again");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			continue loop10;

		}
		}//End of While loop
	}

	private static UserBean forgotPassword() {

		UserBean userBean = new UserBean();
		userBean.setUserId(0);
		
loop9:  while(true){
			try {
				System.out.println("\nPlease Enter User Id: ");
				userBean.setUserId(sc.nextInt());

			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Please Enter numeric value for UserId!");
				continue loop9;
			}
		
			System.out.println("Select your Secret Question: \n");

		System.out.println("1. What is your favourite destination? ");
		System.out.println("2. What is your pet's name? ");
		System.out.println("3. What is your birth place? ");

		int ques = sc.nextInt();

		String setQues = null;
		while (true) {
			if (ques == 1) {
				setQues = "What is your favourite destination? ";
				break;
			} else if (ques == 2) {
				setQues = "What is your pet's name? ";
				break;
			} else if (ques == 3) {
				setQues = "What is your birth place? ";
				break;
			} else {
				System.out.println("Invalid Option");
				logger.info("Invalid Account Type Option.");
				continue loop9;
			}
		}
		userBean.setSecretQuestion(setQues);
		
		System.out.println("Enter your Answer:");
		userBean.setSecretAnswer(sc.next());
		
		return userBean;
		}//End of While loop
	}

	private static UserBean populateUserdetails() {
		UserBean userBean = new UserBean();		
		
		System.out.println("\nEnter Details for Security Credentials");
		
loop6: 	while(true){
		existingUserBean= new UserBean();
		userService = new UserService();
	
		System.out.println("\nCreate unique UserId (Please enter numeric value): ");
		try {
			userBean.setUserId(sc.nextLong());
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Please enter a numeric value for user Id!");
				continue loop6;
			}
		
		try {
			existingUserBean= userService.authenticUserId(userBean);
			} catch (BankException e1) {				
				e1.printStackTrace();
			}

		if(existingUserBean==null){		
				userBean.getUserId();			
			//break;
		}else{
			System.out.println("User Id is already in use.");
			existingUserBean=null;
			continue loop6;
		}
		//}			
		
		
        boolean validQues = true;
        boolean quesNum = false;
        String setQues = null;
		int ques = 0;
		do{
		do{
			System.out.println("Select Secret Question");

			System.out.println("1. What is your favourite destination? ");
			System.out.println("2. What is your pet's name? ");
			System.out.println("3. What is your birth place? ");
		try{
		 ques = sc.nextInt();
		 validQues = true;
		}
		catch(InputMismatchException e)
		{
			sc.nextLine();
			System.out.println("Please enter numeric value");
			validQues = false;
		}}while(!validQues);		
    	
			if (ques == 1) {
				setQues = "What is your favourite destination? ";
				quesNum = true;
			} else if (ques == 2) {
				setQues = "What is your pet's name? ";
				quesNum = true;
			} else if (ques == 3) {
				setQues = "What is your birth place? ";
				quesNum = true;
			} else {
				System.out.println("Invalid Option, Please choose again.");
				quesNum = false;
				//continue loop6;
			}
		
		}while(!quesNum);
		userBean.setSecretQuestion(setQues);

		System.out.println("Enter your Answer: ");
		sc.nextLine();
		userBean.setSecretAnswer(sc.nextLine());
		boolean validPassword = true;
     do{
		System.out.println("Set your Transaction Password \n");
		System.out.println("** A digit must occur at least once");
		System.out.println("** An alphabet must occur at least once");
		System.out.println("** A special character must occur at least once");
		System.out.println("** At least 6 characters");
		System.out.println("** At Most 10 characters");
		userBean.setTransactionPassword(sc.next());

		userService = new UserService();
		
       
		try {
			if (userService.validateUserCredentials(userBean)) {
				validPassword = true;
				return userBean;
			} else {
				return null;
			}
		} catch (BankException bankexception) {
			logger.error("exception occured while setting transaction password", bankexception);
			System.err.println("Invalid Password! Please Enter the password again.");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			validPassword = false;
			//continue loop6;
		}
       }while(!validPassword);
	  }
	}

	private static AccountMasterBean populateAccountdetails() {
		AccountMasterBean accountMasterBean = new AccountMasterBean();
		boolean validOption = true;
		System.out.println("\n---Enter Details Related to Account Opening---");
loop8:  while(true){
		System.out.println("Account Type: ");
		System.out.println("1. Savings Account");
		System.out.println("2. Current Account");
		System.out.println("Enter the Account Type you prefer: ");
		
		int AccTypeOpt = 0;
		do{
		try{
			Scanner sc = new Scanner(System.in);
		 AccTypeOpt = sc.nextInt();
		 validOption = true;
		}catch(InputMismatchException e)
		{
			System.out.println("Enter only numeric value");
			validOption = false;
		}
		}while(!validOption);
		String type = null;
		while (true) {
			if (AccTypeOpt == 1) {
				type = "Savings";
				break;
			} else if (AccTypeOpt == 2) {
				type = "Current";
				break;
			} else {
				System.out.println("Invalid Option");
				logger.info("Invalid Account Type Option.");
				continue loop8;
			}
		}
		accountMasterBean.setAccountType(type);

		boolean validBalance = true;
		System.out.println("Enter Opening Balance Amount: ");
		int balance = 0;
		do {
			try {
				Scanner sc = new Scanner(System.in);
				balance = sc.nextInt();
				validBalance = true;
			} catch (InputMismatchException e) {
				System.out.println("Please Enter Balance in number");
				validBalance = false;
			}
		} while (!validBalance);
		accountMasterBean.setAccountBalance(balance);

		accountMasterService = new AccountMasterService();

		try {
			if (accountMasterService.validateAccount(accountMasterBean)) {
				return accountMasterBean;
				
			} else {
				continue loop8;
			}
		} catch (BankException bankexception) {
			logger.error("exception occured", bankexception);
			System.err.println("Opening Balance should be atleast 2000");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			continue loop8;
		}
	  }//End of while
	}

	private static CustomerBean getAddressDetails(long AccountId) {

		CustomerBean customerbean = new CustomerBean();
		CustomerService = new CustomerService();

		try {
			customerbean = CustomerService.viewAddressDetails(AccountId);
		} catch (BankException e) {
			logger.error("exception occured", e);
			System.err.println("Invalid data! ");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

		CustomerService = null;
		return customerbean;
	}



	private static CustomerBean getEmailDetails(long AccountId) {

		CustomerBean customerbean = new CustomerBean();
		CustomerService = new CustomerService();

		try {
			customerbean = CustomerService.viewEmailDetails(AccountId);
		} catch (BankException e) {
			logger.error("exception occured", e);
			System.err.println("Invalid data!");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
		}

		CustomerService = null;
		return customerbean;
	}

	private static CustomerBean UpdateEmail() {

loop12: while(true){
		CustomerBean customerbean = new CustomerBean();
		customerbean.setEmail(sc.next());

		CustomerServiceImpl = new CustomerService();

		try {
			CustomerServiceImpl.validateEmail(customerbean);
			return customerbean;
		} catch (BankException bankexception) {
			logger.error("exception occured", bankexception);
			System.err.println(bankexception.getMessage()+"\nPlease Enter the email again. ");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			continue loop12;

		}
	  }
	}

	private static UserBean changePassword() {
		String answer= null;
		UserBean userBean = new UserBean();
loop12: while(true){
		System.out.println("Select your Secret Question: \n");

		System.out.println("1. What is your favourite destination? ");
		System.out.println("2. What is your pet's name? ");
		System.out.println("3. What is your birth place? ");
		boolean validQues = true;
		int ques = 0;
		do{
        try{
        	Scanner sc = new Scanner(System.in);
		   ques = sc.nextInt();
		   validQues = true;
        }
        catch(InputMismatchException e)
        {
   
        	System.out.println("Please enter numeric value");
        	validQues = false;
        }
		}while(!validQues);
		String setQues = null;
		while (true) {
			if (ques == 1) {
				setQues = "What is your favourite destination? ";
				break;
			} else if (ques == 2) {
				setQues = "What is your pet's name? ";
				break;
			} else if (ques == 3) {
				setQues = "What is your birth place? ";
				break;
			} else {
				System.out.println("Invalid Option");
				logger.info("Invalid Account Type Option.");
				continue loop12;
			}
		}
		userBean.setSecretQuestion(setQues);
	//	String c = sc.nextLine();
		System.out.println("Enter your Answer:");
		
		userBean.setSecretAnswer(sc.next());
		
		return userBean;
	  }
	}

	private static UserBean enterUser() {

		UserBean loginBean = new UserBean();
		loginBean.setUserId(0);
		loginBean.setLoginPassword(null);
		
loop13: while(true){
			try {
				System.out.println("\nPlease Enter User Id: ");
				loginBean.setUserId(sc.nextInt());

			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Please Enter numeric value for UserId!");
				continue loop13;
			}


		System.out.println("Please Enter Password: ");
		loginBean.setLoginPassword(sc.next());

		return loginBean;
	 }
	}

	private static CustomerBean populateCustomerdetails() {

		CustomerBean customerbean = new CustomerBean();
		
loop13: while(true){
		System.out.println("\n----Enter Customer Details----\n");

		sc.nextLine();
		System.out.println("Enter Customer name: ");
		String name = sc.nextLine();
		customerbean.setCustomerName(name);

		System.out.println("Enter Customer Email Address: ");
		String email = sc.next();
		customerbean.setEmail(email);
		
		String c = sc.nextLine();
		
		System.out.println("Enter Customer address: ");
		String address = sc.nextLine();
		customerbean.setAddress(address);

		System.out.println("Enter Customer pan card number: ");
		String pan =sc.next();
		customerbean.setPanCard(pan);

		CustomerServiceImpl = new CustomerService();

		try {
			if (CustomerServiceImpl.validateCustomer(customerbean)) {
				return customerbean;
			} else {
				continue loop13;
			}
		} catch (BankException bankexception) {
			logger.error("Exception occured while entering personal details.");
			System.err.println("ERROR : " + bankexception.getMessage());		
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			continue loop13;

		}
	  }
	}

	/*******************************************************************************************************
	 - Function Name	:	populateDetails(FundTransferBean fundTransferBean)
	 - Input Parameters	:	FundTransferBean fundTransferBean
	 - Return Type		:	void
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	07/10/2018
	 - Description		:	Called by Main method which takes user details and validates it
	 ********************************************************************************************************/
	private static FundTransferBean populateDetails(
			FundTransferBean fundTransferBean) {

		// Reading and setting the values for the donorBean

		fundTransferBean = new FundTransferBean();
		System.out.println("-------------------------");
		System.out.println("Details populating");
		// fundTransferBean.setAccountId(0);
		System.out.println("-------------------------");
		System.out.println("\nENTER DETAILS");
loop46: while(true){
		do {
			System.out.println("\nEnter Payee account number ");
			try {
				fundTransferBean.setPayeeAccountId(sc.nextLong());
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.err
						.println("\n\tWarning :*Please enter numeric values for payee account Id");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		} while (fundTransferBean.getPayeeAccountId() <= 0);

		System.out.println("\nEnter amount to transferred:");

		try {
			fundTransferBean.setTransferAmount(sc.nextInt());
		} catch (InputMismatchException e) {
			sc.nextLine();
			System.err
					.println("\n\t\t Warning :*Please enter a numeric value for donation amount, try again");
		}

		FundTransferService fundServiceImpl = new FundTransferService();

		try {
			fundServiceImpl.validateDetails(fundTransferBean);
			return fundTransferBean;
		} catch (BankException bankException) {
			logger.error("exception occured");
			System.err.println("Invalid data:");
			System.err.println(bankException.getMessage() + " \n Try again..");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			continue loop46;
		}
	  }
	}


	
	/*******************************************************************************************************
	 - Function Name	:	doTransaction(long account_id, String tranPwd, long payee_account_id,int other_bank_amount)
	 - Input Parameters	:	long account_id, String tranPwd, long payee_account_id,int other_bank_amount
	 - Return Type		:	int
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	07/10/2018
	 - Description		:	Called by Main method which transfers fund to the payee account of same bank
	 ********************************************************************************************************/
	private static int doTransaction(long l, String tranPwd, long m,
			int other_bank_amount) throws BankException {
		
		char choose = 0;
		int i,index;
		
		transactionService = new TransactionService();
		userService = new UserService();
		fundTransferService = new FundTransferService();
		
		String[] tran_description = new String[]{"1. CREDIT CARD PAYMENT", "2. FEES" ,"3. FUND TRANSFER",
				"4. EMI", "5. PERSONAL", "6. RENT", "7. SALARY", "8. UTILITY PAYMENTS", "9. DONATION","10. OTHER"};
		
		do {
			//Checking for transaction Password
			if (userService.checkTransactionPwd(l, tranPwd)) {

				System.out.println("\nEnter Transaction Description");
				System.out.println("------------------------------");
				System.out.printf("%10s", "Description");
				System.out.println(" ");
				
				for(i=0;i<10;i++){
					System.out.println(tran_description[i]);
				}
				System.out.println("------------------------------");
				loop41: while(true){
					try{
						index = sc.nextInt();
						if(!(index>0 && index<=10)){
							System.err.println("\n\tPlease choose from 1 to 10");
							//index = sc.nextInt();
							continue loop41;
						}
						break;
					}catch(InputMismatchException im){
						sc.nextLine();
						System.err.println("\nPlease choose from 1 to 10");
					}
				}
				

				System.out.println("\nChecking for account balance..");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e1) {
					// logger.error("Thread is kept waited");
					e1.printStackTrace();
				}
				
				do {

					if (accountMasterService.checkAccountBalance(l,
							other_bank_amount)) {
						
						
						System.out.println("\n Checking for max transactions..");
						
						/*The maximum amount of funds transferred is 10lakh per day..If the 
						limit is reached, transaction is not allowed*/
						
						if (fundTransferService.checkMaxFunds(l,
								other_bank_amount)) {

							FundTransferBean fundTransfer = new FundTransferBean(
									l, m, other_bank_amount);
							System.out
									.println("\n Your Transfer is in progress..");
							try {
								TimeUnit.SECONDS.sleep(1);
							} catch (InterruptedException e) {
								// logger.error("Thread is kept waited");
								e.printStackTrace();
							}
							
							payeeService.payPayee(fundTransfer);
							
							System.out.println("\n\t\t Congrats!! Your Transfer is Completed");

							// Update in transaction Table
							transactionService.updateTransaction(l, m,
									tran_description[index-1], other_bank_amount);

							return 1;
						} else {
							//logger.error("Maximum limit reached");
							System.err
									.println("Sorry!You cannot transfer beacuse you reached the limit of 10Lakh for today");
							return 2;
						}

					}
				else {
						//logger.error("Insufficient Balance");
						System.out
								.println("Sorry!No Sufficient Account Balance");
						System.out
								.println("Do you want to continue?If Yes..Press Y or else N");
						while(true){
							try{
								choose = sc.next().charAt(0);
								break;
							}catch(InputMismatchException im){
								sc.nextLine();
								System.err.println("Please enter either Y or N to continue");
							}
						}
						
						
						if (choose == 'N' || choose == 'n'){
							fundBean = null;
							return 0;
						}
							
						System.out
								.println("Enter the amount to be transferred");
						other_bank_amount = sc.nextInt();

					}

				} while (true);

				

			} else {
				//logger.error("Invalid Transaction Password");
				System.out
						.println("Sorry!!Transaction cannot be done because of Invalid transaction Password");

				System.out.println("Enter the transaction Password again");
				tranPwd = sc.next();
			}
		} while (true);
	}// end of dotransaction

	/*******************************************************************************************************
	 - Function Name	:	doTransactionOwnAccount(long account_id, String tranPassword,
								long payee_acc_id, int transferAmount)
	 - Input Parameters	:	long account_id, String tranPassword,
								long payee_acc_id, int transferAmount
	 - Return Type		:	void
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	07/10/2018
	 - Description		:	Called by Main method which transfers fund to the user's own account of same bank
	 ********************************************************************************************************/
	private static int doTransactionOwnAccount(long l, String tranPassword,
			long m, int transferAmount) throws BankException {
		
		char choose = 0;
		int i,index = -1;
		transactionService = new TransactionService();
		userService = new UserService();
		
		String[] tran_description = new String[]{"1. CREDIT CARD PAYMENT", "2. FEES" ,"3. FUND TRANSFER",
				"4. EMI", "5. PERSONAL", "6. RENT", "7. SALARY", "8. UTILITY PAYMENTS", "9. DONATION","10. OTHER"};	

		do {
			
			if (userService.checkTransactionPwd(l, tranPassword)) {

				System.out.println("Enter Transaction Description");
				System.out.println("-------------------------------");
				System.out.printf("%10s","Description");
				System.out.println("");
				System.out.println("-------------------------------");
				for(i=0;i<10;i++){
					System.out.println(tran_description[i]);
				}
				System.out.println("-------------------------------");
				
		loop40: while(true){
				
					try{
						
						index = sc.nextInt();
						if(!(index>0 && index<=10)){
							System.err.println("\n\tPlease choose from 1 to 10");
							//index = sc.nextInt();
							continue loop40;
						}
						
						break;
					}catch(InputMismatchException im){
						sc.nextLine();
						System.err.println("\n\tPlease choose from 1 to 10");
						continue loop40;
					}
				
		}
		
				System.out.println("\nChecking for account balance..");

				do {
					if (accountMasterService.checkAccountBalance(l,
							transferAmount)) {

						FundTransferBean fundTransfer = new FundTransferBean(l,
								m, transferAmount);
						System.out.println("\nYour Transfer is in progress..");
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							// logger.error("Thread is kept waited");
							e.printStackTrace();
						}
						
						payeeService.payPayee(fundTransfer);
						
						System.out.println("\nYour Transfer is Completed.");
						
						transactionService.updateTransaction(l, m, tran_description[index-1],
								transferAmount);

						return 1;
					} else {
						System.out.println("No sufficient account balance");
						System.out.println("Do you want to continue?");
						while(true){
							try{
								choose = sc.next().charAt(0);
								break;
							}catch(InputMismatchException im){
								sc.nextLine();
								System.err.println("Please enter either Y or N to continue");
							}
						}
						
						if (choose == 'N' || choose == 'n'){
							fundBean = null;
							return 0;
						}
							
						System.out
								.println("Enter the amount to be transferred");
						transferAmount = sc.nextInt();
					}
				} while (true);


			} else {
				//logger.error("Invalid Transaction Password");
				System.out
						.println("\n\t\tSorry!!Transaction cannot be done because of Invalid transaction Password");
				
				System.out.println("\nEnter the transaction Password again");
				tranPassword = sc.next();
			}
		} while (true);
	}// end of doTransactionOwnAccount

	

}
