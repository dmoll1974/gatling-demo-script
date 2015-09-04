#!/bin/sh

if [ -z "$1" ];
then
  echo "Usage:"
  echo "  ./run-full-suite.sh <environment>"
  echo ""
  echo "Will run the load, failover, slowbackend, stress, endurance Gatling profiles one"
  echo "by one. In between servers will be restarted The environment parameter allows you"
  echo "to target a specific environment"
  echo "Parameters:"
  echo "  environment          One of these values:"
  echo "                         local"
  echo "                         ite1"
  echo "                         ite2"
  echo "                         ute1"
  echo "                         ute2"
  echo "                         ae1"
  echo "                         ae2"
  echo ""
  echo "Note: these are just example environments, if your project supports other"
  echo "environment profiles, feel free to specify those as an argument. There is no"
  echo "validation on this argument other than its presence."
  
  exit 1
fi

echo "==[ Running stress, load, failover and slowbackend profiles sequentially ]=="
echo " -- This might take a while ^_^"
echo ""
echo "==[ Selected environment: $1"
echo ""

mvn clean install gatling:execute -Prestart
mvn clean install gatling:execute -Pload -P$1
mvn clean install gatling:execute -Prestart
mvn clean install gatling:execute -Pfailover -P$1
mvn clean install gatling:execute -Prestart
mvn clean install gatling:execute -Pslowbackend -P$1
mvn clean install gatling:execute -Prestart
mvn clean install gatling:execute -Pstress -P$1
mvn clean install gatling:execute -Prestart
mvn clean install gatling:execute -Pendurance -P$1
