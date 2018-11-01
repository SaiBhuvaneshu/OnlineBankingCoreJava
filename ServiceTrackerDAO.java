package com.capgemini.obs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.obs.bean.ServiceTrackerBean;
import com.capgemini.obs.exceptions.BankException;
import com.capgemini.obs.util.DBUtil;
import com.capgemini.obs.util.IQueryMapper;



public class ServiceTrackerDAO implements IServiceTrackerDAO{


	static Logger logger = Logger.getLogger("logfile");
	Connection conn = null;
	PreparedStatement pstmt= null;
	
	ResultSet rs = null;
	public ServiceTrackerDAO() {

		PropertyConfigurator.configure("resource//log4j.properties");
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	trackServiceRequest
	 - Input Parameters	:	ServiceTrackerBean object
	 - Return Type		:	ServiceTrackerBean
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	29/09/2018
	 - Description		:	view service request of an account from database  
	 ********************************************************************************************************/
	@Override
	public ServiceTrackerBean trackServiceRequest(ServiceTrackerBean serviceTracker) throws BankException{

		conn = DBUtil.getConnection();
		ServiceTrackerBean serviceRequest = null;
		try {
			pstmt = conn.prepareStatement(IQueryMapper.VIEW_SERVICE_REQUEST_SRN);
			pstmt.setInt(1,serviceTracker.getServiceId());
			pstmt.setLong(2,serviceTracker.getAccountId());
			rs=pstmt.executeQuery();
			if(rs.next())
			{
				serviceRequest = new ServiceTrackerBean();
				serviceRequest.setServiceId(rs.getInt(1));
				serviceRequest.setServiceDescription(rs.getString(2));
				serviceRequest.setAccountId(rs.getLong(3));
				serviceRequest.setServiceRaisedDate(rs.getDate(4));
				serviceRequest.setServiceStatus(rs.getString(5));
			}
			
			if(serviceRequest != null)
			{
				String message="Service Request Having Id as " + serviceTracker.getServiceId()+"tracked";
				logger.info(message);
				
			}
			else
			{
				String message = "No service request found with id "+serviceTracker.getServiceId()+" for account no "+serviceTracker.getAccountId();
				logger.info(message);
			}
			
		} catch (SQLException e) {
			
			logger.error("error occured while tracking Service Request");
			throw new BankException("error occured while tracking Service Request");
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
               
				logger.error("error while closing connection");
			}
		}
		
		return serviceRequest;
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	trackAllRequest
	 - Input Parameters	:	ServiceTrackerBean object
	 - Return Type		:	List
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	29/09/2018
	 - Description		:	view list of service requests of an account from database  
	 ********************************************************************************************************/
	@Override
	public List<ServiceTrackerBean> trackAllRequest(ServiceTrackerBean serviceTracker)	throws BankException {

		conn=DBUtil.getConnection();
		List<ServiceTrackerBean> trackList = new ArrayList<ServiceTrackerBean>();
		try {
			pstmt = conn.prepareStatement(IQueryMapper.VIEW_ALL_SERVICE_REQUEST);
			pstmt.setLong(1, serviceTracker.getAccountId());
			rs = pstmt.executeQuery();
			ServiceTrackerBean serviceRequest = null;
			while(rs.next())
			{
				serviceRequest = new ServiceTrackerBean();
				serviceRequest.setServiceId(rs.getInt(1));
				serviceRequest.setServiceDescription(rs.getString(2));
				serviceRequest.setAccountId(rs.getLong(3));
				serviceRequest.setServiceRaisedDate(rs.getDate(4));
				serviceRequest.setServiceStatus(rs.getString(5));
				trackList.add(serviceRequest);
			}
			
			if(!trackList.isEmpty())
			{
				String message="All Track Requests of Account "+serviceTracker.getAccountId()+" are retrieved";
				logger.info(message);
			}
			else
			{
				String message="No Track Requests found for account "+serviceTracker.getAccountId();
				logger.info(message);
			}
		} catch (SQLException e) {
			
			logger.error("error occured while tracking All Service Request");
			throw new BankException("error occured while tracking All Service Request");
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
                  
				logger.error("error while closing connection");
			}
		}
		return trackList;
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	reqChequeBook
	 - Input Parameters	:	ServiceTrackerBean object
	 - Return Type		:	int
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	29/09/2018
	 - Description		:	gives service id for request from database 
	 ********************************************************************************************************/
	@Override
	public int reqChequeBook(ServiceTrackerBean serviceTracker) throws BankException{

		conn=DBUtil.getConnection();
		int reqServiceId = 0;
		try {
			pstmt=conn.prepareStatement(IQueryMapper.INSERT_REQ_DETAILS);
			pstmt.setString(1,serviceTracker.getServiceDescription());
			pstmt.setLong(2, serviceTracker.getAccountId());
			pstmt.setString(3, serviceTracker.getServiceStatus());
			
			int success = 0;
			success = pstmt.executeUpdate();
			if(success == 0)
			{
				logger.info("error occured while requesting service for account "+serviceTracker.getAccountId());
				throw new BankException("Error occured while requesting service");
			}
			else
			{
				PreparedStatement pst = conn.prepareStatement(IQueryMapper.GET_SERVICE_ID);
				rs = pst.executeQuery();
				if(rs.next())
				{
					reqServiceId = rs.getInt(1);
				}
				logger.info("Service Request successful having id:"+reqServiceId);
			}
		} catch (SQLException e) {
			
			logger.error("error occured while requesting Service");
			throw new BankException("Error while inserting request details");
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {

				logger.error("error while closing connection");
			}
		}
		return reqServiceId;
	}
		

}
		
	

