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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Banks that can be used for banklink authentication.
 * 
 * @author <a href="mailto:siim.sundla@nortal.com">Siim Sundla</a>
 */
@EqualsAndHashCode
@ToString(of = { "name", "protocol" })
@RequiredArgsConstructor(staticName = "of")
public class Bank {
    // Estonian banks constants
    public static final Bank SWEDBANK = Bank.of("SWEDBANK", "swedbank");
    public static final Bank SEB = Bank.of("SEB", "seb");
    public static final Bank DANSKE = Bank.of("DANSKE", "danske");
    public static final Bank NORDEA = Bank.of("NORDEA", "nordea");
    public static final Bank KRED = Bank.of("KRED", "krediidipank");
    public static final Bank LHV = Bank.of("LHV", "lhv");

    @Getter
    private final String name;
    @Getter
    private final String protocol;
    @Getter
    private final String spec;

    public static Bank of(String name, String spec) {
        return of(name, "IPIZZA", spec);
    }

    public String name() {
        return getName();
    }

    public static Bank[] banks() {
        return new Bank[] { SWEDBANK, SEB, DANSKE, NORDEA, KRED, LHV };
    }
}
