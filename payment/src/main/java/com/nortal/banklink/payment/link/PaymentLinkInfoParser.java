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
package com.nortal.banklink.payment.link;

import com.nortal.banklink.link.base.BankLinkInfoParser;
import com.nortal.banklink.payment.PaymentLinkInfo;

/**
 * Parser for banklink message user info (VK_INFO for old ipizza, B02K_CUSTID+B02K_CUSTNAME for old nordea, etc)
 * 
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
public interface PaymentLinkInfoParser extends BankLinkInfoParser<PaymentLinkInfo> {}
