package cl.duoc.newrentacar.api.endpoint;

import cl.duoc.newrentacar.api.endpoint.model.Subsidiary;
import cl.duoc.newrentacar.api.service.SubsidiaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class SubsidiaryControllerTest {
  @Mock
  private SubsidiaryService subsidiaryService;
  @InjectMocks
  private SubsidiaryController subsidiaryController;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void getNames() {
    when(subsidiaryService.getAllSubsidiaries()).thenReturn(listOfSubsidiaries());
    var response = subsidiaryController.getNames();
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
  }

  private List<Subsidiary> listOfSubsidiaries() {
    Subsidiary e1 = new Subsidiary();
    e1.setName("name1");
    Subsidiary e2 = new Subsidiary();
    e2.setName("name2");
    Subsidiary e3 = new Subsidiary();
    e3.setName("name1");
    return List.of(
      e1,
      e2,
      e3
    );
  }

  @Test
  void get() {
    when(subsidiaryService.getAllSubsidiaries()).thenReturn(listOfSubsidiaries());
    var response = subsidiaryController.get();
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(3, response.getBody().size());
  }
}
