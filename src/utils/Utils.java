package utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import env.jme.Situation;

public class Utils {
	public static void saveSituation(String file, Situation sit)
	{
		if(sit == null) return;
		String res = sit.toCSVFile();
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		SimpleDateFormat ft = new SimpleDateFormat("MM-dd-YYYY-HH-mm-ss");
		String id = ft.format(date);
		System.out.println(res);
		try{
		    PrintWriter writer = new PrintWriter(file + "/Mosimu_"+id+".csv", "UTF-8");
		    writer.println(res);
		    writer.close();
		    System.out.println("Execution result saved in " + file);
		} catch (IOException e) {
		  System.out.println(e);
		  System.out.println("Experiment saving failed");
		}
	}
}
