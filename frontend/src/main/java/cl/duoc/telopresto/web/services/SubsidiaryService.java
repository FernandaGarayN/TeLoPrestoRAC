package cl.duoc.telopresto.web.services;

import cl.duoc.telopresto.web.apiclients.subsidiary.SubsidiaryClient;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SubsidiaryService {
    private final SubsidiaryClient subsidiaryClient;
    public List<Map<String, String>> getListOfSubsidiaries(){
        return subsidiaryClient.getSubsidiaries()
                .stream()
                .map(subsidiary -> Map.of("id", subsidiary.getId(), "name", subsidiary.getName() + " / " + subsidiary.getAddress()))
                .toList();
    }
}
