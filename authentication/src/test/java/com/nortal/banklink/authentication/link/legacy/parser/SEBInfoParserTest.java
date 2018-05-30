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
package com.nortal.banklink.authentication.link.legacy.parser;

import com.nortal.banklink.link.Bank;

import com.nortal.banklink.authentication.link.legacy.parser.SEBLegacyInfoParser;
import com.nortal.banklink.authentication.AuthLinkInfo;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
public class SEBInfoParserTest {
    private static final String VK_INFO_SPACE_SEPARATOR = "ISIK:12345678909;NIMI:LASTNAME,FIRSTNAME";
    private static final String VK_INFO_NO_SPACES = "ISIK:12345678909;NIMI:LASTNAME,FIRSTNAME";
    private static final String VK_INFO_EXTRA_SPACES = "ISIK:12345678909;NIMI:LASTNAME , FIRSTNAME";

    private SEBLegacyInfoParser parser = new SEBLegacyInfoParser();

    @Test
    public void testSebVkInfoParsing() {
        validateVkInfoParserResult(VK_INFO_SPACE_SEPARATOR);
        validateVkInfoParserResult(VK_INFO_NO_SPACES);
        validateVkInfoParserResult(VK_INFO_EXTRA_SPACES);
    }

    private void validateVkInfoParserResult(final String info) {
        AuthLinkInfo authInfo = parseVkInfo(info);
        Assert.assertEquals("12345678909", authInfo.getCode());
        Assert.assertEquals("Firstname", authInfo.getFirstName());
        Assert.assertEquals("Lastname", authInfo.getLastName());
        Assert.assertEquals("Firstname Lastname", authInfo.getName());
    }

    private AuthLinkInfo parseVkInfo(final String info) {
        return parser.vkInfoMapToBankLinkInfo(Bank.SEB, parser.vkInfoToMap(info));
    }
}
