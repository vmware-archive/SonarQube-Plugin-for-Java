/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package org.sonar.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.*;

@Rule(key = "V1007")
public class ServletResponseGetOutputStreamCheck extends AbstractMethodCharsetDetection {
  private static final String STRING = "java.lang.String";
  private static final String CHARSET = "java.nio.charset.Charset";
  private static final String GET_OUTPUT_STREAM = "getOutputStream";
  private static final String FOR_NAME = "forName";
  private static final String SERVLET_RESPONSE = "javax.servlet.ServletResponse";

  @Override
  protected MethodMatchers getMethodInvocationMatchers() {
    return MethodMatchers.create()
        .ofTypes(SERVLET_RESPONSE).names(GET_OUTPUT_STREAM).addWithoutParametersMatcher().build();
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
      reportIssue(mit, "Use the getWriter instead of getOutputStream to exchange text (not binary) data.");
  }
}
