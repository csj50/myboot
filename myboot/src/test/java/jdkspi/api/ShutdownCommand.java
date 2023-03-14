package jdkspi.api;

public class ShutdownCommand implements Command {

	@Override
	public void execute() {
		System.out.println("shutdown...");
	}

}
