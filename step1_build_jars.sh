#!/usr/bin/env bash

echo "\033[0;32m STARTING TO BUILD JARs... \033[0m"
./mvnw clean install
if [ $? -ne 0 ]; then
   echo -e "\033[0;31m FAIL! \033[0m" >&2
   set -e
   exit 1
fi
echo "\033[0;32m APPS ARE BUILT SUCCESSFULLY. \033[0m"
