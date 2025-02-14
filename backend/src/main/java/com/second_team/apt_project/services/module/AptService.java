package com.second_team.apt_project.services.module;


import com.second_team.apt_project.exceptions.DataNotFoundException;
import com.second_team.apt_project.domains.Apt;
import com.second_team.apt_project.repositories.AptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AptService {
    private final AptRepository aptRepository;

    public Apt get(Long aptId) {
        return this.aptRepository.get(aptId).orElseThrow(() -> new DataNotFoundException("not found apt"));
    }
  
    public Apt save(String roadAddress, String aptName, Double x, Double y) {
    
        return aptRepository.save(Apt.builder()
                .roadAddress(roadAddress)
                .aptName(aptName)
                .x(x)
                .y(y)
                .build());
    }

    public void update(Apt apt, String url, String aptName) {
        apt.setAptName(aptName);
        aptRepository.save(apt);
    }

    public List<Apt> getAptList() {
        return this.aptRepository.findAll();
    }

}
