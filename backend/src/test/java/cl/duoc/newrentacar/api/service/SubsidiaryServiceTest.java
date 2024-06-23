package cl.duoc.newrentacar.api.service;

import cl.duoc.newrentacar.api.repository.SubsidiaryFirebaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class SubsidiaryServiceTest {
  @Mock
  private SubsidiaryFirebaseRepository subsidiaryFirebaseRepository;
  @InjectMocks
  private SubsidiaryService subsidiaryService;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void init() {
    assertDoesNotThrow(() -> subsidiaryService.init());
  }

  @Test
  void getAllSubsidiaries() {
    assertDoesNotThrow(() -> subsidiaryService.getAllSubsidiaries());
  }
}
