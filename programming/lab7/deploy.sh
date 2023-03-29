#!/bin/bash
sftp helios:labs/programming/lab7 <<< $'put server/build/libs/server.jar'
sftp helios:labs/programming/lab7 <<< $'put client/build/libs/client.jar'
sftp helios:labs/programming/lab7 <<< $'put lb/build/libs/lb.jar'
sftp helios:labs/programming/lab7 <<< $'put -r migrations'


