package command;

import tool.ConfigSystem;
import tool.Response;
import util.GroupMessage;

import java.util.regex.Pattern;

/**
 * Created by Eldath on 2017/3/11 0011.
 *
 * @author Eldath Ray
 */
public class TestGroup extends GroupMessageCommand {
    private static TestGroup instance = null;

    public static TestGroup getInstance() {
        if (instance == null) instance = new TestGroup();
        return instance;
    }

    private void doTest(GroupMessage message) {
        Response.responseGroup(message.getGroupUid(), "@\u2005测试？");
    }

    @Override
    public void doPost(GroupMessage message) {
        for (long thisAdminUid : (long[]) ConfigSystem.getInstance().getConfig("Admin_Uid")) {
            if (message.getSenderUid() == thisAdminUid)
                doTest(message);
        }
    }

    @Override
    public String getHelpMessage() {
        return "";
    }

    @Override
    public Pattern getKeyWordRegex() {
        return Pattern.compile("avalon test group");
    }
}