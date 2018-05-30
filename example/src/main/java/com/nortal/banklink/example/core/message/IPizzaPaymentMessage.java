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
import com.nortal.banklink.core.algorithm.Algorithm008;
import com.nortal.banklink.core.packet.Packet;
import com.nortal.banklink.core.packet.PacketFactory;
import com.nortal.banklink.example.core.config.ExtIPizzaConfig;
import com.nortal.banklink.example.core.constants.IPizzaParams;
import com.nortal.banklink.example.core.constants.MessageResult;
import com.nortal.banklink.example.core.model.GeneralPayment;
import com.nortal.banklink.example.core.model.MessageHolder;
import com.nortal.banklink.example.core.model.PacketWrapper;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * IPizza protocol specific payment message packets processing implementation.
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 05.03.2014
 */
public class IPizzaPaymentMessage extends AbstractPaymentMessage<ExtIPizzaConfig> {

  /** Expected result for the payment processing from the bank **/
  private static final Map<String, MessageResult> PAYMENT_RESULTS = new HashMap<>();

  static {
    PAYMENT_RESULTS.put("1101", MessageResult.OK);
    PAYMENT_RESULTS.put("1901", MessageResult.CANCEL);
    PAYMENT_RESULTS.put("1902", MessageResult.ERROR);
  }

  public IPizzaPaymentMessage(String packetId, ExtIPizzaConfig config) {
    super(packetId, config);
  }

  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected Packet createRequestPacket() {
    try {
      Algorithm algorithm = new Algorithm008();
      algorithm.setCharset(getConfig().getEncoding());
      algorithm.initSign(getConfig().getClientPriv());
      return PacketFactory.getPacket(getConfig().getBankSpec(), getPacketId(), algorithm);
    } catch (Exception e) {
      throw new RuntimeException(MessageFormat.format(
          "IPizzaPaymentMessage.createRequestPacket: packet creating failed for packetId={0}", getPacketId()), e);
    }
  }

  @Override
  protected Map<String, String> toRequestParams(GeneralPayment payment, String returnUrl) {
        Map<String, String> result = new HashMap<>();

    result.put(IPizzaParams.VK_STAMP, String.valueOf((int) (Math.random() * Integer.MAX_VALUE)));
    result.put(IPizzaParams.VK_NAME, payment.getReceiverName());
    result.put(IPizzaParams.VK_ACC, payment.getReceiverAccount());
    result.put(IPizzaParams.VK_AMOUNT, String.valueOf(payment.getAmount()));
    result.put(IPizzaParams.VK_CURR, payment.getCurrency());
    result.put(IPizzaParams.VK_REF, payment.getRefNo());
    result.put(IPizzaParams.VK_MSG, payment.getMessage());
    result.put(IPizzaParams.VK_RETURN, returnUrl);
    result.put(IPizzaParams.VK_CANCEL,
        MessageFormat.format("{0}?{1}={2}", returnUrl, RESULT_PARAM, MessageResult.CANCEL));
    return result;
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public MessageHolder readResponse(HttpServletRequest req) {
    String packetId = req.getParameter(IPizzaParams.VK_SERVICE);

    PacketWrapper wrapper = null;
    try {
      Algorithm algorithm = new Algorithm008();
      algorithm.setCharset(getConfig().getEncoding());
      algorithm.initVerify(getConfig().getBankPub());
      wrapper = new PacketWrapper(PacketFactory.getPacket(getConfig().getBankSpec(), packetId, algorithm));
    } catch (Exception e) {
      throw new RuntimeException(MessageFormat.format(
          "IPizzaPaymentMessage.readResponse: packet creating failed for packetId={0}", packetId), e);
    }
    wrapper.getPacket().init(req);

    return new MessageHolder(wrapper, wrapper.getPacket().verify() ? PAYMENT_RESULTS.get(packetId)
        : MessageResult.ERROR);
  }
}
