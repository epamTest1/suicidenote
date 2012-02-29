/*package com.epam.projectname.utils;

import java.io.File;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.epam.fourseasons.tests.core.BaseExcelDataProvider;
import com.epam.fourseasons.tests.core.Utils;

*//**
 * Warning!!!
 * This file is performs actions ONLY with "boookingDataFile", that means this file MUST NOT contain ACTIONS with OTHER FILE  
 * Sheets names and indexes
 * Version info 0
 * Contents 1
 * A1-St.1-inv. 2
 * A1-St.1-val. 3
 * A2-St.2-inv. 4 
 * A2-St.2-val. 5
 * A2-St.3-inv. 6
 * A2-St.3-val. 7 
 * A3-St.3-inv. 8
 * A3-St.3-val. 9
 * A2-St.4-inv. 10 
 * A2-St.4-val. 11 
 * A3-St.4-val. 12
 * A4-St.4-val. 13
 * A5-St.4-val. 14
 * A6-St.4-val. 15
 * A7-St.4-val. 16
 * TODO: Return list instead of 2xArrays
 *//*
public class BookingDataProvider {
	
	private static File workbook;
	@Parameters({ "boookingDataFile" })
	public BookingDataProvider(String boookingDataFileProperty){		
		workbook = new File(boookingDataFileProperty);
		System.out.println(boookingDataFileProperty);
	}

	*//**
	 * A1-St.1-inv. 1
	 * @param startRowIndex
	 * @return
	 *//*
	private static String[][] getInvalidFirstStepDataS1 (int startRowIndex){		
		return BaseExcelDataProvider.loadData(workbook, 1, 11, startRowIndex);
	}
	
	public static String[][] getInvalidFirstStepDataS1 (){
		int max = 61;
		return getInvalidFirstStepDataS1( Utils.getRandomValue(max));
	}
	
	*//**
	 * A1-St.1-val. 2
	 * @param startRowIndex
	 * @return
	 *//*
	public static String[][] getValidFirstStepDataS1 (int startRowIndex){			
		return BaseExcelDataProvider.loadData(workbook, 2, 11, startRowIndex);
	}
	
	public static String[][] getValidFirstStepDataForCalendarTest (int startRowIndex){			
		return BaseExcelDataProvider.loadData(workbook, 2, 11, startRowIndex);
	}
	
	
	public static String[][] getValidFirstStepDataS1 (){	
		int max = 74;
		return getValidFirstStepDataS1( Utils.getRandomValue(max));		
	}

	
	*//**
	 *  A2-St.2-inv. 3
	 * @param startRowIndex
	 * @return
	 *//*
	private static String[][] getInvalidSecondStepDataS2 (int startRowIndex){			
		return BaseExcelDataProvider.loadData(workbook, 3, 7, startRowIndex);
	}
	
	public static String[][] getInvalidSecondStepDataS2 (){	
		int max = 48;
		return getInvalidSecondStepDataS2( Utils.getRandomValue(max)); 
	}
	
	
	*//**
	 * A2-St.2-val. 4
	 * @param startRowIndex
	 * @return
	 *//*
	private static String[][] getValidSecondStepDataS2 (int startRowIndex){	
		return BaseExcelDataProvider.loadData(workbook, 4, 7, startRowIndex);
	}
	public static String[][] getValidSecondStepDataS2 (){	
		int max = 48;
		return getValidSecondStepDataS2( Utils.getRandomValue(max));
	}
	
	
	*//**
	 * A2-St.3-inv. 5
	 * @param startRowIndex
	 * @return
	 *//*
	private static String[][] getInvalidPaymentDataS3 (int startRowIndex){			
		return BaseExcelDataProvider.loadData(workbook, 5, 14, startRowIndex);
	}
	
	public static String[][] getInvalidPaymentDataS3 (){	
		int max = 406;
		int ranNum = Utils.getRandomValue(max);
//		System.out.println(ranNum);
		return getInvalidPaymentDataS3(ranNum );
	}
	
	*//**
	 * A2-St.3-val. 6
	 * @param startRowIndex
	 * @return
	 *//*
	private static String[][] getValidPaymentDataS3 (int startRowIndex){	
		return BaseExcelDataProvider.loadData(workbook, 6, 14, startRowIndex);
	}
	
	public static String[][] getValidPaymentDataS3 (){	
		int max = 82;
		return getValidPaymentDataS3( Utils.getRandomValue(max));
	}
	
	
	*//**
	 * A3-St.3-inv. 7
	 * @param startRowIndex
	 * @return
	 *//*
	private static String[][] getInvalidPasswordDataS3 (int startRowIndex){			
		return BaseExcelDataProvider.loadData(workbook, 7, 3, startRowIndex);
	}
	public static String[][] getInvalidPasswordDataS3 (){	
		int max = 22;
		return getInvalidPasswordDataS3( Utils.getRandomValue(max));
	}
	
	
	*//**
	 * A3-St.3-val. 8
	 * @param startRowIndex
	 * @return
	 *//*
	public static String[][] getValidPasswordDataS3 (int startRowIndex){		
		return BaseExcelDataProvider.loadData(workbook, 8, 3, startRowIndex);
	}
	public static String[][] getValidPasswordDataS3 (){		
		int max = 5;
		return getValidPasswordDataS3(Utils.getRandomValue( Utils.getRandomValue(max)));
	}
	
	
	*//**
	 *  A2-St.4-inv. 9
	 * @param startRowIndex
	 * @return
	 *//*
	private static String[][] getInvalidGuestsDataS4 (int startRowIndex){		
		return BaseExcelDataProvider.loadData(workbook, 9, 3, startRowIndex);
	}
	
	public static String[][] getInvalidGuestsDataS4 (){
		int max = 129;
		return getInvalidGuestsDataS4( Utils.getRandomValue(max));
	}
	
	
	*//**
	 *  A2-St.4-val. 10
	 * @param startRowIndex
	 * @return
	 *//*
	private static String[][] getValidGuestsDataS4 (int startRowIndex){				
		return BaseExcelDataProvider.loadData(workbook, 10, 3, startRowIndex);
	}
	
	public static String[][] getValidGuestsDataS4 (){		
		int max = 54;
		return getValidGuestsDataS4( Utils.getRandomValue(max));
	}
	
	
	*//**
	 *  A3-St.4-val. 11
	 * @param startRowIndex
	 * @return
	 *//*
	private static String[][] getValidSmokingDataS4 (int startRowIndex){		
		return BaseExcelDataProvider.loadData(workbook, 11, 1, startRowIndex);
	}
	
	public static String[][] getValidSmokingDataS4 (){
		int max = 2;
		return getValidSmokingDataS4( Utils.getRandomValue(max));
	}
	
	*//**
	 *  A4-St.4-val. 12
	 * @param startRowIndex
	 * @return
	 *//*
	private static String[][] getValidAmenitiesDataS4 (int startRowIndex){		
		return BaseExcelDataProvider.loadData(workbook, 12, 2, startRowIndex);
	}
	
	public static String[][] getValidAmenitiesDataS4 (){
		int max = 4;
		return getValidAmenitiesDataS4( Utils.getRandomValue(max));
	}
	
	
	*//**
	 *  A5-St.4-val. 13
	 * @param startRowIndex
	 * @return
	 *//*
	private static String[][] getValidTripDetailsDataS4 (int startRowIndex){		
		return BaseExcelDataProvider.loadData(workbook, 13, 3, startRowIndex);
	}
	
	public static String[][] getValidTripDetailsDataS4 (){
		int max = 36;
		return getValidTripDetailsDataS4( Utils.getRandomValue(max));
	}
	
	*//**
	 *  A6-St.4-val. 14
	 * @param startRowIndex
	 * @return
	 *//*
	private static String[][] getValidTransportaitionDataS4 (int startRowIndex){		
		return BaseExcelDataProvider.loadData(workbook, 14, 5, startRowIndex);
	}
	
	
	public static String[][] getValidTransportaitionDataS4 (){
		int max = 16;
		return getValidTransportaitionDataS4( Utils.getRandomValue(max));
	}
	
	*//**
	 *  A7-St.4-val. 15
	 * @param startRowIndex
	 * @return
	 *//*
	private static String[][] getValidSpecialRequestDataS4 (int startRowIndex){		
		return BaseExcelDataProvider.loadData(workbook, 15, 1, startRowIndex);
	}
	
	public static String getValidSpecialRequestDataS4 (){	
		int max = 6;
		return getValidSpecialRequestDataS4( Utils.getRandomValue(max))[0][0];
	}
	
	*//**
	 *  general_questions_val 18
	 * @param startRowIndex
	 * @return
	 *//*

	private static String[][] getValidGeneralQuestionsData (int startRowIndex){		
		return BaseExcelDataProvider.loadData(workbook, 18, 8, startRowIndex);
	}
	
	public static String[][] getValidGeneralQuestionsData (){	
		int max = 59;
		return getValidGeneralQuestionsData( Utils.getRandomValue(max));
	}

		
	@Test(groups = { "system" })
	public static void testValidFirstStepDataS1() {
		String[][] result = BookingDataProvider.getValidFirstStepDataS1(3);
		System.out.println(result[0][0]);
		System.out.println(result[0][1]);
		System.out.println(result[0][2]);
		System.out.println(result[0][3]);
		System.out.println(result[0][4]);
		System.out.println(result[0][5]);
	}
	
	@Test(groups = { "system" })
	public static void testValidPaymentDataS3() {
		String[][] result = BookingDataProvider.getValidPaymentDataS3(3);
		System.out.println(result[0][0]);
		System.out.println(result[0][1]);
		System.out.println(result[0][2]);
		System.out.println(result[0][3]);
		System.out.println(result[0][4]);
		System.out.println(result[0][5]);
		System.out.println(result[0][6]);
		System.out.println(result[0][7]);
		System.out.println(result[0][8]);
		System.out.println(result[0][9]);
		System.out.println(result[0][10]);
		System.out.println(result[0][11]);
		System.out.println(result[0][12]);		
	}

}
*/