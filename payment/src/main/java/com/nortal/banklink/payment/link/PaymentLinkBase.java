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

import com.nortal.banklink.link.BankLinkConfig;
import com.nortal.banklink.link.base.BankLinkBase;
import com.nortal.banklink.payment.PaymentLink;
import com.nortal.banklink.payment.PaymentLinkInfo;

/**
 * Base class for authentication links.
 * 
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 * @param <CFG>
 *            configuration object type current authentication link implementation.
 */
public abstract class PaymentLinkBase<CFG extends BankLinkConfig> extends BankLinkBase<CFG, PaymentLinkInfo, PaymentLink> implements PaymentLink {}
