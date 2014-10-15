package com.resolve.qa.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Random;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import org.apache.commons.lang.RandomStringUtils;

import com.resolve.qa.framework.InvalidCase;
import com.resolve.qa.framework.common.*;

public class GenerateFilterUtil
{
    private static GenerateFilterUtil instance = null;
    static HashMap<String, Class<?>> conditionToClass = new HashMap<String, Class<?>>();
    static final long TIME_RANGE= 3000000L;
    static final long SECOND= 1000L;
//    final static long MILLSEC_DAY = 1000L*3600L*24L;
    final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    
    protected GenerateFilterUtil()
    {
        super();
        conditionToClass.put("auto", STRING_CONDITION.class);
        conditionToClass.put("date", DATE_CONDITION.class);
        conditionToClass.put("bool", BOOL_CONDITION.class);
        conditionToClass.put("float", FLOAT_CONDITION.class);
    }

    public static GenerateFilterUtil getInstance() {
        if(instance == null) {
           instance = new GenerateFilterUtil();
        }
        return instance;
     }
    
    interface Condition
    {
        String getValue(String raw, boolean positive) throws ParseException;
    }

    public enum DATE_CONDITION implements Condition
    {
        
        on
        {
            @Override
            public String getValue(String raw, boolean positive) throws ParseException
            {
                Date date = dateFormat.parse(XXXtoZ(raw));
                if(positive) return dateToString(date);
                else {
                    Random rand = new Random();
                    date.setTime(date.getTime()+(1L+rand.nextLong()%TIME_RANGE)*SECOND);
                    return dateToString(date);
                }
            }
        },
        before
        {
            @Override
            public String getValue(String raw, boolean positive) throws ParseException
            {
                Date date = dateFormat.parse(XXXtoZ(raw));
                Random rand = new Random();
                if(positive) date.setTime(date.getTime()+(long)(1L+rand.nextDouble()*TIME_RANGE)*SECOND);
                else {
                    date.setTime(date.getTime()-(long)(rand.nextDouble()*TIME_RANGE)*SECOND);
                }
                return dateToString(date);
            }
        },
        after
        {
            @Override
            public String getValue(String raw, boolean positive) throws ParseException
            {
                Date date = dateFormat.parse(XXXtoZ(raw));
                Random rand = new Random();
                if(positive) date.setTime(date.getTime()-(long)(1L+rand.nextDouble()*TIME_RANGE)*SECOND);
                else {
                    date.setTime(date.getTime()+(long)(rand.nextDouble()*TIME_RANGE)*SECOND);
                }
                return dateToString(date);
            }
        };
/*        public Date getDate(String raw)
        {
            Date date = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            
            try
            {
                long l=0;
                if(raw.equalsIgnoreCase("sysDate")) {
                    l = System.currentTimeMillis();
                    l = l-l%MILLSEC_DAY;
                }
                else if(raw.equalsIgnoreCase("sysTime")) {
                    l = System.currentTimeMillis();
                }
                else{
                    l = Long.parseLong(raw);
                }
                date = new Date(l);
            }
            catch (NumberFormatException nfe)
            {
                try
                {
                    date = dateFormat.parse(raw);
                }
                catch (ParseException e)
                {
                    Log.log(Log.getIndent() + "FAIL: wrong date format " + e.getMessage());
                }
            }
            return date;
        }*/

        public String dateToString(Date date)
        {
            return ZtoXXX(dateFormat.format(date));
        }
        public abstract String getValue(String raw, boolean positive) throws ParseException;
    }

