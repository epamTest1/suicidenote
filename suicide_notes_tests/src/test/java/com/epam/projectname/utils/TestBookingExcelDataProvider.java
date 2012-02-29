/*package com.epam.projectname.utils;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.fourseasons.tests.core.BaseExcelDataProvider;

public class TestBookingExcelDataProvider extends BaseExcelDataProvider {

	private static final String BOOKING_DATA_FILE = "boookingDataFile";

	@DataProvider(name = "A1_St1_inv_boookingDataProvider")
	public static String[][] A1_St1_inv_boookingDataProvider(ITestContext context, Method method) {
		return baseDataProvider(BOOKING_DATA_FILE, 2, context, method);
	}

	@DataProvider(name = "A1_St1_val_boookingDataProvider")
	public static String[][] A1_St1_val_boookingDataProvider(ITestContext context, Method method) {
		return baseDataProvider(BOOKING_DATA_FILE, 3, context, method);
	}

	@Test(groups = { "system" }, dataProvider = "A1_St1_inv_boookingDataProvider")
	public void loginDataTest(String n1, String n2, String n3, String n4, String n5, String n6) {
		System.out.println(n1 + " " + n2 + " " + n3 + " " + n4 + " " + n5 + " " + n6);
	}

	@Test(groups = { "system" }, dataProvider = "A1_St1_val_boookingDataProvider")
	public void workbookXlsDataTest(String n1, String n2, String n3, String n4, String n5, String n6) {
		System.out.println(n1 + " " + n2 + " " + n3 + " " + n4 + " " + n5 + " " + n6);
	}

}
*/