/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
import java.io.*;
import java.nio.charset.StandardCharsets;

class OutputStreamWriterConstructorCheck {

  OutputStreamWriter methodA1(OutputStream is) {
    return new OutputStreamWriter(is);  // Noncompliant {{Use an explicit, valid encoding/charset argument with OutputStreamWriter.}}
  }

  OutputStreamWriter methodB1(OutputStream is) throws UnsupportedEncodingException {
    return new OutputStreamWriter(is, "UTF-8");
  }

  OutputStreamWriter methodB2(OutputStream is) throws UnsupportedEncodingException {
    return new OutputStreamWriter(is, "US-ASCII");  // Noncompliant {{Use an explicit, valid encoding/charset argument with OutputStreamWriter.}}
  }

  OutputStreamWriter methodC1(OutputStream is) throws UnsupportedEncodingException {
    return new OutputStreamWriter(is, StandardCharsets.UTF_8);
  }

  OutputStreamWriter methodC2(OutputStream is) throws UnsupportedEncodingException {
    return new OutputStreamWriter(is, StandardCharsets.ISO_8859_1);  // Noncompliant {{Use an explicit, valid encoding/charset argument with OutputStreamWriter.}}
  }
}
