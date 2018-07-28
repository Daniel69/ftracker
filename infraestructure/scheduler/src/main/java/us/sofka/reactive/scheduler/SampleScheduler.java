
package us.sofka.reactive.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.sofka.reactive.alerts.SamplePeriodicBusinessUseCase;

@Component
public class SampleScheduler {

    @Autowired
    SamplePeriodicBusinessUseCase useCase;

    @Scheduled(cron = "0 * * * * *")
    public void checkExpiredMicrochips(){
        useCase.businessProcess();
        System.out.println("Sample Scheduled Execution - " + Thread.currentThread().getName());
    }
}
