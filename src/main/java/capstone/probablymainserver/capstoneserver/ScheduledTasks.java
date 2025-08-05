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
    
    // ⏰ 매일 5시, 11시, 17시, 20시에 각각 식사 설정
    @Scheduled(cron = "0 0 5 * * *", zone = "Asia/Seoul")
    public void setMorningMeal() {
        System.out.println("[Log] 아침 식사 설정 " + System.currentTimeMillis());
        UseAi.setTodayMeal("아침");
        return;
    }

    @Scheduled(cron = "0 0 11 * * *", zone = "Asia/Seoul")
    public void setLunchMeal() {
        System.out.println("[Log] 점심 식사 설정 " + System.currentTimeMillis());
        UseAi.setTodayMeal("점심");
        return;
    }

    @Scheduled(cron = "0 0 17 * * *", zone = "Asia/Seoul")
    public void setDinnerMeal() {
        System.out.println("[Log] 저녁 식사 설정 " + System.currentTimeMillis());
        UseAi.setTodayMeal("저녁");
        return;
    }

    @Scheduled(cron = "0 0 20 * * *", zone = "Asia/Seoul")
    public void setLateNightMeal() {
        System.out.println("[Log] 새벽 식사 설정 " + System.currentTimeMillis());
        UseAi.setTodayMeal("새벽");
        return;
    }
}
