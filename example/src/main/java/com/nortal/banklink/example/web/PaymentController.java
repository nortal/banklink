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
package com.nortal.banklink.example.web;

import com.nortal.banklink.link.Bank;

import com.nortal.banklink.core.packet.InvalidParameterException;
import com.nortal.banklink.example.core.TransactionService;
import com.nortal.banklink.example.core.constants.PaymentState;
import com.nortal.banklink.example.core.model.Payment;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentController.
 */
@Controller
@SessionAttributes("payment")
@RequestMapping("/payment")
public class PaymentController {

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(PaymentController.class);

    /** The transaction service. */
    @Resource
    private TransactionService transactionService;

    /**
     * Do get.
     * 
     * @param model
     *            the model
     * @param payment
     *            the payment
     * @param req
     *            the req
     * @return the string
     */
    @RequestMapping(method = RequestMethod.GET)
    public String doGet(Model model, Payment payment, HttpServletRequest req) {
        LOG.info(MessageFormat.format("PaymentController.doGet: payment={0}", payment));
        try {
            if (payment.getState().equals(PaymentState.TRANSACTION) && !req.getParameterMap().isEmpty()) {
                verifyTransaction(payment, model, req);
            } else {
                model.addAttribute("payment", new Payment());
            }
        } catch (Exception e) {
            handleException(payment, model, e);
        }
        return "payment";
    }

    /**
     * Do post.
     * 
     * @param model
     *            the model
     * @param payment
     *            the payment
     * @param req
     *            the req
     * @return the string
     */
    @RequestMapping(method = RequestMethod.POST)
    public String doPost(Model model, Payment payment, HttpServletRequest req) {
        LOG.info(MessageFormat.format("PaymentController.doPost: payment={0}", payment));
        String view = "payment";

        try {
            switch (payment.getState()) {
            case CREATE:
                payment.setState(PaymentState.BANK_SELECT);
                List<String> supportedBanks = new ArrayList<>();
                for (Bank bank : Bank.banks()) {
                    supportedBanks.add(bank.name());
                }
                model.addAttribute("supportedBanks", supportedBanks);
                break;
            case BANK_SELECT:
                try {
                    payment.setParams(transactionService.startTransaction(payment.getBank(), payment).getPacket().getParams());
                    payment.setState(PaymentState.TRANSACTION);
                    model.addAttribute("bankUrl", transactionService.getBankUrl(payment.getBank()));
                } catch (InvalidParameterException e) {
                    LOG.error("PaymentController.doPost: transaction start failed", e);
                    payment.setState(PaymentState.CREATE);
                    model.addAttribute("error", e.getMessage());
                }
                break;
            case TRANSACTION:
                verifyTransaction(payment, model, req);
                break;
            case DONE:
                view = "redirect:" + view;
            }
        } catch (Exception e) {
            handleException(payment, model, e);
        }
        return view;
    }

    /**
     * Verify transaction.
     * 
     * @param payment
     *            the payment
     * @param model
     *            the model
     * @param req
     *            the req
     */
    private void verifyTransaction(Payment payment, Model model, HttpServletRequest req) {
        try {
            payment.setResult(transactionService.verifyTransaction(payment.getBank(), payment, req).getResult());
        } catch (Exception e) {
            LOG.error("PaymentController.verifyTransaction: payment verifying failed", e);
            model.addAttribute("error", e.getMessage());
        }
        payment.setState(PaymentState.DONE);
    }

    /**
     * Handle exception.
     * 
     * @param payment
     *            the payment
     * @param model
     *            the model
     * @param e
     *            the e
     */
    private void handleException(Payment payment, Model model, Exception e) {
        LOG.error(MessageFormat.format("PaymentController.handleException: exception occured for payment={0}", payment), e);
        model.addAttribute("error", e.getMessage());
        payment.setState(PaymentState.DONE);
    }
}
