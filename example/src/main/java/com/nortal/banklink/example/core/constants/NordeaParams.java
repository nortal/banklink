/**
 *   Copyright 2014 Nortal AS
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.nortal.banklink.example.core.constants;

/**
 * Solo protocol parameter constants
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 05.03.2014
 */
public class NordeaParams {
  // Bank specific configuration keys
  public static final String SOLOPMT_VERSION = "SOLOPMT_VERSION";
  public static final String SOLOPMT_RETURN_VERSION = "SOLOPMT_RETURN_VERSION";
  public static final String SOLOPMT_RETURN = "SOLOPMT_RETURN";
  public static final String SOLOPMT_CANCEL = "SOLOPMT_CANCEL";
  public static final String SOLOPMT_REJECT = "SOLOPMT_REJECT";

  // Payment specific configuration keys
  public static final String SOLOPMT_STAMP = "SOLOPMT_STAMP";
  public static final String SOLOPMT_RCV_NAME = "SOLOPMT_RCV_NAME";
  public static final String SOLOPMT_RCV_ACCOUNT = "SOLOPMT_RCV_ACCOUNT";
  public static final String SOLOPMT_AMOUNT = "SOLOPMT_AMOUNT";
  public static final String SOLOPMT_CUR = "SOLOPMT_CUR";
  public static final String SOLOPMT_REF = "SOLOPMT_REF";
  public static final String SOLOPMT_MSG = "SOLOPMT_MSG";
}
