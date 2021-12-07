/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package org.sonar.java.checks.utils;

import org.sonar.java.matcher.MethodMatcher;

public class PositionMethodMatcher extends MethodMatcher {

  // -1 means no checked parameter
  private int position = -1;

  public static PositionMethodMatcher create() {
    return new PositionMethodMatcher();
  }

  public PositionMethodMatcher position(int position) {
    this.position = position;
    return this;
  }

  public int getPosition() {
    return position;
  }
}
