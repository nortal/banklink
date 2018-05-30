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

import com.nortal.banklink.core.algorithm.Algorithm;
import com.nortal.banklink.core.algorithm.AlgorithmNordea;
import com.nortal.banklink.core.packet.Packet;
import com.nortal.banklink.core.packet.nordea.NordeaPacketFactory;
import com.nortal.banklink.example.core.config.ExtNordeaConfig;
import com.nortal.banklink.example.core.constants.MessageResult;
import com.nortal.banklink.example.core.constants.NordeaParams;
import com.nortal.banklink.example.core.model.GeneralPayment;
import com.nortal.banklink.example.core.model.MessageHolder;
import com.nortal.banklink.example.core.model.PacketWrapper;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Solo protocol specific payment message packets processing implementation.
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 05.03.2014
 */
public class NordeaPaymentMessage extends AbstractPaymentMessage<ExtNordeaConfig> {

    private static final String SERVICE_PREFIX = "PmntResponse";

    public NordeaPaymentMessage(String packetId, ExtNordeaConfig config) {
        super(packetId, config);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Algorithm getAlgorithm() {
        Algorithm algorithm = new AlgorithmNordea();
        algorithm.setCharset(getConfig().getEncoding());
        algorithm.initSign(getConfig().getMac());
        return algorithm;
    }

    @Override
    protected Packet createRequestPacket() {
        try {
            return NordeaPacketFactory.getPacket(getPacketId(), getAlgorithm());
        } catch (Exception e) {
            throw new RuntimeException(MessageFormat.format(
                    "IPizzaPaymentMessage.createRequestPacket: packet creating failed for packetId={0}", getPacketId()), e);
        }
    }

    @Override
    protected Map<String, String> toRequestParams(GeneralPayment payment, String returnUrl) {
        Map<String, String> result = new HashMap<>();

        result.put(NordeaParams.SOLOPMT_STAMP, String.valueOf((int) (Math.random() * Integer.MAX_VALUE)));
        result.put(NordeaParams.SOLOPMT_RCV_NAME, payment.getReceiverName());
        result.put(NordeaParams.SOLOPMT_RCV_ACCOUNT, payment.getReceiverAccount());
        result.put(NordeaParams.SOLOPMT_AMOUNT, String.valueOf(payment.getAmount()));
        result.put(NordeaParams.SOLOPMT_CUR, payment.getCurrency());
        result.put(NordeaParams.SOLOPMT_REF, payment.getRefNo());
        result.put(NordeaParams.SOLOPMT_MSG, payment.getMessage());
        result.put(NordeaParams.SOLOPMT_RETURN, returnUrl);
        result.put(NordeaParams.SOLOPMT_CANCEL,
                MessageFormat.format("{0}?{1}={2}", returnUrl, RESULT_PARAM, MessageResult.CANCEL));
        result.put(NordeaParams.SOLOPMT_REJECT,
                MessageFormat.format("{0}?{1}={2}", returnUrl, RESULT_PARAM, MessageResult.ERROR));

        return result;
    }

    @Override
    public MessageHolder readResponse(HttpServletRequest req) {
        String packetId = req.getParameter(NordeaParams.SOLOPMT_VERSION);
        if (StringUtils.isBlank(packetId)) {
            packetId = req.getParameter(NordeaParams.SOLOPMT_RETURN_VERSION);
        }
        PacketWrapper wrapper = new PacketWrapper(NordeaPacketFactory.getPacket(SERVICE_PREFIX + packetId, getAlgorithm()));

        MessageHolder result = new MessageHolder(wrapper);
        if (!ArrayUtils.isEmpty(req.getParameterValues(RESULT_PARAM))) {
            result.setResult(MessageResult.valueOf(req.getParameter(RESULT_PARAM)));
        } else {
            result.setResult(MessageResult.OK);
        }

        if (!wrapper.getPacket().verify() && result.getResult() == null) {
            result.setResult(MessageResult.ERROR);
        }
        return result;
    }
}
