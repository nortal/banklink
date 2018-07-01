Banklink library
============================

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.nortal.banklink/banklink-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.nortal.banklink/banklink-core)
[![License](https://img.shields.io/hexpm/l/plug.svg)](https://github.com/nortal/banklink/blob/master/LICENSE)

The Nortal Banklink project is a set of Java libraries that assist developers when implementing the authentication and payment links to Estonian banks.


Project structure
------------

The Project contains a set of submodules:
* **banklink-core** - contains the bank-specific protocol descriptions (in the form of `.spec` files) and provides basic tools for request signing and response verification.
* **banklink-authentication** - classes to create banklink authentication messages and read/verify the bank's authentication response.
* **banklink-link** - convenience classes with a preconfigured list of banks to set up a specific bank link implementation.
* **banklink-payment** - classes to create banklink payment request messages and read/verify the response.

The iPizza protocol support
------------

The standard iPizza protocol specification v1.1 can be found [here](https://media.voog.com/0000/0042/1620/files/Pangalingi_tehniline_spetsifikatsioon_1.1.pdf). See Table 1 for detailed list of supported banks and their respective implementation details.

*Table 1 - Supported banks*

| Bank name | Link to specification |
| :------ | :----- |
| Coop | [Technical specification](https://www.cooppank.ee/s3fs-public/Pangalink/Pangalink%20tehniline%20spetsifikatsioon%2C%20Coop%20Pank.pdf) |
| Danske | [Technical specification](https://www.danskebank.ee/et/14732.html) |
| LHV | [Technical specification](https://partners.lhv.ee/et/banklink/) |
| Luminor | [Technical specification](https://www.luminor.ee/sites/default/files/documents/files/common/pangalingi-tehniline-spetsifikatsioon-ee.pdf) |
| SEB | [Technical specification](https://www.seb.ee/ariklient/igapaevapangandus/maksete-kogumine/maksete-kogumine-internetis/pangalingi-tehniline-spetsifikatsioon) |
| Swedbank | [Technical specification](https://www.swedbank.ee/static/pdf/business/d2d/paymentcollection/Pangalingi_paringute_tehniline_spetsifikatsioon_09_10_2014.pdf) |

Example implementation
------------

Please refer to the [**example project**](example/README) for reference implementation.
