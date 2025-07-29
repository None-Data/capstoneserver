package capstone.probablymainserver.capstoneserver;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class BannedIng {
    public static final Map<Integer, String> NAME = new HashMap<>();
    public static final Map<String, Integer> VALUE = new HashMap<>();

    static {
        NAME.put(1, "돼지고기");
        NAME.put(2, "쇠고기");
        NAME.put(4, "닭고기");
        NAME.put(8, "고등어");
        NAME.put(16, "게");
        NAME.put(32, "새우");
        NAME.put(64, "오징어");
        NAME.put(128, "조개류");
        NAME.put(256, "대두");
        NAME.put(512, "땅콩");
        NAME.put(1024, "메밀");
        NAME.put(2048, "밀");
        NAME.put(4096, "잣");
        NAME.put(8192, "호두");
        NAME.put(16384, "복숭아");
        NAME.put(32768, "토마토");
        NAME.put(65536, "난류");
        NAME.put(131072, "우유");
        NAME.put(262144, "아황산");

        // 역방향 매핑 (문자열 -> 비트값)
        for (Map.Entry<Integer, String> entry : NAME.entrySet()) {
            VALUE.put(entry.getValue(), entry.getKey());
        }
    }

    // 비트값을 받아 문자열 목록으로 반환
    public static ArrayList<String> getBannedList(int bannedBits) {
    	ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : NAME.entrySet()) {
            if ((bannedBits & entry.getKey()) != 0) {
                result.add(entry.getValue());
            }
        }
        return result;
    }

    // 문자열 목록을 받아 비트값으로 변환
    public static int getBitValue(ArrayList<String> names) {
        int result = 0;
        for (String name : names) {
            Integer value = VALUE.get(name);
            if (value != null) {
                result |= value;
            }
        }
        return result;
    }
}
