/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package org.sonar.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ArraysSortCheckTest {
  @Test
  public void test() {
    JavaCheckVerifier.verify("src/test/files/ArraysSortCheck.java", new ArraysSortCheck());
  }
}
