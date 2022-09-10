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
./golem8/gallade \
./golem8/nidorino/numel \
./golem8/nidorino/grimer \
./golem8/nidorino/scyther/alakazam \
./golem8/nidorino/scyther/kricketot \
./golem8/nidorino/chingling/sudowoodo \
./golem8/nidorino/chingling/musharna 2>/tmp/errors \
| grep -v "total" | sort -k1
