package com.capgemini.obs.util;

public class IQueryMapper {
	
	public static final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	public static final String UNAME="system";
	public static final String PASSWORD="Capgemini123";
	
	//-----------Customer Details Entered while SignUp-------------------------------------------------
	public static final String INSERT_QUERY="INSERT INTO customer VALUES(?,?,?,?,?)";
	public static final String SELECT_CUSTOMER_QUERY="SELECT * from customer where account_id=?";
	
	public static final String INSERT_lOGIN_QUERY="INSERT INTO user_table VALUES(?,?,?,?,?,?,?)";
	//public static final String INSERT_lOGIN_QUERY="INSERT INTO user_table VALUES(?,seq_user_id.NEXTVAL,?,?,?,?,?)";
	//public static final String LOGIN_USERID_QUERY_SEQUENCE="SELECT seq_user_id.CURRVAL FROM DUAL";
	public static final String UPDATE_lOGIN_QUERY="UPDATE user_table SET account_id=? where user_id=?";
	public static final String SELECT_lOGIN_QUERY="SELECT * from user_table where user_id=?";
	
	//-----------ChequeBook Request Details Entered----------------------------------------------------
	/*public static final String INSERT_CHEQUEBOOK_QUERY="INSERT INTO service_tracker VALUES(seq_request_id.NEXTVAL,?,?,SYSDATE,?)";
	public static final String SERVICE_REQUESTID_QUERY_SEQUENCE="SELECT seq_request_id.CURRVAL FROM DUAL";
	*/	
	//-----------ChequeBook Request Details Entered----------------------------------------------------
	/*public static final String INSERT_lOGIN_QUERY="INSERT INTO user_table VALUES(user_id.NEXTVAL,?,?,?,?,?)";
	public static final String LOGIN_USERID_QUERY_SEQUENCE="SELECT user_id.CURRVAL FROM DUAL";
	*/
	
	//-------------Account Master----------------------------------------------------------------------
	public static final String INSERT_CREATE_ACC_QUERY="INSERT INTO Account_Master VALUES(seq_acc_id.NEXTVAL,?,?,SYSDATE)";
	public static final String ACCID_QUERY_SEQUENCE="SELECT seq_acc_id.CURRVAL FROM DUAL";

	//------------Check Login details of Registered User------------------------------------------------------
	public static final String SELECT_CHECKlOGIN_QUERY="SELECT * from user_table where user_id=? and login_password=?";
	public static final String UPDATE_lOCK_QUERY="UPDATE user_table SET lock_status=? where user_id=?";

	//------------Update Login Password of Customer-----------------------------------------------------------
	public static final String UPDATE_LOGIN_PASSWORD_QUERY="UPDATE user_table SET login_password=? where user_id=?";
	public static final String SELECT_FORGOT_PASSWORD_QUERY = "SELECT secret_question, secret_answer from user_table where user_id=?";
	
	//------------Update Address of Customer------------------------------------------------------------------
	public static final String GET_ADDRESS_QUERY="SELECT address from customer where Account_id = ?";
	public static final String UPDATE_ADDRESS_QUERY = "UPDATE customer SET Address = ? WHERE Account_id = ?";
	
	//------------Update Email Address of Customer------------------------------------------------------------------	
	public static final String GET_EMAIL_QUERY="SELECT email from customer where Account_id = ?";
	public static final String UPDATE_QUERY_EMAIL = "UPDATE customer SET email = ? WHERE Account_id = ?";
	
	//------------View All Transactions of all Customers By Bank Admin------------------------------------------------------------------		
	public static final String GET_DAILY_TRANSACTION_QUERY="SELECT * from Transactions where (dateoftransaction >= ? and dateoftransaction < ?)";
	public static final String GET_MY_TRANSACTION_QUERY = "select * from transactions where (dateoftransaction >= ? and dateoftransaction < ?)";
	
	//------------Fund Transfer By Brahmani------------------------------------------------------------------------
	public static final String FUND_TRANSFER_ACCOUNT = "INSERT INTO FUND_TRANSFER VALUES(fundTransfer_Id_seq.NEXTVAL,?,?,SYSDATE,?)";
	public static final String CHECK_ACCOUNT_EXIST = "SELECT ACCOUNT_ID FROM ACCOUNT_MASTER WHERE ACCOUNT_ID = ?";
	public static final String SELECT_PAYEE_ID = "SELECT PAYEE_ACCOUNT_ID,NICK_NAME FROM PAYEETABLE WHERE ACCOUNT_ID = ?";
	public static final String INSERT_PAYEE = "INSERT INTO PAYEETABLE VALUES(?,?,?)";
	public static final String INSERT_TRANSFER_TABLE = "INSERT INTO TRANSACTIONS VALUES(transferId.NEXTVAL,?,SYSDATE,?,?,?)";
	public static final String CHECK_TRANSACTION_PWD = "SELECT TRANSACTION_PASSWORD FROM USER_TABLE WHERE ACCOUNT_ID = ?";
	public static final String CHECK_ACCOUNT_BALANCE = "SELECT ACCOUNT_BALANCE FROM ACCOUNT_MASTER WHERE ACCOUNT_ID = ?";
	public static final String CURR_DATE = "SELECT TO_CHAR(SYSDATE,'DD-MON-YYYY') FROM DUAL";
	public static final String CHECK_MAX_LIMIT = "SELECT DATE_OF_TRANSFER,TRANSFER_AMOUNT FROM FUND_TRANSFER WHERE ACCOUNT_ID = ?";
	public static final String UPDATE_ACCOUNT_BAL = "UPDATE ACCOUNT_MASTER SET ACCOUNT_BALANCE = ? WHERE ACCOUNT_ID = ?";
	
	//------------By Bhuvanesh------------------------------------------------------------------------
	public static final String GET_ACCOUNTS="select account_id from customer where pancard=(select pancard from customer where account_id=?)";
	public static final String VIEW_MINI_STATEMENT = "select * from (SELECT * from transactions where(account_id=?) order by dateoftransaction desc) where rownum<=10";
	public static final String VIEW_DETAIL_STATEMENT="select * from transactions where dateoftransaction >= ? and dateoftransaction < ?  and account_id=? order by dateoftransaction desc";
	
	
	public static final String VIEW_SERVICE_REQUEST_SRN="select * from service_tracker where (service_id=?) and (account_id=?)";
	public static final String VIEW_ALL_SERVICE_REQUEST="select * from service_tracker where (account_id=?) and (service_raised_date between sysdate-120 and sysdate) order by service_id desc";
	
	public static final String INSERT_REQ_DETAILS="insert into service_tracker values(serviceid_sequence.NEXTVAL,?,?,sysdate,?)";
	public static final String GET_SERVICE_ID="select serviceid_sequence.CURRVAL from DUAL";
	
	//------------TO create Multiple accounts by Bank Admin------------------------------------------------------------------------
	
	public static final String FETCH_USERID_TO_ADD_ACCOUNT_QUERY = "SELECT * from user_table where user_id=?";
	public static final String SELECT_USER_QUERY_NEW_ACCID = "SELECT * from user_table where account_id=?";
	public static final String INSERT_NEW_ACC_USER_QUERY = "INSERT INTO User_table VALUES(?,?,?,?,?,?,?)";
	public static final String GET_USERNAME = "SELECT customer_name from customer where (account_id = ?)";

}
