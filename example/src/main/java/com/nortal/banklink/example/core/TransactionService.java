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
package com.nortal.banklink.example.core;

import com.nortal.banklink.link.Bank;

import com.nortal.banklink.example.core.model.GeneralPayment;
import com.nortal.banklink.example.core.model.MessageHolder;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * Payment message services description.
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 04.03.2014
 */
@Component
public interface TransactionService {

  /**
   * Returns bank service endpoint url.
   * 
   * @param bank
   *          {@link Bank}
   * @return the bank url
   */
  public String getBankUrl(Bank bank);

  /**
   * Creates payment request message according to input
   * 
   * @param bank
   *          {@link Bank}
   * @param payment
   *          {@link GeneralPayment}
   * @return {@link MessageHolder}
   */
  MessageHolder startTransaction(Bank bank, GeneralPayment payment);

  /**
   * Verifies bank response message according to input.
   * 
   * @param bank
   *          {@link Bank}
   * @param payment
   *          {@link GeneralPayment}
   * @param req
   *          the req
   * @return {@link MessageHolder}
   */
  MessageHolder verifyTransaction(Bank bank, GeneralPayment payment, HttpServletRequest req);
}
