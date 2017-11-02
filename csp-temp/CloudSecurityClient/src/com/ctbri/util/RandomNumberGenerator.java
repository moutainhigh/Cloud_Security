package com.ctbri.util;

import java.util.Random;

public class RandomNumberGenerator {
	 /** 
     * ���ǵ��͵����ϴ���㷨�� 
     * �����Ǵӱ�ѡ������ѡ��һ������Ŀ�������У���ѡȡ������ӱ�ѡ�����Ƴ������󣬲���Сѡ������ 
     * �㷨ʱ�临�Ӷ�O(n) 
     * @return ���8Ϊ���ظ����� 
     */ 
    public static String generateNumber() {  
        String no="";  
        //��ʼ����ѡ����  
        int[] defaultNums = new int[10];  
        for (int i = 0; i < defaultNums.length; i++) {  
            defaultNums[i] = i;  
        }  
   
        Random random = new Random();  
        int[] nums = new int[LENGTH];  
        //Ĭ�������п���ѡ��Ĳ��ֳ���  
        int canBeUsed = 10;  
        //���Ŀ������  
        for (int i = 0; i < nums.length; i++) {  
            //�����ѡȡ�����ִ���Ŀ������  
            int index = random.nextInt(canBeUsed);  
            nums[i] = defaultNums[index];  
            //�����ù�������ӵ���ѡ������󣬲���С��ѡ����  
            swap(index, canBeUsed - 1, defaultNums);  
            canBeUsed--;  
        }  
        if (nums.length>0) {  
            for (int i = 0; i < nums.length; i++) {  
                no+=nums[i];  
            }  
        }  
   
        return no;  
    }  
    private static final int LENGTH = 8;  
   
    private static void swap(int i, int j, int[] nums) {  
        int temp = nums[i];  
        nums[i] = nums[j];  
        nums[j] = temp;  
    }  
       
    public static String generateNumber2() {  
        String no="";  
        int num[]=new int[8];  
        int c=0;  
        for (int i = 0; i < 8; i++) {  
            num[i] = new Random().nextInt(10);  
            c = num[i];  
            for (int j = 0; j < i; j++) {  
                if (num[j] == c) {  
                    i--;  
                    break;  
                }  
            }  
        }  
        if (num.length>0) {  
            for (int i = 0; i < num.length; i++) {  
                no+=num[i];  
            }  
        }  
        return no;  
    }  
   
    public static void main(String[] args) {  
        for (int i = 0; i < 10; i++) {  
//            System.out.println(generateNumber());  
        }  
        System.out.println(generateNumber2());  
    } 
}
