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
package com.nortal.banklink.example.core.message;

import com.nortal.banklink.example.core.config.ExtIPizzaConfig;
import com.nortal.banklink.example.core.config.ExtNordeaConfig;
import com.nortal.banklink.example.core.config.GeneralBanklinkConfig;
import com.nortal.banklink.example.core.model.GeneralPayment;
import com.nortal.banklink.example.core.model.MessageHolder;
import javax.servlet.http.HttpServletRequest;

/**
 * General payment packet messages processing description
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 05.03.2014
 */
public interface GeneralPaymentMessage {
  /**
   * Returns current messages bank configuration (either {@link ExtNordeaConfig}
   * or {@link ExtIPizzaConfig})
   * 
   * @return {@link GeneralBanklinkConfig}
   */
  GeneralBanklinkConfig getConfig();

  /**
   * Creates, initializes, validates and signs payment request message packet as
   * described by the bank specification
   * 
   * @param payment
   *          {@link GeneralPayment}
   * @return {@link MessageHolder}
   */
  MessageHolder createRequest(GeneralPayment payment);

  /**
   * Reads, validates and verifies payment response message data from the bank
   * as described by the bank specification
   * 
   * @param reqPOST
   *          request with payment response data from the bank
   * @return {@link MessageHolder}
   */
  MessageHolder readResponse(HttpServletRequest req);
}
