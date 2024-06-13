package cl.duoc.telopresto.web.apiclients.subsidiary;

import cl.duoc.telopresto.web.config.feign.FeignSubsidiaryConfig;
import cl.duoc.telopresto.web.services.Subsidiary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "subsidiary-client",
        url = "${spring.properties.feign.subsidiaries}",
        configuration = FeignSubsidiaryConfig.class)
public interface SubsidiaryClient {
    @GetMapping
    List<Subsidiary> getSubsidiaries();

    @GetMapping("/names")
    List<String> listOfSubsidiary();
}
