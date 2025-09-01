package tttc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tttc.resource.APIWrapperResponse;
import tttc.resource.LeaderBoardPlayerResource;
import tttc.service.LeaderBoardService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {
    private final LeaderBoardService leaderBoardService;

    @GetMapping("/profiles")
    public ResponseEntity<APIWrapperResponse<List<LeaderBoardPlayerResource>>> getAllProfiles(
            @RequestParam(name = "limit", required = false, defaultValue = "20") Integer limit
    ) {
        return ResponseEntity.ok(APIWrapperResponse.of(leaderBoardService.getAllProfiles(limit)));
    }

    // --------------- Get Profile Info ----------------------
    @GetMapping("/profile/{id}")
    public ResponseEntity<APIWrapperResponse<LeaderBoardPlayerResource>> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(APIWrapperResponse.of(leaderBoardService.getProfileById(id)));
    }
}
