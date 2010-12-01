/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2009 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.core.timemachine;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;
import org.sonar.api.CoreProperties;
import org.sonar.jpa.test.AbstractDbUnitTestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TimeMachineConfigurationTest extends AbstractDbUnitTestCase {

  @Test
  public void shouldSkipTendencies() {
    PropertiesConfiguration conf = new PropertiesConfiguration();
    conf.setProperty(CoreProperties.SKIP_TENDENCIES_PROPERTY, true);
    assertThat(new TimeMachineConfiguration(conf, null).skipTendencies(), is(true));
  }

  @Test
  public void shouldNotSkipTendenciesByDefault() {
    PropertiesConfiguration conf = new PropertiesConfiguration();
    assertThat(new TimeMachineConfiguration(conf, null).skipTendencies(), is(false));
  }

  @Test
  public void shouldGetPeriodVariationTargets() {
    PropertiesConfiguration conf = new PropertiesConfiguration();
    conf.setProperty("sonar.timemachine.variation1", "7");
    conf.setProperty("sonar.timemachine.variation2", "30");
    PeriodLocator periodLocator = mock(PeriodLocator.class);
    new TimeMachineConfiguration(conf, getSession(), periodLocator).getVariationTargets();
    verify(periodLocator).locate(7);
    verify(periodLocator).locate(30);
  }

}
