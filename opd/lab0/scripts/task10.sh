#!/usr/bin/bash
echo "TASK 10:"
wc -m \
  ./accelgor3/roserade/snubbull \
./accelgor3/lampent/sudowoodo/accelgor3/roserade/snubbull \
./accelgor3/lampent/sudowoodo/murkrow/accelgor3/roserade/snubbull \
./accelgor3/roserade/paras \
./accelgor3/lampent/sudowoodo/accelgor3/roserade/paras \
./accelgor3/lampent/sudowoodo/murkrow/accelgor3/roserade/paras \
./accelgor3/lampent/sudowoodo/accelgor3/gible \
./accelgor3/lampent/sudowoodo/murkrow/accelgor3/gible \
./accelgor3/gible \
./golem7/gallade \
./golem7/nidorino/numel \
./golem7/nidorino/grimer \
./golem7/nidorino/scyther/alakazam \
./golem7/nidorino/scyther/kricketot \
./golem7/nidorino/chingling/sudowoodo \
./golem7/nidorino/chingling/musharna 2>/tmp/errors \
| grep -v "total" | sort -k1
