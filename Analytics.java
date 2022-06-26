package com.thesis.fixitadmin.chart;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.thesis.fixitadmin.model.ModelPost;

public class Analytics {

	public static Map<String, Report> reports;

	public static void sampleUsage(){

		List<ModelPost> posts = new ArrayList<ModelPost>(); //to implement by getting data from database
		generateReport(posts);

		System.out.println("Electrical Complaints");
		System.out.println("Total complaints: " + getTotalPerType("Electrical Complaints"));
		System.out.println("Total complaints in June: " + getTotalPerMonth("Electrical Complaints", "Jun"));
		System.out.println("Total complaints in June week 2: " + getTotalPerWeek("Electrical Complaints", "Jun", "week2"));
		System.out.println("Total complaints in June week 2 mondays: " + getTotalPerDayOfWeek("Electrical Complaints", "Jun", "week2", "Mon"));
		System.out.println("Total complaints in July week 2 mondays: " + getTotalPerDayOfWeek("Electrical Complaints", "Jul", "week2", "Mon"));

		System.out.println("Public Incident Complaints");
		System.out.println("Total complaints: " + getTotalPerType("Public Incidents"));
		System.out.println("Total complaints in June: " + getTotalPerMonth("Public Incidents", "Jun"));
		System.out.println("Total complaints in April: " + getTotalPerMonth("Public Incidents", "Apr"));
		System.out.println("Total complaints in April week 4: " + getTotalPerWeek("Public Incidents", "Apr", "week4"));
		System.out.println("Total complaints in April week 4 saturdays: " + getTotalPerDayOfWeek("Public Incidents", "Apr", "week4", "Sat"));
		System.out.println("Total complaints in April week 4 sundays: " + getTotalPerDayOfWeek("Public Incidents", "Apr", "week4", "Sun"));
		System.out.println("Total complaints in July week 2 mondays: " + getTotalPerDayOfWeek("Public Incidents", "Jul", "week2", "Mon"));

	}


	@SuppressLint("NewApi")
	public static void generateReport(List<ModelPost> posts)
	{
		reports = new HashMap<String, Report>();

		for (ModelPost post:posts) {
			String pTime = post.getpTime();
			Date d = new Date(Long.valueOf(pTime));
			String []splitsdate = d.toString().split(" ");

			String dayOfWeek = splitsdate[0];
			String month = splitsdate[1];
			String day = splitsdate[2];
			String week = getWeek(Integer.parseInt(day));

			reports.putIfAbsent(post.getType(), new Report());

			Report report = reports.get(post.getType());
			report.setTotal(report.getTotal() + 1);

			if (report.getMonthly() == null)
			{
				report.setMonthly(new HashMap<String, Monthly>());
			}

			Map<String, Monthly> monthly = report.getMonthly();

			monthly.putIfAbsent(month, new Monthly());
			monthly.get(month).setTotal(monthly.get(month).getTotal() + 1);

			if (monthly.get(month).getWeekly() == null)
			{
				monthly.get(month).setWeekly(new HashMap<String,Weekly>());
			}

			Map<String, Weekly> weekly = monthly.get(month).getWeekly();

			weekly.putIfAbsent(week, new Weekly());
			weekly.get(week).setTotal(weekly.get(week).getTotal() + 1);

			if(weekly.get(week).getDaily() == null)
			{
				weekly.get(week).setDaily(new HashMap<String, Integer>());
			}

			Map<String, Integer> daily = weekly.get(week).getDaily();
			daily.putIfAbsent(dayOfWeek, 0);
			daily.put(dayOfWeek, daily.get(dayOfWeek) + 1);
		}
	}

	public static int getTotalPerType(String type)
	{
		try {
			return reports.get(type).getTotal();
		}
		catch (NullPointerException e) {
			return 0;
		}
	}
	public static int getTotalPerMonth(String type, String month)
	{
		try {
			return reports.get(type).getMonthly().get(month).getTotal();
		}
		catch (NullPointerException e) {
			return 0;
		}
	}
	public static int getTotalPerWeek(String type, String month, String week)
	{
		try {
			return reports.get(type).getMonthly().get(month).getWeekly().get(week).getTotal();
		}
		catch (NullPointerException e) {
			return 0;
		}
	}

	public static int getTotalPerDayOfWeek(String type, String month, String week, String dayOfWeek)
	{
		try {
			return reports.get(type).getMonthly().get(month).getWeekly().get(week).getDaily().get(dayOfWeek);
		}
		catch (NullPointerException e) {
			return 0;
		}
	}

	private static String getWeek(int day)
	{
		if (day >= 22 )
		{
			return "week4";
		} else if (day >= 15)
		{
			return "week3";
		} else if (day >= 8)
		{
			return "week2";
		} else {
			return "week1";
		}
	}
}

class Monthly{
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Map<String, Weekly> getWeekly() {
		return weekly;
	}
	public void setWeekly(Map<String, Weekly> weekly) {
		this.weekly = weekly;
	}
	private int total;
	private Map<String, Weekly> weekly;

}
class Weekly{
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Map<String, Integer> getDaily() {
		return daily;
	}
	public void setDaily(Map<String, Integer> daily) {
		this.daily = daily;
	}
	private int total;
	private Map<String, Integer> daily;



}

class Report {
	public Map<String, Monthly> getMonthly() {
		return monthly;
	}
	public void setMonthly(Map<String, Monthly> monthly) {
		this.monthly = monthly;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	private int total;
	private Map<String, Monthly> monthly;


}