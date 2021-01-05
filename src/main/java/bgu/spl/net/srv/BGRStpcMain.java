package bgu.spl.net.srv;

import bgu.spl.net.api.BGRSEncoderDecoder;
import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;
import bgu.spl.net.api.Database;
import bgu.spl.net.impl.newsfeed.NewsFeed;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;
import bgu.spl.net.impl.rci.RemoteCommandInvocationProtocol;

public class BGRStpcMain {

    public static void main(String[] args) {
        Database database = Database.getInstance(); //one shared object

        Server.threadPerClient(
                7777, //port
                () -> new BGRSProtocol(database), //protocol factory
                BGRSEncoderDecoder::new //message encoder decoder factory
        ).serve();


    }
}
