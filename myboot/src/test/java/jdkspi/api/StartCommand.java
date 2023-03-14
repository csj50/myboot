package jdkspi.api;

public class StartCommand implements Command {

	@Override
	public void execute() {
		System.out.println("start...");
	}

}
