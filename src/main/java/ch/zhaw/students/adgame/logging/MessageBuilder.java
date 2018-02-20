package ch.zhaw.students.adgame.logging;

import java.io.OutputStream;
import java.io.PrintStream;

class MessageBuilder extends PrintStream {
	private StringBuilder builder;

	public MessageBuilder(OutputStream fallback) {
		super(fallback);
		builder = new StringBuilder();
	}
	
	public String getMessage() {
		return builder.toString();
	}
	
	public void clearMessage() {
		builder = new StringBuilder();
	}
	
	@Override
	public void print(boolean b) {
		builder.append(b);
	}
	
	@Override
	public void print(char c) {
		builder.append(c);
	}
	
	@Override
	public void print(char[] s) {
		builder.append(s);
	}
	
	@Override
	public void print(double d) {
		builder.append(d);
	}
	
	@Override
	public void print(float f) {
		builder.append(f);
	}
	
	@Override
	public void print(int i) {
		builder.append(i);
	}
	
	@Override
	public void print(long l) {
		builder.append(l);
	}
	
	@Override
	public void print(Object obj) {
		builder.append(obj);
	}
	
	@Override
	public void print(String s) {
		builder.append(s);
	}
	
	@Override
	public void println() {
		builder.append("\n");
	}
	
	@Override
	public void println(boolean x) {
		print(x);
		println();
	}
	
	@Override
	public void println(char x) {
		print(x);
		println();
	}
	
	@Override
	public void println(char[] x) {
		print(x);
		println();
	}
	
	@Override
	public void println(double x) {
		print(x);
		println();
	}
	
	@Override
	public void println(float x) {
		print(x);
		println();
	}
	
	@Override
	public void println(int x) {
		print(x);
		println();
	}
	
	@Override
	public void println(long x) {
		print(x);
		println();
	}
	
	@Override
	public void println(Object x) {
		print(x);
		println();
	}
	
	@Override
	public void println(String x) {
		print(x);
		println();
	}
}
