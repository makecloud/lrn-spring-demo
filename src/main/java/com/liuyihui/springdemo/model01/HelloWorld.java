package com.liuyihui.springdemo.model01;

import org.joda.time.LocalTime;

public class HelloWorld {
	public static void main(String[] args) {
		LocalTime currentTime = new LocalTime();
		System.out.println(currentTime);
		Greeter greeter = new Greeter();
		System.out.println();
		System.out.println(greeter.sayHello());
	}
}
