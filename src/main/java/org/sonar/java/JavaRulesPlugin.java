/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package org.sonar.java;

import org.sonar.api.Plugin;

/**
 * Entry point of your plugin containing your custom rules
 */
public class JavaRulesPlugin implements Plugin {

  @Override
  public void define(Context context) {

    // server extensions -> objects are instantiated during server startup
    context.addExtension(JavaRulesDefinition.class);

    // batch extensions -> objects are instantiated during code analysis
    context.addExtension(JavaFileCheckRegistrar.class);

  }

}
