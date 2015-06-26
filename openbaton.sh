#!/bin/bash

version="0.3-SNAPSHOT"

function start {

    if [ ! -d build/  ]
        then
            compile_nfvo
    fi

    if [ 0 -eq $? ]
        then
            java -jar "build/libs/neutrino-$version.jar"
    fi
}

function compile_sdk {
    cd sdk
    ./gradlew build -x test install
    cd ..
}

function compile_nfvo {
    compile_sdk
    ./gradlew build -x test install
}

function tests {
    ./gradlew test
}

function clean {
    ./gradlew clean
}

function end {
    exit
}
function usage {
    echo -e "Open-Baton\n"
    echo -e "Usage:\n\t ./openbaton.sh <option>\n\t"
    echo -e "where option is"
    echo -e "\t\t * compile"
    echo -e "\t\t\t\t * sdk"
    echo -e "\t\t\t\t * nfvo"
    echo -e "\t\t * start"
    echo -e "\t\t * test"
    echo -e "\t\t * clean"
}

##
#   MAIN
#   TODO start activemq and/or define application.properties
##

if [ $# -eq 0 ]
   then
        usage
        exit 1
fi

for var in "$@"
do
    case $var in
        "clean" )
            clean ;;
        "sc" )
            clean
            compile_nfvo
            start ;;
        "start" )
            start ;;
        "compile nfvo" )
            compile_nfvo ;;
        "compile sdk" )
            compile_sdk ;;
        "test" )
            tests ;;
        * )
            usage
            end ;;
    esac
done
