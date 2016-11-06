#!/bin/bash
while true; do
    gradle run
    if [ $? -ne 0 ]
    then
        print "An error occur!"
        exit 1
    fi

    echo "Rebuilding project.."
    sleep 1
done