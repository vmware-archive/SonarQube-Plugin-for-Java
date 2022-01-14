/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package org.sonar.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.*;

@Rule(key = "V1002")
public class StringGetBytesCheck extends AbstractMethodCharsetDetection {

  @Override
  protected MethodMatchers getMethodInvocationMatchers() {
    return MethodMatchers.or(
        MethodMatchers.create()
            .ofTypes("java.lang.String").names("getBytes").addWithoutParametersMatcher().build(),
        MethodMatchers.create()
            .ofTypes("java.lang.String").names("getBytes").addParametersMatcher("java.nio.charset.Charset").build(),
        MethodMatchers.create()
            .ofTypes("java.lang.String").names("getBytes").addParametersMatcher("int", "int", "byte[]", "int").build(),
        MethodMatchers.create()
            .ofTypes("java.lang.String").names("getBytes").addParametersMatcher("java.lang.String").build()
    );
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
    Arguments args = mit.arguments();
    if (args.isEmpty()) {
      reportIssue(mit, "Use an explicit, valid encoding/charset argument with getBytes.");
      return;
    }

    if (args.size() == 4) {
      reportIssue(mit, "This String.getBytes() is a deprecated method and it also can not accept an encoding argument.");
      return;
    }

    if (isInvalidCharset(args.get(0))) {
      reportIssue(args.get(0), "Encoding argument passed to String.getBytes() is not valid.");
      return;
    }
  }
}
