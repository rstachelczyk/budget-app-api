#!/bin/bash

function main() {
  checkstyle
  pmd
  spotbugs
  unit-tests
  playwright
}

function checkstyle() {
  echo "++--------------------------------------------------++";
  echo "++--------------------------------------------------++";
  echo "||  ____ _               _        _         _       ||";
  echo "|| / ___| |__   ___  ___| | _____| |_ _   _| | ___  ||";
  echo "||| |   | '_ \ / _ \/ __| |/ / __| __| | | | |/ _ \ ||";
  echo "||| |___| | | |  __/ (__|   <\__ \ |_| |_| | |  __/ ||";
  echo "|| \____|_| |_|\___|\___|_|\_\___/\__|\__, |_|\___| ||";
  echo "||                                    |___/         ||";
  echo "++--------------------------------------------------++";
  echo "++--------------------------------------------------++";
  mvn -B -Dmaven.test.skip=true checkstyle:check
}

function pmd() {
  echo "++--------------------++";
  echo "++--------------------++";
  echo "|| ____  __  __ ____  ||";
  echo "|||  _ \|  \/  |  _ \ ||";
  echo "||| |_) | |\/| | | | |||";
  echo "|||  __/| |  | | |_| |||";
  echo "|||_|   |_|  |_|____/ ||";
  echo "||                    ||";
  echo "++--------------------++";
  echo "++--------------------++";
  mvn -B -Dmaven.test.skip=true pmd:check -Dpmd.verbose=true
}

function spotbugs() {
  echo "++----------------------------------------------++";
  echo "++----------------------------------------------++";
  echo "|| ____              _   _                      ||";
  echo "||/ ___| _ __   ___ | |_| |__  _   _  __ _ ___  ||";
  echo "||\___ \| '_ \ / _ \| __| '_ \| | | |/ _' / __| ||";
  echo "|| ___) | |_) | (_) | |_| |_) | |_| | (_| \__ \ ||";
  echo "|||____/| .__/ \___/ \__|_.__/ \__,_|\__, |___/ ||";
  echo "||      |_|                          |___/      ||";
  echo "++----------------------------------------------++";
  echo "++----------------------------------------------++";
  mvn -B -Dmaven.test.skip=true compile spotbugs:check
}

function unit-tests() {
  echo "++--------------------------------------------++";
  echo "++--------------------------------------------++";
  echo "|| _   _       _ _     _____         _        ||";
  echo "||| | | |_ __ (_) |_  |_   _|__  ___| |_ ___  ||";
  echo "||| | | | '_ \| | __|   | |/ _ \/ __| __/ __| ||";
  echo "||| |_| | | | | | |_    | |  __/\__ \ |_\__ \ ||";
  echo "|| \___/|_| |_|_|\__|   |_|\___||___/\__|___/ ||";
  echo "||                                            ||";
  echo "++--------------------------------------------++";
  echo "++--------------------------------------------++";
  mvn -B -U test -DexcludedGroups="integration"
}

function playwright() {
  echo "++------------------------------------------------------++";
  echo "++------------------------------------------------------++";
  echo "|| ____  _                           _       _     _    ||";
  echo "|||  _ \| | __ _ _   ___      ___ __(_) __ _| |__ | |_  ||";
  echo "||| |_) | |/ _' | | | \ \ /\ / / '__| |/ _' | '_ \| __| ||";
  echo "|||  __/| | (_| | |_| |\ V  V /| |  | | (_| | | | | |_  ||";
  echo "|||_|   |_|\__,_|\__, | \_/\_/ |_|  |_|\__, |_| |_|\__| ||";
  echo "||               |___/                 |___/            ||";
  echo "++------------------------------------------------------++";
  echo "++------------------------------------------------------++";
  npx playwright test;
}

main
