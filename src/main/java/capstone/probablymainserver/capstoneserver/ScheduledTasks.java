package capstone.probablymainserver.capstoneserver;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    // ⏰ 매일 0시와 12시에 실행 (크론 표현식: "초 분 시 일 월 요일")
    @Scheduled(cron = "0 0 0,12 * * *", zone = "Asia/Seoul")
    public void TodaysIngSet() {
        System.out.println("[Log] 오늘의 식재료 초기화 " + System.currentTimeMillis());
        
        UseAi.setTodayIngredient();
        return;
    }
}
