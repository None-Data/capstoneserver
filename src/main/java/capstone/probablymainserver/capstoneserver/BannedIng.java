package capstone.probablymainserver.capstoneserver;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class BannedIng {
    public static final Map<Long, String> NAME = new HashMap<>();
    public static final Map<String, Long> VALUE = new HashMap<>();

    static {
        NAME.put(1L, "돼지고기");
        NAME.put(2L, "쇠고기");
        NAME.put(4L, "닭고기");
        NAME.put(8L, "고등어");
        NAME.put(16L, "게");
        NAME.put(32L, "새우");
        NAME.put(64L, "오징어");
        NAME.put(128L, "조개류");
        NAME.put(256L, "대두");
        NAME.put(512L, "땅콩");
        NAME.put(1024L, "메밀");
        NAME.put(2048L, "밀");
        NAME.put(4096L, "잣");
        NAME.put(8192L, "호두");
        NAME.put(16384L, "복숭아");
        NAME.put(32768L, "토마토");
        NAME.put(65536L, "난류");
        NAME.put(131072L, "우유");
        NAME.put(262144L, "아황산");

        // 역방향 매핑 (문자열 -> 비트값)
        for (Map.Entry<Long, String> entry : NAME.entrySet()) {
            VALUE.put(entry.getValue(), entry.getKey());
        }
    }

    // 비트값을 받아 문자열 목록으로 반환
    public static ArrayList<String> getBannedList(long bannedBits) {
    	ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<Long, String> entry : NAME.entrySet()) {
            if ((bannedBits & entry.getKey()) != 0) {
                result.add(entry.getValue());
            }
        }
        return result;
    }

    // 문자열 목록을 받아 비트값으로 변환
    public static long getBitValue(ArrayList<String> names) {
    	long result = 0;
        for (String name : names) {
            Long value = VALUE.get(name);
            if (value != null) {
                result |= value;
            }
        }
        return result;
    }
}
