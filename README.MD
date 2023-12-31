# SPI in Android example

This repository contains example of the SPI (Service Provider Interface) usage in the Android
application. The mechanism is demonstrated based on the push services implementation.

More info on the SPI mechanism usage can be found in my article in [Medium](https://medium.com/@artsemkurantsou/9f712760df13).

## Repository structure

The project contains several modules:

- **app** - this module is a "dummy" application module that is used as an example of the push
  services integration.
- **push-service:core** - this module contains abstraction for the push services and initialization
  logic (based on the SPI mechanism).
- **push-service:firebase** - Firebase Cloud Messaging implementation of the push service
- **push-service:amazon** - Amazon Device Messaging implementation of the push service
- **push-service:huawei** - Huawei Messaging Service implementation of the push service
- **push-service:baidu** - Baidu Push SDK implementation of the push service
