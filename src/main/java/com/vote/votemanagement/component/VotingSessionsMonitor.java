package com.vote.votemanagement.component;


import com.vote.votemanagement.service.AssemblyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class VotingSessionsMonitor {

    @Autowired
    AssemblyService assemblyService;

    @Async
    @Scheduled(cron = "0 * * * * *")
    public void openVotingSessionMonitor() throws InterruptedException {
        assemblyService.verifyOpenVotingSessions();
    }

}