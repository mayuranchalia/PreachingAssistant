/**
 * 
 */
package com.temple.repository;

/**
 * @author Mayur Jain
 *
 */
public class RepositoryCommunicationFactory {

	
	public static IRepositroyCommunication newInstance() throws Exception{
		
			return new RepositoryCommunication();
	}
}
