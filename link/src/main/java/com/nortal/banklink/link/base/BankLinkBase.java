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
package com.nortal.banklink.link.base;

import com.nortal.banklink.core.packet.Packet;
import com.nortal.banklink.link.BankLink;
import com.nortal.banklink.link.BankLinkConfig;
import com.nortal.banklink.link.BankLinkInfo;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 *
 * @param <CFG>
 */
public abstract class BankLinkBase<CFG extends BankLinkConfig, INFO extends BankLinkInfo, LINK extends BankLink<INFO>> implements BankLink<INFO> {
    /** Configuration used by this banklink */
    @Getter
    @Setter
    private CFG config;

    /**
     * Use byte length in mac.
     * 
     * @return true, if value length means value.getBytes(getEncoding()).length instead of value.length();
     */
    @Setter
    @Getter(AccessLevel.PROTECTED)
    private boolean useByteLengthInMac = false;

    @SuppressWarnings("unchecked")
    public LINK config(CFG cfg) {
        setConfig(cfg);
        return (LINK) this;
    }

    /**
     * Gets the return url.
     * 
     * @return the return url
     */
    protected String getReturnUrl() {
        return config.getReturnUrl();
    }

    /*
     * @see com.nortal.banklink.authentication.AuthLink#getUrl()
     */
    @Override
    public String getUrl() {
        return config.getUrl();
    }

    /*
     * @see com.nortal.banklink.authentication.AuthLink#getPacketInfo(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public INFO getPacketInfo(HttpServletRequest req) {
        return getParser().parse(getBank(), getPacket(req));
    }

    @Override
    public Packet createOutgoingPacket() {
        return createOutgoingPacket(new HashMap<String, String>());
    }

    /**
     * Gets the parser.
     * 
     * @return the parser
     */
    protected abstract BankLinkInfoParser<INFO> getParser();
}