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

import com.nortal.banklink.link.base.BankLinkManagerImpl;
import com.nortal.banklink.payment.PaymentLink;
import com.nortal.banklink.payment.PaymentLinkInfo;
import com.nortal.banklink.payment.PaymentLinkManager;

/**
 * AuthLink manager. Holds all authentication links and decides wich to use.
 * 
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
public class PaymentLinkManagerImpl extends BankLinkManagerImpl<PaymentLinkInfo, PaymentLink> implements PaymentLinkManager {}
