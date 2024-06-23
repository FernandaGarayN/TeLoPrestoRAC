package cl.duoc.newrentacar.api.service;

import cl.duoc.newrentacar.api.endpoint.model.Subsidiary;
import cl.duoc.newrentacar.api.repository.SubsidiaryFirebaseRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class SubsidiaryService {
  private SubsidiaryFirebaseRepository subsidiaryFirebaseRepository;

  @PostConstruct
  public void init() {
    subsidiaryFirebaseRepository = new SubsidiaryFirebaseRepository();
  }

  public List<Subsidiary> getAllSubsidiaries() {
    return subsidiaryFirebaseRepository.getAllSubsidiaries();
  }
}
