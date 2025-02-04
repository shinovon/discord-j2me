package com.gtrxac.discord;

import javax.microedition.lcdui.*;

public class NotificationDialog extends Dialog implements CommandListener, Strings {
    private State s;
    private Command viewCommand;
    private Command closeCommand;
    private Displayable lastScreen;

    private Notification notif;

    public static String createString(Notification notif, String location, Message msg) {
        StringBuffer sb = new StringBuffer();
        sb.append(msg.author.name);
        if (location == null) {
            sb.append(Locale.get(NOTIFICATION_DM));
        } else {
            sb.append(Locale.get(NOTIFICATION_SERVER)).append(location).append(": \"");
        }
        sb.append(msg.content).append("\"");
        return sb.toString();
    }
    
    public NotificationDialog(State s, Notification notif, String location, Message msg) {
        super(s.disp, Locale.get(NOTIFICATION_TITLE), "");
        setCommandListener(this);
        this.s = s;
        this.notif = notif;
        lastScreen = s.disp.getCurrent();

        setString(createString(notif, location, msg));

        viewCommand = Locale.createCommand(VIEW, Command.OK, 1);
        closeCommand = Locale.createCommand(CLOSE, Command.BACK, 0);
        addCommand(viewCommand);
        addCommand(closeCommand);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == viewCommand) {
            notif.view(s);
        }
        else if (c == closeCommand) {
            s.disp.setCurrent(lastScreen);
        }
    }
}
