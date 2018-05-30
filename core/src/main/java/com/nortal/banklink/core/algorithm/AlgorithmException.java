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
package com.nortal.banklink.core.algorithm;

import com.nortal.banklink.core.BanklinkException;

// TODO: Auto-generated Javadoc
/**
 * The Class AlgorithmException.
 */
public class AlgorithmException extends BanklinkException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new algorithm exception.
     */
    public AlgorithmException() {
        super();
    }

    /**
     * Instantiates a new algorithm exception.
     * 
     * @param error
     *            the error
     */
    public AlgorithmException(String error) {
        super(error);
    }

    /**
     * Instantiates a new algorithm exception.
     * 
     * @param error
     *            the error
     * @param e
     *            the e
     */
    public AlgorithmException(String error, Exception e) {
        super(error, e);
    }
}
