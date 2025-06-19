
package xyz;

class Calculator{
	int calculator(int a, int b) {
		return a+b;
	}
	double calculate(double a,double b) {
		return a*b;
	}
	int calculate(int a,int b,int c) {
		return a*b*c;
	}
}
public class OverloadingExample {

	public static void main(String[] args) {
		Calculator c = new Calculator();
		System.out.println(c.calculator(12,30));
		System.out.println(c.calculate(12.5,3.5));
		System.out.println(c.calculate(2,3,4));
	}

}
