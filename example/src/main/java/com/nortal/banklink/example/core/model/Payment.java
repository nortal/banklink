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
package com.nortal.banklink.example.core.model;

import com.nortal.banklink.link.Bank;

import com.nortal.banklink.example.core.constants.MessageResult;
import com.nortal.banklink.example.core.constants.PaymentState;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * Demo application payment implementation with predefined values
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
@Data
public class Payment implements GeneralPayment {
    private static final long serialVersionUID = 1L;

    private PaymentState state = PaymentState.CREATE;
    private Bank bank;
    private String receiverName = "Õige Mäger";
    private String receiverAccount = "EE871600161234567892";
    private BigDecimal amount = BigDecimal.valueOf(10.55d);
    private String currency = "EUR";
    private String refNo = "1234561";
    private String message = "Mingisugune seletus maksele";
    private Map<String, String> params = new HashMap<>();
    private MessageResult result;
}
