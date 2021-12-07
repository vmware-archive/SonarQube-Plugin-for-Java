/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
import java.io.*;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

class ServletResponseGetOutputStreamCheck {

  ServletOutputStream methodA1(ServletResponse servletResponse) {
    return servletResponse.getOutputStream();  // Noncompliant {{Use the getWriter instead of getOutputStream to exchange text (not binary) data.}}
  }
}
