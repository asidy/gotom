package cn.gotom.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.hibernate.ejb.packaging.JarVisitorFactory;

import cn.gotom.pojos.Right;
import cn.gotom.util.Converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Test
{

	public Test()
	{

	}
    public static Date BCDToDateTime()
    {
        GregorianCalendar clc = new GregorianCalendar();
        clc.setTime(new Date());
        clc.set(2013, 11 - 1, 27, 11, 59, 59);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println("yyyy-MM-dd HH:mm:ss.SSS  :" + dateformat.format(clc.getTime()));        
        String time = Converter.format(clc.getTime(),"yyyy-MM-dd HH:mm:ss");
        Date date = Converter.parse(time, "yyyy-MM-dd HH:mm:ss");
        System.out.println("yyyy-MM-dd HH:mm:ss.SSS  :" + dateformat.format(date));
		return clc.getTime();
    }
	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		String[] array = new String[]{"a","b"};
		System.out.println(array.toString());
		BCDToDateTime();
		System.out.println(Pattern.matches("[^cat]", "one cat,two cats in the yard"));
		
		JarVisitorFactory.getURLFromPath("url:/");
		Calendar calendar = Calendar.getInstance();
		calendar.set(2013, 0, 1);
		System.out.println(calendar.getTimeInMillis());
		//1359676800000
		System.out.println(Converter.format(calendar.getTime(), "yyyy-MM-dd 23:59:59"));
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println(new Date()+"今天  :" + dateformat.format(new Date()));
		
		ResourceBundle bundle = ResourceBundle.getBundle("DefaultData", Locale.ROOT);
		String rightString = bundle.getString("cn.gotom.pojos.Right");
		Gson gson = new Gson();
		List<Right> rights = (List<Right>) gson.fromJson(rightString, new TypeToken<List<Right>>(){}.getType());		
		rights = new ArrayList<Right>();
		rights.add(new Right());
		rights.get(0).setSort(1000);
		rights.add(new Right());
		rights.get(1).setSort(900);
		Collections.sort(rights, new Comparator<Right>()
		{

			public int compare(Right arg0, Right arg1)
			{
				return arg0.getSort() > arg1.getSort() ? 1 : 0;
			}
		});
		//		JSON json = JSONSerializer.toJSON(rightString);
//		JsonConfig jsonConfig = new JsonConfig();
//		List<Right> rights = new ArrayList<Right>();
//		jsonConfig.setRootClass(Right.class);
//		rights = (List<Right>) JSONSerializer.toJava(json, jsonConfig);
		System.out.println(rights);
		// JSONArray.toc(json, Right.class);
		// System.out.println(JSONArray.fromObject(resList).toString());
	}

}
