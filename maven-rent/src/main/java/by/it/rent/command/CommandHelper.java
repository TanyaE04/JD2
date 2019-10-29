package by.it.rent.command;

import java.util.HashMap;
import java.util.Map;

import by.it.rent.command.impl.AddCar;
import by.it.rent.command.impl.AddOrder;
import by.it.rent.command.impl.AuthorizationCommand;
import by.it.rent.command.impl.BlockUser;
import by.it.rent.command.impl.NoSuchCommand;
import by.it.rent.command.impl.OrderCar;
import by.it.rent.command.impl.OrderData;
import by.it.rent.command.impl.ChooseCar;
import by.it.rent.command.impl.CompleteOrder;
import by.it.rent.command.impl.Damage;
import by.it.rent.command.impl.EditOrder;
import by.it.rent.command.impl.EditUser;
import by.it.rent.command.impl.FilterSearch;
import by.it.rent.command.impl.LogOut;
import by.it.rent.command.impl.RegistrationCommand;
import by.it.rent.command.impl.Search;
import by.it.rent.command.impl.ShowCar;
import by.it.rent.command.impl.ShowOrder;
import by.it.rent.command.impl.ShowUser;
import by.it.rent.command.impl.UpdateOrder;
import by.it.rent.command.impl.UpdateUser;
import by.it.rent.command.impl.UserData;

public class CommandHelper {

	private static final CommandHelper instance = new CommandHelper();

	private Map<CommandName, Command> commands = new HashMap<>();

	public CommandHelper() {
		commands.put(CommandName.AUTHORIZATION, new AuthorizationCommand());
		commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
		commands.put(CommandName.REGISTRATION, new RegistrationCommand());
		commands.put(CommandName.SHOWCAR, new ShowCar());
		commands.put(CommandName.CHOOSECAR, new ChooseCar());
		commands.put (CommandName.ORDERCAR, new OrderCar ());
		commands.put (CommandName.SHOWORDER, new ShowOrder ());
		commands.put (CommandName.SHOWUSER, new ShowUser ());
		commands.put(CommandName.LOGOUT, new LogOut ());
		commands.put(CommandName.USERDATA, new UserData ());
		commands.put(CommandName.ORDERDATA, new OrderData ());
		commands.put(CommandName.UPDATEUSER, new UpdateUser ());
		commands.put(CommandName.EDITUSER, new EditUser ());
		commands.put(CommandName.BLOCKUSER, new BlockUser ());
		commands.put(CommandName.SEARCH, new Search ());
		commands.put(CommandName.FILTER, new FilterSearch ());
		commands.put(CommandName.COMPLETEORDER, new CompleteOrder ());
		commands.put(CommandName.ADDCAR, new AddCar ());
		commands.put(CommandName.ADDORDER, new AddOrder ());
		commands.put(CommandName.EDITORDER, new EditOrder ());
		commands.put(CommandName.DAMAGE, new Damage ());
		commands.put(CommandName.UPDATEORDER, new UpdateOrder ());
	}

	public static CommandHelper getInstance() {
		return instance;
	}
	
	public Command getCommand(String commandName){
		CommandName name = CommandName.valueOf(commandName.toUpperCase());
		Command command;
		if (null != name){
			command = commands.get(name);
		} else{
			command = commands.get(CommandName.NO_SUCH_COMMAND);
		}
		return command;		
	} 
}
