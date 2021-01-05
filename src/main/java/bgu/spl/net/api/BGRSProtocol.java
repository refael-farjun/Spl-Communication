package bgu.spl.net.api;

import bgu.spl.net.api.commands.LogInCommand;
import bgu.spl.net.api.commands.LogOutCommand;

public class BGRSProtocol implements MessagingProtocol<Command> {
    private boolean shouldTerminate = false;
    private String curUserName = null;
    private String curPassword = null;
    Database database;

    public BGRSProtocol(Database database){
        this.database = database;
    }
    @Override
    public Command process(Command msg) {
        if (msg instanceof LogOutCommand){
            shouldTerminate = true;
            curUserName = null;
            curPassword = null;
        }

        else if (msg instanceof LogInCommand){
            curUserName = ((LogInCommand) msg).getUserName();
            curPassword = ((LogInCommand) msg).getPassword();
        }
        return msg.react(this);
    }

    public String getCurPassword() {
        return curPassword;
    }

    public String getCurUserName() {
        return curUserName;
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
