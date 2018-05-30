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
package com.nortal.banklink.payment;

import com.nortal.banklink.link.Bank;
import com.nortal.banklink.link.BankLinkInfo;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Value;
import lombok.experimental.Builder;

/**
 * Payment information extracted from banklink response message.
 *
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
@Value
@Builder
public class PaymentLinkInfo implements BankLinkInfo {
    private static final long serialVersionUID = 1L;

    /** The bank. */
    private Bank bank;

    /** payment result (depending on response VK_SERVICEID, 1111=ok) */
    private boolean success;
    /**
     * Marker showing whether this message was sent in background (ibank -> system) or via browser (after clicking return to shop)
     */
    private boolean auto;

    /** Amount payed with the transaction */
    private BigDecimal amount;
    /** Currency of the transaction */
    private String currency;
    /** Timestamp of the transaction */
    private Date timestamp;
    /** Identifier for the transaction in the information system */
    private String stamp;
    /** Document number assigned to the payment order in bank */
    private String documentNumber;
    /** Payment receiver bank account */
    private String payeeAccount;
    /** Payment receiver name */
    private String payeeName;
    /** Payer name */
    private String payerName;
    /** Payer account */
    private String payerAccount;
    /** Reference number */
    private String referenceNumber;

    /** Optional message from bank */
    private String message;
}