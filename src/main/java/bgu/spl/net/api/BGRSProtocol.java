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
//        if (msg instanceof LogOutCommand){ // dont think need that - all will happened in the react()
//            shouldTerminate = true;
//            curUserName = null;
//            curPassword = null;
//        }

        if (msg instanceof LogInCommand){
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

    public void setShouldTerminate(boolean shouldTerminate) {
        this.shouldTerminate = shouldTerminate;
    }

    public void setCurPassword(String curPassword) {
        this.curPassword = curPassword;
    }

    public void setCurUserName(String curUserName) {
        this.curUserName = curUserName;
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
