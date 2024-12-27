package com.dreamgames.backendengineeringcasestudy.service;
import com.dreamgames.backendengineeringcasestudy.dto.request.ClaimRewardRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.GetBalloonsInfoRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.UpdateBalloonProgressRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.ClaimRewardResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.GetBalloonsInfoResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.UpdateBalloonProgressResponse;
import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Partnership;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.PartnershipRepository;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PartnershipService {

    private final EventService eventService;

    private final UserRepository userRepository;

    private final PartnershipRepository partnershipRepository;

    @Autowired
    public PartnershipService(EventService eventService, PartnershipRepository partnershipRepository, UserRepository userRepository) {
        this.eventService = eventService;
        this.partnershipRepository = partnershipRepository;
        this.userRepository = userRepository;
    }


    public void createPartnership(User inviter, User invited, Event event) {

        boolean partnershipExists = partnershipRepository.existsByUser1AndUser2AndEvent(inviter, invited, event);
        if (partnershipExists) {
            throw new RuntimeException("Partnership already exists between these users for this event");
        }

        Partnership newPartnership = new Partnership();

        newPartnership.setUser1(inviter);
        newPartnership.setUser2(invited);
        newPartnership.setEvent(eventService.getActiveEvent().orElseThrow(() -> new RuntimeException("no active event")));
        newPartnership.setAbGroup(inviter.getAbGroup());

        partnershipRepository.save(newPartnership);
    }

    @Transactional
    public UpdateBalloonProgressResponse updateBalloonProgress(UpdateBalloonProgressRequest request) {

        Partnership partnership = partnershipRepository.findById(request.getPartnershipId())
            .orElseThrow(() -> new RuntimeException("Partnership not found"));


        if (!partnership.isActive()) {
            throw new RuntimeException("Partnership is not active.");
        }

        if (eventService.isEventActive(partnership.getEvent())) {
            throw new RuntimeException("The associated event is not active.");
        }

        int progressThreshold = partnership.getAbGroup() == 'A' ? 1000 : 1500;

        int newProgress = partnership.getBalloonProgress() + partnership.getHeliumCount();

        if (newProgress > progressThreshold) {newProgress = progressThreshold;}

        partnership.setBalloonProgress(newProgress);
        partnership.setHeliumCount(0);

        partnershipRepository.save(partnership);

        UpdateBalloonProgressResponse response = new UpdateBalloonProgressResponse();
        response.setNewBalloonProgress(partnership.getBalloonProgress());
        response.setRemainingHelium(partnership.getHeliumCount());

        return response;
    }

    public GetBalloonsInfoResponse getBalloonsInfo(GetBalloonsInfoRequest request) {
        Partnership partnership = partnershipRepository.findById(request.getPartnershipId())
                .orElseThrow(() -> new RuntimeException("Partnership not found"));


        if (!partnership.isActive()) {
            throw new RuntimeException("Partnership is not active.");
        }

        if (eventService.isEventActive(partnership.getEvent())) {
            throw new RuntimeException("The associated event is not active.");
        }

        return new GetBalloonsInfoResponse(
                partnership.getUser1().getId(),
                partnership.getUser2().getId(),
                partnership.getEvent().getId(),
                partnership.getBalloonProgress(),
                partnership.isRewardClaimed(),
                partnership.getHeliumCount(),
                partnership.isActive(),
                partnership.getAbGroup()
        );

    }

    @Transactional
    public ClaimRewardResponse claimReward(ClaimRewardRequest request) {

        Optional<Partnership> partnershipOpt = partnershipRepository.findById(request.getPartnershipId());

        Partnership partnership;

        if (partnershipOpt.isPresent()) {
            partnership = partnershipOpt.get();
        }else {
            throw new RuntimeException("no such partnership");
        }

        if (!partnership.isActive()) {
            throw new RuntimeException("Partnership is not active.");
        }

        if (partnership.isRewardClaimed()) {
            throw new RuntimeException("Reward has already been claimed.");
        }

        int progressAndReward = partnership.getAbGroup() == 'A' ? 1000 : 1500;

        if (partnership.getBalloonProgress() < progressAndReward) {
            throw new RuntimeException("Balloon progress is insufficient to claim the reward.");
        }

        User user1 = partnership.getUser1();
        User user2 = partnership.getUser2();
        user1.setCoins(user1.getCoins() + progressAndReward);
        user2.setCoins(user2.getCoins() + progressAndReward);

        partnership.setRewardClaimed(true);
        partnership.setActive(false);

        userRepository.save(user1);
        userRepository.save(user2);
        partnershipRepository.save(partnership);

        return new ClaimRewardResponse("Reward claimed successfully");

    }

}
