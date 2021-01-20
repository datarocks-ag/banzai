package org.datarocks.banzai.configuration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import org.junit.jupiter.api.Test;

class HandlerConfigurationTest {

  @Test
  void testTypeCast() {
    final Date date = new Date();

    HandlerConfiguration handlerConfiguration =
        HandlerConfiguration.builder().handlerConfigurationItem("key1", date).build();

    assertEquals(date, handlerConfiguration.getConfigurationItem(Date.class, "key1").orElse(null));
    assertNull(handlerConfiguration.getConfigurationItem(String.class, "key1").orElse(null));
    assertNull(handlerConfiguration.getConfigurationItem(Date.class, "key2").orElse(null));
    assertNotNull(handlerConfiguration.getConfigurationItem(Object.class, "key1").orElse(null));
    assertEquals(
        date, handlerConfiguration.getConfigurationItem(Object.class, "key1").orElse(null));
  }
}
