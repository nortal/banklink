/**
 *   Copyright 2015 Nortal AS
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
package com.nortal.banklink.payment.link.standard;

import com.nortal.banklink.core.BanklinkException;
import com.nortal.banklink.core.packet.Packet;
import com.nortal.banklink.core.packet.param.PacketDateTimeParameter;
import com.nortal.banklink.link.Bank;
import com.nortal.banklink.payment.PaymentLinkInfo;
import com.nortal.banklink.payment.PaymentLinkInfo.PaymentLinkInfoBuilder;
import com.nortal.banklink.payment.link.PaymentLinkInfoParser;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Standardized ipizza authentication info parser.
 * 
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
public class IPizzaStandardPaymentInfoParser implements PaymentLinkInfoParser {
    @Override
    public PaymentLinkInfo parse(Bank bank, Packet packet) {
        PaymentLinkInfoBuilder pli = PaymentLinkInfo.builder();
        pli.bank(bank);

        boolean success = "1111".equals(packet.getParameterValue("VK_SERVICE"));

        pli.auto("Y".equalsIgnoreCase(packet.getParameterValue("VK_AUTO")));
        pli.success(success);

        pli.stamp(packet.getParameterValue("VK_STAMP"));
        pli.referenceNumber(packet.getParameterValue("VK_REF"));
        pli.message(packet.getParameterValue("VK_MSG"));

        if (success) {
            pli.amount(new BigDecimal(packet.getParameterValue("VK_AMOUNT")));
            pli.currency(packet.getParameterValue("VK_CURR"));
            pli.timestamp(parseDateTime(packet.getParameterValue("VK_T_DATETIME")));
            pli.documentNumber(packet.getParameterValue("VK_T_NO"));

            pli.payeeAccount(packet.getParameterValue("VK_REC_ACC"));
            pli.payeeName(packet.getParameterValue("VK_REC_NAME"));
            pli.payerAccount(packet.getParameterValue("VK_SND_ACC"));
            pli.payerName(packet.getParameterValue("VK_SND_NAME"));
        }

        return pli.build();
    }

    private Date parseDateTime(String dateTimeStr) {
        try {
            return new SimpleDateFormat(PacketDateTimeParameter.DATETIME_FORMAT).parse(dateTimeStr);
        } catch (ParseException e) {
            throw new BanklinkException("Unparseable datetime in banklink message: " + dateTimeStr, e);
        }
    }
}
