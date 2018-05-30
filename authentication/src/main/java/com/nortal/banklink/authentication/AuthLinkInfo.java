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
package com.nortal.banklink.authentication;

import com.nortal.banklink.link.Bank;
import com.nortal.banklink.link.BankLinkInfo;
import lombok.Data;

/**
 * The Class AuthLinkInfo.
 *
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
@Data
public class AuthLinkInfo implements BankLinkInfo {
    private static final long serialVersionUID = 1L;

    /** Person Code (ssn). */
    private String code;
    /** Person country code, 2 character code EE/LV/LT etc */
    private String country;

    private String firstName;
    private String lastName;

    /** Person full name */
    private String name;

    // can be null
    private String companyCode;
    private String companyName;

    /** The account number. */
    private String accountNumber;

    /** The bank. */
    private Bank bank;
}