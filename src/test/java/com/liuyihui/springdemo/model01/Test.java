package com.liuyihui.springdemo.model01;

public class Test {
	@org.junit.Test
	public void test(){
		int[] arr = new int[] {8,2,1,0,3};
		int[] index = new int[] {2,0,3,2,4,0,1,3,2,3,3};
		String tel = "";
		for (int i : index){
			tel += arr[i];
		}
		System.out.println("联系方式："+tel);
	}
}
