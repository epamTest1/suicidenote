/*package com.epam.projectname.utils;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.fourseasons.tests.core.BaseExcelDataProvider;

*//**
 * This class is aimed to test {@link BaseExcelDataProvider} class
 * 
 * @author Oleksandr_Kachur
 * 
 *//*
public class TestExcelDataProvider extends BaseExcelDataProvider {

	private static final String LOGIN_DATA_FILE = "loginDataFile";
	private static final String WORKBOOK_XLS_DATA_FILE = "workbookXlsDataFile";
	private static final String WORKBOOK_XLSX_DATA_FILE = "workbookXlsxDataFile";
	private static final String BOOKING_DATA_FILE = "boookingDataFile";

	@DataProvider(name = "loginDataProvider")
	public static String[][] loginDataProvider(ITestContext context,
			Method method) {
		return baseDataProvider(LOGIN_DATA_FILE, context, method);
	}
	
	@DataProvider(name = "boookingDataProvider")
	public static String[][] boookingDataProvider(ITestContext context,
			Method method) {
		return baseDataProvider(BOOKING_DATA_FILE, 1, context, method);
	}

	@DataProvider(name = "workbookXlsDataProvider")
	public static String[][] workbookXlsDataProvider(ITestContext context,
			Method method) {
		return baseDataProvider(WORKBOOK_XLS_DATA_FILE, context, method);
	}

	@DataProvider(name = "workbookXlsxDataProvider")
	public static String[][] workbookXlsxDataProvider(ITestContext context,
			Method method) {
		return baseDataProvider(WORKBOOK_XLSX_DATA_FILE, context, method);
	}

	@Test(groups = { "system" }, dataProvider = "loginDataProvider")
	public void loginDataTest(String n1, String n2) {
//		 System.out.println(n1 + " " + n2);
	}

	@Test(groups = { "system" }, dataProvider = "workbookXlsDataProvider")
	public void workbookXlsDataTest(String n1, String n2, String n3, String n4,
			String n5, String n6, String n7, String n8, String n9, String n10) {
	}

	@Test(groups = { "system" }, dataProvider = "workbookXlsxDataProvider")
	public void workbookXlsxDataTest(String n1, String n2, String n3,
			String n4, String n5, String n6, String n7, String n8, String n9,
			String n10) {
	}
}
*/