    public enum FLOAT_CONDITION implements Condition
    {
        equals
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                if (positive)
                    return raw;
                else
                {
                    Random rand = new Random();
                    if (rand.nextInt() > 0.5)
                        return String.valueOf((int) Float.parseFloat(raw) + 1 + rand.nextInt(10));
                    else
                        return String.valueOf((int) Float.parseFloat(raw) - 1 - rand.nextInt(10));
                }
            }
        },
        notEquals
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                if (!positive)
                    return raw;
                else
                {
                    Random rand = new Random();
                    if (rand.nextInt() > 0.5)
                        return String.valueOf((int) Float.parseFloat(raw) + 1 + rand.nextInt(10));
                    else
                        return String.valueOf((int) Float.parseFloat(raw) - 1 - rand.nextInt(10));
                }
            }
        },
        greaterThan
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                Random rand = new Random();
                if (positive)
                    return String.valueOf((int) Float.parseFloat(raw) - 1 - rand.nextInt(10));
                else
                    return String.valueOf((int) Float.parseFloat(raw) + rand.nextInt(10));
            }
        },
        greaterThanOrEqualTo
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                Random rand = new Random();
                if (positive)
                    return String.valueOf((int) Float.parseFloat(raw) - rand.nextInt(10));
                else
                    return String.valueOf((int) Float.parseFloat(raw) + rand.nextInt(10)) + 1;
            }

        },
        lessThan
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                Random rand = new Random();
                if (!positive)
                    return String.valueOf((int) Float.parseFloat(raw) - rand.nextInt(10));
                else
                    return String.valueOf((int) Float.parseFloat(raw) + rand.nextInt(10)) + 1;
            }
        },
        lessThanOrEqualTo
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                Random rand = new Random();
                if (!positive)
                    return String.valueOf((int) Float.parseFloat(raw) - 1 - rand.nextInt(10));
                else
                    return String.valueOf((int) Float.parseFloat(raw) + rand.nextInt(10));
            }

        };

        public abstract String getValue(String raw, boolean positive);
    }

    public enum BOOL_CONDITION implements Condition
    {
        equals
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                if (positive)
                    return raw;
                else
                {
                    if (raw.equalsIgnoreCase("True"))
                        return new String("False");
                    else
                        return new String("True");
                }
            }
        },
        notEquals
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                if (!positive)
                    return raw;
                else
                {
                    if (raw.equalsIgnoreCase("True"))
                        return new String("False");
                    else
                        return new String("True");
                }
            }
        };

        public abstract String getValue(String raw, boolean positive);
    }

    public enum STRING_CONDITION implements Condition
    {
        equals
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                if (positive)
                    return raw;
                else
                {
                    if (raw.length() == 0) return RandomStringUtils.randomAscii(1);
                    String temp;
                    do
                    {
                        temp = RandomStringUtils.randomAscii(raw.length());
                    } while (raw.equalsIgnoreCase(temp));
                    return temp;
                }
            }
        },
        notEquals
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                if (!positive)
                    return raw;
                else
                {
                    if (raw.length() == 0) return RandomStringUtils.randomAscii(1);
                    String temp;
                    do
                    {
                        temp = RandomStringUtils.randomAscii(raw.length());
                    } while (raw.equalsIgnoreCase(temp));
                    return temp;
                }
            }
        },
        contains
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                if (positive)
                {
                    if (raw.length() == 0) return raw;
                    Random rand = new Random();
                    int start = rand.nextInt(raw.length());
                    int end = rand.nextInt(raw.length() - start) + start + 1;
                    return raw.substring(start, end);
                }
                else
                {
                    if (raw.length() == 0) return RandomStringUtils.randomAscii(1);
                    String temp;
                    do
                    {
                        temp = RandomStringUtils.randomAscii(raw.length());
                        Random rand = new Random();
                        int start = rand.nextInt(raw.length());
                        int end = rand.nextInt(raw.length() - start) + start + 1;
                        temp=temp.substring(start, end);
                    } while (raw.toLowerCase().contains(temp.toLowerCase()));
                    return temp;
                }
            }
        },
        notContains
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                if (!positive)
                {
                    if (raw.length() == 0) return raw;
                    Random rand = new Random();
                    int start = rand.nextInt(raw.length());
                    int end = rand.nextInt(raw.length() - start) + start + 1;
                    return raw.substring(start, end);
                }
                else
                {
                    if (raw.length() == 0) return RandomStringUtils.randomAscii(1);
                    String temp;
                    do
                    {
                        temp = RandomStringUtils.randomAscii(raw.length());
                        Random rand = new Random();
                        int start = rand.nextInt(raw.length());
                        int end = rand.nextInt(raw.length() - start) + start + 1;
                        temp=temp.substring(start, end);
                    } while (raw.toLowerCase().contains(temp.toLowerCase()));
                    return temp;
                }
            }
        },
        startsWith
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                if (positive)
                {
                    if (raw.length() == 0) return raw;
                    Random rand = new Random();
                    int end = rand.nextInt(raw.length()) + 1;
                    return raw.substring(0, end);
                }
                else
                {
                    if (raw.length() == 0) return RandomStringUtils.randomAscii(1);
                    String temp;
                    do
                    {
                        temp = RandomStringUtils.randomAscii(raw.length());
                        Random rand = new Random();
                        int start = rand.nextInt(raw.length());
                        temp=temp.substring(start, raw.length());
                    } while (raw.toLowerCase().startsWith(temp.toLowerCase()));
                    return temp;
                }
            }
        },
        notStartsWith
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                if (!positive)
                {
                    if (raw.length() == 0) return raw;
                    Random rand = new Random();
                    int end = rand.nextInt(raw.length()) + 1;
                    return raw.substring(0, end);
                }
                else
                {
                    if (raw.length() == 0) return RandomStringUtils.randomAscii(1);
                    String temp;
                    do
                    {
                        temp = RandomStringUtils.randomAscii(raw.length());
                        Random rand = new Random();
                        int start = rand.nextInt(raw.length());
                        temp=temp.substring(start, raw.length());
                    } while (raw.toLowerCase().startsWith(temp.toLowerCase()));
                    return temp;
                }
            }
        },
        endsWith
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                if (positive)
                {
                    if (raw.length() == 0) return raw;
                    Random rand = new Random();
                    int start = rand.nextInt(raw.length());
                    return raw.substring(start, raw.length());
                }
                else
                {
                    if (raw.length() == 0) return RandomStringUtils.randomAscii(1);
                    String temp;
                    do
                    {
                        temp = RandomStringUtils.randomAscii(raw.length());
                        Random rand = new Random();
                        int end = rand.nextInt(raw.length()) + 1;
                        temp=temp.substring(0, end);
                    } while (raw.toLowerCase().endsWith(temp.toLowerCase()));
                    return temp;
                }
            }
        },
        notEndsWith
        {
            @Override
            public String getValue(String raw, boolean positive)
            {
                if (!positive)
                {
                    if (raw.length() == 0) return raw;
                    Random rand = new Random();
                    int start = rand.nextInt(raw.length());
                    return raw.substring(start, raw.length());
                }
                else
                {
                    if (raw.length() == 0) return RandomStringUtils.randomAscii(1);
                    String temp;
                    do
                    {
                        temp = RandomStringUtils.randomAscii(raw.length());
                        Random rand = new Random();
                        int end = rand.nextInt(raw.length()) + 1;
                        temp=temp.substring(0, end);
                    } while (raw.toLowerCase().endsWith(temp.toLowerCase()));
                    return temp;
                }
            }
        };

        public abstract String getValue(String raw, boolean positive);
    }

    static public String ZtoXXX(String date) {
        return date.substring(0, date.length()-2).concat(new String(":").concat(date.substring(date.length()-2, date.length())));
    }
    
    static public String XXXtoZ(String date) {
        return date.substring(0, date.length()-3).concat(date.substring(date.length()-2, date.length()));
    }
    
    static public String getDate(String raw)
    {
        Date date = null;
        try
        {
            long l=0;
            if(raw.equalsIgnoreCase("sysDate")) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY,0);
                cal.set(Calendar.MINUTE,0);
                cal.set(Calendar.SECOND,0);
                cal.set(Calendar.MILLISECOND,0);
                date = cal.getTime();
//                l = System.currentTimeMillis();
          //      l = l-l%MILLSEC_DAY;
            }
            else {
                if(raw.equalsIgnoreCase("sysTime")) {
            
                    l = System.currentTimeMillis();
                }
                else{
                    l = Long.parseLong(raw);
                }
                date = new Date(l);
            }
            
        }
        catch (NumberFormatException nfe)
        {
            try
            {
                date = dateFormat.parse(XXXtoZ(raw));
            }
            catch (ParseException e)
            {
                Log.log(Log.getIndent() + "FAIL: wrong date format " + e.getMessage());
            }
        }
        return ZtoXXX(dateFormat.format(date));
    }
    
    public static <T> ArrayList<T> generateFilter(ArrayList<T> validCase, boolean positive) throws Exception
    {
        
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode filters;
        boolean isAll = false;
        InvalidCase cacheInvalidCase = null;
        for (int i = 0; i < validCase.size(); i++)
        {
            isAll = false;
            if(positive) filters = (ArrayNode) mapper.readTree((String)validCase.get(i));
            else {
                cacheInvalidCase = (InvalidCase)validCase.get(i);
                filters = (ArrayNode) mapper.readTree(cacheInvalidCase.getValue());
            }
            
            if (!filters.isArray()) throw new Exception("Filter should be array");
            for (int j = 0; j < filters.size(); j++)
            {
                ObjectNode obj = (ObjectNode) filters.get(j);

                if(obj.get("type").getTextValue().equals("date")) {
                    String date = getDate(obj.get("value").getTextValue());
                    obj.remove("value");
                    obj.put("value", date);

                    // [NumaCanedo] [10/02/2014] [START] Replace sysDate String not being replaced in filter Fix
                    if(positive)validCase.set(i, (T)filters.toString());
                    else validCase.set(i, (T)new InvalidCase(filters.toString(),cacheInvalidCase.getHandleResponse(),cacheInvalidCase.getTestOps()));
                    // [NumaCanedo] [10/02/2014] [ END ] Replace sysDate String not being replaced in filter Fix
                }
                if (obj.get("condition").getTextValue().equals("all"))
                {
                    isAll = true;
                    String type = obj.get("type").getTextValue();
                    if (!conditionToClass.containsKey(type)) throw new Exception("Currently, it doesn't support type " + obj.get("type") + " in filter");

                    for (Object condition : EnumSet.allOf((Class<Enum>) conditionToClass.get(type)))
                    {
                        String value = ((Condition) condition).getValue(obj.get("value").getTextValue(), positive);
                        ObjectNode temp = (ObjectNode) mapper.readTree(obj.toString());
                        ArrayNode tempFilter = (ArrayNode) mapper.readTree(filters.toString());

                        temp.remove("value");
                        temp.put("value", value);
                        temp.remove("condition");
                        temp.put("condition", condition.toString());

                        tempFilter.remove(j);
                        tempFilter.add(temp);
                        if(positive)validCase.add((T)tempFilter.toString());
                        else validCase.add((T)new InvalidCase(tempFilter.toString(),cacheInvalidCase.getHandleResponse(),cacheInvalidCase.getTestOps()));
                    }
                }
            }
            if (isAll)
            {
                validCase.remove(i);
                i--;
            }

        }
        ArrayList<String> listValue = new ArrayList<String>();

        return validCase;
    }
}
