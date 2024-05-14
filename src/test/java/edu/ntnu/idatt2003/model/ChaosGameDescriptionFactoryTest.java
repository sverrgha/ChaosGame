package edu.ntnu.idatt2003.model;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ChaosGameDescriptionFactoryTest {

  private ChaosGameDescription sierpinskiDescription;
  private ChaosGameDescription barnsleyFernDescription;
  private ChaosGameDescription juliaDescription;

  @BeforeEach
  public void setup() {
    sierpinskiDescription = ChaosGameDescriptionFactory.get(ChaosGameDescriptionFactory.descriptionTypeEnum.SIERPINSKI_TRIANGLE);
    barnsleyFernDescription = ChaosGameDescriptionFactory.get(ChaosGameDescriptionFactory.descriptionTypeEnum.BARNSLEY_FERN);
    juliaDescription = ChaosGameDescriptionFactory.get(ChaosGameDescriptionFactory.descriptionTypeEnum.JULIA);
  }

  @Test
  public void testSierpinskiTriangleDescription() {
    assertNotNull(sierpinskiDescription.toString());
  }

  @Test
  public void testBarnsleyFernDescription() {
    assertNotNull(barnsleyFernDescription);
  }

  @Test
  public void testJuliaDescription() {
    assertNotNull(juliaDescription);
  }
}






