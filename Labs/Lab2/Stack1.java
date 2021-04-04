/*
 * ----------Lab2 Stack1--------------
 * Name: Daniel Elmaliach ,ID: 206068629
 */
package lab2;
public class Stack1 {
	private int[] data;
	private int top;
	public Stack1() {
		top = -1;
		data = new int[10];
	}
	public void push(int value) {
		top += 1;
		if (top < data.length)
			data[top] = value;
		else {
			int[] temp = new int[data.length + 5];
			for (int i = 0; i < data.length; i++)
				temp[i] = data[i];
			temp[top]=value;
			data = temp;
		}
	}
	public int pop() {return data[top--];}
	public int top() {return data[top];}
	public boolean empty() {return top==-1;}
	public String toString() {
		String temp="";
		for (int i = 1; i <= top; i++)
			temp+=", "+data[i];
		return "["+data[0]+temp+"]" ;
	}
}
