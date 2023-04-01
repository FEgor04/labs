#!/bin/bash
sftp helios:labs/programming/lab8 <<< $'put server/build/libs/server.jar'
sftp helios:labs/programming/lab8 <<< $'put client/build/libs/client.jar'
sftp helios:labs/programming/lab8 <<< $'put lb/build/libs/lb.jar'
sftp helios:labs/programming/lab8 <<< $'put -r migrations'


