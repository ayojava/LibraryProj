/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.util;

import java.util.Calendar;
import java.util.Random;

/**
 *
 * @author ayojava
 */
public class RandomUtil 
{
    private static char[] SYMBOLS = {'#', '&', '*', '!', '%', '@', '?'};
    private static RandomUtil randomNumberGenerator;
    private Random random;
    private long min;
    private long max;
    private long seed;
    private static final String ALPHA_NUM = "0123456789";

    public RandomUtil() {
        random = new Random(System.currentTimeMillis());
    }

    public RandomUtil(long seed) {
        this.seed = seed;
        random = new Random(seed);
    }

    public static RandomUtil getInstance() {
        if (randomNumberGenerator != null) {
            return randomNumberGenerator;
        } else {
            randomNumberGenerator = new RandomUtil();
            return randomNumberGenerator;
        }
    }

    /**
     * @return the min
     */
    public long getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(long min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public long getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(long max) {
        this.max = max;
    }

    /**
     * @return the seed
     */
    public long getSeed() {
        return seed;
    }

    /**
     * @param seed the seed to set
     */
    public void setSeed(long seed) {
        random.setSeed(seed);
        this.seed = seed;
    }

    //***************************************
    /**
     * Utility method for generating random double values in the range
     * specified by the low and high attributes. Used during the
     * storeIventory initialization.
     *
     * @param min  min range of the random number to be generated.
     * @param max max range of the random number to be generated.
     * @return random number in the between the numbers low and high.
     */
    public double getRandomDouble(double min, double max) {
        double rand = random.nextDouble();
        double x = rand * (max - min);
        return x + min;
    }

    public double getRandomDouble() {
        double rand = random.nextDouble();
        double x = rand * (max - min);
        return x + min;
    }

    public long getRandomLong(double low, double high) {
        return (long) getRandomDouble(low, high);
    }

    public long getRandomLong() {
        return (long) getRandomDouble();
    }

    /**
     * Utility method for generating random double values in the range
     * specified by the low and high attributes. Used during the
     * storeIventory initialization.
     *
     * @param min  min range of the random number to be generated.
     * @param max max range of the random number to be generated.
     * @return random number in the between the numbers low and high.
     */
    public int getRandomInteger(int min, int max) {

        return (int) getRandomDouble(min, max);
    }

    public int getRandomInteger() {
        return (int) getRandomDouble();
    }

    public char getRandomAlphabet() {
        return Character.forDigit(getRandomInteger(10, 36), 36);
    }

//    public static void main(String... a){
//      //  System.out.println(Character.forDigit(10, 10)+" dig");
//        for(int i=0;i<10;i++){
//            int x=RandomGenerator.getInstance().random.nextInt(10);
//            System.out.println(x+"  x");
//            System.out.println(Character.forDigit(x, 10)+" dig");
//        //System.out.println(RandomGenerator.getInstance(). getRandomPassword(10,12,false));
//        }
//
//    }
    public String getRandomPassword(int min, int max, boolean includeSymbols) {
        int passwordLength = getRandomInteger(min, max);
        char[] passwordChars = new char[passwordLength];
        for (int x = 0; x < passwordLength; x++) {
            int charType = random.nextInt(includeSymbols ? 4 : 3);
            switch (charType) {
                case 0:
                    passwordChars[x] = Character.forDigit(random.nextInt(10), 10);
                    break;
                case 1:
                    passwordChars[x] = getRandomAlphabet();
                    break;
                case 2:
                    passwordChars[x] = Character.toUpperCase(getRandomAlphabet());
                    break;
                case 3:
                    passwordChars[x] = SYMBOLS[random.nextInt(SYMBOLS.length)];
                    break;
                default:
                    passwordChars[x] = Character.forDigit(random.nextInt(10), 10);
                    break;

            }
        }
        return new String(passwordChars);
    }

    public String getRandomPassword(int min, int max, boolean includeSymbols, Boolean uppercase) {
        int passwordLength = getRandomInteger(min, max);
        char[] passwordChars = new char[passwordLength];
        for (int x = 0; x < passwordLength; x++) {
            int charType = random.nextInt(includeSymbols ? 4 : 3);
            switch (charType) {
                case 0:
                    passwordChars[x] = Character.forDigit(random.nextInt(10), 10);
                    break;
                case 1:
                    passwordChars[x] = getRandomAlphabet();
                    passwordChars[x] = uppercase != null && uppercase ? Character.toUpperCase(passwordChars[x]) : passwordChars[x];
                    break;
                case 2:
                    passwordChars[x] = uppercase == null || uppercase ? Character.toUpperCase(getRandomAlphabet()) : getRandomAlphabet();
                    break;
                case 3:
                    passwordChars[x] = SYMBOLS[random.nextInt(SYMBOLS.length)];
                    break;
                default:
                    passwordChars[x] = Character.forDigit(random.nextInt(10), 10);
                    break;

            }

        }
        return new String(passwordChars);
    }

    public static String getRandomPassword(int type) {
        Long password = new Long(System.currentTimeMillis());
        String passwordv = password.toString();
        int value1 = 0;
        StringBuilder sb = new StringBuilder(11);
        if (type == 1) {
            value1 = 11;
        } else if (type == 2) {
            value1 = 27;
        }
        for (int i = 0; i < value1; i++) {
            int ndx = (int) (Math.random() * ALPHA_NUM.length());
            sb.append(ALPHA_NUM.charAt(ndx));
        }
        String value = sb.toString() + passwordv.substring(8, 13);
        return value;
    }

//    //***********************
//    public static void main(String[] a) {
//        // Calendar c = Calendar.getInstance();
//        RandomUtil r = new RandomUtil();
//        // String date = Integer.toString(c.get(Calendar.YEAR)) + r.trimToLast2digit(c.get(Calendar.MONTH) + 1) + r.trimToLast2digit(c.get(Calendar.DATE));
//        // String time = r.trimToLast2digit(c.get(Calendar.HOUR)) + r.trimToLast2digit(c.get(Calendar.MINUTE)) + r.trimToLast2digit(c.get(Calendar.SECOND));
//        System.out.println(r.getRandomPassword(8, 8, false,true));
//    }

    public String getRandomDateTime(int min, int max) {
        Calendar c = Calendar.getInstance();
        String date = Integer.toString(c.get(Calendar.YEAR)) + trimToLast2digit(c.get(Calendar.MONTH) + 1) + trimToLast2digit(c.get(Calendar.DATE));
        String time = trimToLast2digit(c.get(Calendar.HOUR)) + trimToLast2digit(c.get(Calendar.MINUTE)) + trimToLast2digit(c.get(Calendar.SECOND));
        return date + time + getRandomInteger(min, max);
    }

    private String trimToLast2digit(int value) {
        String v = Integer.toString(value).trim();

        if (v.length() > 2) {
            v = v.substring(v.length() - 2);
        } else if (v.length() < 2) {
            v = zeroPadding(2, value);
        }
        return v;
    }

    private String zeroPadding(int max, String num) {
        if (num.length() > max) {
            return num;
        }
        max = max - num.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < max; i++) {
            sb.append("0");
        }
        sb.append(num);
        return sb.toString();
    }

    private String zeroPadding(int max, int num) {

        int num_count = String.valueOf(num).length();
        if (num_count > max) {
            return Integer.toString(num);
        }
        max = max - num_count;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < max; i++) {
            sb.append("0");
        }
        sb.append(num);
        return sb.toString();
    }

    private String trim(String v) {
        return v != null ? v.trim() : "";
    }

}
