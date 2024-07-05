package com.second_team.apt_project.controllers;

import com.second_team.apt_project.Exception.DataNotFoundException;
import com.second_team.apt_project.dtos.AptRequestDto;
import com.second_team.apt_project.records.TokenRecord;
import com.second_team.apt_project.services.MultiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/apt")
public class AptController {
    private final MultiService multiService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AptRequestDto aptRequestDto, @RequestHeader("Authorization") String accessToken) {
        try {
            TokenRecord tokenRecord = this.multiService.checkToken(accessToken);
            if (tokenRecord.isOK()) {
                String username = tokenRecord.username();
                this.multiService.saveApt(aptRequestDto.getRoadAddress(), aptRequestDto.getAptName(), aptRequestDto.getX(), aptRequestDto.getY(), username);
            }
            return tokenRecord.getResponseEntity("문제 없음");
        } catch (IllegalArgumentException | DataNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody AptRequestDto aptRequestDto, @RequestHeader("Authorization") String accessToken) {
        try {
            TokenRecord tokenRecord = this.multiService.checkToken(accessToken);
            if (tokenRecord.isOK()) {
                String username = tokenRecord.username();
                this.multiService.updateApt(aptRequestDto.getAptId(), aptRequestDto.getAptName(), username);

            }
            return tokenRecord.getResponseEntity("문제 없음");
        } catch (IllegalArgumentException | DataNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }
}
