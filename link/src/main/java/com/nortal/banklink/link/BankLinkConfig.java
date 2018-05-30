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
package com.nortal.banklink.link;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.security.cert.X509Certificate;

/**
 * Instantiates a new bank link config.
 * 
 * @param url
 *            the url
 * @param returnUrl
 *            the return url
 * @param senderId
 *            the sender id
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
public abstract class BankLinkConfig {

    /** The url. */
    private final String url;

    /** The return url. */
    private final String returnUrl;

    /** The sender id. */
    private final String senderId;

    private BankLinkConfig(String url, String returnUrl, String senderId) {
        this.url = url;
        this.returnUrl = returnUrl;
        this.senderId = senderId;
    }

    /**
     * Nordea.
     * 
     * @param url
     *            the url
     * @param returnUrl
     *            the return url
     * @param senderId
     *            the sender id
     * @param mac
     *            the mac
     * @return the nordea config
     */
    @Deprecated
    public static NordeaConfig nordea(String url, String returnUrl, String senderId, String mac) {
        return new NordeaConfig(url, returnUrl, senderId, mac);
    }

    /**
     * Ipizza.
     * 
     * @param url
     *            banklink url on the bank side
     * @param returnUrl
     *            banklink return url on application side
     * @param senderId
     *            merchant id in banklink contract
     * @param receiverId
     *            bank identifier
     * @param cert
     *            bank certificate used to validate bank messages
     * @param priv
     *            private key we use to sign our messages to be sent to bank
     * @return the ipizza configuration object
     */
    public static IPizzaConfig ipizza(String url, String returnUrl, String senderId, String receiverId, X509Certificate cert, PrivateKey priv) {
        return ipizza(url, returnUrl, senderId, receiverId, cert.getPublicKey(), priv);
    }

    /**
     * Ipizza.
     * 
     * @param url
     *            the url
     * @param returnUrl
     *            the return url
     * @param senderId
     *            the sender id
     * @param receiverId
     *            the receiver id
     * @param pub
     *            the pub
     * @param priv
     *            the priv
     * @return the i pizza config
     */
    public static IPizzaConfig ipizza(String url, String returnUrl, String senderId, String receiverId, PublicKey pub, PrivateKey priv) {
        return new IPizzaConfig(url, returnUrl, senderId, receiverId, pub, priv);
    }

    public String getUrl() {
        return url;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getSenderId() {
        return senderId;
    }

    /**
     * Gets the mac.
     * 
     * @return the mac
     */
    @Deprecated
    public static class NordeaConfig extends BankLinkConfig {

        /** The mac. */
        private final String mac;

        public String getMac() {
            return mac;
        }

        /**
         * Instantiates a new nordea config.
         * 
         * @param url
         *            the url
         * @param returnUrl
         *            the return url
         * @param senderId
         *            the sender id
         * @param mac
         *            the mac
         */
        public NordeaConfig(String url, String returnUrl, String senderId, String mac) {
            super(url, returnUrl, senderId);
            this.mac = mac;
        }
    }

    /**
     * Gets the client priv.
     * 
     * @return the client priv
     */
    public static class IPizzaConfig extends BankLinkConfig {

        /** The receiver id. */
        private final String receiverId;

        /** The bank pub. */
        private final PublicKey bankPub;

        /** The client priv. */
        private final PrivateKey clientPriv;

        public String getReceiverId() {
            return receiverId;
        }

        public PublicKey getBankPub() {
            return bankPub;
        }

        public PrivateKey getClientPriv() {
            return clientPriv;
        }

        /**
         * Instantiates a new i pizza config.
         * 
         * @param url
         *            the url
         * @param returnUrl
         *            the return url
         * @param senderId
         *            the sender id
         * @param receiverId
         *            the receiver id
         * @param pub
         *            the pub
         * @param priv
         *            the priv
         */
        public IPizzaConfig(String url, String returnUrl, String senderId, String receiverId, PublicKey pub, PrivateKey priv) {
            super(url, returnUrl, senderId);
            this.receiverId = receiverId;
            this.bankPub = pub;
            this.clientPriv = priv;
        }
    }
}
