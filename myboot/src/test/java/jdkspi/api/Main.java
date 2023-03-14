package jdkspi.api;

import java.util.ServiceLoader;

public class Main {
	public static void main(String[] args) {
		ServiceLoader<Command> serviceLoader = ServiceLoader.load(Command.class);
		
		for (Command command : serviceLoader) {
			command.execute();
		}
	}
}
