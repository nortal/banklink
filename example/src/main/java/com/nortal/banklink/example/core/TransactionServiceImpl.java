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

import com.nortal.banklink.authentication.link.AuthLinkManagerImpl;
import com.nortal.banklink.example.core.config.ExtIPizzaConfig;
import com.nortal.banklink.example.core.config.ExtNordeaConfig;
import com.nortal.banklink.example.core.message.GeneralPaymentMessage;
import com.nortal.banklink.example.core.message.IPizzaPaymentMessage;
import com.nortal.banklink.example.core.message.NordeaPaymentMessage;
import com.nortal.banklink.example.core.model.GeneralPayment;
import com.nortal.banklink.example.core.model.MessageHolder;
import com.nortal.banklink.example.core.model.PacketConfig;
import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

/**
 * Payment messages services implementation. Since this service also extends {@link AuthLinkManagerImpl} we could initialize
 * authentication messages here
 * as well while configuring payment messages.
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 03.03.2014
 */
@Component
public class TransactionServiceImpl extends AuthLinkManagerImpl implements TransactionService {
    private static final Logger LOG = Logger.getLogger(TransactionServiceImpl.class);

    private static final String BANK_SPEC = "BANK_SPEC";
    private static final String BANK_URL = "BANK_URL";
    private static final String RETURN_URL = "RETURN_URL";
    private static final String PAYMENT_MSG_ID = "PAYMENT_MSG_ID";
    private static final String ENCODING = "ENCODING";
    private static final String MAC = "MAC";

    private Map<Bank, GeneralPaymentMessage> messages = new HashMap<>();

    {
        for (Bank bank : Bank.banks()) {
            try {
                Properties props = PropertiesLoaderUtils.loadProperties(new ClassPathResource(MessageFormat.format(
                        "{0}/config.properties", bank.name())));
                PacketConfig packetsConfig = TransactionUtil.readPacketConfig(MessageFormat.format("/{0}/request.spec",
                        bank.name()));

                String packetId = props.getProperty(PAYMENT_MSG_ID);
                String url = props.getProperty(BANK_URL);
                String returnUrl = props.getProperty(RETURN_URL);
                String senderId = null;
                String receiverId = null;
                String bankSpec = props.getProperty(BANK_SPEC);
                String encoding = props.getProperty(ENCODING);

                GeneralPaymentMessage message = null;

                if (bank.equals(Bank.NORDEA)) {
                    message = new NordeaPaymentMessage(packetId,
                            new ExtNordeaConfig(url, returnUrl, senderId, props.getProperty(MAC), bankSpec, encoding, packetsConfig));
                } else {
                    String certKeyPath = MessageFormat.format("{0}/{1}", bank.name(), "bank_cert.pem");
                    String privKeyPath = MessageFormat.format("{0}/{1}", bank.name(), "user_key.pem");

                    // Load bank keys
                    PublicKey pub = ((X509Certificate) TransactionUtil.getPemObject(certKeyPath)).getPublicKey();
                    PrivateKey priv = ((KeyPair) TransactionUtil.getPemObject(privKeyPath)).getPrivate();

                    message = new IPizzaPaymentMessage(packetId, new ExtIPizzaConfig(url, returnUrl, senderId, receiverId, pub,
                            priv, bankSpec, encoding, packetsConfig));
                }
                messages.put(bank, message);
            } catch (IOException e) {
                LOG.error(MessageFormat.format("TransactionService.init: failed to init bank={0}", bank.name()), e);
            }
        }
    }

    @Override
    public String getBankUrl(Bank bank) {
        LOG.debug(MessageFormat.format("TransactionServiceImpl.getBankUrl: bank={0}", bank));

        return messages.get(bank).getConfig().getUrl();
    }

    @Override
    public MessageHolder startTransaction(Bank bank, GeneralPayment payment) {
        LOG.debug(MessageFormat.format("TransactionServiceImpl.verifyTransaction: bank={0}, payment={1}", bank, payment));

        return messages.get(bank).createRequest(payment);
    }

    @Override
    public MessageHolder verifyTransaction(Bank bank, GeneralPayment payment, HttpServletRequest req) {
        LOG.debug(MessageFormat.format("TransactionServiceImpl.verifyTransaction: bank={0}, payment={1}, params={2}", bank,
                payment, req.getParameterMap()));

        return messages.get(bank).readResponse(req);
    }
}
