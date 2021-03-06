package avalon.extend;

import avalon.tool.pool.Constants;
import avalon.tool.system.Config;
import avalon.tool.system.RunningData;
import avalon.util.FriendMessage;
import avalon.util.GroupMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eldath on 2017/2/3 0003.
 *
 * @author Eldath
 */
public class Recorder {
	private static final List<FriendMessage> friendMessageRecord = new ArrayList<>();
	private static final List<GroupMessage> groupMessageRecord = new ArrayList<>();
	private static Recorder instance = null;
	private static final int MAX_RECODE_LIST_SIZE = 10;
	private static final int MAX_RECORD_GROUP_MESSAGE_COUNT =
			(int) Config.INSTANCE.get("max_recorded_group_message_amount");
	private static final int MAX_RECORD_FRIEND_MESSAGE_COUNT =
			(int) Config.INSTANCE.get("max_recorded_friend_message_amount");
	private static int nowGroupCount = RunningData.getInstance().getInt("group_message_recorded_count");
	private static int nowFriendCount = RunningData.getInstance().getInt("friend_message_recorded_count");

	public static Recorder getInstance() {
		if (instance == null) instance = new Recorder();
		return instance;
	}

	public void recodeGroupMessage(GroupMessage message) {
		if (nowGroupCount > MAX_RECORD_GROUP_MESSAGE_COUNT) return;
		groupMessageRecord.add(message);
		nowGroupCount++;
		if (groupMessageRecord.size() > MAX_RECODE_LIST_SIZE)
			flushNow();
	}

	public void recodeFriendMessage(FriendMessage message) {
		if (nowFriendCount > MAX_RECORD_FRIEND_MESSAGE_COUNT) return;
		friendMessageRecord.add(message);
		nowFriendCount++;
		if (friendMessageRecord.size() > MAX_RECODE_LIST_SIZE)
			flushNow();
	}

	public void flushNow() {
		for (GroupMessage thisGroupMessage : groupMessageRecord)
			Constants.Database.currentDatabaseOperator.add(thisGroupMessage);
		groupMessageRecord.clear();
		for (FriendMessage thisFriendMessage : friendMessageRecord)
			Constants.Database.currentDatabaseOperator.add(thisFriendMessage);
		friendMessageRecord.clear();
	}

	public static int getNowGroupCount() {
		return nowGroupCount;
	}

	public static int getNowFriendCount() {
		return nowFriendCount;
	}

	public static int getMaxRecordGroupMessageCount() {
		return MAX_RECORD_GROUP_MESSAGE_COUNT;
	}

	public static int getMaxRecordFriendMessageCount() {
		return MAX_RECORD_FRIEND_MESSAGE_COUNT;
	}
}
